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
        super("SasEntree");
        loi = null;
    }

    public void setLoi(String l) {
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
        if (loi.equalsIgnoreCase("uniforme")) {
            builder.append("\tdelai(").append(6).append(",").append(3).append(");\n");
        } else if (loi.equalsIgnoreCase("gaussienne")) {
            builder.append("\tdouble x = 0.0;\n");
            builder.append("\tx = delaiGauss(").append(10).append(",").append(4).append(");\n");
            builder.append("\tusleep(x*1000000);\n");
        } else {
            builder.append("\tdouble x = 0.0;\n");
            builder.append("\tx = delaiExponentiel(").append(0.1).append(");\n");
            builder.append("\tusleep(x*1000000);\n");
        }

        if (gestionnaireSuccesseur.nbEtapes() >= 2) {
            builder.append("\t\nint seedEntree = getpid()+3456").append(";\n");
            builder.append("\tsrand(seedEntree);\n");
            builder.append("\tint nbEntree").append(" = rand()%").append(this.gestionnaireSuccesseur.nbEtapes()).append(";\n");
            builder.append("\n\tswitch (nbEntree").append(") {\n");
            for (int i = 0; i < gestionnaireSuccesseur.nbEtapes(); i++) {
                builder.append("\tcase ").append(i).append(":\n");
                builder.append("\ttransfert(").append(this.numEtape).append(",").append(this.gestionnaireSuccesseur.getEtape(i).getNumEtape()).append(");\n");
                builder.append(gestionnaireSuccesseur.getEtape(i).toC());
                builder.append("\tbreak;\n");
            }
            builder.append("\t}\n\n");
        } else {
            builder.append("\ttransfert(").append(this.numEtape).append(",").append(this.gestionnaireSuccesseur.getSucc().getNumEtape()).append(");\n");
            builder.append(this.gestionnaireSuccesseur.getSucc().toC());
        }
        return builder.toString();
    }
}