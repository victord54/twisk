package twisk.vues;

import javafx.scene.control.ContentDisplay;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import twisk.mondeIG.*;

import java.util.Iterator;

public class VueMondeIG extends Pane implements Observateur {
    final MondeIG monde;

    public VueMondeIG(MondeIG monde) {

        this.monde = monde;
        monde.ajouterObservateur(this);
        monde.setVueMondeIG(this);

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

    public void miseAJourPointsDeControle(EtapeIG e) {
        for (PointDeControleIG p : e) {
            VuePointDeControleIG vue = new VuePointDeControleIG(p, monde);
            if (monde.getPointsControleSelectionnes().contains(p)) {
                vue.setColorCircle(Color.rgb(237, 37, 78));
            }
            this.getChildren().add(vue);
        }
    }

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

    @Override
    public void reagir() {
        this.getChildren().clear();
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
        }
    }
}
