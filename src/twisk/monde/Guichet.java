package twisk.monde;

import outils.FabriqueNumero;

public class Guichet extends Etape {
    protected int nbJetons;
    protected int numSemaphore;

    public Guichet(String nom) {
        super(nom);
        numSemaphore = FabriqueNumero.getInstance().getNumeroSemaphore();
    }

    public Guichet(String nom, int nb) {
        super(nom);
        this.nbJetons = nb;
        numSemaphore = FabriqueNumero.getInstance().getNumeroSemaphore();
    }

    @Override
    public boolean estUnGuichet() {
        return true;
    }
}