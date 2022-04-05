package twisk.vues;

import javafx.scene.layout.HBox;
import twisk.mondeIG.GuichetIG;
import twisk.mondeIG.MondeIG;
import twisk.outils.TailleComposants;

public class VueGuichetIG extends VueEtapeIG implements Observateur {
    private MondeIG monde;
    private GuichetIG guide;
    private HBox hbox;

    public VueGuichetIG(MondeIG monde, GuichetIG guichet) {
        super(monde, guichet);
        hbox = new HBox();


        this.setPrefSize(TailleComposants.getInstance().getLargeurActivite(), TailleComposants.getInstance().getHauteurActivite());
        this.getChildren().add(hbox);
    }


    @Override
    public void reagir() {
    }
}
