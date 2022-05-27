/**
 * Classe représentant un sas d'entrée.
 *
 * @author Kurth Claire et Dallé Victor
 * @since 02/02/2022
 */

package twisk.monde;

public class SasEntree extends Activite {
    private String loi;
    /**
     * Constructeur par défaut de la classe.
     */
    public SasEntree() {
        super("entree");
        loi = null;
    }

    public void setLoi(String l){
        loi = l;
    }
    /**
     * Méthode définissant le code c à ajouter pour un sas d'entrée.
     *
     * @return Le code c.
     */
    @Override
    public String toC() {
        StringBuilder builder = new StringBuilder();
        builder.append("\tentrer(").append(this.numEtape).append(");\n");
        if (loi.equalsIgnoreCase("uniforme")){
            builder.append("\tdelai(").append(10).append(",").append(4).append(");\n");
        } else if (loi.equalsIgnoreCase("gaussienne")){
            builder.append("\tdouble x = 0.0;\n");
            builder.append("\tx = delaiGauss(").append(10).append(",").append(4).append(");\n");
            builder.append("\tusleep(x*1000000);\n");
        } else {
            builder.append("\tdouble x = 0.0;\n");
            builder.append("\tx = delaiExponentiel(").append(0.1).append(");\n");
            builder.append("\tusleep(x*1000000);\n");
        }

        builder.append("\ttransfert(").append(this.numEtape).append(",").append(this.gestionnaireSuccesseur.getSucc().getNumEtape()).append(");\n");
        builder.append(this.gestionnaireSuccesseur.getSucc().toC());
        return builder.toString();
    }
}