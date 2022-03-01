package twisk.monde;

public class ActiviteRestreinte extends Activite {

    public ActiviteRestreinte(String nom) {
        super(nom);
    }

    public ActiviteRestreinte(String nom, int t, int e) {
        super(nom, t, e);
    }

    @Override
    public String toC(){
        StringBuilder builder = new StringBuilder();
        builder.append("delai(" + temps + "," + ecartTemps +");\n");
        builder.append("transfert(" + this.numEtape + "," + this.gestionnaireSuccesseur.getSucc(0).getNom() + ");\n");
        return builder.toString();
    }
}
