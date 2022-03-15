package twisk.simulation;

import twisk.monde.Monde;
import twisk.outils.KitC;

public class Simulation {
    public native int [] start_simulation(int nbEtapes, int nbGuichets, int nbClients, int tabJetonsGuichet[]);
    public native void nettoyage();
    public native int [] ou_sont_les_clients(int nbEtapes, int nbClients);

    private KitC kit;

    public Simulation() {
        kit = new KitC();
        kit.creerEnvironnement();
    }

    public void simuler(Monde monde) {
        kit.creerFichier(monde.toC());
        kit.compiler();
        kit.construireLaLibrairie();
    }

}
