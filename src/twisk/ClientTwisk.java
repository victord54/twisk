package twisk;

import twisk.monde.*;
import twisk.simulation.Simulation;

public class ClientTwisk {

    public static Monde monde1() {
        Monde m = new Monde();
        Guichet e1 = new Guichet("Guichet");
        ActiviteRestreinte e2 = new ActiviteRestreinte("zoo");
        Etape e3 = new Activite("toboggan");
        Etape e4 = new Activite("piscine");
        e2.aCommeGuichet(e1.getNumSemaphore());
        e1.ajouterSuccesseur(e2);
        e2.ajouterSuccesseur(e3);
        e3.ajouterSuccesseur(e4);
        m.ajouter(e1,e2,e3,e4);
        m.aCommeEntree(e1);
        m.aCommeSortie(e4);
        return m;
    }

    public static void main(String[] args) {
        Simulation simu = new Simulation();
        simu.simuler(monde1());
    }
}
