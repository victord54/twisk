/**
 * Classe représentant le mondeIG.
 *
 * @author Victor Dallé et Claire Kurth
 */
package twisk.mondeIG;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;
import javafx.application.Platform;
import javafx.scene.control.TextInputDialog;
import twisk.simulation.ClientTwisk;
import twisk.exceptions.*;
import twisk.monde.Activite;
import twisk.monde.ActiviteRestreinte;
import twisk.monde.Guichet;
import twisk.monde.Monde;
import twisk.outils.CorrespondanceEtapes;
import twisk.outils.FabriqueIdentifiant;
import twisk.outils.TailleComposants;
import twisk.simulation.Client;
import twisk.simulation.GestionnaireClients;
import twisk.vues.Observateur;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Optional;


public class MondeIG extends SujetObserve implements Iterable<EtapeIG>, Observateur {
    /**
     * Champ correspondant aux étapesIG du mondeIG.
     */
    private final HashMap<String, EtapeIG> etapesIG;

    /**
     * Champ correspondant au singleton définissant la taille des composants.
     */
    private final TailleComposants tailleComposants = TailleComposants.getInstance();

    /**
     * Champ correspondant aux arcs du mondeIG.
     */
    private final ArrayList<ArcIG> arcs;

    /**
     * Champ correspondant aux points de contrôles sélectionnés.
     */
    private final ArrayList<PointDeControleIG> pointsControleSelectionnes;

    /**
     * Champ correspondant aux étapes sélectionnées.
     */
    private final ArrayList<EtapeIG> etapesSelectionnees;

    /**
     * Champ correspondant aux arcs sélectionnées.
     */
    private final ArrayList<ArcIG> arcsSelectionnees;

    /**
     * Champ correspondant aux entrées du mondeIG.
     */
    private final ArrayList<EtapeIG> entrees;

    /**
     * Champ correspondant aux sorties du mondeIG.
     */
    private final ArrayList<EtapeIG> sorties;

    /**
     * Champ correspondant au gestionnaire des clients du mondeIG.
     */
    private GestionnaireClients gestionnaireClients;

    /**
     * Champ correspondant à la correspondance des étapes entre MondeIG et Monde.
     */
    private CorrespondanceEtapes correspondanceEtapes;

    /**
     * Champ correspondant au nombre de clients du mondeIG.
     */
    private int nbClients;

    /**
     * Champ correspondant au sens de circulation des clients dans le mondeIG.
     */
    private String sensCircu;

    /**
     * Champ correspondant à la loi d'arrivée des clients en cours dans le mondeIG.
     */
    private String loi;

    /**
     * Champ correspondant au fait que la simulation est en cours ou non.
     */
    private boolean simEnCours;

    /**
     * Constructeur par défaut.
     */
    public MondeIG() {
        etapesIG = new HashMap<>();
        arcs = new ArrayList<>();
        pointsControleSelectionnes = new ArrayList<>();
        etapesSelectionnees = new ArrayList<>();
        arcsSelectionnees = new ArrayList<>();
        entrees = new ArrayList<>();
        sorties = new ArrayList<>();
        this.ajouter("Activité");
        gestionnaireClients = null;
        nbClients = 5;
        simEnCours = false;
        loi = "uniforme";
        sensCircu = null;
    }

    /**
     * Setter de la loi d'arrivée des clients du mondeIG.
     *
     * @param l La nouvelle loi.
     */
    public void setLoi(String l) {
        this.loi = l;
        notifierObservateurs();
    }

    /**
     * Getter du sens de circulation des clients dans le monde.
     *
     * @return Le sens de circulation.
     */
    public String getSensCircu() {
        return sensCircu;
    }

    /**
     * Méthode permettant de récupérer l'étape correspondant à l'id passé en paramètre.
     *
     * @param id L'identifiant de l'étape à récupérer.
     * @return L'étape correspondante.
     */
    public EtapeIG getEtape(String id) {
        return etapesIG.get(id);
    }

    /**
     * Getter des points de contrôles sélectionnés.
     *
     * @return La liste des points de contrôles sélectionnés.
     */
    public ArrayList<PointDeControleIG> getPointsControleSelectionnes() {
        return pointsControleSelectionnes;
    }

    /**
     * Getter des étapesIG sélectionnées.
     *
     * @return La liste des étapesIG sélectionnées.
     */
    public ArrayList<EtapeIG> getEtapesSelectionnees() {
        return etapesSelectionnees;
    }

    /**
     * Méthode permettant de savoir si le mondeIG possède des arcs ou non.
     *
     * @return true si la liste des arcs est vide, false si non.
     */
    public boolean arcIsEmpty() {
        return arcs.isEmpty();
    }

    /**
     * Getter des arcs sélectionnées.
     *
     * @return La liste des arcs sélectionnées.
     */
    public ArrayList<ArcIG> getArcsSelectionnees() {
        return arcsSelectionnees;
    }

