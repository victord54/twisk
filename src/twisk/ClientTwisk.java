/**
 * Classe principale du projet.
 *
 * @author Kurth Claire et Dallé Victor
 * @since 02/02/2022
 */

package twisk;

import twisk.monde.*;
import twisk.outils.ClassLoaderPerso;
import twisk.simulation.Simulation;
import twisk.outils.ClassLoaderPerso;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ClientTwisk {

    /**
     * Un monde parmi d'autres pour la simulation.
     *
     * @return Le monde créé.
     */
    public static Monde monde1() {
        Monde monde = new Monde();

        Activite zoo = new Activite("balade au zoo", 3, 1);
        Guichet guichet = new Guichet("accès au toboggan", 2);
        Activite tob = new ActiviteRestreinte("toboggan", 2, 1);

        zoo.ajouterSuccesseur(guichet);
        guichet.ajouterSuccesseur(tob);

        monde.ajouter(zoo, tob, guichet);

        monde.aCommeEntree(zoo);
        monde.aCommeSortie(tob);

        return monde;
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
        ClassLoaderPerso loaderPerso = new ClassLoaderPerso(this.getClass().getClassLoader());
        try {
            loaderPerso.loadClass("twisk.simulation.Simulation");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Class<?> s = loaderPerso.getClass();
            Object sim = s.newInstance();
            Method setNbClient = s.getMethod("setNbClients",int.class);
            setNbClient.invoke(5);
            Method simuler = s.getMethod("simuler");
            simuler.invoke(monde1());
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
