package twisk.outils;

public class TailleComposants {
    final int largeurActivite;
    final int hauteurActivite;
    private static final TailleComposants instance = new TailleComposants();

    private TailleComposants() {
        largeurActivite = 220;
        hauteurActivite = 120;
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
}
