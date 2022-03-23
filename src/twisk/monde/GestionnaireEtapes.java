/**
 * Classe représentant le gestionnaire des étapes du monde.
 *
 * @author Kurth Claire et Dallé Victor
 * @since 02/02/2022
 */
package twisk.monde;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class GestionnaireEtapes implements Iterable<Etape> {
    /**
     * ArrayList contenant les étapes du monde.
     */
    private final ArrayList<Etape> etapes;

    /**
     * Constructeur de la classe
     */
    public GestionnaireEtapes() {
        etapes = new ArrayList<>();
    }

    /**
     * Getter donnant le nombre d'étapes dans le gestionnaire.
     *
     * @return Le nombre d'étapes dans le gestionnaire d'étapes.
     */
    public int nbEtapes() {
        return etapes.size();
    }

    /**
     * Getter d'une étape du gestionnaire d'étapes.
     *
     * @param i Indice de l'étape à renvoyer.
     * @return L'étape à l'indice i.
     */
    public Etape getEtape(int i) {
        return etapes.get(i);
    }

    /**
     * Méthode qui permet d'ajouter des étapes au gestionnaire.
     *
     * @param etapes Les étapes à ajouter.
     */
    public void ajouter(Etape... etapes) {
        this.etapes.addAll(Arrays.asList(etapes));
    }

    /**
     * Méthode définissant un nouvel itérateur pour le gestionnaire d'étapes.
     *
     * @return Le nouvel itérateur.
     */
    @Override
    public Iterator<Etape> iterator() {
        return etapes.iterator();
    }

    /**
     * Méthode toString().
     *
     * @return Les étapes que contient le gestionnaire.
     */
    @Override
    public String toString() {
        StringBuilder bd = new StringBuilder();
        for (Etape e : etapes) {
            bd.append(e.toString()).append("\n");
        }
        return bd.toString();
    }
}