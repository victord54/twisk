/**
 * Classe principale du projet.
 *
 * @author Kurth Claire et Dallé Victor
 * @since 02/02/2022
 */

package twisk;

import twisk.monde.*;
import twisk.outils.ClassLoaderPerso;
import twisk.simulation.Client;
import twisk.simulation.Simulation;
import twisk.outils.ClassLoaderPerso;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ClientTwisk {


    public ClientTwisk(){

    }
    /**
     * Un monde parmi d'autres pour la simulation.
     *
     * @return Le monde créé.
     */
    public Monde monde1() {
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
    public Monde monde2() {
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

    public void lancementSimulation(Monde monde, int nbClients){
        ClassLoaderPerso loaderPerso = new ClassLoaderPerso(this.getClass().getClassLoader());
        try {
            Class<?> s = loaderPerso.loadClass("twisk.simulation.Simulation");
            System.out.println(s);
            Object sim = s.getDeclaredConstructor().newInstance();
            Method setNbClient = s.getMethod("setNbClients",int.class);
            setNbClient.invoke(sim,nbClients);
            Method simuler = s.getMethod("simuler", Monde.class);
            simuler.invoke(sim, monde);
            loaderPerso.finalize();



        } catch (ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode main permettant l'exécution du programme.
     *
     * @param args Arguments lors de l'exécution.
     */
    public static void main(String[] args) {
        ClientTwisk client = new ClientTwisk();
        client.lancementSimulation(client.monde2(),5);
        client.lancementSimulation(client.monde1(),8);

    }
}
