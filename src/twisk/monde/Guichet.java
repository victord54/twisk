package twisk.monde;

public class Guichet extends Etape {
    protected int nbJetons;

    public Guichet(String nom) {
        super(nom);
    }

    public Guichet(String nom, int nb) {
        super(nom);
        this.nbJetons = nb;
    }

    @Override
    public boolean estUnGuichet() {
        return true;
    }
}