/**
 * Classe représentant la vue d'un point de contrôles.
 *
 * @author Victor Dallé et Claire Kurth.
 */
package twisk.vues;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import twisk.exceptions.ArcTwiskException;
import twisk.mondeIG.MondeIG;
import twisk.mondeIG.PointDeControleIG;

public class VuePointDeControleIG extends Circle implements Observateur {
    /**
     * Champ représentant le mondeIG.
     */
    private final MondeIG monde;

    /**
     * Champ représentant le point de contrôle correspondant.
     */
    private final PointDeControleIG controle;

    /**
     * Constructeur.
     *
     * @param c Le point de contrôle correspondant.
     * @param m Le mondeIG.
     */
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

    /**
     * Setter de la couleur du point de contrôle.
     *
     * @param paint La couleur.
     */
    public void setColorCircle(Paint paint) {
        this.setFill(paint);
    }

    /**
     * Méthode de mise à jour de la vue.
     */
    @Override
    public void reagir() {
        this.setColorCircle(Color.rgb(95, 173, 86));
    }
}