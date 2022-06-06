/**
 * Classe représentant la vue d'une ActivitésIG.
 *
 * @author Victor Dallé et Claire Kurth.
 */
package twisk.vues;

import javafx.scene.layout.HBox;
import twisk.mondeIG.ActiviteIG;
import twisk.mondeIG.MondeIG;
import twisk.outils.TailleComposants;

public class VueActiviteIG extends VueEtapeIG {
    /**
     * Champ représentant la HBox.
     */
    private final HBox hBox;

    /**
     * Constructeur.
     *
     * @param monde Le mondeIG.
     * @param activite L'activitéIG correspondante.
     */
    public VueActiviteIG(MondeIG monde, ActiviteIG activite) {
        super(monde, activite);
        titre.setText(activite.getNom() + " = " + activite.getDelai() + "±" + activite.getEcart());
        hBox = new HBox();


        this.setPrefSize(TailleComposants.getInstance().getLargeurActivite(), TailleComposants.getInstance().getHauteurActivite());
        this.getChildren().add(hBox);

        this.reagir();
    }

    /**
     * Méthode de mise à jour de la vue.
     */
    @Override
    public void reagir() {
    }
}
