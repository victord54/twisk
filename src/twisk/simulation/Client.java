package twisk.simulation;

import twisk.monde.Etape;

public class Client {
    protected int numeroClient;
    protected int rang;
    protected Etape etape;

    public Client(int numero){
        this.numeroClient = numero;
    }

    public void allerA(Etape etape, int rang){
        this.etape = etape;
        this.rang = rang;
    }

    public int getNumeroClient(){
        return this.numeroClient;
    }

    public Etape getEtape(){
        return this.etape;
    }
}
