/**
 * Classe représentant un arc du mondeIG.
 *
 * @author Victor Dallé et Claire Kurth
 */

package twisk.mondeIG;

public class ArcIG {
    /**
     * Champ correspondant au point de controle où démarre l'arc.
     */
    private final PointDeControleIG pt1;

    /**
     * Champ correspondant au point de controle où arrive l'arc.
     */
    private final PointDeControleIG pt2;

    /**
     * Constructeur.
     *
     * @param pt1 Le point de contrôle du départ de l'arc.
     * @param pt2 Le point de controle d'arrivé de l'arc.
     */
    public ArcIG(PointDeControleIG pt1, PointDeControleIG pt2) {
        this.pt1 = pt1;
        this.pt2 = pt2;

        pt1.getEtape().ajouterSucesseur(pt2.getEtape());
    }

    /**
     * Getter du point de contrôle de départ de l'arc.
     *
     * @return Le point de contrôle de départ.
     */
    public PointDeControleIG getPt1() {
        return pt1;
    }

    /**
     * Getter du point de contrôle d'arrivé de l'arc.
     *
     * @return Le point de contrôle d'arrivé.
     */
    public PointDeControleIG getPt2() {
        return pt2;
    }

    /**
     * Méthode permettant de savoir si l'arc existe déjà.
     *
     * @param pt1 Le point de contrôle de départ.
     * @param pt2 Le point de contrôle d'arrivé.
     * @return true si l'arc existe déjà.
     */
    public boolean isDoublon(PointDeControleIG pt1, PointDeControleIG pt2) {
        return pt1.equals(this.pt1) && pt2.equals(this.pt2) || pt1.equals(this.pt2) && pt2.equals(this.pt1);
    }
}
