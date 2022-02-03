package twisk.monde;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class GestionnaireSuccesseur implements Iterable {
    private ArrayList<Etape> etapes;

    public GestionnaireSuccesseur() {
        etapes = new ArrayList<Etape>();
    }

    public void ajouter(Etape... etapes) {
        this.etapes.addAll(Arrays.asList(etapes));
    }

    public int nbEtapes() {
        return etapes.size();
    }

    @Override
    public Iterator iterator() {
        return etapes.iterator();
    }
}
