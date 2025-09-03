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
        // Auto-scroll as dialogContainer grows.
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Injects the Sakura instance.
     *
     * @param sakura Sakura instance to use for responses.
     */
    public void setSakura(Sakura sakura) {
        this.sakura = sakura;
    }

    /**
     * Called by FXML when the user hits Enter or clicks Send.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        if (input == null || input.isBlank()) {
            return;
        }

        String response = sakura.getResponse(input);

        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getSakuraDialog(response, sakuraImage)
        );

        userInput.clear();
    }
}
