package controleur;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import modele.data.User;

/**
 * Controller for the second step of the "Forgot Password" process.
 * This class handles the logic for verifying the user's email address.
 * If the email matches the one associated with the user, it navigates to the next page.
 * Otherwise, it shows an error alert
 * @author Eliot ZIEGELMEYER, Iann VION, Ilias BELOUAHRANI
 * @version 1.0
 */
public class MotDePasseOublie2Controller {

    /** TextField for entering the user's email. */
    @FXML
    private TextField emailField;

    /** Button to continue the process after entering the email. */
    @FXML
    private Button continuerButton;

    /** ImageView for the back button image. */
    @FXML
    private ImageView backImage;

    /** The user whose email is being verified. */
    private User user;

    /**
     * Sets the user whose email is being verified.
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Handles the action of the back button.
     * Switches the scene to the login page.
     */
    @FXML
    private void handleBack(MouseEvent event) {
        this.switchToPage("/pageconnexion.fxml");
    }

    /**
     * Handles the action of the continue button.
     * Validates the email and checks if it matches the user's email.
     * If the email is correct, navigates to the next page; otherwise, shows an error alert.
     */
    @FXML
    private void handleContinuer(ActionEvent event) {
        String email = emailField.getText();
        if (this.user.getEmail().equals(email)) {
            this.switchToPageWithUser("/motdepasseoublie3.fxml", this.user);
        } else {
            this.showAlert(AlertType.ERROR, "Email incorrect", "L'email est incorrect.");
        }
    }

    /**
     * Shows an alert with the specified type, title, and message.
     *
     * @param alertType the type of the alert
     * @param title the title of the alert
     * @param message the message of the alert
     */
    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Switches the scene to the specified FXML file.
     * @param fxmlFile the path to the FXML file
     */
    private void switchToPage(String fxmlFile) {
        try {
            Stage stage = (Stage) this.backImage.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Switches the scene to the specified FXML file and passes the user data to the next controller.
     * @param fxmlFile the path to the FXML file
     * @param user the user data to pass to the next controller
     */
    private void switchToPageWithUser(String fxmlFile, User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            // Obtenir le contrôleur de la deuxième page et lui passer l'utilisateur
            MotDePasseOublie3Controller controller = loader.getController();
            controller.setUser(user);

            Stage stage = (Stage) continuerButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
