/**
 * Classe représentant la vue d'un Client.
 *
 * @author Victor Dallé et Claire Kurth.
 */
package twisk.vues;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import twisk.mondeIG.EtapeIG;
import twisk.outils.TailleComposants;
import twisk.simulation.Client;

import java.util.Random;

public class VueClient extends Circle {
    /**
     * Constructeur.
     *
     * @param client Le client correspondant.
     * @param etapeIG L'étapeIG où se trouve le client.
     * @param sensCircu Le sens de circulation du mondeIG.
     */
    public VueClient(Client client, EtapeIG etapeIG, String sensCircu) {
        super();
        Random random = new Random();
        if (etapeIG.estUnGuichet()){
            if (sensCircu.equalsIgnoreCase("gaucheVersDroite")){
                this.setCenterX(etapeIG.getPosX() + TailleComposants.getInstance().getLargeurActivite() - client.getRang() * 25); // Partie droite de l'étape décalée de n rangs vers la gauche.
                this.setCenterY(etapeIG.getPosY() + 65);
            } else{
                this.setCenterX(etapeIG.getPosX() + client.getRang()*25); // Clients arrivent à droite donc vont à gauche
                this.setCenterY(etapeIG.getPosY() + 65);
            }
        }
        else{
            this.setCenterX(etapeIG.getPosX() + TailleComposants.getInstance().getLargeurActivite() - client.getRang() * 25); // Partie droite de l'étape décalée de n rangs vers la gauche.
            this.setCenterY(etapeIG.getPosY() + 100);
        }
             this.setRadius(TailleComposants.getInstance().getRadiusClient());
        this.setFill(Color.rgb(client.getNumeroClient()*12%256, client.getNumeroClient()*123%256, client.getNumeroClient()*1234%256));
    }

}