    /**
     * Getter des entrées du mondeIG.
     *
     * @return La liste des entrées.
     */
    public ArrayList<EtapeIG> getEntrees() {
        return entrees;
    }

    /**
     * Getter des sorties du mondeIG.
     *
     * @return La liste des sorties.
     */
    public ArrayList<EtapeIG> getSorties() {
        return sorties;
    }

    /**
     * Méthode permettant de savoir si la simulation est en cours.
     *
     * @return true si la simulation est en cours, false si non.
     */
    public boolean isSimEnCours() {
        return simEnCours;
    }

    /**
     * Setter de la simulation en cours (true si en cours, false si non).
     *
     * @param b La nouvelle valeur de la simulation.
     */
    public void setSimEnCours(boolean b) {
        simEnCours = b;
        notifierObservateurs();
    }

    /**
     * Méthode permettant d'ajouter une nouvelle étape aux étapes du mondeIG.
     *
     * @param type Le type d'étape que l'on veut ajouter (Activité ou Guichet).
     */
    public void ajouter(String type) {
        String id = FabriqueIdentifiant.getInstance().getIdentifiantEtape();
        if (type.equals("Activité") || type.equals("Activite"))
            etapesIG.put(id, new ActiviteIG("Activité", id, tailleComposants.getLargeurActivite(), tailleComposants.getHauteurActivite()));
        if (type.equals("Guichet"))
            etapesIG.put(id, new GuichetIG("Guichet", id, tailleComposants.getLargeurActivite(), tailleComposants.getHauteurGuichet(), 2));

        notifierObservateurs();
    }

    /**
     * Méthode permettant d'ajouter une étapeIG au monde IG.
     *
     * @param e L'étape à ajouter au mondeIG.
     */
    public void ajouter(EtapeIG e) {
        String id = FabriqueIdentifiant.getInstance().getIdentifiantEtape(); //Pour que la fabrique soit au même stade que le nombre d'étapes.
        etapesIG.put(e.getId(), e);
    }

    /**
     * Méthode permettant d'ajouter un nouvel arc au mondeIG.
     *
     * @param pt1 Le point de contrôle de départ de l'arc.
     * @param pt2 Le point de contrôle d'arrivé de l'arc.
     * @throws ArcTwiskException si le sens de circulation n'est pas respecté ou si l'arc entraine un cycle.
     */
    public void ajouterArc(PointDeControleIG pt1, PointDeControleIG pt2) throws ArcTwiskException {
        EtapeIG et1 = pt1.getEtape();
        EtapeIG et2 = pt2.getEtape();

        if (sensCircu == null) {
            if (et2.estUnGuichet()) {
                if (pt2.getId().contains("0")) {
                    sensCircu = "gaucheVersDroite";
                } else {
                    sensCircu = "droiteVersGauche";
                }
            } else {
                if (et1.estUnGuichet()) {
                    if (pt1.getId().contains("0")) {
                        sensCircu = "droiteVersGauche";
                    } else {
                        sensCircu = "gaucheVersDroite";
                    }
                }
            }
        }

        if (!et1.estAccessibleDepuis(et2)) {
            if (sensCircu != null) {
                if (sensCircu.equalsIgnoreCase("gaucheVersDroite")) {
                    if (et2.estUnGuichet() && !pt2.getId().contains("ctrl0")) { //L'arc ne vient pas du pt gauche
                        throw new ArcTwiskException("Le sens de circulation du guichet n'est pas bon ! Il est actuellement de la gauche vers la droite.\nL'arc doit donc arrivé à gauche.");
                    }
                    if (et1.estUnGuichet() && pt1.getId().contains("ctrl0")) {//L'arc ne part pas du pt droit
                        throw new ArcTwiskException("Le sens de circulation du guichet n'est pas bon ! Il est actuellement de la gauche vers la droite.\nL'arc doit donc partir à droite.");
                    }
                }
                if (sensCircu.equalsIgnoreCase("droiteVersGauche")) {
                    if (et2.estUnGuichet() && !pt2.getId().contains("ctrl1")) { //L'arc ne vient pas du pt gauche
                        throw new ArcTwiskException("Le sens de circulation du guichet n'est pas bon ! Il est actuellement de la droite vers la gauche.\nL'arc doit donc arrivé à droite.");
                    }
                    if (et1.estUnGuichet() && pt1.getId().contains("ctrl1")) {//L'arc ne part pas du pt gauche
                        System.out.println(pt1.getId());
                        throw new ArcTwiskException("Le sens de circulation du guichet n'est pas bon ! Il est actuellement de la droite vers la gauche.\nL'arc doit donc partir à gauche.");
                    }
                }
            }
            arcs.add(new ArcIG(pt1, pt2));
        } else {
            throw new ArcTwiskException("Impossible de créer un arc ici, cela fait un cycle ! ");
        }
        notifierObservateurs();
    }

