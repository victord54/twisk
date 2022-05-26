package twisk.outils;

import twisk.mondeIG.PointDeControleIG;

public class FabriqueIdentifiant {
    private int noEtape;
    private int noPointDeControle;
    private int noGuichet;
    private static final FabriqueIdentifiant instance = new FabriqueIdentifiant();

    private FabriqueIdentifiant() {
    }

    public static FabriqueIdentifiant getInstance() {
        return instance;
    }

    public String getIdentifiantGuichet(){
        noGuichet++;
        return "guichet" + (noGuichet - 1);
    }

    public String getIdentifiantEtape() {
        noEtape++;
        return "etape" + (noEtape - 1);
    }

    public String getIdentifiantPointDeControle(PointDeControleIG pt) {
        noPointDeControle++;
        return "" + pt.getIdEtape() + "@pt-ctrl" + (noPointDeControle - 1);
    }

    public void reset() {
        noEtape = 0;
        noPointDeControle = 0;
    }

    public void resetPointDeControle(){
        noPointDeControle = 0;
    }
}
