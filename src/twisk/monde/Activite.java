package twisk.monde;

public class Activite extends Etape{
    private int temps;
    private int ecartTemps;

    public Activite(String nom){
        super(nom);
    }

    public Activite(String nom, int t, int e){
        super(nom);
        this.temps = t;
        this.ecartTemps = e;

    }


    public boolean estUneActivite(){
        return true;
    }

    public boolean estUnGuichet(){
        return false;
    }
}
