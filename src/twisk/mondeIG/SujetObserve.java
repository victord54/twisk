/**
 * Classe représentant le Sujet observé.
 *
 * @author Victor Dallé et Claire Kurth
 */
package twisk.mondeIG;

import twisk.vues.Observateur;

import java.util.ArrayList;

public class SujetObserve {
    /**
     * Champ représentant la liste des observateurs du sujet observé.
     */
    protected final ArrayList<Observateur> observateurs;

    /**
     * Constructeur.
     */
    public SujetObserve() {
        observateurs = new ArrayList<>();
    }

    /**
     * Méthode permettant d'ajouter un observateur à la liste des observateurs.
     * @param v L'observateur à ajouter.
     */
    public void ajouterObservateur(Observateur v) {
        observateurs.add(v);
    }

    /**
     * Méthode permettant de notifier les observateurs de la liste des observateurs.
     */
    public void notifierObservateurs() {
        for (Observateur observateur : observateurs) {
            observateur.reagir();
        }
    }


}