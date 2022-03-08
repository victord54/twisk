package twisk.simulation;

import twisk.monde.Monde;
import twisk.outils.KitC;

public class Simulation {
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
