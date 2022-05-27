package twisk.simulation;

import twisk.monde.Etape;

import java.util.ArrayList;
import java.util.Iterator;

public class GestionnaireClients implements Iterable<Client> {
    protected ArrayList<Client> clients;
    protected int nbClients;

    private boolean suppressionDesClients;

    public GestionnaireClients() {
        this.clients = new ArrayList<>();
        suppressionDesClients = false;
    }

    public GestionnaireClients(int nbClients) {
        this.clients = new ArrayList<>();
        this.nbClients = nbClients;
    }

    public boolean isSuppressionDesClients() {
        return suppressionDesClients;
    }

    public void setSuppressionDesClients(boolean b) {
        suppressionDesClients = b;
    }

    public ArrayList<Client> getClients() {
        return clients;
    }

    public void setClients(int... nbClients) {
        for (int n : nbClients) {
            this.clients.add(new Client(n));
        }
    }

    public void setNbClients(int nbClients) {
        this.nbClients = nbClients;
    }


    public void allerA(int numeroClient, Etape etape, int rang) {
        for (Client c : clients) {
            if (c.getNumeroClient() == numeroClient) {
                c.allerA(etape, rang);
            }
        }
    }

    public void nettoyer() {
        this.clients.clear();
        suppressionDesClients = true;
    }


    public int getNbClients() {
        return this.nbClients;
    }

    @Override
    public Iterator<Client> iterator() {
        return this.clients.iterator();
    }

    @Override
    public String toString() {
        return "GestionnaireClients{" + "clients=" + clients + ", nbClients=" + nbClients + '}';
    }
}
