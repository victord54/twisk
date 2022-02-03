package twisk.monde;

public class Guichet extends Etape {
    private int nbJetons;

    public Guichet(String nom){
        super(nom);
    }

    public Guichet(String nom, int nb){
        super(nom);
        this.nbJetons = nb;
    }

    public boolean estUneActivite(){
        return false;
    }

    public boolean estUnGuichet(){
        return true;
    }
}
