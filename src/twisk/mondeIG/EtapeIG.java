/**
 * Classe abstraite représentant une étape du mondeIG.
 *
 * @author Victor Dallé et Claire Kurth
 */

package twisk.mondeIG;

import twisk.outils.FabriqueIdentifiant;
import twisk.outils.TailleComposants;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public abstract class EtapeIG implements Iterable<PointDeControleIG> {
    /**
     * Champ correspondant au nom de l'étape.
     */
    protected String nom;

    /**
     * Champ correspondant à l'identifiant de l'étape.
     */
    protected final String identifiant;

    /**
     * Champ correspondant à la position sur l'axe des abscisses de l'étape.
     */
    protected int posX;

    /**
     * Champ correspondant à la position sur l'axe des ordonnées de l'étape.
     */
    protected int posY;

    /**
     * Champ correspondant à la largeur de l'étape.
     */
    protected final int largeur;

    /**
     * Champ correspondant à la hauteur de l'étape.
     */
    protected final int hauteur;

    /**
     * ArrayList contenant les points de contrôles de l'étapes.
     */
    protected final ArrayList<PointDeControleIG> controles;

    /**
     * ArrayList contenant les étapes successeures de l'étape.
     */
    protected final ArrayList<EtapeIG> successeurs;

    /**
     * Constructeur de l'étape.
     *
     * @param nom Nom de l'étape.
     * @param idf Identifiant de l'étape.
     * @param largeur Largeur de l'étape.
     * @param hauteur Hauteur de l'étape.
     */
    public EtapeIG(String nom, String idf, int largeur, int hauteur) {
        Random rd = new Random();
        controles = new ArrayList<>();
        this.nom = nom;
        identifiant = idf;
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.posX = rd.nextInt(1000 - TailleComposants.getInstance().getLargeurActivite());
        this.posY = rd.nextInt(650 - TailleComposants.getInstance().getHauteurActivite());


        if (this.estUneActivite()){
            /* Pt de contrôle centre-haut */
            controles.add(new PointDeControleIG(posX + largeur / 2, posY, this));

            /* Pt de contrôle centre-bas */
            controles.add(new PointDeControleIG(posX + largeur / 2, posY + hauteur, this));
        }

        /* Pt de contrôle centre-gauche */
        controles.add(new PointDeControleIG(posX, posY + hauteur / 2, this));

        /* Pt de contrôle centre-droit */
        controles.add(new PointDeControleIG(posX + largeur, posY + hauteur / 2, this));

        successeurs = new ArrayList<>();

        FabriqueIdentifiant.getInstance().resetPointDeControle();
    }

    /**
     * Getter du nom de l'étape.
     *
     * @return Le nom.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Getter de la position sur l'axe des abscisses de l'étape.
     *
     * @return La position sur l'axe des abscisses.
     */
    public int getPosX() {
        return posX;
    }

    /**
     * Getter de la position sur l'axe des ordonnées de l'étape.
     *
     * @return La position sur l'axe des ordonnées.
     */
    public int getPosY() {
        return posY;
    }

    /**
     * Getter de la largeur de l'étape.
     *
     * @return La largeur.
     */
    public int getLargeur() {
        return largeur;
    }

    /**
     * Getter de la hauteur de l'étape.
     *
     * @return La hauteur.
     */
    public int getHauteur() {
        return hauteur;
    }

    /**
     * Getter de l'identifiant de l'étape.
     *
     * @return L'identifiant.
     */
    public String getId() {
        return identifiant;
    }

    /**
     * Setter du nom de l'étape.
     *
     * @param s Le nouveau nom de l'étape.
     */
    public void setNom(String s) {
        nom = s;
    }

    /**
     * Méthode permettant de déplacer une étape à de nouvelles coordonnées.
     *
     * @param x La nouvelle coordonnée de l'axe des abscisses.
     * @param y La nouvelle coordonnée de l'axe des ordonnées.
     */
    public void relocate(int x, int y) {
        posX = x;
        posY = y;
    }

    /**
     * Méthode permettant d'actualiser l'emplacement des points de contrôles de l'étape en fonction de ses coordonnées.
     */
    public void actualiserPointsDeControle() {
        if (this.estUneActivite()){
            /* Pt de contrôle centre-haut */
            controles.get(0).relocate(posX + largeur / 2, posY);

            /* Pt de contrôle centre-bas */
            controles.get(1).relocate(posX + largeur / 2, posY + hauteur);
            /* Pt de contrôle centre-gauche */
            controles.get(2).relocate(posX, posY + hauteur / 2);

            /* Pt de contrôle centre-droit */
            controles.get(3).relocate(posX + largeur, posY + hauteur / 2);
        }
        else {
            /* Pt de contrôle centre-gauche */
            controles.get(0).relocate(posX, posY + hauteur / 2);

            /* Pt de contrôle centre-droit */
            controles.get(1).relocate(posX + largeur, posY + hauteur / 2);
        }


    }

    /**
     * Méthode permettant d'ajouter comme successeurs les étapes passées en paramètres.
     *
     * @param etapes Les successeurs de l'étape.
     */
    public void ajouterSucesseur(EtapeIG... etapes) {
        successeurs.addAll(List.of(etapes));
    }

    /**
     * Méthode permettant de retirer des successeurs les étapes passées en paramètres.
     *
     * @param etapes Les étapes à retirer des successeurs.
     */
    public void retirerSucesseur(EtapeIG ... etapes) {
        successeurs.removeAll(List.of(etapes));
    }

    /**
     * Getter de la liste des successeurs de l'étape.
     *
     * @return La liste des successeurs de l'étape.
     */
    public ArrayList<EtapeIG> getSuccesseurs(){
        return successeurs;
    }

    /**
     * Méthode toString().
     *
     * @return Le nom, l'identifiant, les coordonnées, la largeur et la hauteur de l'étape.
     */
    @Override
    public String toString() {
        return "EtapeIG{" + "nom='" + nom + '\'' + ", identifiant='" + identifiant + '\'' + ", posX=" + posX + ", posY=" + posY + ", largeur=" + largeur + ", hauteur=" + hauteur + '}';
    }

    /**
     * Méthode définissant un nouvel itérateur sur l'étape.
     *
     * @return Un itérator sur les points de contrôles.
     */
    @Override
    public Iterator<PointDeControleIG> iterator() {
        return controles.iterator();
    }

    /**
     * Méthode permettant de savoir si l'étape est un guichet.
     *
     * @return Toujours false.
     */
    public boolean estUnGuichet() {
        return false;
    }

    /**
     * Méthode permettant de savoir si l'étape est une activité.
     *
     * @return Toujours false.
     */
    public boolean estUneActivite() {
        return false;
    }

    /**
     * Méthode permettant de savoir si l'étape est une activité restreinte.
     *
     * @return Toujours false.
     */
    public boolean estUneActiviteRestreinte() {
        return false;
    }

    /**
     * Méthode permettant de savoir si l'étape est accessible depuis l'étape passée en paramètre.
     *
     * @param etape L'étape depuis laquelle on veut savoir si l'objet est accessible.
     * @return true si l'objet est accesible depuis l'étape en paramètre, false si non.
     */
    public boolean estAccessibleDepuis(EtapeIG etape){
        for (EtapeIG e : etape.getSuccesseurs()){
            if (e.getSuccesseurs().contains(this)){
                return true;
            }
            return this.estAccessibleDepuis(e);
        }

        return false;
    }

    /**
     * Méthode permettant de récupérer le point de contrôle correspondant à l'identifiant passé en paramètre.
     *
     * @param id Identifiant du point de contrôle que l'on veut récupérer.
     * @return Le point de contrôle correspondant à l'identifiant passé en paramètre.
     */
    public PointDeControleIG getPointDeControle(String id){
        for (PointDeControleIG pdt : controles){
            if (pdt.getId().equals(id)){
                return pdt;
            }
        }
        return null;
    }
}