    /**
     * Méthode permettant d'ajouter des points de contrôles à la liste des points de contrôles sélectionnés et qui créé un arc lorsque celle ci en contient 2.
     *
     * @param c Le point de contrôle à ajouter.
     * @throws ArcTwiskException Si 2 points de contrôles appartiennent à la même étape ou si un arc existe déjà entre ces 2 points de contrôles.
     */
    public void ajouterPointDeControle(PointDeControleIG c) throws ArcTwiskException {
        /* Ajout des Points de contrôle dans controleTmp pour créer l'arc */
        if (pointsControleSelectionnes.isEmpty()) {
            pointsControleSelectionnes.add(c);
        } else {
            if (pointsControleSelectionnes.get(0).getIdEtape().equals(c.getIdEtape())) {
                throw new ArcTwiskException("Un arc ne peut pas être construit sur la même étape.");
            } else {
                pointsControleSelectionnes.add(c);
            }
        }

        /* Ajout de l'arc */
        if (pointsControleSelectionnes.size() == 2) {
            PointDeControleIG pt1 = pointsControleSelectionnes.get(0);
            PointDeControleIG pt2 = pointsControleSelectionnes.get(1);
            pointsControleSelectionnes.clear();

            if (arcs.isEmpty()) {
                ajouterArc(pt1, pt2);
            } else {
                for (ArcIG arc : arcs) {
                    if (arc.isDoublon(pt1, pt2)) {
                        throw new ArcTwiskException("Un arc à déjà été ajouté sur ces 2 points de contrôle.");
                    }
                }
                ajouterArc(pt1, pt2);
            }
        }
        notifierObservateurs();
    }

    /**
     * Méthode permettant de reset les données du mondeIG.
     */
    public void reset() {
        etapesIG.clear();
        arcs.clear();
        pointsControleSelectionnes.clear();
        entrees.clear();
        sorties.clear();
        FabriqueIdentifiant.getInstance().reset();
        sensCircu = null;
        notifierObservateurs();
    }

    /**
     * Méthode permettant de retirer le dernier arc ajouté au mondeIG.
     */
    public void retirerDernierArc() {
        arcs.get(arcs.size() - 1).getPt1().getEtape().retirerSucesseur(arcs.get(arcs.size() - 1).getPt2().getEtape());
        arcs.remove(arcs.size() - 1);

        resetSensCircu();
        notifierObservateurs();
    }

    /**
     * Méthode permettant de reset le sens de circulation si il n'y a plus d'arcs avec un guichet.
     */
    private void resetSensCircu() {
        boolean arcGuichet = true;
        for (ArcIG arc : arcs) {
            if (arc.getPt1().getEtape().estUnGuichet() || arc.getPt2().getEtape().estUnGuichet()) {
                arcGuichet = false;
                break;
            }
        }
        if (arcGuichet) {
            sensCircu = null;
        }
    }

    /**
     * Méthode permettant d'ajouter une étape passée en paramètre à la liste des étapes séléctionnées.
     *
     * @param etape L'étape sélectionnée.
     */
    public void selectionnerEtape(EtapeIG etape) {
        if (etapesSelectionnees.isEmpty()) {
            etapesSelectionnees.add(etape);
        } else if (etapesSelectionnees.contains(etape)) {
            etapesSelectionnees.remove(etape);
        } else {
            etapesSelectionnees.add(etape);
        }
        notifierObservateurs();
    }

    /**
     * Méthode permettant de supprimer les arcs et les étapes sélectionnés.
     *
     * @throws EtapeTwiskException Si la liste des arcs ou des étapes sélectionnés est vide.
     */
    public void supprimerSelection() throws EtapeTwiskException {
        if (etapesSelectionnees.isEmpty() && arcsSelectionnees.isEmpty()) {
            throw new EtapeTwiskException("Sélectionner au moins 1 étape ou 1 arc à supprimer.");
        }
        for (EtapeIG etape : etapesSelectionnees) {
            etapesIG.remove(etape.getId());
            arcs.removeIf(arc -> arc.getPt1().getIdEtape().equals(etape.getId()) || arc.getPt2().getIdEtape().equals(etape.getId()));
        }
        etapesSelectionnees.clear();

        for (ArcIG arc : arcsSelectionnees) {
            arc.getPt1().getEtape().retirerSucesseur(arc.getPt2().getEtape());
            arcs.remove(arc);
        }
        arcsSelectionnees.clear();

        resetSensCircu();
        System.out.println(sensCircu);
        notifierObservateurs();
    }

