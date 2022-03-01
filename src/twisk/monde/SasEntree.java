package twisk.monde;

public class SasEntree extends Activite {

    public SasEntree() {
        super("entree");

    }

    @Override
    public String toC(){
        StringBuilder builder = new StringBuilder();
        builder.append("entrer(" + this.numEtape + ");\n");
        builder.append("delai(" + this.temps + "," + this.ecartTemps + ");\n");
        builder.append("transfert(" + this.numEtape + "," + this.gestionnaireSuccesseur.getSucc(0).getNumEtape() + ");\n");
        this.gestionnaireSuccesseur.getSucc(0).toC();
        return builder.toString();
    }
}