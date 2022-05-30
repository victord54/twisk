package twisk.vues;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import twisk.mondeIG.EtapeIG;
import twisk.outils.TailleComposants;
import twisk.simulation.Client;

import java.util.Random;

public class VueClient extends Circle {
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
            /*int max = etapeIG.getPosX()+ TailleComposants.getInstance().getLargeurActivite();
            int min = etapeIG.getPosX();
            this.setCenterX(random.nextInt(max - min) + min);
            System.out.println(client.getNumeroClient());
            max = etapeIG.getPosY() + 100;
            min = etapeIG.getPosY() + 20;
            int y = random.nextInt(max- min ) + min;
            this.setCenterY(y);
            System.out.println("Et y : " + min + "L : " + max + " y : "+ y);
            System.out.println("------------");*/


            this.setCenterX(etapeIG.getPosX() + TailleComposants.getInstance().getLargeurActivite() - client.getRang() * 25); // Partie droite de l'étape décalée de n rangs vers la gauche.
            this.setCenterY(etapeIG.getPosY() + 100);
        }
        //this.setCenterX(etapeIG.getPosX() + TailleComposants.getInstance().getLargeurActivite() - client.getRang() * 25); // Partie droite de l'étape décalée de n rangs vers la gauche.
        //this.setCenterY(etapeIG.getPosY() + 100);
        this.setRadius(TailleComposants.getInstance().getRadiusClient());
        this.setFill(Color.rgb(client.getNumeroClient()*12%256, client.getNumeroClient()*123%256, client.getNumeroClient()*1234%256));
    }

}