    /**
     * Méthode permettant de renommer les étapes sélectionnées.
     *
     * @throws EtapeTwiskException Si aucune étape n'est sélectionnée.
     */
    public void renommerEtapesSelectionnees() throws EtapeTwiskException {
        if (etapesSelectionnees.isEmpty()) {
            throw new EtapeTwiskException("Sélectionner au moins 1 étape à renommer.");
        } else {
            for (EtapeIG etape : etapesSelectionnees) {
                TextInputDialog tid = new TextInputDialog();
                tid.setContentText("Saisir le nouveau nom de l'activité");
                Optional<String> optionalS = tid.showAndWait();
                optionalS.ifPresent(etape::setNom);
            }
            etapesSelectionnees.clear();
            notifierObservateurs();
        }
    }

    /**
     * Setter des entrées du mondeIG.
     *
     * @throws EtapeTwiskException Si aucune étape n'est sélectionnée.
     */
    public void setEntree() throws EtapeTwiskException {
        if (etapesSelectionnees.isEmpty())
            throw new EtapeTwiskException("Sélectionner au moins une étape à ajouter en entrée !");
        for (EtapeIG etape : etapesSelectionnees) {
            if (entrees.contains(etape)) entrees.remove(etape);
            else if (sorties.contains(etape)) {
                sorties.remove(etape);
                entrees.add(etape);
            } else {
                entrees.add(etape);
            }
        }
        etapesSelectionnees.clear();
        notifierObservateurs();
    }

    /**
     * Setter des sorties du mondeIG.
     *
     * @throws EtapeTwiskException Si aucune étape n'est sélectionnée.
     */
    public void setSortie() throws EtapeTwiskException {
        if (etapesSelectionnees.isEmpty())
            throw new EtapeTwiskException("Sélectionner au moins une étape à ajouter en sortie !");
        for (EtapeIG etape : etapesSelectionnees) {
            if (etape.estUnGuichet()) {
                throw new EtapeTwiskException("Une sortie ne peut pas être un guichet !");
            }
            if (sorties.contains(etape)) sorties.remove(etape);
            else if (entrees.contains(etape)) {
                entrees.remove(etape);
                sorties.add(etape);
            } else {
                sorties.add(etape);
            }
        }
        etapesSelectionnees.clear();
        notifierObservateurs();
    }

    /**
     * Méthode permettant de set le délai de l'étape sélectionnée.
     *
     * @throws EtapeTwiskException S'il n'y a pas exactement 1 étape sélectionnée.
     */
    public void setDelais() throws EtapeTwiskException {
        if (etapesSelectionnees.size() != 1) {
            throw new EtapeTwiskException("Une seule étape doit être sélectionnée pour changer le délai.");
        } else {
            ActiviteIG activiteIG = (ActiviteIG) etapesSelectionnees.get(0);
            TextInputDialog tid = new TextInputDialog();
            tid.setContentText("Saisir le nouveau délai de l'activité");
            Optional<String> optionalS = tid.showAndWait();
            activiteIG.setDelai(Integer.parseInt(optionalS.get()));

        }
        etapesSelectionnees.clear();
        notifierObservateurs();
    }

    /**
     * Méthode permettant de set l'écart-type de l'étape sélectionnée.
     *
     * @throws EtapeTwiskException S'il n'y a pas exactement 1 étape sélectionnée.
     */
    public void setEcarts() throws EtapeTwiskException {
        if (etapesSelectionnees.size() != 1) {
            throw new EtapeTwiskException("Une seule étape doit être sélectionnée pour changer l'écart.");
        } else {
            ActiviteIG activiteIG = (ActiviteIG) etapesSelectionnees.get(0);
            TextInputDialog tid = new TextInputDialog();
            tid.setContentText("Saisir le nouvel écart de l'activité");
            Optional<String> optionalS = tid.showAndWait();
            activiteIG.setEcart(Integer.parseInt(optionalS.get()));

        }
        etapesSelectionnees.clear();
        notifierObservateurs();
    }

    /**
     * Méthode permettant d'annuler la sélection.
     */
    public void annulerSelectionEtapes() {
        etapesSelectionnees.clear();
        arcsSelectionnees.clear();
        notifierObservateurs();
    }

    /**
     * Méthode permettant d'ajouter un arc aux arcs sélectionnés.
     *
     * @param arc Le nouvel arc sélectionné.
     */
    public void selectionnerArc(ArcIG arc) {
        if (arcsSelectionnees.isEmpty()) {
            arcsSelectionnees.add(arc);
        } else if (arcsSelectionnees.contains(arc)) {
            arcsSelectionnees.remove(arc);
        } else {
            arcsSelectionnees.add(arc);
        }
        notifierObservateurs();
    }

