package twisk.monde;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class GestionnaireSuccesseur implements Iterable<Etape> {
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
    public Iterator<Etape> iterator() {
        return etapes.iterator();
    }

    public Etape getSucc(int i){
        return etapes.get(i);
    }
}
