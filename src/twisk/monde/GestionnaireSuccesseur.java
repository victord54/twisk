/**
 * Classe représentant le gestionnaire des successeurs des étapes.
 *
 * @author Kurth Claire et Dallé Victor
 * @since 02/02/2022
 */

package twisk.monde;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class GestionnaireSuccesseur implements Iterable<Etape> {
    /**
     * ArrayList contenant les successeurs d'une étape.
     */
    private final ArrayList<Etape> etapes;

    /**
     * Constructeur de la classe.
     */
    public GestionnaireSuccesseur() {
        etapes = new ArrayList<>();
    }

    /**
     * Getter qui donne le nombre de successeurs de l'étape.
     *
     * @return Le nombre de successeurs.
     */
    public int nbEtapes() {
        return etapes.size();
    }

    /**
     * Getter qui donne un successeur.
     *
     * @param i Indice de l'étape à renvoyer.
     * @return L'étape à l'indice i.
     */
    public Etape getEtape(int i) {
        return etapes.get(i);
    }

    /**
     * Getter qui donne le premier successeur.
     *
     * @return Le 1er successeur.
     */
    public Etape getSucc() {
        return getEtape(0);
    }

    /**
     * Méthode qui permet d'ajouter des étapes au gestionnaire.
     *
     * @param etapes Les successeurs.
     */
    public void ajouter(Etape... etapes) {
        this.etapes.addAll(Arrays.asList(etapes));
    }

    /**
     * Méthode qui définie un nouvel itérateur pour le gestionnaire de successeurs.
     *
     * @return Le nouvel itérateur.
     */
    @Override
    public Iterator<Etape> iterator() {
        return etapes.iterator();
    }

    /**
     * Méthode toString()
     *
     * @return Les successeurs de l'étape concernée.
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Etape e : etapes) {
            builder.append(" - ").append(e.getNom());
        }

        return builder.toString();
    }
}
