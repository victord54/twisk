package outils;

public class FabriqueNumero {
    private int cptEtape;
    private static final FabriqueNumero instance = new FabriqueNumero();
    private int cptSemaphore;

    private FabriqueNumero() {
    }

    public static FabriqueNumero getInstance() {
        return instance;
    }

    public int getNumeroEtape() {
        cptEtape++;
        return cptEtape - 1;
    }

    public int getNumeroSemaphore() {
        cptSemaphore++;
        return cptSemaphore;
    }

    public void reset() {
        cptEtape = 0;
        cptSemaphore = 0;
    }


}