    /**
     * Méthode permettant de relocaliser l'étape à de nouvelles coordonnées.
     *
     * @param idEtape Identifiant de l'étape à relocaliser.
     * @param newPosX La nouvelle coordonnée sur l'axe des abscisses.
     * @param newPosY La nouvelle coordonnée sur l'axe des ordonnées.
     */
    public void repositionnerEtape(String idEtape, int newPosX, int newPosY) {
        EtapeIG etapeTmp = etapesIG.get(idEtape);
        etapeTmp.relocate(newPosX, newPosY);
        etapeTmp.actualiserPointsDeControle();
        etapesIG.remove(idEtape);
        etapesIG.put(etapeTmp.getId(), etapeTmp);
        notifierObservateurs();
    }

    /**
     * Setter du nombre de jetons du guichet sélectionné.
     *
     * @throws GuichetTwiskException S'il n'y a pas exactement 1 guichet sélectionné.
     */
    public void setJetons() throws GuichetTwiskException {
        if (etapesSelectionnees.size() != 1) {
            throw new GuichetTwiskException("Un seul guichet doit être sélectionné");
        } else if (!etapesSelectionnees.get(0).estUnGuichet()) {
            throw new GuichetTwiskException("Seul un guichet peut être sélectionné");
        } else {
            GuichetIG guichetIG = (GuichetIG) etapesSelectionnees.get(0);
            TextInputDialog tid = new TextInputDialog();
            tid.setContentText("Saisir le nombre de jetons du guichet");
            Optional<String> optionalS = tid.showAndWait();
            guichetIG.setJetons(Integer.parseInt(optionalS.get()));
        }
        etapesSelectionnees.clear();
        notifierObservateurs();
    }

    /**
     * Setter du nombre clients.
     *
     * @throws ClientException Si le nombre de clients n'est pas entre [1;49]
     */
    public void setNbClients() throws ClientException {
        TextInputDialog tid = new TextInputDialog();
        tid.setContentText("Saisir le nombre de jetons du guichet");
        Optional<String> optionalS = tid.showAndWait();
        int nb = Integer.parseInt(optionalS.get());
        if (nb >= 50) {
            throw new ClientException("Le nombre de clients ne peut pas être supérieur ou égal à 50!");
        }
        if (nb == 0) {
            throw new ClientException("Le nombre de clients ne peut pas être égale à 0 !");
        }
        nbClients = nb;
    }

    /**
     * Méthode permettant de récupérer le nombre de clients qui ne sont pas encore sortis.
     *
     * @return Le nombre de clients restant.
     */
    public int getNombreClientsRestant() {
        int nb = 0;
        if (gestionnaireClients != null) {
            for (Client c : gestionnaireClients) {
                if (c.getEtape().getNom().equals("SasSortie")) {
                    nb++;
                }
            }
        }
        return nbClients - nb;
    }

    /**
     * Méthode permettant de lancer la simulation.
     *
     * @throws TwiskException Si exception au niveau du monde ou des guichets.
     */
    public void simuler() throws TwiskException {
        ClientTwisk client = new ClientTwisk(this);
        try {
            verifierMondeIG();
            client.lancementSimulation(creerMonde(), nbClients);
        } catch (GuichetTwiskException | MondeException e) {
            throw new MondeException(e.toString());
        }
    }

    /**
     * Méthode permettant de vérifier que le mondeIG est est conforme pour lancer une simulation.
     *
     * @throws MondeException        Si il n'y a pas de sorties ou d'entrées dans le monde.
     * @throws GuichetTwiskException Si il y a plusieurs étapes directement après un guichet.
     */
    public void verifierMondeIG() throws MondeException, GuichetTwiskException {
        if (entrees.size() == 0) throw new MondeException("Il n'y a pas d'entrée dans votre monde !");
        else if (sorties.size() == 0) throw new MondeException("Il n'y a pas de sortie dans votre monde !");
        for (EtapeIG etapeIG : this) {
            GuichetIG guichetIG;
            if (etapeIG.estUnGuichet()) {
                guichetIG = (GuichetIG) etapeIG;
                if (guichetIG.getSuccesseurs().size() > 1) {
                    throw new GuichetTwiskException("1 seule étape en sortie de guichet !");
                }
            }
        }
    }

