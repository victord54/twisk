/**
 * Classe du client Twisk permettant de lancer une simulation.
 *
 * @author Kurth Claire et Dallé Victor
 * @since 02/02/2022
 */

package twisk.simulation;

import twisk.monde.*;
import twisk.mondeIG.MondeIG;
import twisk.outils.ClassLoaderPerso;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ClientTwisk {
    /**
     * Le mondeIG correspondant.
     */
    private final MondeIG mondeIG;

    /**
     * Constructeur.
     *
     * @param m Le mondeIG.
     */
    public ClientTwisk(MondeIG m) {
        mondeIG = m;
    }

    /**
     * Méthode permettant de lancer une simulation.
     *
     * @param monde Le monde à simuler.
     * @param nbClients Le nombre de clients.
     */
    public void lancementSimulation(Monde monde, int nbClients) {
        ClassLoaderPerso loaderPerso = new ClassLoaderPerso(this.getClass().getClassLoader());
        try {
            Class<?> s = loaderPerso.loadClass("twisk.simulation.Simulation");
            Object sim = s.getConstructor(MondeIG.class).newInstance(mondeIG);
            Method setNbClient = s.getMethod("setNbClients", int.class);
            setNbClient.invoke(sim, nbClients);
            Method simuler = s.getMethod("simuler", Monde.class);
            simuler.invoke(sim, monde);
            loaderPerso.finalize();

        } catch (ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
}
