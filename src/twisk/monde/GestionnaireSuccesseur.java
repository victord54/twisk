package twisk.monde;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class GestionnaireSuccesseur {
    private ArrayList<Etape> etapes;

    public GestionnaireSuccesseur(ArrayList<Etape> etapes) {
        this.etapes = etapes;
    }

    public void ajouter(Etape... etapes) {
        this.etapes.addAll(Arrays.asList(etapes));
    }

    public int nbEtapes() {
        return etapes.size();
    }

    public Iterator<Etape> iterator() {
        return etapes.iterator();
    }
}
