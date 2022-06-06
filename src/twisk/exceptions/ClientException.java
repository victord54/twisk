/**
 * Classe d'exception liée aux Clients.
 * @author Victor Dallé & Claire Kurth
 */

package twisk.exceptions;

import javafx.animation.PauseTransition;
import javafx.scene.control.Alert;
import javafx.util.Duration;

public class ClientException extends TwiskException{
    /**
     * Constructeur.
     * @param s Message de l'exception à afficher.
     */
    public ClientException(String s) {
        super(s);
    }

    /**
     * Méthode qui permet d'afficher une Alert avec le message de l'exception.
     */
    @Override
    public void afficherMessage() {
        Alert a = new Alert(Alert.AlertType.ERROR, this.getMessage());
        a.setTitle("Erreur utilisateur");
        a.setHeaderText("Erreur sur les clients !");
        a.show();
        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(n -> a.close());
        pause.play();
    }
}
