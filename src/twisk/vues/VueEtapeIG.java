package twisk.vues;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.MondeIG;

public abstract class VueEtapeIG extends VBox implements Observateur {
    protected final MondeIG monde;
    protected final EtapeIG etape;
    protected final Label titre;

    public VueEtapeIG(MondeIG monde, EtapeIG etape) {
        this.monde = monde;
        this.etape = etape;
        titre = new Label(etape.getNom());
        titre.setId("titre");
        titre.setAlignment(Pos.BASELINE_CENTER);
        this.getChildren().add(titre);
        this.relocate(etape.getPosX(), etape.getPosY());

        this.setOnMouseClicked(event -> monde.selectionnerEtape(etape));

        this.setOnDragDetected(mouseEvent -> {
            Dragboard dragboard = this.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            WritableImage activiteImage = this.snapshot(null, null);
            content.putImage(activiteImage);
            content.putString(this.etape.getId());
            dragboard.setContent(content);
            mouseEvent.consume();
        });
    }

    public Label getTitre() {
        return titre;
    }
}
