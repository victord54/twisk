package twisk.vues;

import javafx.concurrent.Task;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import twisk.exceptions.GuichetTwiskException;
import twisk.exceptions.MondeException;
import twisk.exceptions.TwiskException;
import twisk.mondeIG.MondeIG;
import twisk.outils.ThreadsManager;

public class VueOutils extends TilePane implements Observateur {
    private final MondeIG monde;
    private Button boutonAjouter;
    private Button boutonAjouterG;
    private Button boutonRetour;
    private Button boutonEffacer;
    private Button boutonLancement;

    public VueOutils(MondeIG monde) {
        boutonLancement = null;
        boutonAjouter = null;
        boutonAjouterG = null;
        boutonRetour = null;
        boutonEffacer = null;
        this.monde = monde;
        monde.ajouterObservateur(this);

        reagir();
    }

    public void animer(){
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                try {
                    monde.simuler();
                    monde.setSimEnCours(false);
                } catch (TwiskException e) {
                    e.afficherMessage();
                }
                monde.notifierObservateurs();
                return null;
            }
        };
        ThreadsManager.getInstance().lancerTask(task);
    }

    @Override
    public void reagir() {
        this.getChildren().clear();
        /*------------------ Ajout d'activité -------------------*/
        Image img = new Image("/images/activite-add.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(50);
        view.setPreserveRatio(true);
        view.setSmooth(true);

        boutonAjouter = new Button();
        boutonAjouter.getStyleClass().add("bouton-outils");
        boutonAjouter.setGraphic(view);
        if (monde.isSimEnCours()) {
            boutonAjouter.setDisable(true);
        }

        boutonAjouter.setOnAction(actionEvent -> monde.ajouter("Activité"));
        this.getChildren().add(boutonAjouter);

        /*------------------ Ajout de guichet -------------------*/

        img = new Image("/images/guichet-add.png");
        view= new ImageView(img);
        view.setFitHeight(50);
        view.setPreserveRatio(true);
        view.setSmooth(true);

        boutonAjouterG = new Button();
        boutonAjouterG.getStyleClass().add("bouton-outils");
        boutonAjouterG.setGraphic(view);
        if (monde.isSimEnCours()) {
            boutonAjouterG.setDisable(true);
        }

        boutonAjouterG.setOnAction(actionEvent -> monde.ajouter("Guichet"));
        this.getChildren().add(boutonAjouterG);


        /* --------------- Revenir en arrière --------------------- */
        img = new Image("/images/left.png");
        view = new ImageView(img);
        view.setFitHeight(50);
        view.setPreserveRatio(true);
        view.setSmooth(true);

        boutonRetour = new Button();
        boutonRetour.getStyleClass().add("bouton-outils");
        boutonRetour.setGraphic(view);
        if (monde.isSimEnCours()) {
            boutonRetour.setDisable(true);
        }

        boutonRetour.setOnAction(actionEvent -> {
            if (!monde.arcIsEmpty())
                monde.retirerDernierArc();
        });
        this.getChildren().add(boutonRetour);

        /* ------------------ Réinitialiser ------------------ */
        img = new Image("/images/delete.png");
        view = new ImageView(img);
        view.setFitHeight(50);
        view.setPreserveRatio(true);
        view.setSmooth(true);

        boutonEffacer = new Button();
        boutonEffacer.getStyleClass().add("bouton-outils");
        boutonEffacer.setGraphic(view);
        if (monde.isSimEnCours()) {
            boutonEffacer.setDisable(true);
        }

        boutonEffacer.setOnAction(actionEvent -> monde.reset());
        this.getChildren().add(boutonEffacer);

        /*----------------- Lancer la simulation et l'arrêter ---------------------*/
        boutonLancement = new Button();
        boutonLancement.getStyleClass().add("bouton-outils");
        boutonLancement.setGraphic(view);
        if (monde.isSimEnCours()) {
            img = new Image("/images/arret.png");
            view = new ImageView(img);
            view.setFitHeight(50);
            view.setPreserveRatio(true);
            view.setSmooth(true);
            boutonLancement.setGraphic(view);
            boutonLancement.setOnAction(actionEvent -> {
                System.out.println("Arret...");
                monde.detruireClients();
                monde.setSimEnCours(false);
                reagir();
            });

        } else {
            img = new Image("/images/lancement.png");
            view = new ImageView(img);
            view.setFitHeight(50);
            view.setPreserveRatio(true);
            view.setSmooth(true);
            boutonLancement.setGraphic(view);
            boutonLancement.setOnAction(actionEvent -> {
                monde.setSimEnCours(true);
                reagir();
                //Faire vérifier le monde avant de lancer animer car bug d'affichage des alert à cause du Thread.
                try {
                    monde.verifierMondeIG();
                } catch (MondeException | GuichetTwiskException e) {
                    monde.setSimEnCours(false);
                    e.afficherMessage();
                    reagir();
                }
                animer();
            });
        }
        this.getChildren().add(boutonLancement);
    }
}
