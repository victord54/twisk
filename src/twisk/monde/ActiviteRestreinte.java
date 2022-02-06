package twisk.monde;

public class ActiviteRestreinte extends Activite {

    public ActiviteRestreinte(String nom) {
        super(nom);
    }

    public ActiviteRestreinte(String nom, int nbEtape){
        super(nom,nbEtape);
    }

    public ActiviteRestreinte(String nom, int t, int e) {
        super(nom,t,e);
    }
}
