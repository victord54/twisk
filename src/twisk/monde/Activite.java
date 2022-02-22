package twisk.monde;

public class Activite extends Etape {
    protected int temps;
    protected int ecartTemps;

    public Activite(String nom){
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
    public String toC(){
        StringBuilder builder = new StringBuilder();
        builder.append("delai(" + temps + "," + ecartTemps +");\n");
        builder.append("transfert(" + this.nom + "," + this.gestionnaireSuccesseur.getSucc(0).getNom() + ");\n");
        return builder.toString();
    }

}