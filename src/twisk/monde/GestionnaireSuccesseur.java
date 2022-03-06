package twisk.monde;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class GestionnaireSuccesseur implements Iterable<Etape> {
    private final ArrayList<Etape> etapes;

    public GestionnaireSuccesseur() {
        etapes = new ArrayList<>();
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Etape e : etapes) {
            builder.append(" - ").append(e.getNom());
        }
        
        return builder.toString();
    }
    public Etape getEtape(int i) {
        return etapes.get(i);
    }

    public Etape getSucc(){
        return getEtape(0);
    }


}
