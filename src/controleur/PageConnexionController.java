package controleur;

import modele.dao.UserDAO;
import modele.data.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Controller for the login page.
 * This class handles the logic for user authentication and navigation to other pages.
 * @author Eliot ZIEGELMEYER, Iann VION, Ilias BELOUAHRANI
 * @version 1.0
 */
public class PageConnexionController {

    /** Button to navigate to the registration page. */
    @FXML
    private Button inscrireButton;

    /** Button to navigate to the "Forgot Password" page. */
    @FXML
    private Button motDePasseOublieButton;

    /** Button to initiate the login process. */
    @FXML
    private Button seConnecterButton;

    /** TextField for entering the username. */
    @FXML
    private TextField usernameField;

    /** PasswordField for entering the password. */
    @FXML
    private PasswordField passwordField;
    
    /**
     * Initializes the controller class.
     * Sets up the action handlers for the buttons.
     */
    @FXML
    private void initialize() {
        this.inscrireButton.setOnAction(event -> switchToPage("/inscription.fxml"));
        this.motDePasseOublieButton.setOnAction(event -> switchToPage("/motdepasseoublie.fxml"));
    }

    private void switchToPage(String fxmlFile) {
        try {
            Stage stage = (Stage) inscrireButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void switchToPageWithUser(String fxmlFile, User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load(); // Charger le fichier FXML en premier
    
            // Obtenir le contrôleur de la deuxième page et lui passer l'utilisateur
            AccueilController controller = loader.getController();
            controller.setUser(user);
    
            Stage stage = (Stage) this.inscrireButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the login process when the "Se connecter" button is clicked.
     * It validates the input fields and checks the credentials.
     * If any validation fails, it shows an appropriate alert.
     */
    @FXML
    private void handleConnexion(ActionEvent event) {
        String username = this.usernameField.getText();
        String password = this.passwordField.getText();

        this.resetFieldStyles();

        if (username.isEmpty() || password.isEmpty()) {
            this.showAlert(AlertType.ERROR, "Erreur de validation", "Veuillez remplir tous les champs.");
            this.highlightEmptyFields(username, password);
            return;
        }

        UserDAO userDao = new UserDAO();
        User user = userDao.findByLoginPwd(username, password);
        if (user == null) {
            this.showAlert(AlertType.ERROR, "Erreur de connexion", "Nom d'utilisateur ou mot de passe invalide.");
            this.usernameField.setStyle("-fx-border-color: red;");
            this.passwordField.setStyle("-fx-border-color: red;");
            return;
        }
        this.showAlert(AlertType.INFORMATION, "Connexion reussie", "Vous vous etes connecte avec succes !");
        this.switchToPageWithUser("/accueil.fxml", user);
    }

    /**
     * Resets the styles of the input fields to their default state.
     */
    private void resetFieldStyles() {
        this.usernameField.setStyle(null);
        this.passwordField.setStyle(null);
    }

    /**
     * Highlights the empty input fields by setting their border color to red.
     * @param username the username input
     * @param password the password input
     */
    private void highlightEmptyFields(String username, String password) {
        if (username.isEmpty()) {
            this.usernameField.setStyle("-fx-border-color: red;");
        }
        if (password.isEmpty()) {
            this.passwordField.setStyle("-fx-border-color: red;");
        }
    }

    /**
     * Shows an alert with the specified type, title, and message.
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
