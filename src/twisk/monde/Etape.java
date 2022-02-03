package twisk.monde;

import java.util.Iterator;

public abstract class Etape implements Iterable {
    protected String nom;
    protected GestionnaireSuccesseur gestionnaireSuccesseur;

    public Etape(String nom) {
        this.nom = nom;
    }

    public void ajouterSuccesseur(Etape... etapes) {
        gestionnaireSuccesseur.ajouter(etapes);
    }

    public boolean estUneActivite() {
        return false;
    }

    public boolean estUnGuichet() {
        return false;
    }

    public Iterator<Etape> iterator() {
        return gestionnaireSuccesseur.iterator();
    }
}