package twisk.vues;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import twisk.mondeIG.ArcIG;
import twisk.mondeIG.MondeIG;

public class VueArcIG extends Group implements Observateur {
    private final MondeIG monde;
    private final ArcIG arc;
    private Line line;
    private Polyline triangle;

    public VueArcIG(ArcIG arc, MondeIG monde) {
        this.monde = monde;
        this.arc = arc;
        this.reagir();
    }

    public Line getLigne() {
        return (Line) this.getChildren().get(0);
    }

    public Polyline getTriangle() {
        return (Polyline) this.getChildren().get(1);
    }

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
