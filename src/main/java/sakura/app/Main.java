package sakura.app;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * The JavaFX entry point for the Sakura chatbot GUI application.
 * Sets up the main window and links the Sakura logic to the controller.
 */
public class Main extends Application {

    /** The core Sakura chatbot instance used by the GUI. */
    private Sakura sakura = new Sakura();

    /**
     * Starts the JavaFX application and initializes the main window.
     *
     * @param stage the primary stage for this application
     */
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(
                    Main.class.getResource("/view/MainWindow.fxml"));
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
