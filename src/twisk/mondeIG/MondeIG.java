package twisk.mondeIG;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;
import javafx.application.Platform;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import twisk.ClientTwisk;
import twisk.exceptions.*;
import twisk.monde.Activite;
import twisk.monde.ActiviteRestreinte;
import twisk.monde.Guichet;
import twisk.monde.Monde;
import twisk.outils.CorrespondanceEtapes;
import twisk.outils.FabriqueIdentifiant;
import twisk.outils.TailleComposants;
import twisk.simulation.GestionnaireClients;
import twisk.vues.Observateur;
import twisk.vues.VueMondeIG;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Optional;


public class MondeIG extends SujetObserve implements Iterable<EtapeIG>, Observateur {
    private final HashMap<String, EtapeIG> etapesIG;
    private final TailleComposants tailleComposants = TailleComposants.getInstance();
    private final ArrayList<ArcIG> arcs;
    private final ArrayList<PointDeControleIG> pointsControleSelectionnes;
    private final ArrayList<EtapeIG> etapesSelectionnees;
    private final ArrayList<ArcIG> arcsSelectionnees;
    private final ArrayList<EtapeIG> entrees;
    private final ArrayList<EtapeIG> sorties;
    private GestionnaireClients gestionnaireClients;
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
        gestionnaireClients = null;
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

    public void ajouter(EtapeIG e){
        String id = FabriqueIdentifiant.getInstance().getIdentifiantEtape();
        etapesIG.put(e.getId(),e);
    }

    public void ajouterArc(PointDeControleIG pt1, PointDeControleIG pt2) throws ArcTwiskException{
        EtapeIG et1 = pt1.getEtape();
        EtapeIG et2 = pt2.getEtape();


        if (!et1.estAccessibleDepuis(et2)){
            arcs.add(new ArcIG(pt1, pt2));
        } else{
            throw new ArcTwiskException("Impossible de créer un arc ici, cela fait un cycle ! ");
        }
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
            }
        }
        notifierObservateurs();
    }

    public void reset() {
        etapesIG.clear();
        arcs.clear();
        pointsControleSelectionnes.clear();
        entrees.clear();
        sorties.clear();
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

    public void simuler() throws TwiskException {
        ClientTwisk client = new ClientTwisk(this);
        try {
            client.lancementSimulation(creerMonde(), 8);
        } catch (GuichetTwiskException | MondeException e) {
            throw new MondeException(e.toString());
        }
    }

    public void verifierMondeIG() throws MondeException, GuichetTwiskException {
        if (entrees.size() == 0)
            throw new MondeException("Il n'y a pas d'entrée dans votre monde !");
        else if (sorties.size() == 0)
            throw new MondeException("Il n'y a pas de sortie dans votre monde !");
        for (EtapeIG etapeIG: this) {
            GuichetIG guichetIG;
            if (etapeIG.estUnGuichet()) {
                guichetIG = (GuichetIG) etapeIG;
                if (guichetIG.getSuccesseurs().size() > 1) {
                    System.out.println("ou lala");
                    throw new GuichetTwiskException("1 seule étape en sortie de guichet !");
                }
            }
        }
    }

    private Monde creerMonde() throws GuichetTwiskException, MondeException {
            verifierMondeIG();
        correspondanceEtapes = new CorrespondanceEtapes();
        Monde monde = new Monde();
        // Ajout en premier de toutes les etapes dans le gestionnaire d'étapes et ensuite ajout de tous les successeurs de chaques étapes.

        for (EtapeIG e : this){
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
            if (!e.getSuccesseurs().isEmpty()) {
                for (EtapeIG etapeSuc: e.getSuccesseurs()) {
                    if (!e.estUnGuichet()) {
                        ActiviteIG tmpIG = (ActiviteIG) e;
                        Activite tmp = (Activite) correspondanceEtapes.getEtape(tmpIG);
                        tmp.ajouterSuccesseur(correspondanceEtapes.getEtape(etapeSuc));
                    } 
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

    @Override
    public void reagir() {
        Runnable command = this::notifierObservateurs;
        if (Platform.isFxApplicationThread()) {
            command.run();
        } else {
            Platform.runLater(command);
        }
    }

    public GestionnaireClients getGestionnaireClients() {
        return gestionnaireClients;
    }

    public void setGestionnaireClients(GestionnaireClients g){
        gestionnaireClients = g;
    }

    public CorrespondanceEtapes getCorrespondanceEtapes() {
        return correspondanceEtapes;
    }

    public void sauvegarder(String emplacement) {
        JsonWriter writer = null;
        try {
            writer = new JsonWriter(new FileWriter(emplacement));
            writer.beginObject();

            /*----Enregistrement des EtapesIG du monde-----*/
            writer.name("EtapesIG");
            writer.beginArray();
            for (EtapeIG s : this){
                writer.beginObject();
                writer.name("Nom");
                writer.value(s.getNom());

                writer.name("ID");
                writer.value(s.getId());

                writer.name("PosX");
                writer.value(s.getPosX());

                writer.name("PosY");
                writer.value(s.getPosY());

                if(s.estUneActivite()){
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
                if (entrees.contains(s)){
                    writer.name("Entree");
                    writer.value("oui");
                } else{
                    writer.name("Entree");
                    writer.value("non");
                }

                if (sorties.contains(s)){
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
            for (ArcIG a : arcs){
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

    public void ouvrir(String emplacement){
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
                if (type.equalsIgnoreCase("Activité")){
                    int delai = objEt.get("Delai").getAsInt();
                    int ecart = objEt.get("Ecart").getAsInt();
                    ActiviteIG tmp = new ActiviteIG(nom,id, tailleComposants.getLargeurActivite(), tailleComposants.getHauteurActivite());
                    tmp.relocate(posX,posY);
                    tmp.setDelai(delai);
                    tmp.setEcart(ecart);
                    tmp.actualiserPointsDeControle();
                    ajouter(tmp);

                    /*---Gestion si etape est une entrée ou une sortie---*/
                    if (entreeA.equalsIgnoreCase("oui")){
                        this.entrees.add(tmp);
                    }
                    if (sortieA.equalsIgnoreCase("oui")){
                        this.sorties.add(tmp);
                    }

                }
                if (type.equalsIgnoreCase("Guichet")){
                    int jetons = objEt.get("Jetons").getAsInt();
                    GuichetIG tmp = new GuichetIG(nom,id, tailleComposants.getLargeurActivite(), tailleComposants.getHauteurActivite(), jetons);
                    tmp.relocate(posX,posY);
                    tmp.actualiserPointsDeControle();
                    ajouter(tmp);

                    /*---Gestion si etape est une entrée ou une sortie---*/
                    if (entreeA.equalsIgnoreCase("oui")){
                        this.entrees.add(tmp);
                    }
                    if (sortieA.equalsIgnoreCase("oui")){
                        this.sorties.add(tmp);
                    }
                }
            });

            JsonArray listeArcIG = obj.getAsJsonArray("Arcs");
            listeArcIG.forEach(arc ->{
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

                this.arcs.add(new ArcIG(pt1, pt2));

            });

        } catch (IOException e) {
            e.printStackTrace();
        }
        notifierObservateurs();
    }



}