package twisk.mondeIG;

public class GuichetIG extends EtapeIG {

    public GuichetIG(String nom, String idf, int largeur, int hauteur){
        super(nom,idf,largeur,hauteur);
    }

    public boolean estUnGuichet(){
        return true;
    }
}
