package twisk.vues;

import javafx.application.Platform;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import twisk.exceptions.EtapeTwiskException;
import twisk.mondeIG.MondeIG;


public class VueMenu extends MenuBar {

    public VueMenu(MondeIG monde) {
//      ------------------------------------------------- Menu Fichier -------------------------------------------------
        Menu fileMenu = new Menu("Fichier");
        MenuItem quit = new MenuItem("Quitter");
        quit.setOnAction(actionEvent -> Platform.exit());
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
//      ----------------------------------------------- Ajout des items ------------------------------------------------
        fileMenu.getItems().add(quit);
        editMenu.getItems().addAll(delete, rename, cancel);
        worldMenu.getItems().addAll(input, output);
        settingsMenu.getItems().addAll(delay, ecart);
//      ----------------------------------------------- Ajout des menus ------------------------------------------------
        this.getMenus().addAll(fileMenu, editMenu, worldMenu, settingsMenu);
    }
}
