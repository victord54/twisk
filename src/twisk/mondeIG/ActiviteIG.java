package twisk.mondeIG;

public class ActiviteIG extends EtapeIG {
    private int delai;
    private int ecart;

    public ActiviteIG(String nom, String idf, int largeur, int hauteur) {
        super(nom, idf, largeur, hauteur);
        delai = 3;
        ecart = 2;
    }

    public int getDelai() {
        return delai;
    }

    public int getEcart() {
        return ecart;
    }

    public void setDelai(int delai) {
        this.delai = delai;
    }

    public void setEcart(int ecart) {
        this.ecart = ecart;
    }
}
