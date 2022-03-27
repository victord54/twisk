/**
 * Classe représentant une activité restreinte du monde.
 *
 * @author Kurth Claire et Dallé Victor
 * @since 02/02/2022
 */

package twisk.monde;

public class ActiviteRestreinte extends Activite {
    /**
     * Indique le numéro de sémaphore du guichet.
     */
    private int numSemGuichet;

    /**
     * Constructeur de la classe
     *
     * @param nom Nom de l'activité restreinte.
     */
    public ActiviteRestreinte(String nom) {
        super(nom);
    }

    /**
     * Autre constructeur de la classe.
     *
     * @param nom Nom de l'activité restreinte.
     * @param t   Durée de l'activité restreinte.
     * @param e   Incertitude sur la durée (t +/- e) de l'activité restreinte.
     */
    public ActiviteRestreinte(String nom, int t, int e) {
        super(nom, t, e);
    }

    /**
     * Méthode définissant le guichet auquel l'activité restreinte est reliée.
     *
     * @param semaphore Identifiant unique du guichet.
     */
    public void setSemaphoreGuichet(int semaphore) {
        this.numSemGuichet = semaphore;
    }

    /**
     * Méthode définissant le code c à ajouter pour une activité restreinte.
     *
     * @return Le code c.
     */
    @Override
    public String toC() {
        StringBuilder str = new StringBuilder();
        str.append("delai(").append(temps).append(",").append(ecartTemps).append(");\n");
        str.append("V(ids,").append(numSemGuichet).append(");\n");
        if (!this.estUneSortie()) {
            str.append("transfert(").append(this.numEtape).append(",").append(this.gestionnaireSuccesseur.getSucc().getNumEtape()).append(");\n");
        } else {
            str.append("transfert(").append(this.numEtape).append(",1);\n");
        }
        return str.toString();
    }
}
