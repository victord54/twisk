/**
 * Classe représentant un guichet.
 *
 * @author Kurth Claire et Dallé Victor
 * @since 02/02/2022
 */

package twisk.monde;

import twisk.outils.FabriqueNumero;

public class Guichet extends Etape {
    /**
     * Champs contenant le nombre de jetons disponibles.
     */
    protected int nbJetons;

    /**
     * Champs contenant l'identifiant unique du guichet.
     */
    protected int numSemaphore;

    /**
     * Constructeur de la classe.
     * @param nom Nom associé au guichet.
     */
    public Guichet(String nom) {
        super(nom);
        numSemaphore = FabriqueNumero.getInstance().getNumeroSemaphore();
        nbJetons = 3;
    }

    /**
     * Constructeur de la classe.
     * @param nom Nom associé au guichet.
     * @param nb Nombre de jetons pour le guichet.
     */
    public Guichet(String nom, int nb) {
        super(nom);
        this.nbJetons = nb;
        numSemaphore = FabriqueNumero.getInstance().getNumeroSemaphore();
    }

    /**
     * Méthode vérifiant si l'Etape est un guichet.
     * @return Vrai, car c'est un guichet.
     */
    @Override
    public boolean estUnGuichet() {
        return true;
    }

    /**
     * Getter donnant l'id du guichet.
     * @return Numéro de sémaphore.
     */
    public int getNumSemaphore() {
        return numSemaphore;
    }

    /**
     * Méthode définissant le code c à ajouter pour un guichet.
     * @return Le code c.
     */
    @Override
    public String toC() {
        StringBuilder str = new StringBuilder();
        str.append("P(ids,").append(this.numSemaphore).append(");\n");
        str.append("transfert(").append(this.numEtape).append(",").append(this.gestionnaireSuccesseur.getSucc().getNumEtape()).append(");\n");
        return str.toString();
    }

    /**
     * Getter donnant le nombre de jetons disponibles pour le guichet.
     * @return Le nombre de jetons.
     */
    public int getNbJetons(){
        return nbJetons;
    }
}