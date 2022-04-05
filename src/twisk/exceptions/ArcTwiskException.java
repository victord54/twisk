package twisk.exceptions;

import javafx.animation.PauseTransition;
import javafx.scene.control.Alert;
import javafx.util.Duration;

public class ArcTwiskException extends TwiskException {
    public ArcTwiskException(String s) {
        super(s);
    }

    @Override
    public void afficherMessage() {
        Alert a = new Alert(Alert.AlertType.ERROR, this.getMessage());
        a.setTitle("Erreur utilisateur");
        a.setHeaderText("Erreur sur un arc !");
        a.show();
        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(n -> a.close());
        pause.play();
    }
}
