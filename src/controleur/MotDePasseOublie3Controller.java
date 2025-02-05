package controleur;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import modele.dao.UserDAO;
import modele.data.User;

/**
 * Controller for the third step of the "Forgot Password" process.
 * This class handles the logic for resetting the user's password.
 * It validates the new password and updates it in the database if valid.
 * @author Eliot ZIEGELMEYER, Iann VION, Ilias BELOUAHRANI
 * @version 1.0
 */
public class MotDePasseOublie3Controller {

    /** Button to reset the password. */
    @FXML
    private Button reinitialiserButton;

    /** PasswordField for entering the new password. */
    @FXML
    private PasswordField passwordField;

    /** PasswordField for confirming the new password. */
    @FXML
    private PasswordField confirmPasswordField;

    /** ImageView for the back button image. */
    @FXML
    private ImageView backImage;

    /** The user whose password is being reset. */
    private User user;

    /**
     * Sets the user whose password is being reset.
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
     * Handles the action of the reset button.
     * Validates the new password and updates it in the database if valid.
     * If the passwords do not match or are empty, shows an error alert.
     */
    @FXML
    private void handleReinitialiser(ActionEvent event) {
        String password = this.passwordField.getText();
        String confirmPassword = this.confirmPasswordField.getText();

        this.resetFieldStyles();

        if (password.isEmpty() || confirmPassword.isEmpty()) {
            this.showAlert(AlertType.ERROR, "Erreur de validation", "Veuillez remplir tous les champs.");
            this.highlightEmptyFields(password, confirmPassword);
            return;
        }

        if (!password.equals(confirmPassword)) {
            this.showAlert(AlertType.ERROR, "Erreur de validation", "Les mots de passe ne correspondent pas.");
            this.passwordField.setStyle("-fx-border-color: red;");
            this.confirmPasswordField.setStyle("-fx-border-color: red;");
            return;
        }

        try {
            user.setPwd(confirmPassword);
            UserDAO userDao = new UserDAO();
            int result = userDao.update(user);
            if (result == -1) {
                this.showAlert(AlertType.ERROR, "Erreur de la base de donnees", "Echec de la mise a jour de l'utilisateur dans la base de donnees.");
                return;
            }
        } catch (IllegalArgumentException exception) {
            this.showAlert(AlertType.ERROR, "Erreur de validation", exception.getMessage());
            this.highlightEmptyFields(password, confirmPassword);
            return;
        }
        this.showAlert(AlertType.INFORMATION, "Reinitialisation reussi", "Votre mot de passe a ete reinitialise avec succes !");
        this.switchToPage("/pageconnexion.fxml");
    }

    /**
     * Resets the styles of the input fields to their default state.
     */
    private void resetFieldStyles() {
        this.passwordField.setStyle(null);
        this.confirmPasswordField.setStyle(null);
    }

    /**
     * Highlights the empty input fields by setting their border color to red.
     *
     * @param username the username input
     * @param password the password input
     */
    private void highlightEmptyFields(String password, String confirmPassword) {
        if (password.isEmpty()) {
            this.passwordField.setStyle("-fx-border-color: red;");
        }
        if (confirmPassword.isEmpty()) {
            this.confirmPasswordField.setStyle("-fx-border-color: red;");
        }
    }

    /**
     * Switches the scene to the specified FXML file.
     * @param fxmlFile the path to the FXML file
     */
    private void switchToPage(String fxmlFile) {
        try {
            Stage stage = (Stage) reinitialiserButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
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
}
