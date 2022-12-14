/**
 * Classe représentant le monde.
 *
 * @author Kurth Claire et Dallé Victor
 * @since 02/02/2022
 */

package twisk.monde;

import twisk.outils.FabriqueNumero;
import twisk.simulation.GestionnaireClients;

import java.util.Iterator;

public class Monde implements Iterable<Etape> {
    /**
     * Champ correspondant au numéro du monde.
     */
    protected int numMonde;
    /**
     * Champ contenant le sas d'entrée du monde.
     */
    protected SasEntree entree;

    /**
     * Champ contenant le sas de sortie du monde.
     */
    protected SasSortie sortie;

    /**
     * Champ contenant le gestionnaire d'étapes du monde.
     */
    protected GestionnaireEtapes gestionnaireEtapes;

    /**
     * Constructeur par défaut de la classe.
     */
    public Monde() {
        entree = new SasEntree();
        sortie = new SasSortie();
        gestionnaireEtapes = new GestionnaireEtapes();
        gestionnaireEtapes.ajouter(entree, sortie);
        numMonde = FabriqueNumero.getInstance().getNumeroMonde();

    }

    /**
     * Setter permettant de set la Loi en cours.
     *
     * @param loi La loi à set (uniforme, gausienne, exponentiel)
     */
    public void setLoi(String loi){
        this.entree.setLoi(loi);
    }

    /**
     * Getter donnant le nombre d'étapes du monde.
     *
     * @return Nombre d'étapes du monde.
     */
    public int nbEtapes() {
        return gestionnaireEtapes.nbEtapes();
    }

    /**
     * Getter donnant le nombre de guichets du monde.
     *
     * @return Nombre de guichets du monde.
     */
    public int nbGuichets() {
        int n = 0;
        for (Etape etape : gestionnaireEtapes) {
            if (etape.estUnGuichet())
                n++;
        }
        return n;
    }

    /**
     * Getter donnant le sas d'entrée du monde.
     *
     * @return Sas d'entrée.
     */
    public Etape getSasEntree() {
        return entree;
    }

    /**
     * Getter donnant le sas de sortie du monde.
     *
     * @return Sas de sortie.
     */
    public Etape getSasSortie() {
        return sortie;
    }

    /**
     * Méthode définissant les points d'entrée du monde.
     *
     * @param etapes Les étapes d'entrée.
     */
    public void aCommeEntree(Etape... etapes) {
        entree.ajouterSuccesseur(etapes);
    }

    /**
     * Méthode définissant les points de sortie du monde.
     *
     * @param etapes Les étapes de sortie.
     */
    public void aCommeSortie(Etape... etapes) {
        for (Etape e : etapes) {
            e.ajouterSuccesseur(sortie);
        }
    }

    /**
     * Méthode ajoutant des étapes au monde.
     *
     * @param etapes Les étapes à ajouter au monde.
     */
    public void ajouter(Etape... etapes) {
        gestionnaireEtapes.ajouter(etapes);
    }

    /**
     * Méthode définissant un nouvel itérateur sur le monde.
     *
     * @return Itérateur sur les étapes.
     */
    public Iterator<Etape> iterator() {
        return gestionnaireEtapes.iterator();
    }

    /**
     * Méthode toString().
     *
     * @return Les étapes constituant le monde..
     */
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Etape etape: gestionnaireEtapes) {
            stringBuilder.append(etape.toString()).append("\n");
        }
        return stringBuilder.toString();
    }

    /**
     * Méthode définissant le code c à ajouter pour le monde.
     *
     * @return Le code c.
     */
    public String toC() {
        StringBuilder str = new StringBuilder();
        Etape etape = entree;
        str.append(etape.toC());
        return str.toString();
    }

    /**
     * Getter permettant de récupérer l'étape dont l'indice est passé en paramètre.
     *
     * @param i i-ème étape à récupérer.
     * @return La i-ème étape.
     */
    public Etape getEtape(int i){
        return this.gestionnaireEtapes.getEtape(i);
    }

    /**
     * Getter du numéro du monde.
     *
     * @return Le numéro du monde.
     */
    public int getNumMonde(){
        return this.numMonde;
    }

    /**
     * Méthode permettant de savoir si le Monde contient l'étape passée en paramètre.
     *
     * @param e L'étape dont on veut savoir si elle fait partie au Monde.
     * @return true si l'étape fait partie du Monde, False si non.
     */
    public boolean contient(Etape e){
        return gestionnaireEtapes.contient(e);
    }


}
