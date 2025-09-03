package sakura.app;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Main application class for Hanami.
 * Launches the JavaFX application and sets up the primary stage.
 */
public class Main extends Application {

    private Sakura sakura = new Sakura();

    /**
     * Starts the JavaFX application by loading the main window.
     */
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();

            Scene scene = new Scene(ap);
            stage.setScene(scene);

            fxmlLoader.<MainWindow>getController().setSakura(sakura);

            stage.setTitle("Hanami");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
