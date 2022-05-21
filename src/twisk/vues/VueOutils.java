package twisk.vues;

import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import twisk.exceptions.MondeException;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.MondeIG;
import twisk.outils.ThreadsManager;

public class VueOutils extends TilePane implements Observateur {
    private final MondeIG monde;
    final Button boutonAjouter, boutonAjouterG;
    final Button boutonRetour;
    final Button boutonEffacer;
    final Button boutonLancement;

    public VueOutils(MondeIG monde) {

        this.monde = monde;
        monde.ajouterObservateur(this);

        Image img = new Image("/images/activite-add.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(50);
        view.setPreserveRatio(true);
        view.setSmooth(true);

        boutonAjouter = new Button();
        boutonAjouter.getStyleClass().add("bouton-outils");
        boutonAjouter.setGraphic(view);

        boutonAjouter.setOnAction(actionEvent -> monde.ajouter("Activité"));
        this.getChildren().add(boutonAjouter);

        /*-------------------------------------*/

        img = new Image("/images/guichet-add.png");
        view= new ImageView(img);
        view.setFitHeight(50);
        view.setPreserveRatio(true);
        view.setSmooth(true);

        boutonAjouterG = new Button();
        boutonAjouterG.getStyleClass().add("bouton-outils");
        boutonAjouterG.setGraphic(view);

        boutonAjouterG.setOnAction(actionEvent -> monde.ajouter("Guichet"));
        this.getChildren().add(boutonAjouterG);


        /* ------------------------------------ */
        img = new Image("/images/left.png");
        view = new ImageView(img);
        view.setFitHeight(50);
        view.setPreserveRatio(true);
        view.setSmooth(true);

        boutonRetour = new Button();
        boutonRetour.getStyleClass().add("bouton-outils");
        boutonRetour.setGraphic(view);

        boutonRetour.setOnAction(actionEvent -> {
            if (!monde.arcIsEmpty())
                monde.retirerDernierArc();
        });
        this.getChildren().add(boutonRetour);

        /* ------------------------------------ */
        img = new Image("/images/delete.png");
        view = new ImageView(img);
        view.setFitHeight(50);
        view.setPreserveRatio(true);
        view.setSmooth(true);

        boutonEffacer = new Button();
        boutonEffacer.getStyleClass().add("bouton-outils");
        boutonEffacer.setGraphic(view);

        boutonEffacer.setOnAction(actionEvent -> monde.reset());
        this.getChildren().add(boutonEffacer);
        this.reagir();
        /*--------------------------------------*/
        img = new Image("/images/lancement.png");
        view = new ImageView(img);
        view.setFitHeight(50);
        view.setPreserveRatio(true);
        view.setSmooth(true);

        boutonLancement = new Button();
        boutonLancement.getStyleClass().add("bouton-outils");
        boutonLancement.setGraphic(view);

        boutonLancement.setOnAction(actionEvent -> {
            try {
                monde.simuler();
            } catch (MondeException e) {
                throw new RuntimeException(e);
            }
        });
        this.getChildren().add(boutonLancement);
        this.reagir();
    }

    public void animer(){
        Task<Void> task = new Task<Void>(){
            @Override
            protected Void call() throws Exception{
                try{
                    monde.simuler();
                    Thread.sleep(10);
                    monde.notifierObservateurs();
                }catch (InterruptedException e){

                }
                return null;

            }
        };
        ThreadsManager.getInstance().lancerTask(task);
    }

    @Override
    public void reagir() {
        StringBuilder str = new StringBuilder();
        for (EtapeIG etape: monde)
            str.append(etape).append("\n");
        Tooltip tooltip = new Tooltip();
        tooltip.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        Label label = new Label(str.toString());
        tooltip.setGraphic(label);
        boutonAjouter.setTooltip(tooltip);
    }
}
