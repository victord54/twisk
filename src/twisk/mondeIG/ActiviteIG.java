/**
 * Classe représentant une activité du mondeIG.
 *
 * @author Victor Dallé et Claire Kurth
 */
package twisk.mondeIG;

public class ActiviteIG extends EtapeIG {
    /**
     * Champ correspondant au délai de l'étape.
     */
    private int delai;

    /**
     * Champ correspondant à l'écart-type de l'étape.
     */
    private int ecart;

    /**
     * Constructeur.
     *
     * @param nom Nom de l'étape.
     * @param idf Identifiant de l'étape.
     * @param largeur Largeur de l'étape.
     * @param hauteur Hauteur de l'étape.
     */
    public ActiviteIG(String nom, String idf, int largeur, int hauteur) {
        super(nom, idf, largeur, hauteur);
        delai = 3;
        ecart = 2;
    }

    /**
     * Getter du délai de l'étape.
     *
     * @return Le délai.
     */
    public int getDelai() {
        return delai;
    }

    /**
     * Getter de l'écart-type de l'étape.
     *
     * @return L'écart-type.
     */
    public int getEcart() {
        return ecart;
    }

    /**
     * Setter du délai de l'étape.
     *
     * @param delai Le nouveau délai.
     */
    public void setDelai(int delai) {
        this.delai = delai;
    }

    /**
     * Setter de l'écart-type de l'étape.
     *
     * @param ecart Le nouveau écart-type.
     */
    public void setEcart(int ecart) {
        this.ecart = ecart;
    }

    /**
     * Méthode permettant de savoir si l'étape est une activité.
     *
     * @return Toujours true.
     */
    @Override
    public boolean estUneActivite() {
        return true;
    }

    /**
     * Méthode permettant de savoir si l'étape est une activité restreinte.
     *
     * @return Toujours true.
     */
    @Override
    public boolean estUneActiviteRestreinte() {
        return true;
    }
}
