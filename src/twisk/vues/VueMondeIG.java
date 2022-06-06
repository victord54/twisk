/**
 * Classe représentant la vue du MondeIG.
 *
 * @author Victor Dallé et Claire Kurth.
 */
package twisk.vues;

import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import twisk.monde.Etape;
import twisk.mondeIG.*;
import twisk.simulation.Client;

import java.util.Iterator;

public class VueMondeIG extends Pane implements Observateur {
    /**
     * Champ représentant le mondeIG correspondant.
     */
    private final MondeIG monde;

    /**
     * Constructeur.
     *
     * @param monde Le mondeIG correspondant.
     */
    public VueMondeIG(MondeIG monde) {

        this.monde = monde;
        monde.ajouterObservateur(this);

        this.setOnDragOver(dragEvent -> {
            dragEvent.acceptTransferModes(TransferMode.MOVE);
            dragEvent.consume();
        });

        this.setOnDragDropped(dragEvent -> {
            boolean success = false;
            try {
                Dragboard dragboard = dragEvent.getDragboard();
                String id = dragboard.getString();
                EtapeIG etape = monde.getEtape(id);
                monde.repositionnerEtape(id, (int) dragEvent.getX() - etape.getLargeur() / 2, (int) dragEvent.getY() - etape.getHauteur() / 2);
                success = true;
            } catch (Exception e) {
                System.out.println(e);
            } finally {
                dragEvent.setDropCompleted(success);
                dragEvent.consume();
                monde.notifierObservateurs();
            }
        });

        this.reagir();
    }

    /**
     * Méthode permettant de mettre à jours les vues des points de contrôles.
     * @param e L'étapeIG dont les points de contrôles doivent être mise à jours.
     */
    public void miseAJourPointsDeControle(EtapeIG e) {
        for (PointDeControleIG p : e) {
            VuePointDeControleIG vue = new VuePointDeControleIG(p, monde);
            if (monde.getPointsControleSelectionnes().contains(p)) {
                vue.setColorCircle(Color.rgb(237, 37, 78));
            }
            this.getChildren().add(vue);
        }
    }

    /**
     * Méthode permettant de mettre à jours les vues des arcsIG.
     */
    public void miseAjourArcs() {
        Iterator<ArcIG> it = monde.iteratorArcs();
        while (it.hasNext()) {
            ArcIG arc = it.next();
            VueArcIG vue = new VueArcIG(arc, monde);
            if (monde.getArcsSelectionnees().contains(arc)) {
                vue.getLigne().setStroke(Color.rgb(237, 37, 78));
                vue.getTriangle().setStroke(Color.rgb(237, 37, 78));
                vue.getTriangle().fillProperty().setValue(Color.rgb(237, 37, 78));
            }
            this.getChildren().add(vue);
        }
    }
    /**
     * Méthode de mise à jour de la vue.
     */
    @Override
    public void reagir() {
//        System.out.println("sim : " + monde.isSimEnCours()); // debug
        this.getChildren().clear();
        if (monde.isSimEnCours()) {
            if (monde.getNombreClientsRestant() == 0) {
                monde.detruireClients();
                monde.setSimEnCours(false);
                monde.notifierObservateurs();
            } else {
                Label label = new Label();
                label.relocate(1200, 550);
                label.setText(Integer.toString(monde.getNombreClientsRestant()));
                label.setFont(new Font(50));
                this.getChildren().add(label);
            }
        }

        miseAjourArcs();
        for (EtapeIG etape : monde) {
            VueEtapeIG vueEtape;
            if (etape.estUnGuichet()) {
                vueEtape = new VueGuichetIG(monde, (GuichetIG) etape);
            } else {
                vueEtape = new VueActiviteIG(monde, (ActiviteIG) etape);
            }
            if (monde.getEtapesSelectionnees().contains(etape)) {
                vueEtape.setId("VueActiviteSelect");
            }
            if (monde.getEntrees().contains(etape)) {
                ImageView img = new ImageView("/images/input.png");
                img.setPreserveRatio(true);
                img.setFitHeight(20);
                vueEtape.getTitre().setGraphic(img);
                vueEtape.getTitre().setContentDisplay(ContentDisplay.LEFT);
            } else if (monde.getSorties().contains(etape)) {
                ImageView img = new ImageView("/images/output.png");
                img.setPreserveRatio(true);
                img.setFitHeight(20);
                vueEtape.getTitre().setGraphic(img);
                vueEtape.getTitre().setContentDisplay(ContentDisplay.LEFT);
            }
            this.getChildren().add(vueEtape);
            miseAJourPointsDeControle(etape);

            if (monde.getGestionnaireClients() != null) {
                for (Client c : monde.getGestionnaireClients()) {
                    Etape tmpEtape = c.getEtape();
                    EtapeIG etapeIGTmp = monde.getCorrespondanceEtapes().getEtapeIG(tmpEtape);
                    if (etapeIGTmp != null && c.getRang() <= 8) {
                        this.getChildren().add(new VueClient(c, etapeIGTmp, monde.getSensCircu()));
                    }
                }
            }
        }
    }
}
