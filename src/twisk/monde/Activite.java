/**
 * Classe représentant une activité du monde.
 *
 * @author Kurth Claire et Dallé Victor
 * @since 02/02/2022
 */

package twisk.monde;

public class Activite extends Etape {
    protected int temps;
    protected int ecartTemps;

    /**
     * Constructeur de la classe.
     * @param nom Nom de l'activité.
     */
    public Activite(String nom) {
        super(nom);
        temps = 4;
        ecartTemps = 2;
    }

    /**
     * Autre constructeur de la classe.
     * @param nom Nom de l'activité.
     * @param t   Durée de l'activité.
     * @param e   Incertitude sur la durée (t +/- e) de l'activité.
     */
    public Activite(String nom, int t, int e) {
        super(nom);
        this.temps = t;
        this.ecartTemps = e;
    }

    /**
     * Méthode qui vérifie si une étape est une activité.
     * @return Un booléen qui renvoie true car c'est une activité.
     */
    @Override
    public boolean estUneActivite() {
        return true;
    }

    /**
     * Méthode définissant le code c à ajouter pour une activité.
     * @return Le code c.
     */
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

    /**
     * Getter donnant le nombre de jetons.
     * @return 0, car c'est une activité et non un guichet.
     */
    @Override
    public int getNbJetons() {
        return 0;
    }
}