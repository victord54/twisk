package twisk.monde;

public class Activite extends Etape {
    protected int temps;
    protected int ecartTemps;

    public Activite(String nom) {
        super(nom);
        temps = 4;
        ecartTemps = 2;
    }

    public Activite(String nom, int t, int e) {
        super(nom);
        this.temps = t;
        this.ecartTemps = e;
    }

    @Override
    public boolean estUneActivite() {
        return true;
    }

    @Override
    public String toC() {
        StringBuilder builder = new StringBuilder();
        builder.append("delai(").append(temps).append(",").append(ecartTemps).append(");\n");
        if (!this.estUneSortie()) {
            builder.append("transfert(").append(this.numEtape).append(",").append(this.gestionnaireSuccesseur.getSucc().getNumEtape()).append(");\n");
            this.gestionnaireSuccesseur.getSucc().toC();
        } else {
            builder.append("transfert(").append(this.numEtape).append(",1);\n");
        }
        return builder.toString();
    }

    public int getNbJetons(){
        return 0;
    }
}