package twisk;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import twisk.mondeIG.MondeIG;
import twisk.vues.VueMenu;
import twisk.vues.VueMondeIG;
import twisk.vues.VueOutils;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        MondeIG monde = new MondeIG();
        stage.setTitle("Twisk");
        BorderPane root = new BorderPane();
        root.setId("root");

        root.setTop(new VueMenu(monde, stage));
        root.setCenter(new VueMondeIG(monde));
        root.setBottom(new VueOutils(monde));

        Scene scene = new Scene(root, 1280, 720);
        scene.getStylesheets().add("/styles/style.css");
        stage.getIcons().add(new Image("/images/logo.png"));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}