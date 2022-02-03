package twisk.monde;

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
}