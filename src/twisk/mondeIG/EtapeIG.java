package twisk.mondeIG;

import twisk.outils.TailleComposants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public abstract class EtapeIG implements Iterable<PointDeControleIG> {
    protected String nom;
    protected final String identifiant;
    protected int posX;
    protected int posY;
    protected final int largeur;
    protected final int hauteur;
    protected final ArrayList<PointDeControleIG> controles;

    public EtapeIG(String nom, String idf, int largeur, int hauteur) {
        Random rd = new Random();
        controles = new ArrayList<>();
        this.nom = nom;
        identifiant = idf;
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.posX = rd.nextInt(1000 - TailleComposants.getInstance().getLargeurActivite());
        this.posY = rd.nextInt(650 - TailleComposants.getInstance().getHauteurActivite());

        /* Pt de contrôle centre-haut */
        controles.add(new PointDeControleIG(posX + largeur / 2, posY, this));

        /* Pt de contrôle centre-bas */
        controles.add(new PointDeControleIG(posX + largeur / 2, posY + hauteur, this));

        /* Pt de contrôle centre-gauche */
        controles.add(new PointDeControleIG(posX, posY + hauteur / 2, this));

        /* Pt de contrôle centre-droit */
        controles.add(new PointDeControleIG(posX + largeur, posY + hauteur / 2, this));
    }

    public String getNom() {
        return nom;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getLargeur() {
        return largeur;
    }

    public int getHauteur() {
        return hauteur;
    }

    public String getId() {
        return identifiant;
    }

    public void setNom(String s) {
        nom = s;
    }

    public void relocate(int x, int y) {
        posX = x;
        posY = y;
    }

    public void actualiserPointsDeControle() {
        /* Pt de contrôle centre-haut */
        controles.get(0).relocate(posX + largeur / 2, posY);

        /* Pt de contrôle centre-bas */
        controles.get(1).relocate(posX + largeur / 2, posY + hauteur);

        /* Pt de contrôle centre-gauche */
        controles.get(2).relocate(posX, posY + hauteur / 2);

        /* Pt de contrôle centre-droit */
        controles.get(3).relocate(posX + largeur, posY + hauteur / 2);
    }

    @Override
    public String toString() {
        return "EtapeIG{" + "nom='" + nom + '\'' + ", identifiant='" + identifiant + '\'' + ", posX=" + posX + ", posY=" + posY + ", largeur=" + largeur + ", hauteur=" + hauteur + '}';
    }

    @Override
    public Iterator<PointDeControleIG> iterator() {
        return controles.iterator();
    }
}
