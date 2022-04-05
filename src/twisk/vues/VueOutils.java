package twisk.vues;

import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.MondeIG;

public class VueOutils extends TilePane implements Observateur {
    private final MondeIG monde;
    final Button boutonAjouter;
    final Button boutonRetour;
    final Button boutonEffacer;

    public VueOutils(MondeIG monde) {

        this.monde = monde;
        monde.ajouterObservateur(this);

        Image img = new Image("/images/add.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(50);
        view.setPreserveRatio(true);
        view.setSmooth(true);

        boutonAjouter = new Button();
        boutonAjouter.getStyleClass().add("bouton-outils");
        boutonAjouter.setGraphic(view);

        boutonAjouter.setOnAction(actionEvent -> monde.ajouter("Activité"));
        this.getChildren().add(boutonAjouter);

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
