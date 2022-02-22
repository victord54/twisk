package twisk.monde;

import java.util.Iterator;

public class Monde implements Iterable<Etape> {
    protected SasEntree entree;
    protected SasSortie sortie;
    protected GestionnaireEtapes gestionnaireEtape;


    public Monde() {
        sortie = new SasSortie();
        entree = new SasEntree();
        gestionnaireEtape = new GestionnaireEtapes();
        gestionnaireEtape.ajouter(entree,sortie);
    }

    public void aCommeEntree(Etape... etapes) {
        entree.ajouterSuccesseur(etapes);
    }

    public void aCommeSortie(Etape... etapes) {
        for (Etape e : etapes) {
            e.ajouterSuccesseur(sortie);
        }
    }

    public void ajouter(Etape... etapes) {
        gestionnaireEtape.ajouter(etapes);

    }

    public int nbEtapes() {
        return gestionnaireEtape.nbEtapes();
    }

    public int nbGuichets() {
        int n = 0;
        for (Etape etape : gestionnaireEtape) {
            if (etape.estUnGuichet())
                n++;
        }
        return n;
    }

    public Etape getSasSortie(){
        return sortie;
    }

    public Etape getSasEntree(){
        return entree;
    }

    public Iterator<Etape> iterator() {
        return gestionnaireEtape.iterator();
    }

    public String toString(){
        return gestionnaireEtape.toString();
    }

    public String toC(){
        return null;
    }
}
