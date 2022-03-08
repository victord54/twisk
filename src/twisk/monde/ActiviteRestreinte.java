package twisk.monde;

public class ActiviteRestreinte extends Activite {
    private int numSemGuichet;

    public ActiviteRestreinte(String nom) {
        super(nom);
    }

    public ActiviteRestreinte(String nom, int t, int e) {
        super(nom, t, e);
    }

    public void aCommeGuichet(int semaphore){
        this.numSemGuichet = semaphore;
    }

    @Override
    public String toC() {
        StringBuilder str = new StringBuilder();
        str.append("delai(").append(temps).append(",").append(ecartTemps).append(");\n");
        str.append("V(ids,").append(numSemGuichet).append(");\n");
        if (!this.estUneSortie()) {
            str.append("transfert(").append(this.numEtape).append(",").append(this.gestionnaireSuccesseur.getSucc().getNumEtape()).append(");\n");
            this.gestionnaireSuccesseur.getSucc().toC();
        } else {
            str.append("transfert(").append(this.numEtape).append(",1);\n");
        }
        return str.toString();
    }
}
