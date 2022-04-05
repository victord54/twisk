package twisk.mondeIG;

import twisk.outils.FabriqueIdentifiant;

public class PointDeControleIG {
    private int posX;
    private int posY;
    private final String id;
    private final EtapeIG etape;

    public PointDeControleIG(int x, int y, EtapeIG etape) {
        posX = x;
        posY = y;
        this.etape = etape;
        id = FabriqueIdentifiant.getInstance().getIdentifiantPointDeControle(this);
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public String getId() {
        return id;
    }

    public String getIdEtape() {
        return etape.getId();
    }

    public void relocate(int x, int y) {
        posX = x;
        posY = y;
    }

    @Override
    public String toString() {
        return "PointDeControleIG{" +
                "posX=" + posX +
                ", posY=" + posY +
                ", id='" + id + '\'' +
                ", etape=" + etape +
                '}';
    }
}
