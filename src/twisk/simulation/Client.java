package twisk.simulation;

import twisk.monde.Etape;

public class Client {
    protected int numeroClient;
    protected int rang;
    protected Etape etape;

    public Client(int numero){
        this.numeroClient = numero;
    }

    public int getNumeroClient() {
        return numeroClient;
    }

    public void allerA(Etape etape, int rang){
        this.etape = etape;
        this.rang = rang;
    }

    public Etape getEtape(){
        return this.etape;
    }

    public int getRang(){
        return rang;
    }

    @Override
    public String toString() {
        return "Client{" +
                "numeroClient=" + numeroClient +
                ", rang=" + rang +
                ", etape=" + etape.getNom() +
                '}';
    }
}
