package twisk.vues;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import twisk.mondeIG.EtapeIG;
import twisk.outils.CorrespondanceEtapes;
import twisk.outils.TailleComposants;
import twisk.simulation.Client;

import java.util.Random;

public class VueClient extends Circle {

    private Client client;
    private final EtapeIG etapeIG;

    private int x;
    private int y;
    private final int radius = TailleComposants.getInstance().getRadiusClient();

    public VueClient(Client client, EtapeIG etapeIG) {
        super();
        this.etapeIG = etapeIG;
        this.setCenterX(etapeIG.getPosX() + TailleComposants.getInstance().getLargeurActivite() - client.getRang()*25); // Partie droite de l'étape décalée de n rangs vers la gauche.
        this.setCenterY(etapeIG.getPosY() + 100);
        this.setRadius(radius);
        Random random = new Random();
        this.setFill(Color.rgb(client.getNumeroClient()*12%256, client.getNumeroClient()*123%256, client.getNumeroClient()*1234%256));

        //System.out.println(this);
    }

}
