package sakura.app;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

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
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    public void setSakura(Sakura sakuraInstance) {
        this.sakura = sakuraInstance;
    }

    @FXML
    private void handleUserInput() {
        String inputText = userInput.getText();
        if (inputText == null || inputText.isBlank()) {
            return;
        }

        String responseText = sakura.getResponse(inputText);

        var userDialog = DialogBox.getUserDialog(inputText, userImage);
        userDialog.getChildren().forEach(node -> {
            if (node instanceof javafx.scene.control.Label label) {
                label.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-font-family: 'Arial';");
            }
        });
        dialogContainer.getChildren().add(userDialog);

        var sakuraDialog = DialogBox.getSakuraDialog(responseText, sakuraImage);
        sakuraDialog.getChildren().forEach(node -> {
            if (node instanceof javafx.scene.control.Label label) {
                label.setStyle("-fx-text-fill: lightpink; -fx-font-size: 16px; -fx-font-weight: bold; -fx-font-family: 'Arial';");
            }
        });
        dialogContainer.getChildren().add(sakuraDialog);

        userInput.clear();
    }

}
