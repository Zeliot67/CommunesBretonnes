package controleur;

import modele.dao.UserDAO;
import modele.data.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

/**
 * Controller for managing user registration.
 * @author Eliot ZIEGELMEYER, Iann VION, Ilias BELOUAHRANI
 * @version 1.0
 */
public class InscriptionControleur {

    /** Button to switch to the login page. */
    @FXML
    private Button seConnecterButton;

    /** ImageView for displaying the logo. */
    @FXML
    private ImageView logoImageView;

    /** TextField for entering the user's email */
    @FXML
    private TextField emailField;

    /** TextField for entering the user's username. */
    @FXML
    private TextField usernameField;

    /** PasswordField for entering the user's password. */
    @FXML
    private PasswordField passwordField;

    /** PasswordField for confirming the user's password. */
    @FXML
    private PasswordField confirmPasswordField;

    /**
     * Initializes the controller. Configures the login button to switch pages.
     */
    @FXML
    private void initialize() {
        this.seConnecterButton.setOnAction(event -> switchToPage("/pageconnexion.fxml"));
    }

    /**
     * Switches the current scene to the one specified by the FXML file.
     * @param fxmlFile the path to the FXML file to load.
     */
    private void switchToPage(String fxmlFile) {
        try {
            Stage stage = (Stage) seConnecterButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the registration of a new user.
     * Validates the fields, creates a new user, and adds it to the database.
     */
    @FXML
    private void handleInscription(ActionEvent event) {
        String email = this.emailField.getText();
        String username = this.usernameField.getText();
        String password = this.passwordField.getText();
        String confirmPassword = this.confirmPasswordField.getText();
        User user;

        this.resetFieldStyles();

        if (!password.equals(confirmPassword)) {
            this.showAlert(AlertType.ERROR, "Erreur de validation", "Les mots de passe ne correspondent pas.");
            this.passwordField.setStyle("-fx-border-color: red;");
            this.confirmPasswordField.setStyle("-fx-border-color: red;");
            return;
        }

        try {
            user = new User(username, password, email);
            UserDAO userDao = new UserDAO();
            List<User> uList = userDao.findAll();
            for (User u : uList) {
                if (u.getLogin().equals(username)) {
                    this.showAlert(AlertType.ERROR, "Utilisateur deja existant", "Un utilisateur possede deja ce login.");
                    return;
                }
            }
            int result = userDao.create(user);
            if (result == -1) {
                this.showAlert(AlertType.ERROR, "Erreur de la base de donnees", "Echec de la creation de l'utilisateur dans la base de donnees.");
                return;
            }
        } catch (IllegalArgumentException exception) {
            this.showAlert(AlertType.ERROR, "Erreur de validation", exception.getMessage());
            this.highlightEmptyFields(email, username, password, confirmPassword);
            return;
        }
        this.showAlert(AlertType.INFORMATION, "Inscription reussie", "Vous etes inscrit avec succes !");
        this.switchToPage("/pageconnexion.fxml");
    }

    /**
     * Resets the styles of the text fields.
     */
    private void resetFieldStyles() {
        this.emailField.setStyle(null);
        this.usernameField.setStyle(null);
        this.passwordField.setStyle(null);
        this.confirmPasswordField.setStyle(null);
    }

    /**
     * Highlights empty fields by surrounding them with a red border.
     * @param email the user's email.
     * @param username the user's username.
     * @param password the user's password.
     * @param confirmPassword the confirmation of the user's password.
     */
    private void highlightEmptyFields(String email, String username, String password, String confirmPassword) {
        if (email.isEmpty()) {
            this.emailField.setStyle("-fx-border-color: red;");
        }
        if (username.isEmpty()) {
            this.usernameField.setStyle("-fx-border-color: red;");
        }
        if (password.isEmpty()) {
            this.passwordField.setStyle("-fx-border-color: red;");
        }
        if (confirmPassword.isEmpty()) {
            this.confirmPasswordField.setStyle("-fx-border-color: red;");
        }
    }

    /**
     * Displays an alert with the specified type, title, and message.
     * @param alertType the type of the alert.
     * @param title the title of the alert.
     * @param message the message of the alert.
     */
    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