    /**
     * Méthode permettant de créer un Monde à partir du MondeIG.
     *
     * @return Le monde créé à partir du MondeIG.
     */
    private Monde creerMonde() {
        correspondanceEtapes = new CorrespondanceEtapes();
        Monde monde = new Monde();
        // Ajout en premier de toutes les etapes dans le gestionnaire d'étapes et ensuite ajout de tous les successeurs de chaques étapes.

        for (EtapeIG e : this) {
            if (!monde.contient(correspondanceEtapes.getEtape(e))) {
                if (!e.estUnGuichet()) {
                    ActiviteIG tmpIG = (ActiviteIG) e;
                    Activite tmp = new Activite(tmpIG.getNom(), tmpIG.getDelai(), tmpIG.getEcart());
                    correspondanceEtapes.ajouter(tmpIG, tmp);
                    monde.ajouter(tmp);
                } else {
                    GuichetIG tmpIG = (GuichetIG) e;
                    Guichet tmp = new Guichet(tmpIG.getNom(), tmpIG.getJetons());
                    correspondanceEtapes.ajouter(tmpIG, tmp);
                    monde.ajouter(tmp);
                    ActiviteIG sucIG = (ActiviteIG) tmpIG.getSuccesseurs().get(0);
                    ActiviteRestreinte suc = new ActiviteRestreinte(sucIG.getNom(), sucIG.getDelai(), sucIG.getEcart());
                    correspondanceEtapes.ajouter(sucIG, suc);
                    tmp.ajouterSuccesseur(suc);
                    monde.ajouter(suc);
                }
            }
        }

        for (EtapeIG entree : entrees) {
            if (!entree.estUnGuichet()) {
                ActiviteIG tmpIG = (ActiviteIG) entree;
                Activite tmp = (Activite) correspondanceEtapes.getEtape(tmpIG);
                monde.aCommeEntree(tmp);
            } else {
                GuichetIG tmpIG = (GuichetIG) entree;
                Guichet tmp = (Guichet) correspondanceEtapes.getEtape(tmpIG);
                monde.aCommeEntree(tmp);
            }
        }

        for (EtapeIG sortie : sorties) {
            if (!sortie.estUnGuichet()) {
                ActiviteIG tmpIG = (ActiviteIG) sortie;
                Activite tmp = (Activite) correspondanceEtapes.getEtape(tmpIG);
                monde.aCommeSortie(tmp);
            }
        }

        for (EtapeIG e : this) {
            if (!e.getSuccesseurs().isEmpty()) {
                for (EtapeIG etapeSuc : e.getSuccesseurs()) {
                    if (!e.estUnGuichet()) {
                        ActiviteIG tmpIG = (ActiviteIG) e;
                        Activite tmp = (Activite) correspondanceEtapes.getEtape(tmpIG);
                        tmp.ajouterSuccesseur(correspondanceEtapes.getEtape(etapeSuc));
                    }
                }
            }
        }
        monde.setLoi(this.loi);
        return monde;
    }

    /**
     * Méthode définissant un nouvel itérateur sur le monde.
     *
     * @return Un itaror sur les étapesIG du mondeIG.
     */
    @Override
    public Iterator<EtapeIG> iterator() {
        return etapesIG.values().iterator();
    }

    /**
     * Méthode définissant un nouvel itérateur sur le monde.
     *
     * @return Un iterator sur les arcs.
     */
    public Iterator<ArcIG> iteratorArcs() {
        return arcs.iterator();
    }

    /**
     * Méthode réagir.
     */
    @Override
    public void reagir() {
        Runnable command = this::notifierObservateurs;
        if (Platform.isFxApplicationThread()) {
            command.run();
        } else {
            Platform.runLater(command);
        }
    }

    /**
     * Getter du gestionnaire clients du mondeIG.
     *
     * @return Le gestionnaireClients.
     */
    public GestionnaireClients getGestionnaireClients() {
        return gestionnaireClients;
    }

    /**
     * Setter du gestionnaire clients du mondeIG.
     *
     * @param g Le gestionnaire clients.
     */
    public void setGestionnaireClients(GestionnaireClients g) {
        gestionnaireClients = g;
    }

    /**
     * Getter du correspondance étape de mondeIG.
     *
     * @return Le correspondanceEtapes du mondeIG.
     */
    public CorrespondanceEtapes getCorrespondanceEtapes() {
        return correspondanceEtapes;
    }

