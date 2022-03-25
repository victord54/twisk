/**
 * Classe représentant les outils nécessaire au bon fonctionnement de la simulation.
 *
 * @author Kurth Claire et Dallé Victor
 * @since 02/02/2022
 */

package twisk.simulation;

import twisk.monde.Etape;
import twisk.monde.Monde;
import twisk.outils.KitC;

import java.util.Arrays;

public class Simulation {
    /**
     * Champs représentant le nombre de clients.
     */
    private int nbClients;
    /**
     * Champs représentant les outils pour compiler, exécuter le code c.
     */
    private final KitC kit;

    /**
     * Constructeur par défaut de la classe.
     */
    public Simulation() {
        kit = new KitC();
        kit.creerEnvironnement();
        this.nbClients = 5;
    }

    /**
     * Méthode permettant le démarrage de la simulation (code c).
     *
     * @param nbEtapes         Le nombre d'étapes du monde.
     * @param nbGuichets       Le nombre de guichets du monde.
     * @param nbClients        Le nombre de clients du monde.
     * @param tabJetonsGuichet Le nombre de jetons par guichets.
     * @return Le tableau contenant les clients.
     */
    public native int[] start_simulation(int nbEtapes, int nbGuichets, int nbClients, int[] tabJetonsGuichet);

    /**
     * Méthode qui stoppe les processus.
     */
    public native void nettoyage();

    /**
     * Méthode donnant l'emplacement des clients dans les différentes étapes du monde.
     *
     * @param nbEtapes  Le nombre d'étapes du monde.
     * @param nbClients Le nombre de clients du monde.
     * @return Le tableau contenant l'emplacement des clients en fonction des étapes.
     */
    public native int[] ou_sont_les_clients(int nbEtapes, int nbClients);

    public int[] clientsConcernes(int[] tab, int depart, int arrivee) {
        int[] tabFinal = new int[(arrivee - depart) + 1];
        int n = 0;
        for (int i = depart; i <= arrivee; i++) {
            tabFinal[n] = tab[i];
            n++;
        }
        return tabFinal;
    }

    /**
     * Méthode qui permet de set les nombres de clients
     * @param nb le nombre de clients
     */
    public void setNbClients(int nb){
        this.nbClients = nb;
    }

    /**
     * Méthode lançant la simulation.
     *
     * @param monde Le monde.
     */
    public void simuler(Monde monde) {
        System.out.println(monde.toString());
        kit.creerFichier(monde.toC());
        kit.compiler();
        kit.construireLaLibrairie();
        System.load("/tmp/twisk/libTwisk.so");


        int nb_etapes = monde.nbEtapes() + 1;
        int nb_guichets = monde.nbGuichets();
        int nb_clients = this.nbClients;

        int[] tab_jetons_guichet = new int[nb_guichets];
        int j = 0;

        for (Etape e : monde) {
            if (e.estUnGuichet()) {
                tab_jetons_guichet[j] = e.getNbJetons();
                j++;
            }
        }

        int[] tab = start_simulation(nb_etapes, nb_guichets, nb_clients, tab_jetons_guichet);
        int[] tab_client = ou_sont_les_clients(nb_etapes, nb_clients);

        // Affichage des PID des clients
        System.out.print("les clients : ");
        for (int i = 0; i < nb_clients; i++) {
            System.out.print(tab[i] + " ");
        }
        System.out.println();

        // Affichage des PID des clients par étape
        while (tab_client[(nb_clients + 1)] != nb_clients) {
            tab_client = ou_sont_les_clients(nb_etapes, nb_clients);
//            int decalage = 0;
//            int nb_a_afficher = tab_client[0];
//            for (Etape e : monde) {
//                System.out.print("Etape " + e.getNumEtape() + " " + e.getNom() + " - nb clients : " + nb_a_afficher + " - ");
//                for (int i = decalage + 1; i < decalage + 1 + nb_a_afficher; i++) {
//                    System.out.print(tab_client[i] + " ");
//                }
//                System.out.println();
//                decalage += nb_clients + 1;
//                nb_a_afficher = tab_client[decalage];
//            }
            Etape etape = monde.getSasEntree();
            int decalage = 0;
            int i = nb_etapes - 1;
            while (etape.nbSuccesseurs() >= 1) {
                // Gestion de tout
                if (etape.getNumEtape() != 1) {
                    System.out.println("Etape " + (nb_etapes-i) + " : " + etape.getNom() + " : " + Arrays.toString(clientsConcernes(tab_client, (etape.getNumEtape()*(nb_clients)) + 1 + decalage, (etape.getNumEtape()*(nb_clients)) + 8 + decalage)));
                }
                // L'étape devient son successeur
                etape = etape.getSuccesseur();
                decalage += 0; // Une valeur
                i--;
            }
            System.out.println("Etape " + (nb_etapes-i) + " : " + etape.getNom() + " : " + Arrays.toString(clientsConcernes(tab_client, nb_clients+1, 2*nb_clients+1)));

            try {
                Thread.sleep(1000);
            } catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
        nettoyage();
    }
}
