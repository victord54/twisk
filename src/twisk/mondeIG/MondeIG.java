package twisk.mondeIG;

import javafx.scene.control.TextInputDialog;
import twisk.ClientTwisk;
import twisk.exceptions.ArcTwiskException;
import twisk.exceptions.EtapeTwiskException;
import twisk.exceptions.GuichetTwiskException;
import twisk.exceptions.MondeException;
import twisk.monde.Activite;
import twisk.monde.Etape;
import twisk.monde.Guichet;
import twisk.monde.Monde;
import twisk.outils.CorrespondanceEtapes;
import twisk.outils.FabriqueIdentifiant;
import twisk.outils.TailleComposants;
import twisk.simulation.Simulation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Optional;

public class MondeIG extends SujetObserve implements Iterable<EtapeIG> {
    private final HashMap<String, EtapeIG> etapesIG;
    private final TailleComposants tailleComposants = TailleComposants.getInstance();
    private final ArrayList<ArcIG> arcs;
    private final ArrayList<PointDeControleIG> pointsControleSelectionnes;
    private final ArrayList<EtapeIG> etapesSelectionnees;
    private final ArrayList<ArcIG> arcsSelectionnees;
    private final ArrayList<EtapeIG> entrees;
    private final ArrayList<EtapeIG> sorties;
    private CorrespondanceEtapes correspondanceEtapes;

    public MondeIG() {
        etapesIG = new HashMap<>();
        arcs = new ArrayList<>();
        pointsControleSelectionnes = new ArrayList<>();
        etapesSelectionnees = new ArrayList<>();
        arcsSelectionnees = new ArrayList<>();
        entrees = new ArrayList<>();
        sorties = new ArrayList<>();
        this.ajouter("Activité");
        
    }

    public EtapeIG getEtape(String id) {
        return etapesIG.get(id);
    }

    public ArrayList<PointDeControleIG> getPointsControleSelectionnes() {
        return pointsControleSelectionnes;
    }

    public ArrayList<EtapeIG> getEtapesSelectionnees() {
        return etapesSelectionnees;
    }

    public boolean arcIsEmpty() {
        return arcs.isEmpty();
    }

    public ArrayList<ArcIG> getArcsSelectionnees() {
        return arcsSelectionnees;
    }

    public ArrayList<EtapeIG> getEntrees() {
        return entrees;
    }

    public ArrayList<EtapeIG> getSorties() {
        return sorties;
    }


    public void ajouter(String type) {
        String id = FabriqueIdentifiant.getInstance().getIdentifiantEtape();
        if (type.equals("Activité") || type.equals("Activite"))
            etapesIG.put(id, new ActiviteIG("Activité", id, tailleComposants.getLargeurActivite(), tailleComposants.getHauteurActivite()));
        if (type.equals("Guichet"))
            etapesIG.put(id, new GuichetIG("Guichet",id, tailleComposants.getLargeurActivite(), tailleComposants.getHauteurActivite(), 2));

        notifierObservateurs();
    }

    public void ajouterArc(PointDeControleIG pt1, PointDeControleIG pt2) {
        arcs.add(new ArcIG(pt1, pt2));
        notifierObservateurs();
    }

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
                System.out.println("arc ajouté");
            }
        }
        notifierObservateurs();
    }

    public void reset() {
        etapesIG.clear();
        arcs.clear();
        pointsControleSelectionnes.clear();
        FabriqueIdentifiant.getInstance().reset();
        notifierObservateurs();
    }

    public void retirerDernierArc() {
        arcs.get(arcs.size()-1).getPt1().getEtape().retirerSucesseur(arcs.get(arcs.size()-1).getPt2().getEtape());
        arcs.remove(arcs.size() - 1);
        notifierObservateurs();
    }

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
        notifierObservateurs();
    }

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

    public void setSortie() throws EtapeTwiskException {
        if (etapesSelectionnees.isEmpty())
            throw new EtapeTwiskException("Sélectionner au moins une étape à ajouter en sortie !");
        for (EtapeIG etape : etapesSelectionnees) {
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

    public void annulerSelectionEtapes() {
        etapesSelectionnees.clear();
        arcsSelectionnees.clear();
        notifierObservateurs();
    }

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

    public void repositionnerEtape(String idEtape, int newPosX, int newPosY) {
        EtapeIG etapeTmp = etapesIG.get(idEtape);
        etapeTmp.relocate(newPosX, newPosY);
        etapeTmp.actualiserPointsDeControle();
        etapesIG.remove(idEtape);
        etapesIG.put(etapeTmp.getId(), etapeTmp);
        notifierObservateurs();
    }

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

    public void simuler() throws MondeException {
        /*Simulation sim = new Simulation();
        sim.setNbClients(5);
        sim.simuler(creerMonde());*/
        System.out.println(creerMonde().toString());
    }

    private Monde creerMonde() throws MondeException {
        correspondanceEtapes = new CorrespondanceEtapes();
        Monde monde = new Monde();
        // Ajout en premier de toutes les etapes dans le gestionnaire d'étapes et ensuite ajout de tous les successeurs de chaques étapes.

        for (EtapeIG e : this){
            if (!e.estUnGuichet()) {
                ActiviteIG tmpIG = (ActiviteIG) e;
                Activite tmp = new Activite(tmpIG.getNom(), tmpIG.getDelai(), tmpIG.getEcart());
                correspondanceEtapes.ajouter(tmpIG,tmp);
                monde.ajouter(tmp);
            } else {
                GuichetIG tmpIG = (GuichetIG) e;
                Guichet tmp = new Guichet(tmpIG.getNom(), tmpIG.getJetons());
                correspondanceEtapes.ajouter(tmpIG,tmp);
                monde.ajouter(tmp);
            }
        }

        for (EtapeIG entree: entrees) {
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

        for (EtapeIG sortie: sorties) {
            if (sortie.estUnGuichet()) {
                throw new MondeException("Une sortie ne peut pas être un guichet !");
            } else {
                ActiviteIG tmpIG = (ActiviteIG) sortie;
                Activite tmp = (Activite) correspondanceEtapes.getEtape(tmpIG);
                monde.aCommeSortie(tmp);
            }
        }

        for (EtapeIG e : this){
            if (e.getSuccesseurs().size() > 0) {
                if (!e.estUnGuichet()) {
                    ActiviteIG tmpIG = (ActiviteIG) e;
                    Activite tmp = (Activite) correspondanceEtapes.getEtape(tmpIG);
                    monde.aCommeEntree(tmp);
                } else {
                    GuichetIG tmpIG = (GuichetIG) e;
                    Guichet tmp = (Guichet) correspondanceEtapes.getEtape(tmpIG);
                    monde.aCommeEntree(tmp);
                }
            }
        }
        return monde;
    }


    @Override
    public Iterator<EtapeIG> iterator() {
        return etapesIG.values().iterator();
    }

    public Iterator<ArcIG> iteratorArcs() {
        return arcs.iterator();
    }
}