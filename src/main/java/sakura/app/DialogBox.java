package sakura.app;

import java.io.IOException;
import javafx.scene.Node;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Represents a dialog box consisting of an ImageView and a Label.
 * Uses fx:root in DialogBox.fxml and sets controller/root in constructor.
 */
public class DialogBox extends HBox {

    private static final String FXML_PATH = "/view/DialogBox.fxml";

    @FXML
    private Label dialog;

    @FXML
    private ImageView displayPicture;

    /**
     * Constructs a DialogBox with given text and image.
     */
    public DialogBox(String text, Image img) {
        assert text != null : "Dialog text must not be null";
        assert img != null : "Display image must not be null";

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource(FXML_PATH));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            assert false : "FXML loading failed for " + FXML_PATH;
        }

        assert dialog != null : "Dialog label must be injected";
        assert displayPicture != null : "DisplayPicture must be injected";

        dialog.setText(text);
        displayPicture.setImage(img);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        assert getChildren() != null : "DialogBox children must not be null";

        // Reverse the children manually (instead of stream)
        int size = getChildren().size();
        for (int i = 0; i < size / 2; i++) {
            Node temp = getChildren().get(i);
            getChildren().set(i, getChildren().get(size - 1 - i));
            getChildren().set(size - 1 - i, temp);
        }

        setAlignment(Pos.TOP_LEFT);
    }

    /**
     * Factory method for creating user dialog.
     * @return a DialogBox for the user
     */
    public static DialogBox getUserDialog(String text, Image img) {
        assert text != null : "User dialog text must not be null";
        assert img != null : "User dialog image must not be null";
        return new DialogBox(text, img);
    }

    /**
     * Factory method for creating Sakura's dialog (flipped layout).
     * @return a DialogBox for Sakura
     */
    public static DialogBox getSakuraDialog(String text, Image img) {
        assert text != null : "Sakura dialog text must not be null";
        assert img != null : "Sakura dialog image must not be null";

        DialogBox dialogBox = new DialogBox(text, img);
        dialogBox.flip();
        return dialogBox;
    }
}