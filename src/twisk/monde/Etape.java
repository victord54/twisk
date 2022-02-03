package twisk.monde;

import java.util.Iterator;

public abstract class Etape implements Iterable {
    protected String nom;
    protected GestionnaireSuccesseur gestionnaireSuccesseur;

    public Etape(String nom) {
        this.nom = nom;
        gestionnaireSuccesseur = new GestionnaireSuccesseur();
    }

    public void ajouterSuccesseur(Etape... etapes) {
        gestionnaireSuccesseur.ajouter(etapes);
    }

    public int nbSuccesseurs() {
        return gestionnaireSuccesseur.nbEtapes();
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

    public String toString(){
        return this.nom;
    }
}