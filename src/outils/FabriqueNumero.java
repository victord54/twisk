package outils;

public class FabriqueNumero {
    private int cptEtape;
    private static FabriqueNumero instance = new FabriqueNumero();

    private FabriqueNumero(){}

    public static FabriqueNumero getInstance() {
        return instance;
    }

    public int getNumeroEtape(){
        cptEtape ++;
        return cptEtape - 1;
    }

    public void reset(){
        cptEtape = 0;
    }


}
