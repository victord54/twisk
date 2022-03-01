package twisk.monde;

import twisk.outils.FabriqueNumero;

import java.util.Iterator;

public abstract class Etape implements Iterable<Etape> {
    protected int numEtape;
    protected String nom;
    protected GestionnaireSuccesseur gestionnaireSuccesseur;

    public Etape(String nom) {
        this.nom = nom;
        gestionnaireSuccesseur = new GestionnaireSuccesseur();
        numEtape = FabriqueNumero.getInstance().getNumeroEtape();
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

    public boolean estUneSortie() {
        for (Etape etape : gestionnaireSuccesseur) {
            if (etape.getNom().equalsIgnoreCase("sortie")) {
                return true;
            }
        }
        return false;
    }

    public Iterator<Etape> iterator() {
        return gestionnaireSuccesseur.iterator();
    }

    public String getNom(){
        return this.nom;
    }

    public int getNumEtape(){
        return numEtape;
    }

    public String toString(){
        return this.nom + " : " + nbSuccesseurs() + " successeur(s) " + gestionnaireSuccesseur.toString();
    }

    public abstract String toC();


}