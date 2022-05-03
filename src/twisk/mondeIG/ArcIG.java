package twisk.mondeIG;

public class ArcIG {
    private final PointDeControleIG pt1;
    private final PointDeControleIG pt2;

    public ArcIG(PointDeControleIG pt1, PointDeControleIG pt2) {
        this.pt1 = pt1;
        this.pt2 = pt2;

        pt1.getEtape().ajouterSucesseur(pt2.getEtape());
    }

    public PointDeControleIG getPt1() {
        return pt1;
    }

    public PointDeControleIG getPt2() {
        return pt2;
    }

    public boolean isDoublon(PointDeControleIG pt1, PointDeControleIG pt2) {
        return pt1.equals(this.pt1) && pt2.equals(this.pt2) || pt1.equals(this.pt2) && pt2.equals(this.pt1);
    }
}
