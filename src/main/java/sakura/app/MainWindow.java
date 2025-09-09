package sakura.app;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI.
 */
public class MainWindow {

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private VBox dialogContainer;

    @FXML
    private TextField userInput;

    @FXML
    private Button sendButton;

    private Sakura sakura;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/cherubi.png"));
    private Image sakuraImage = new Image(this.getClass().getResourceAsStream("/images/comfey.png"));

    @FXML
    public void initialize() {
        // auto-scroll as dialogContainer grows
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Sakura instance */
    public void setSakura(Sakura sakuraInstance) {
        this.sakura = sakuraInstance;
    }

    /**
     * Called by FXML when user hits Enter or clicks Send.
     */
    @FXML
    private void handleUserInput() {
        String inputText = userInput.getText();

        if (inputText == null || inputText.isBlank()) {
            return;
        }

        String responseText = sakura.getResponse(inputText);

        // Add user dialog
        dialogContainer.getChildren().add(DialogBox.getUserDialog(inputText, userImage));

        // Add Sakura dialog
        dialogContainer.getChildren().add(DialogBox.getSakuraDialog(responseText, sakuraImage));

        userInput.clear();
    }
}
