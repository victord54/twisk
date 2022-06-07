/**
 * Classe représentant la vue du Menu.
 *
 * @author Victor Dallé et Claire Kurth.
 */
package twisk.vues;

import javafx.application.Platform;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import twisk.exceptions.ClientException;
import twisk.exceptions.EtapeTwiskException;
import twisk.exceptions.GuichetTwiskException;
import twisk.mondeIG.MondeIG;
import twisk.outils.ExemplesDeMondes;

import java.io.File;
import java.util.Objects;


public class VueMenu extends MenuBar {

    /**
     * Constructeur.
     *
     * @param monde Le mondeIG.
     * @param stage Le stage de l'application.
     */
    public VueMenu(MondeIG monde, Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json"));
//      ------------------------------------------------- Menu Fichier -------------------------------------------------
        Menu fileMenu = new Menu("Fichier");
        MenuItem quit = new MenuItem("Quitter");
        quit.setOnAction(actionEvent -> {
            monde.detruireClients();
                Platform.exit();
        });

        MenuItem open = new MenuItem("Ouvrir");
        open.setOnAction(actionEvent -> {
            File file = fileChooser.showOpenDialog(stage);

            if (file != null) {
                monde.ouvrir(file.getAbsolutePath());
            }

        });

        MenuItem save = new MenuItem("Sauvegarder sous ...");
        save.setOnAction(actionEvent -> {
            File file = fileChooser.showSaveDialog(stage);

            if (file != null) {
                monde.sauvegarder(file.getAbsolutePath() + ".json");
            }

        });

//      ------------------------------------------------- Menu Édition -------------------------------------------------
        Menu editMenu = new Menu("Édition");
        MenuItem delete = new MenuItem("Supprimer la selection");
        delete.setOnAction(actionEvent -> {
            try {
                monde.supprimerSelection();
            } catch (EtapeTwiskException e) {
                e.afficherMessage();
            }
        });

        MenuItem rename = new MenuItem("Renommer la sélection");
        rename.setOnAction(actionEvent -> {
            try {
                monde.renommerEtapesSelectionnees();
            } catch (EtapeTwiskException e) {
                e.afficherMessage();
            }
        });

        MenuItem cancel = new MenuItem("Annuler la sélection");
        cancel.setOnAction(actionEvent -> monde.annulerSelectionEtapes());
//      -------------------------------------------------- Menu Monde --------------------------------------------------
        Menu worldMenu = new Menu("Monde");
        MenuItem input = new MenuItem("Entrée");
        input.setOnAction(actionEvent -> {
            try {
                monde.setEntree();
            } catch (EtapeTwiskException e) {
                e.afficherMessage();
            }
        });

        MenuItem output = new MenuItem("Sortie");
        output.setOnAction(actionEvent -> {
            try {
                monde.setSortie();
            } catch (EtapeTwiskException e) {
                e.afficherMessage();
            }
        });
//      ------------------------------------------------ Menu Paramètres -----------------------------------------------
        Menu settingsMenu = new Menu("Paramètres");
        MenuItem delay = new MenuItem("Modifier le délai");
        delay.setOnAction(actionEvent -> {
            try {
                monde.setDelais();
            } catch (EtapeTwiskException e) {
                e.afficherMessage();
            }
        });

        MenuItem ecart = new MenuItem("Modifier l'écart");
        ecart.setOnAction(actionEvent -> {
            try {
                monde.setEcarts();
            } catch (EtapeTwiskException e) {
                e.afficherMessage();
            }
        });

        MenuItem jeton = new MenuItem("Modifier le nombre de jetons");
        jeton.setOnAction(actionEvent -> {
            try {
                monde.setJetons();
            } catch (GuichetTwiskException e) {
                e.afficherMessage();
            }
        });

        MenuItem clients = new MenuItem("Modifier le nombre de clients");
        clients.setOnAction(actionEvent -> {
            try {
                monde.setNbClients();
            } catch (ClientException e) {
                e.afficherMessage();
            }
        });
//      -------------------------------------------- Menu des lois -----------------------------------------------------
        Menu loiMenu = new Menu("Lois");
        RadioMenuItem uniforme = new RadioMenuItem("Loi uniforme");
        uniforme.setSelected(true);
        uniforme.setOnAction(e-> monde.setLoi("uniforme"));
        RadioMenuItem gaussienne = new RadioMenuItem("Loi Gaussienne");
        gaussienne.setOnAction(e-> monde.setLoi("gaussienne"));
        RadioMenuItem exponentiel = new RadioMenuItem("Loi de Poisson");
        exponentiel.setOnAction(e -> monde.setLoi("exponentiel"));

        ToggleGroup tgLoi = new ToggleGroup();
        uniforme.setToggleGroup(tgLoi);
        gaussienne.setToggleGroup(tgLoi);
        exponentiel.setToggleGroup(tgLoi);

//      ---------------------------------------- Menu des exemples -----------------------------------------------------
        Menu exemplesMenu = new Menu("Exemples");

        MenuItem bifurcations = new MenuItem("Bifurcations");
        bifurcations.setOnAction(actionEvent -> monde.ouvrirExemple(ExemplesDeMondes.getInstance().bifurcations()));

        MenuItem guichetsSorties = new MenuItem("Guichets avec 2 sorties");
        guichetsSorties.setOnAction(actionEvent -> monde.ouvrirExemple(ExemplesDeMondes.getInstance().guichets()));

//      ---------------------------------------- Menu des styles -------------------------------------------------------
        Menu styleMenu = new Menu("Styles");
        ToggleGroup toggleGroup = new ToggleGroup();

        RadioMenuItem style1 = new RadioMenuItem("Style par défaut");
        style1.setToggleGroup(toggleGroup);
        style1.setSelected(true);
        style1.setOnAction(actionEvent -> {
            stage.getScene().getStylesheets().clear();
            stage.getScene().getStylesheets().add("/styles/style.css");
        });

        RadioMenuItem style2 = new RadioMenuItem("Style 2");
        style2.setToggleGroup(toggleGroup);
        style2.setOnAction(actionEvent -> {
            stage.getScene().getStylesheets().clear();
            stage.getScene().getStylesheets().add("/styles/style2.css");
        });

        RadioMenuItem style3 = new RadioMenuItem("Style 3");
        style3.setToggleGroup(toggleGroup);
        style3.setOnAction(actionEvent -> {
            stage.getScene().getStylesheets().clear();
            stage.getScene().getStylesheets().add("/styles/style3.css");
        });


//      ----------------------------------------------- Ajout des items ------------------------------------------------
        fileMenu.getItems().addAll(open,save,quit);
        editMenu.getItems().addAll(delete, rename, cancel);
        worldMenu.getItems().addAll(input, output);
        settingsMenu.getItems().addAll(delay, ecart, jeton, clients);
        loiMenu.getItems().addAll(uniforme,gaussienne,exponentiel);
        exemplesMenu.getItems().addAll(bifurcations, guichetsSorties);
        styleMenu.getItems().addAll(style1, style2,style3);
//      ----------------------------------------------- Ajout des menus ------------------------------------------------
        this.getMenus().addAll(fileMenu, editMenu, worldMenu, settingsMenu,loiMenu, exemplesMenu, styleMenu);
    }
}
