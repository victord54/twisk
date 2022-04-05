package twisk.vues;

import javafx.scene.layout.HBox;
import twisk.mondeIG.ActiviteIG;
import twisk.mondeIG.MondeIG;
import twisk.outils.TailleComposants;

public class VueActiviteIG extends VueEtapeIG {
    final HBox hBox;

    public VueActiviteIG(MondeIG monde, ActiviteIG activite) {
        super(monde, activite);
        titre.setText(activite.getNom() + " = " + activite.getDelai() + "±" + activite.getEcart());
        hBox = new HBox();


        this.setPrefSize(TailleComposants.getInstance().getLargeurActivite(), TailleComposants.getInstance().getHauteurActivite());
        this.getChildren().add(hBox);

        this.reagir();
    }

    @Override
    public void reagir() {
    }
}
