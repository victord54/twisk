/**
 * Classe représentant une activité du monde.
 *
 * @author Kurth Claire et Dallé Victor
 * @since 02/02/2022
 */

package twisk.monde;

public class Activite extends Etape {
    /**
     * Champ contenant le temps passé dans l'étape.
     */
    protected int temps;

    private static int nbSwitch = 0;

    /**
     * Champ contenant l'écart-temps du temps de l'étape.
     */
    protected int ecartTemps;

    /**
     * Constructeur de la classe.
     *
     * @param nom Nom de l'activité.
     */
    public Activite(String nom) {
        super(nom);
        temps = 4;
        ecartTemps = 2;
    }

    /**
     * Autre constructeur de la classe.
     *
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
     *
     * @return Un booléen qui renvoie true car c'est une activité.
     */
    @Override
    public boolean estUneActivite() {
        return true;
    }

    /**
     * Getter donnant le nombre de jetons.
     *
     * @return 0, car c'est une activité et non un guichet.
     */
    @Override
    public int getNbJetons() {
        return 0;
    }

    @Override
    public void setSemaphoreGuichet(int semaphore) {
    }

    /**
     * Méthode définissant le code c à ajouter pour une activité.
     *
     * @return Le code c.
     */
    @Override
    public String toC() {
        StringBuilder builder = new StringBuilder();
        if (this.gestionnaireSuccesseur.nbEtapes() >= 2) {
            nbSwitch++;
            // Génération de la seed de manière différente pour chaque processus et niveaux de switchs.
            builder.append("\t\nint seed = getpid()+1233*").append(nbSwitch).append(";\n");
            builder.append("\tsrand(seed);\n");
            builder.append("\tint nb").append(nbSwitch).append(" = rand()%").append(this.gestionnaireSuccesseur.nbEtapes()).append(";\n");
            builder.append("\n\tswitch (nb").append(nbSwitch).append(") {\n");
            for (int i = 0; i < gestionnaireSuccesseur.nbEtapes(); i++) {
                builder.append("\tcase ").append(i).append(":\n");
                builder.append("\tdelai(").append(temps).append(",").append(ecartTemps).append(");\n");
                if (!this.estUneSortie()) {
                    builder.append("\ttransfert(").append(this.numEtape).append(",").append(this.gestionnaireSuccesseur.getEtape(i).getNumEtape()).append(");\n");
                    builder.append(gestionnaireSuccesseur.getEtape(i).toC());
                } else {
                    builder.append("\ttransfert(").append(this.numEtape).append(",1);\n");
                }
                builder.append("\tbreak;\n");
            }
            builder.append("\t}\n\n");
        } else {
            builder.append("\tdelai(").append(temps).append(",").append(ecartTemps).append(");\n");
            if (!this.estUneSortie()) {
                builder.append("\ttransfert(").append(this.numEtape).append(",").append(this.gestionnaireSuccesseur.getSucc().getNumEtape()).append(");\n");
                builder.append(gestionnaireSuccesseur.getSucc().toC());
            } else {
                builder.append("\ttransfert(").append(this.numEtape).append(",1);\n");
            }
        }
        return builder.toString();
    }

}