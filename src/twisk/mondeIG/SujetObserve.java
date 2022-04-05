package twisk.mondeIG;

import twisk.vues.Observateur;

import java.util.ArrayList;

public class SujetObserve {
    protected final ArrayList<Observateur> observateurs;

    public SujetObserve() {
        observateurs = new ArrayList<>();
    }

    public void ajouterObservateur(Observateur v) {
        observateurs.add(v);
    }

    public void notifierObservateurs() {
        for (Observateur observateur : observateurs) {
            observateur.reagir();
        }
    }
}