/**
 * Classe représentant la fabrique de numéro pour les étapes et les guichets.
 *
 * @author Kurth Claire et Dallé Victor
 * @since 02/02/2022
 */
package twisk.outils;

public class FabriqueNumero {
    /**
     * Compteur d'étapes.
     */
    private int cptEtape;

    /**
     * Compteur de guichets.
     */
    private int cptSemaphore;

    /**
     * Instante pour le singleton.
     */
    private static final FabriqueNumero instance = new FabriqueNumero();

    /**
     * Constructeur par défaut de la classe.
     */
    private FabriqueNumero() {
    }

    /**
     * Getter donnant l'instance du singleton.
     * @return instance.
     */
    public static FabriqueNumero getInstance() {
        return instance;
    }

    /**
     * Getter donnant le numéro unique de la prochaine étape.
     * @return Numéro unique de l'étape.
     */
    public int getNumeroEtape() {
        cptEtape++;
        return cptEtape - 1;
    }

    /**
     * Getter donnant le numéro unique du prochain guichet.
     * @return Sémaphore unique.
     */
    public int getNumeroSemaphore() {
        cptSemaphore++;
        return cptSemaphore;
    }

    /**
     * Méthode remettant à 0 les compteurs d'étapes et de sémaphore.
     */
    public void reset() {
        cptEtape = 0;
        cptSemaphore = 0;
    }
}
