/**
 * Classe représentant un gestionnaire de clients.
 *
 * @author Victor Dallé et Claire Kurth.
 */
package twisk.simulation;

import twisk.monde.Etape;

import java.util.ArrayList;
import java.util.Iterator;

public class GestionnaireClients implements Iterable<Client> {
    /**
     * Champ représentant les clients.
     */
    protected ArrayList<Client> clients;

    /**
     * Champ représentant le nombre de clients.
     */
    protected int nbClients;

    /**
     * Champ représentant si il faut supprimer les clients ou non.
     */
    private boolean suppressionDesClients;

    /**
     * Constructeur par défaut.
     */
    public GestionnaireClients() {
        this.clients = new ArrayList<>();
        suppressionDesClients = false;
    }

    /**
     * Constructeur.
     * @param nbClients Le nombre de clients.
     */
    public GestionnaireClients(int nbClients) {
        this.clients = new ArrayList<>();
        this.nbClients = nbClients;
    }

    /**
     * Getter du champ permettant de savoir si les clients doivent être supprimé ou non.
     *
     * @return false si les clients ne doivent pas être supprimé, true si non.
     */
    public boolean isSuppressionDesClients() {
        return suppressionDesClients;
    }

    /**
     * Setter du champ permettant de savoir si les clients doivent être supprimé ou non.
     *
     * @param b La valeur de suppression.
     */
    public void setSuppressionDesClients(boolean b) {
        suppressionDesClients = b;
    }

    /**
     * Setter des clients.
     *
     * @param nbClients Collection de clients.
     */
    public void setClients(int... nbClients) {
        for (int n : nbClients) {
            this.clients.add(new Client(n));
        }
    }

    /**
     * Setter du nombre de clients.
     * @param nbClients Le nombre de clients.
     */
    public void setNbClients(int nbClients) {
        this.nbClients = nbClients;
    }

    /**
     * Méthode permettant de déplacer un client.
     *
     * @param numeroClient Le numéro du client à déplacer.
     * @param etape L'étape dans laquelle on déplace le client.
     * @param rang Le rang du client dans l'étape où il est déplacé.
     */
    public void allerA(int numeroClient, Etape etape, int rang) {
        for (Client c : clients) {
            if (c.getNumeroClient() == numeroClient) {
                c.allerA(etape, rang);
            }
        }
    }

    /**
     * Méthode permettant de supprimer tous les clients.
     */
    public void nettoyer() {
        this.clients.clear();
        suppressionDesClients = true;
    }

    /**
     * Getter du nombre de clients.
     *
     * @return Le nombre de clients.
     */
    public int getNbClients() {
        return this.nbClients;
    }

    /**
     * Méthode définissant un nouvel itérateur sur le gestionnaire de clients.
     *
     * @return Un itaror sur les clients du Gestionnaire de clients..
     */
    @Override
    public Iterator<Client> iterator() {
        return this.clients.iterator();
    }

    /**
     * Méthode toString.
     * @return Les clients et le nombre de clients.
     */
    @Override
    public String toString() {
        return "GestionnaireClients{" + "clients=" + clients + ", nbClients=" + nbClients + '}';
    }
}
