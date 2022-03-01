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

    @Override
    public String toC(){
        StringBuilder str = new StringBuilder();
        str.append("P(0,"+this.numSemaphore+");\n");
        str.append("transfert(" + this.numEtape + "," + this.gestionnaireSuccesseur.getSucc(0).getNumEtape() + ");\n");
        return "";
    }
}