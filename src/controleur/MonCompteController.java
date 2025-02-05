package controleur;

import modele.dao.UserDAO;
import modele.data.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import java.io.IOException;

/**
 * Controller for managing the user's account.
 * @author Eliot ZIEGELMEYER, Iann VION, Ilias BELOUAHRANI
 * @version 1.0
 */
public class MonCompteController {

    /** TextField for entering the user's email. */
    @FXML
    private TextField emailField;

    /** TextField for displaying the user's ID (username). */
    @FXML
    private TextField idField;

    /** TextField for entering the user's password. */
    @FXML
    private TextField passwordField;

    /** TextField for confirming the user's password. */
    @FXML
    private TextField confirmPasswordField;

    /** Button to save the user's account information. */
    @FXML
    private Button sauvegarderButton;

    /** ImageView for navigating back to the previous page. */
    @FXML
    private ImageView backImage;

    /** The user whose account is being managed. */
    private User user;

    /**
     * Sets the user and initializes the fields with the user's information.
     * @param user the user to set.
     */
    public void setUser(User user) {
        this.user = user;
        this.initializeUser();
    }

    /**
     * Initializes the fields with the user's information.
     */
    private void initializeUser() {
        if (user != null) {
            this.emailField.setText(user.getEmail());
            this.idField.setText(user.getLogin());
            this.idField.setDisable(true);
            this.passwordField.setText(user.getPwd());
            this.confirmPasswordField.setText(user.getPwd());
        }
    }

    /**
     * Handles saving the user's account information.
     * Validates the fields and updates the user in the database.
     */
    @FXML
    private void handleSauvegarder(ActionEvent event) {
        String email = this.emailField.getText();
        String username = this.idField.getText();
        String password = this.passwordField.getText();
        String confirmPassword = this.confirmPasswordField.getText();

        this.resetFieldStyles();

        if (!password.equals(confirmPassword)) {
            this.showAlert(AlertType.ERROR, "Erreur de validation", "Les mots de passe ne correspondent pas.");
            this.passwordField.setStyle("-fx-border-color: red;");
            this.confirmPasswordField.setStyle("-fx-border-color: red;");
            return;
        }

        try {
            this.user.setEmail(email);
            this.user.setPwd(password);
            UserDAO userDao = new UserDAO();
            int result = userDao.update(user);
            if (result == -1) {
                this.showAlert(AlertType.ERROR, "Erreur de la base de donnes", "Echec de la mise a jour de l'utilisateur dans la base de donnees.");
                return;
            }
        } catch (IllegalArgumentException exception) {
            this.showAlert(AlertType.ERROR, "Erreur de validation", exception.getMessage());
            this.highlightEmptyFields(email, username, password, confirmPassword);
            return;
        }
        this.showAlert(AlertType.INFORMATION, "Mise a jour reussie", "Votre compte a ete mis a jour avec succes !");
    }

    /**
     * Handles navigating back to the previous page.
     * @param event the mouse event triggered by clicking the back image.
     */
    @FXML
    private void handleBack(MouseEvent event) {
        this.switchToPageWithUser("/accueil.fxml", this.user);
    }

    /**
     * Switches the current scene to the one specified by the FXML file and passes the user to the new controller.
     * @param fxmlFile the path to the FXML file to load.
     * @param user the user to pass to the new controller.
     */
    private void switchToPageWithUser(String fxmlFile, User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            AccueilController controller = loader.getController();
            controller.setUser(user);

            Stage stage = (Stage) this.sauvegarderButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Resets the styles of the text fields.
     */
    private void resetFieldStyles() {
        this.emailField.setStyle(null);
        this.idField.setStyle(null);
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
            this.idField.setStyle("-fx-border-color: red;");
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