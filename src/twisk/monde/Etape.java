package twisk.monde;

import java.util.Iterator;

public abstract class Etape implements Iterable {
    protected String nom;
    protected GestionnaireSuccesseur gestionnaireSuccesseur;

    public Etape(String nom) {
        this.nom = nom;
    }

    public void ajouterSuccesseur(Etape... etapes) {

    }

    public abstract boolean estUneActivite();

    public abstract boolean estUnGuichet();

    public Iterator<Etape> iterator() {
        return gestionnaireSuccesseur.iterator();
    }
}