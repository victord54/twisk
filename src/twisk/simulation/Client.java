/**
 * Classe correspondant aux Clients du monde.
 *
 * @author Victor Dallé et Claire Kurth.
 */
package twisk.simulation;

import twisk.monde.Etape;

public class Client {
    /**
     * Champ correspondant au numéro du client.
     */
    protected int numeroClient;

    /**
     * Champ correspondant au rang du client dans l'étape.
     */
    protected int rang;

    /**
     * Champ correspondant à l'étape dans laquelle est le client.
     */
    protected Etape etape;

    /**
     * Constructeur.
     *
     * @param numero Le numéro du client.
     */
    public Client(int numero){
        this.numeroClient = numero;
    }

    /**
     * Getter du numéro du client.
     *
     * @return Le numéro client.
     */
    public int getNumeroClient() {
        return numeroClient;
    }

    /**
     * Méthode permettant au client de se déplacer.
     *
     * @param etape L'étape dans laquelle le client va.
     * @param rang Le rang du client dans l'étape.
     */
    public void allerA(Etape etape, int rang){
        this.etape = etape;
        this.rang = rang;
    }

    /**
     * Getter de l'étape dans laquelle le client est.
     *
     * @return L'étape actuelle du client.
     */
    public Etape getEtape(){
        return this.etape;
    }

    /**
     * Getter du rang du client dans l'étape dans laquelle il est.
     *
     * @return Le rang du client dans l'étape.
     */
    public int getRang(){
        return rang;
    }

    /**
     * Méthode toString.
     *
     * @return Le numéro du client, son rang dans l'étape, son étape actuelle.
     */
    @Override
    public String toString() {
        return "Client{" +
                "numeroClient=" + numeroClient +
                ", rang=" + rang +
                ", etape=" + etape.getNom() +
                '}';
    }
}
