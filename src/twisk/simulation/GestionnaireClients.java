package twisk.simulation;

import twisk.monde.Etape;

import java.util.ArrayList;
import java.util.Iterator;

public class GestionnaireClients implements Iterable<Client> {
    protected ArrayList<Client> clients;
    protected int nbClients;

    public GestionnaireClients(){
        this.clients = new ArrayList<>();
    }

    public GestionnaireClients(int nbClients){
        this.clients = new ArrayList<>();
        this.nbClients = nbClients;
    }

    public ArrayList<Client> getClients() {
        return clients;
    }

    public void setClients(int ... nbClients){
        for (int n : nbClients){
            this.clients.add(new Client(n));
        }
    }

    public void setNbClients(int nbClients){
        this.nbClients = nbClients;
    }


    public void allerA(int numeroClient, Etape etape, int rang){
        
    }

    public void nettoyer(){
        this.clients.clear();
    }

    @Override
    public Iterator<Client> iterator(){
        return this.clients.iterator();
    }

    
}
