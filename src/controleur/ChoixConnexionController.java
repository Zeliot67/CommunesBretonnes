package controleur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.IOException;

/**
 * The ChoixConnexionController class manages the choice of connection view of the application.
 * @author Eliot ZIEGELMEYER, Iann VION, Ilias BELOUAHRANI
 * @version 1.0
 */
public class ChoixConnexionController {

    /** Button to navigate to the registration page. */
    @FXML
    private Button inscriptionButton;

    /** Button to navigate to the login page. */
    @FXML
    private Button connexionButton;

    /**
     * Initializes the controller class. This method is automatically called after the FXML file has been loaded.
     * Sets up the action handlers for the buttons.
     */
    @FXML
    private void initialize() {
        this.inscriptionButton.setOnAction(event -> switchToPage("/inscription.fxml"));
        this.connexionButton.setOnAction(event -> switchToPage("/pageconnexion.fxml"));
    }

    /**
     * Switches to a new page specified by the FXML file.
     * @param fxmlFile the FXML file of the new page
     */
    private void switchToPage(String fxmlFile) {
        try {
            Stage stage = (Stage) inscriptionButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
