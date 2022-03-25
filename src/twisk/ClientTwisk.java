/**
 * Classe principale du projet.
 *
 * @author Kurth Claire et Dallé Victor
 * @since 02/02/2022
 */

package twisk;

import twisk.monde.*;
import twisk.simulation.Simulation;

public class ClientTwisk {

    /**
     * Un monde parmi d'autres pour la simulation.
     *
     * @return Le monde créé.
     */
    public static Monde monde1() {
        Monde m = new Monde();
        Guichet e1 = new Guichet("Guichet");
        ActiviteRestreinte e2 = new ActiviteRestreinte("zoo");
        Etape e3 = new Activite("toboggan");
        Etape e4 = new Activite("piscine");
        e2.setSemaphoreGuichet(e1.getNumSemaphore());
        e1.ajouterSuccesseur(e2);
        e2.ajouterSuccesseur(e3);
        e3.ajouterSuccesseur(e4);
        m.ajouter(e1, e2, e4, e3);
        m.aCommeEntree(e1);
        m.aCommeSortie(e4);
        return m;
    }

    /**
     * Un autre monde parmi d'autres pour la simulation.
     *
     * @return Le monde créé.
     */
    public static Monde monde2() {
        Monde monde = new Monde();
        Guichet guichet = new Guichet("ticket", 2);
        Activite act1 = new ActiviteRestreinte("toboggan", 2, 1);
        Etape etape1 = new Activite("musee");
        Etape etape2 = new Activite("boutique");
        etape1.ajouterSuccesseur(etape2);
        etape2.ajouterSuccesseur(guichet);
        guichet.ajouterSuccesseur(act1);
        monde.ajouter(etape2, etape1);
        monde.ajouter(act1);
        monde.ajouter(guichet);
        monde.aCommeEntree(etape1);
        monde.aCommeSortie(act1);
        return monde;
    }

    /**
     * Méthode main permettant l'exécution du programme.
     *
     * @param args Arguments lors de l'exécution.
     */
    public static void main(String[] args) {
        Simulation simulation = new Simulation();
        simulation.setNbClients(8);
        simulation.simuler(monde2());
    }
}
