package twisk;

import simulation.Simulation;
import twisk.monde.Activite;
import twisk.monde.Etape;
import twisk.monde.Guichet;
import twisk.monde.Monde;

public class ClientTwisk {

    public static Monde monde1() {
        Monde m = new Monde();
        Etape e1 = new Activite("zoo");
        Etape e2 = new Guichet("Guichet");
        Etape e3 = new Activite("toboggan");
        Etape e4 = new Activite("piscine");
        e2.ajouterSuccesseur(e1);
        e1.ajouterSuccesseur(e3);
        e3.ajouterSuccesseur(e4);
        m.ajouter(e1,e2,e3,e4);
        m.aCommeEntree(e2);
        m.aCommeSortie(e4);
        return m;
    }

    public static void main(String[] args) {
        Simulation simu = new Simulation();
        simu.simuler(monde1());
    }
}
