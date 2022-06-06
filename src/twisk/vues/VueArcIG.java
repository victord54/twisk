/**
 * Classe représentant la vue d'un ArcIG.
 *
 * @author Victor Dallé et Claire Kurth.
 */
package twisk.vues;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import twisk.mondeIG.ArcIG;
import twisk.mondeIG.MondeIG;

public class VueArcIG extends Group implements Observateur {
    /**
     * Champ correspondant au mondeIG.
     */
    private final MondeIG monde;

    /**
     * Champ correspondant à l'arcIG correspondant.
     */
    private final ArcIG arc;

    /**
     * Champ correspondant à la ligne de l'arc.
     */
    private Line line;

    /**
     * Champ correspondant au bout de la flèche de l'arc.
     */
    private Polyline triangle;

    /**
     * Constructeur.
     *
     * @param arc L'arcIG correspondant.
     * @param monde Le mondeIG.
     */
    public VueArcIG(ArcIG arc, MondeIG monde) {
        this.monde = monde;
        this.arc = arc;
        this.reagir();
    }

    /**
     * Getter de la ligne de l'arc.
     *
     * @return  La Line de l'arc.
     */
    public Line getLigne() {
        return (Line) this.getChildren().get(0);
    }

    /**
     * Getter du du bout de la flèche de l'arc.
     *
     * @return Le polyline de l'arc.
     */
    public Polyline getTriangle() {
        return (Polyline) this.getChildren().get(1);
    }

    /**
     * Méthode de mise à jour de la vue.
     */
    @Override
    public void reagir() {
        line = new Line(arc.getPt1().getPosX(), arc.getPt1().getPosY(), arc.getPt2().getPosX(), arc.getPt2().getPosY());
        line.setStrokeWidth(3);

        line.fillProperty().setValue(Color.rgb(0, 0, 0));
        triangle = new Polyline();
        double x2 = this.arc.getPt1().getPosX();
        double y2 = this.arc.getPt1().getPosY();
        double x1 = this.arc.getPt2().getPosX();
        double y1 = this.arc.getPt2().getPosY();

        int taillePointe = 18;
        int radian = 20;

        double theta = Math.atan2(y2 - y1, x2 - x1);
        double pt1x = x1 + Math.cos(theta + Math.toRadians(radian)) * taillePointe;
        double pt1y = y1 + Math.sin(theta + Math.toRadians(radian)) * taillePointe;
        double pt2x = x1 + Math.cos(theta - Math.toRadians(radian)) * taillePointe;
        double pt2y = y1 + Math.sin(theta - Math.toRadians(radian)) * taillePointe;

        triangle.getPoints().addAll(pt1x, pt1y, pt2x, pt2y, x1, y1);
        triangle.fillProperty().setValue(Color.rgb(0, 0, 0));
        this.setOnMouseClicked(mouseEvent -> monde.selectionnerArc(arc));
        this.getChildren().addAll(line, triangle);
    }
}
