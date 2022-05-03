package twisk.mondeIG;

public class GuichetIG extends EtapeIG {
    private int jetons;
    public GuichetIG(String nom, String idf, int largeur, int hauteur, int jetons) {
        super(nom,idf,largeur,hauteur);
        this.jetons = jetons;
    }

    @Override
    public boolean estUnGuichet(){
        return true;
    }

    public int getJetons() {
        return jetons;
    }

    public void setJetons(int jetons) {
        this.jetons = jetons;
    }
}
