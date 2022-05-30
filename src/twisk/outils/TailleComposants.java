package twisk.outils;

public class TailleComposants {
    final int largeurActivite;
    final int hauteurActivite;
    final int radiusClient;

    final int hauteurGuichet;
    private static final TailleComposants instance = new TailleComposants();

    private TailleComposants() {
        largeurActivite = 220;
        hauteurActivite = 120;
        radiusClient = 10;
        hauteurGuichet = 80;
    }

    public static TailleComposants getInstance() {
        return instance;
    }

    public int getLargeurActivite() {
        return largeurActivite;
    }

    public int getHauteurActivite() {
        return hauteurActivite;
    }

    public int getRadiusClient() {
        return radiusClient;
    }

    public int getHauteurGuichet(){
        return hauteurGuichet;
    }
}
