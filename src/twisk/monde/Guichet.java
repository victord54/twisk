package twisk.monde;

public class Guichet extends Etape {
    protected int nbJetons;

    public Guichet(String nom){
        super(nom);
    }
    public Guichet(String nom, int nbEtape) {
        super(nom, nbEtape);
    }

    public Guichet(String nom, int nbEtape, int nb) {
        super(nom, nbEtape);
        this.nbJetons = nb;
    }

    @Override
    public boolean estUnGuichet() {
        return true;
    }
}