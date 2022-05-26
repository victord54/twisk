/**
 * Classe représentant un sas d'entrée.
 *
 * @author Kurth Claire et Dallé Victor
 * @since 02/02/2022
 */

package twisk.monde;

public class SasEntree extends Activite {

    /**
     * Constructeur par défaut de la classe.
     */
    public SasEntree() {
        super("entree");
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
        builder.append("\tdelai(").append(this.temps).append(",").append(this.ecartTemps).append(");\n");
        builder.append("\ttransfert(").append(this.numEtape).append(",").append(this.gestionnaireSuccesseur.getSucc().getNumEtape()).append(");\n");
        builder.append(this.gestionnaireSuccesseur.getSucc().toC());
        return builder.toString();
    }
}