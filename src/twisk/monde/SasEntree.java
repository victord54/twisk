package twisk.monde;

public class SasEntree extends Activite {

    public SasEntree() {
        super("entree");

    }

    @Override
    public String toC(){
        StringBuilder builder = new StringBuilder();
        builder.append("entrer(").append(this.numEtape).append(");\n");
        builder.append("delai(").append(this.temps).append(",").append(this.ecartTemps).append(");\n");
        builder.append("transfert(").append(this.numEtape).append(",").append(this.gestionnaireSuccesseur.getSucc().getNumEtape()).append(");\n");
        this.gestionnaireSuccesseur.getSucc().toC();
        return builder.toString();
    }
}