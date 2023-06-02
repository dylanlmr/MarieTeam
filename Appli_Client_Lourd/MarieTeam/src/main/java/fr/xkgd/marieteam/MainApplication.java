package fr.xkgd.marieteam;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Objects;

/**
 * Classe principale de l'application
 */
@SuppressWarnings("SpellCheckingInspection")
public class MainApplication extends Application {

    /**
     * Méthode permettant de lancer l'application
     * @param stage Fenêtre principale de l'application
     * @throws Exception Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("view/MainView.fxml")));
        Scene scene = new Scene(root);
        stage.setTitle("MarieTeam !");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Méthode principale de l'application
     * @param args Arguments
     */
    public static void main(String[] args) {
        launch();
    }
}