    /**
     * Méthode permettant de sauvegarder au format .json le mondeIG actuel.
     *
     * @param emplacement Emplacement de la sauvegarde.
     */
    public void sauvegarder(String emplacement) {
        JsonWriter writer;
        try {
            writer = new JsonWriter(new FileWriter(emplacement));
            writer.beginObject();

            /*----Enregistrement des EtapesIG du monde-----*/
            writer.name("EtapesIG");
            writer.beginArray();
            for (EtapeIG s : this) {
                writer.beginObject();
                writer.name("Nom");
                writer.value(s.getNom());

                writer.name("ID");
                writer.value(s.getId());

                writer.name("PosX");
                writer.value(s.getPosX());

                writer.name("PosY");
                writer.value(s.getPosY());

                if (s.estUneActivite()) {
                    ActiviteIG tmp = (ActiviteIG) s;
                    writer.name("Delai");
                    writer.value(tmp.getDelai());

                    writer.name("Ecart");
                    writer.value(tmp.getEcart());

                    writer.name("Type");
                    writer.value("Activité");
                } else {
                    GuichetIG tmp = (GuichetIG) s;
                    writer.name("Jetons");
                    writer.value(tmp.getJetons());

                    writer.name("Type");
                    writer.value("Guichet");
                }

                /*----Gestion des entrées & sorties----*/
                if (entrees.contains(s)) {
                    writer.name("Entree");
                    writer.value("oui");
                } else {
                    writer.name("Entree");
                    writer.value("non");
                }

                if (sorties.contains(s)) {
                    writer.name("Sortie");
                    writer.value("oui");
                } else {
                    writer.name("Sortie");
                    writer.value("non");
                }

                writer.endObject();
            }
            writer.endArray();

            /*----Gestion des arcs-----*/
            writer.name("Arcs");
            writer.beginArray();
            for (ArcIG a : arcs) {
                writer.beginObject();

                writer.name("Pt1ID");
                writer.value(a.getPt1().getId());

                writer.name("Pt1EtapeID");
                writer.value(a.getPt1().getIdEtape());


                /*-----------------------*/
                writer.name("Pt2ID");
                writer.value(a.getPt2().getId());

                writer.name("Pt2EtapeID");
                writer.value(a.getPt2().getIdEtape());

                writer.endObject();
            }
            writer.endArray();

            writer.endObject();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode permettant d'ouvrir un mondeIG sauvegardé au format .json.
     *
     * @param emplacement Emplacement du mondeIG à ouvrir.
     */
    public void ouvrir(String emplacement) {
        reset();

        JsonParser jsonParser = new JsonParser();

        try (FileReader reader = new FileReader(emplacement)) {
            JsonObject obj = (JsonObject) jsonParser.parse(reader);

            JsonArray listeEtapesIG = obj.getAsJsonArray("EtapesIG");
            listeEtapesIG.forEach(etapes -> {
                JsonObject objEt = etapes.getAsJsonObject();

                /*---Récupère les info de bases des étapes---*/
                String nom = objEt.get("Nom").getAsString();
                String id = objEt.get("ID").getAsString();
                int posX = objEt.get("PosX").getAsInt();
                int posY = objEt.get("PosY").getAsInt();

                /*----Vérifie si l'étape est un guichet ou une activité-----*/
                String type = objEt.get("Type").getAsString();
                String entreeA = objEt.get("Entree").getAsString();
                String sortieA = objEt.get("Sortie").getAsString();
                if (type.equalsIgnoreCase("Activité")) {
                    int delai = objEt.get("Delai").getAsInt();
                    int ecart = objEt.get("Ecart").getAsInt();
                    ActiviteIG tmp = new ActiviteIG(nom, id, tailleComposants.getLargeurActivite(), tailleComposants.getHauteurActivite());
                    tmp.relocate(posX, posY);
                    tmp.setDelai(delai);
                    tmp.setEcart(ecart);
                    tmp.actualiserPointsDeControle();
                    ajouter(tmp);

                    /*---Gestion si etape est une entrée ou une sortie---*/
                    if (entreeA.equalsIgnoreCase("oui")) {
                        this.entrees.add(tmp);
                    }
                    if (sortieA.equalsIgnoreCase("oui")) {
                        this.sorties.add(tmp);
                    }

                }
                if (type.equalsIgnoreCase("Guichet")) {
                    int jetons = objEt.get("Jetons").getAsInt();
                    GuichetIG tmp = new GuichetIG(nom, id, tailleComposants.getLargeurActivite(), tailleComposants.getHauteurActivite(), jetons);
                    tmp.relocate(posX, posY);
                    tmp.actualiserPointsDeControle();
                    ajouter(tmp);

                    /*---Gestion si etape est une entrée ou une sortie---*/
                    if (entreeA.equalsIgnoreCase("oui")) {
                        this.entrees.add(tmp);
                    }
                    if (sortieA.equalsIgnoreCase("oui")) {
                        this.sorties.add(tmp);
                    }
                }
            });

            JsonArray listeArcIG = obj.getAsJsonArray("Arcs");
            listeArcIG.forEach(arc -> {
                JsonObject objArc = arc.getAsJsonObject();
                /*----Pt1----*/
                String idEtapePt1 = objArc.get("Pt1EtapeID").getAsString();
                EtapeIG tmp = this.etapesIG.get(idEtapePt1);
                String idPt1 = objArc.get("Pt1ID").getAsString();
                PointDeControleIG pt1 = tmp.getPointDeControle(idPt1);

                /*-----Pt2----*/
                String idEtapePt2 = objArc.get("Pt2EtapeID").getAsString();
                EtapeIG tmp2 = this.etapesIG.get(idEtapePt2);
                String idPt2 = objArc.get("Pt2ID").getAsString();
                PointDeControleIG pt2 = tmp2.getPointDeControle(idPt2);

                if (sensCircu == null) {
                    if (tmp2.estUnGuichet()) {
                        if (pt2.getId().contains("0")) {
                            sensCircu = "gaucheVersDroite";
                        } else {
                            sensCircu = "droiteVersGauche";
                        }
                    } else {
                        if (tmp.estUnGuichet()) {
                            if (pt1.getId().contains("0")) {
                                sensCircu = "droiteVersGauche";
                            } else {
                                sensCircu = "gaucheVersDroite";
                            }
                        }
                    }
                }
                this.arcs.add(new ArcIG(pt1, pt2));

            });

        } catch (IOException e) {
            e.printStackTrace();
        }
        notifierObservateurs();
    }

    /**
     * Méthode permettant d'ouvrir un mondeIG déjà préenregistré.
     *
     * @param json Données en format String du monde.
     */
    public void ouvrirExemple(String json) {
        reset();

        JsonObject object = JsonParser.parseString(json).getAsJsonObject();

        JsonArray listeEtapesIG = object.getAsJsonArray("EtapesIG");
        listeEtapesIG.forEach(etapes -> {
            JsonObject objEt = etapes.getAsJsonObject();

            /*---Récupère les info de bases des étapes---*/
            String nom = objEt.get("Nom").getAsString();
            String id = objEt.get("ID").getAsString();
            int posX = objEt.get("PosX").getAsInt();
            int posY = objEt.get("PosY").getAsInt();

            /*----Vérifie si l'étape est un guichet ou une activité-----*/
            String type = objEt.get("Type").getAsString();
            String entreeA = objEt.get("Entree").getAsString();
            String sortieA = objEt.get("Sortie").getAsString();
            if (type.equalsIgnoreCase("Activité")) {
                int delai = objEt.get("Delai").getAsInt();
                int ecart = objEt.get("Ecart").getAsInt();
                ActiviteIG tmp = new ActiviteIG(nom, id, tailleComposants.getLargeurActivite(), tailleComposants.getHauteurActivite());
                tmp.relocate(posX, posY);
                tmp.setDelai(delai);
                tmp.setEcart(ecart);
                tmp.actualiserPointsDeControle();
                ajouter(tmp);

                /*---Gestion si etape est une entrée ou une sortie---*/
                if (entreeA.equalsIgnoreCase("oui")) {
                    this.entrees.add(tmp);
                }
                if (sortieA.equalsIgnoreCase("oui")) {
                    this.sorties.add(tmp);
                }

            }
            if (type.equalsIgnoreCase("Guichet")) {
                int jetons = objEt.get("Jetons").getAsInt();
                GuichetIG tmp = new GuichetIG(nom, id, tailleComposants.getLargeurActivite(), tailleComposants.getHauteurActivite(), jetons);
                tmp.relocate(posX, posY);
                tmp.actualiserPointsDeControle();
                ajouter(tmp);

                /*---Gestion si etape est une entrée ou une sortie---*/
                if (entreeA.equalsIgnoreCase("oui")) {
                    this.entrees.add(tmp);
                }
                if (sortieA.equalsIgnoreCase("oui")) {
                    this.sorties.add(tmp);
                }
            }
        });

        JsonArray listeArcIG = object.getAsJsonArray("Arcs");
        listeArcIG.forEach(arc -> {
            JsonObject objArc = arc.getAsJsonObject();
            /*----Pt1----*/
            String idEtapePt1 = objArc.get("Pt1EtapeID").getAsString();
            EtapeIG tmp = this.etapesIG.get(idEtapePt1);
            String idPt1 = objArc.get("Pt1ID").getAsString();
            PointDeControleIG pt1 = tmp.getPointDeControle(idPt1);

            /*-----Pt2----*/
            String idEtapePt2 = objArc.get("Pt2EtapeID").getAsString();
            EtapeIG tmp2 = this.etapesIG.get(idEtapePt2);
            String idPt2 = objArc.get("Pt2ID").getAsString();
            PointDeControleIG pt2 = tmp2.getPointDeControle(idPt2);

            if (sensCircu == null) {
                if (tmp2.estUnGuichet()) {
                    if (pt2.getId().contains("0")) {
                        sensCircu = "gaucheVersDroite";
                    } else {
                        sensCircu = "droiteVersGauche";
                    }
                } else {
                    if (tmp.estUnGuichet()) {
                        if (pt1.getId().contains("0")) {
                            sensCircu = "droiteVersGauche";
                        } else {
                            sensCircu = "gaucheVersDroite";
                        }
                    }
                }
            }
            this.arcs.add(new ArcIG(pt1, pt2));

        });
        notifierObservateurs();
    }

    /**
     * Méthode permettant de détruire les processus clients.
     */
    public void detruireClients() {
        if (gestionnaireClients != null) {
            for (Client c : gestionnaireClients) {
                Runtime r = Runtime.getRuntime();
                Process p;
                try {
                    p = r.exec("kill -9 " + c.getNumeroClient());
                    if (p != null) {
                        p.waitFor();
                    }
                } catch (IOException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            gestionnaireClients.nettoyer();
        }
    }
}