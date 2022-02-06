package twisk.monde;

public class Activite extends Etape {
    protected int temps;
    protected int ecartTemps;

    public Activite(String nom){
        super(nom);
    }
    public Activite(String nom, int nbEtape) {
        super(nom, nbEtape);
    }

    public Activite(String nom, int t, int e) {
        super(nom);
        this.temps = t;
        this.ecartTemps = e;
    }

    @Override
    public boolean estUneActivite() {
        return true;
    }

}