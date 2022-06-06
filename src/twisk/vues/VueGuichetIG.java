/**
 * Classe représentant la vue d'un GuichetIG.
 *
 * @author Victor Dallé et Claire Kurth.
 */
package twisk.vues;

import javafx.scene.layout.HBox;
import twisk.mondeIG.GuichetIG;
import twisk.mondeIG.MondeIG;
import twisk.outils.TailleComposants;

public class VueGuichetIG extends VueEtapeIG implements Observateur {
    /**
     * Champ correspondant à la HBox.
     */
    private HBox hbox;

    /**
     * Constructeur.
     *
     * @param monde Le mondeIG.
     * @param guichet Le GuichetIG correspondant.
     */
    public VueGuichetIG(MondeIG monde, GuichetIG guichet) {
        super(monde, guichet);
        titre.setText(guichet.getNom() + " = " + guichet.getJetons() + " jetons");
        hbox = new HBox();


        this.setPrefSize(TailleComposants.getInstance().getLargeurActivite(), TailleComposants.getInstance().getHauteurGuichet());
        this.getChildren().add(hbox);
    }

    /**
     * Méthode de mise à jour de la vue.
     */
    @Override
    public void reagir() {
    }
}
