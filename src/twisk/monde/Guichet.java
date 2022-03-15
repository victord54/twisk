package twisk.monde;

import twisk.outils.FabriqueNumero;

public class Guichet extends Etape {
    protected int nbJetons;
    protected int numSemaphore;

    public Guichet(String nom) {
        super(nom);
        numSemaphore = FabriqueNumero.getInstance().getNumeroSemaphore();
        nbJetons = 3;
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

    public int getNumSemaphore() {
        return numSemaphore;
    }

    @Override
    public String toC() {
        StringBuilder str = new StringBuilder();
        str.append("P(ids,").append(this.numSemaphore).append(");\n");
        str.append("transfert(").append(this.numEtape).append(",").append(this.gestionnaireSuccesseur.getSucc().getNumEtape()).append(");\n");
        return str.toString();
    }

    public int getNbJetons(){
        return nbJetons;
    }
}