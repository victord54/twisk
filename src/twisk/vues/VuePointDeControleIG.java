package twisk.vues;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import twisk.exceptions.ArcTwiskException;
import twisk.mondeIG.MondeIG;
import twisk.mondeIG.PointDeControleIG;

public class VuePointDeControleIG extends Circle implements Observateur {
    private final MondeIG monde;
    private final PointDeControleIG controle;

    public VuePointDeControleIG(PointDeControleIG c, MondeIG m) {

        controle = c;
        monde = m;
        this.setCenterX(controle.getPosX() + 2); // Épaisseur de la bordure
        this.setCenterY(controle.getPosY());
        this.setRadius(5);
        reagir();

        this.setOnMouseClicked(event -> {
            try {
                monde.ajouterPointDeControle(controle);
            } catch (ArcTwiskException e) {
                e.afficherMessage();
                monde.getPointsControleSelectionnes().clear();
                monde.notifierObservateurs();
            }
        });
    }

    public void setColorCircle(Paint paint) {
        this.setFill(paint);
    }

    @Override
    public void reagir() {
        this.setColorCircle(Color.rgb(95, 173, 86));
    }
}