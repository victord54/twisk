/**
 * Classe représentant les points de contrôles d'une étapeIG.
 *
 * @author Victor Dallé et Claire Kurth
 */
package twisk.mondeIG;

import twisk.outils.FabriqueIdentifiant;

public class PointDeControleIG {
    /**
     * Champ correspondant à la position sur l'axe des abscisses.
     */
    private int posX;

    /**
     * Champ correspondant à la position sur l'axe des ordonnées.
     */
    private int posY;

    /**
     * Champ correspondant à l'identifiant du point de contrôles.
     */
    private final String id;

    /**
     * Champ correspondant à l'étape à laquelle appartient le point de contrôle.
     */
    private final EtapeIG etape;

    /**
     * Constructeur.
     *
     * @param x Position sur l'axe des abscisses.
     * @param y Position sur l'axe des ordonnées.
     * @param etape L'étape à laquelle il appartient.
     */
    public PointDeControleIG(int x, int y, EtapeIG etape) {
        posX = x;
        posY = y;
        this.etape = etape;
        id = FabriqueIdentifiant.getInstance().getIdentifiantPointDeControle(this);
    }

    /**
     * Getter de la position sur l'axe des abcisses.
     *
     * @return La position sur l'axe des abscisses.
     */
    public int getPosX() {
        return posX;
    }

    /**
     * Getter de la position sur l'axe des ordonnées.
     *
     * @return La position sur l'axe des ordonnées.
     */
    public int getPosY() {
        return posY;
    }

    /**
     * Getter de l'identifiant du point de contrôle.
     *
     * @return L'identifiant.
     */
    public String getId() {
        return id;
    }

    /**
     * Getter de l'étape du point de contrôle.
     *
     * @return L'étape du point de contrôle.
     */
    public EtapeIG getEtape(){
        return etape;
    }

    /**
     * Getter de l'identifiant de l'étape correspondant au point de contrôle.
     *
     * @return L'idenfiant de l'étape.
     */
    public String getIdEtape() {
        return etape.getId();
    }

    /**
     * Méthode permettant de relocaliser le point contrôle.
     *
     * @param x La nouvelle position sur l'axe des abscisses.
     * @param y La nouvelle position sur l'axe des ordonnées.
     */
    public void relocate(int x, int y) {
        posX = x;
        posY = y;
    }

    /**
     * Méthode toString.
     *
     * @return Les coordoonées, l'identifiant et l'étape du point de contrôle.
     */
    @Override
    public String toString() {
        return "PointDeControleIG{" +
                "posX=" + posX +
                ", posY=" + posY +
                ", id='" + id + '\'' +
                ", etape=" + etape +
                '}';
    }
}
