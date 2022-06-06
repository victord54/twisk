/**
 * Singleton qui fabrique des identifiants.
 *
 * @author Victor Dallé et Claire Kurth.
 */
package twisk.outils;

import twisk.mondeIG.PointDeControleIG;

public class FabriqueIdentifiant {
    /**
     * Champ correspondant au numéro unique d'une étape.
     */
    private int noEtape;

    /**
     * Champ correspondant au numéro unique d'un point de contrôle.
     */
    private int noPointDeControle;

    /**
     * Champ correspondant au numéro unique d'un guichet.
     */
    private int noGuichet;

    /**
     * Instance pour le singleton.
     */
    private static final FabriqueIdentifiant instance = new FabriqueIdentifiant();

    /**
     * Constructeur.
     */
    private FabriqueIdentifiant() {
    }

    /**
     * Getter donnant l'instance du singleton.
     *
     * @return instance.
     */
    public static FabriqueIdentifiant getInstance() {
        return instance;
    }

    /**
     * Fabrication de l'idenfiant unique d'un guichet.
     *
     * @return L'identifiant unique d'un guichet.
     */
    public String getIdentifiantGuichet(){
        noGuichet++;
        return "guichet" + (noGuichet - 1);
    }

    /**
     * Fabrication de l'idenfiant unique d'une étape.
     *
     * @return L'identifiant unique d'une étape.
     */
    public String getIdentifiantEtape() {
        noEtape++;
        return "etape" + (noEtape - 1);
    }

    /**
     * Fabrication de l'idenfiant unique d'un point de contrôle.
     *
     * @return L'identifiant unique d'un point de contrôle.
     */
    public String getIdentifiantPointDeControle(PointDeControleIG pt) {
        noPointDeControle++;
        return "" + pt.getIdEtape() + "@pt-ctrl" + (noPointDeControle - 1);
    }

    /**
     * Méthode permettant de reset.
     */
    public void reset() {
        noEtape = 0;
        noPointDeControle = 0;
    }

    /**
     * Méthode permettant de reset les points de contrôles.
     */
    public void resetPointDeControle(){
        noPointDeControle = 0;
    }
}
