/**
 * Singleton correspondant à la taille des composants.
 *
 * @author Claire Kurth et Victor Dallé.
 */
package twisk.outils;

public class TailleComposants {
    /**
     * Champ correspondant à la largeur définitive d'une activité.
     */
    final int largeurActivite;

    /**
     * Champ correspondant à la hauteur définitive d'une activité.
     */
    final int hauteurActivite;

    /**
     * Champ correspondant au radius définitif d'un client.
     */
    final int radiusClient;

    /**
     * Champ correspondant à la hauteur définitive d'un guichet.
     */
    final int hauteurGuichet;

    /**
     * Instance pour le singleton.
     */
    private static final TailleComposants instance = new TailleComposants();

    /**
     * Constructeur.
     */
    private TailleComposants() {
        largeurActivite = 220;
        hauteurActivite = 120;
        radiusClient = 10;
        hauteurGuichet = 80;
    }

    /**
     * Getter donnant l'instance du singleton.
     *
     * @return instance.
     */
    public static TailleComposants getInstance() {
        return instance;
    }

    /**
     * Getter de la largeur d'une activité.
     *
     * @return La largeur.
     */
    public int getLargeurActivite() {
        return largeurActivite;
    }

    /**
     * Getter de la hauteur d'une activité.
     * @return La hauteur.
     */
    public int getHauteurActivite() {
        return hauteurActivite;
    }

    /**
     * Getter du radius d'un client.
     *
     * @return Le radius.
     */
    public int getRadiusClient() {
        return radiusClient;
    }

    /**
     * Getter de la hauteur d'un guichet.
     *
     * @return La hauteur.
     */
    public int getHauteurGuichet(){
        return hauteurGuichet;
    }
}
