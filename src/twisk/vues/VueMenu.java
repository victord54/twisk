package twisk.vues;

import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import twisk.exceptions.EtapeTwiskException;
import twisk.exceptions.GuichetTwiskException;
import twisk.mondeIG.MondeIG;
import twisk.outils.ThreadsManager;

import java.io.File;


public class VueMenu extends MenuBar {

    public VueMenu(MondeIG monde, Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json"));
//      ------------------------------------------------- Menu Fichier -------------------------------------------------
        Menu fileMenu = new Menu("Fichier");
        MenuItem quit = new MenuItem("Quitter");
        quit.setOnAction(actionEvent -> {
            ThreadsManager.getInstance().detruiretout();
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
        clients.setOnAction(actionEvent -> monde.setNbClients());
//      ----------------------------------------------- Ajout des items ------------------------------------------------
        fileMenu.getItems().addAll(open,save,quit);
        editMenu.getItems().addAll(delete, rename, cancel);
        worldMenu.getItems().addAll(input, output);
        settingsMenu.getItems().addAll(delay, ecart, jeton, clients);
//      ----------------------------------------------- Ajout des menus ------------------------------------------------
        this.getMenus().addAll(fileMenu, editMenu, worldMenu, settingsMenu);
    }
}
