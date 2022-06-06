/**
 * Classe représentant un guichet du mondeIG.
 *
 * @author Victor Dallé et Claire Kurth
 */
package twisk.mondeIG;

public class GuichetIG extends EtapeIG {
    /**
     * Champ correspondant aux jetons du guichet.
     */
    private int jetons;

    /**
     * Constructeur.
     *
     * @param nom Le nom du guichet.
     * @param idf L'identifiant du guichet.
     * @param largeur La largeur du guichet.
     * @param hauteur La hauteur du guichet.
     * @param jetons Le nombre de jetons du guichet.
     */
    public GuichetIG(String nom, String idf, int largeur, int hauteur, int jetons) {
        super(nom,idf,largeur,hauteur);
        this.jetons = jetons;
    }

    /**
     * Méthode permettant de savoir si l'étape est un guichet.
     *
     * @return Toujours true.
     */
    @Override
    public boolean estUnGuichet(){
        return true;
    }

    /**
     * Getter du nombre de jetons du guichet.
     *
     * @return Le nombre de jetons du guichet.
     */
    public int getJetons() {
        return jetons;
    }

    /**
     * Setter du nombre de jetons du guichet.
     *
     * @param jetons Le nouveau nombre de jetons.
     */
    public void setJetons(int jetons) {
        this.jetons = jetons;
    }
}
