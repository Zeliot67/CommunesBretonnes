package controleur;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
import java.io.IOException;
import java.util.List;

/**
 * Controller for managing the forgot password functionnality.
 * @author Eliot ZIEGELMEYER, Iann VION, Ilias BELOUAHRANI
 * @version 1.0
 */
public class MotDePasseOublieController {

    /** TextField for entering the user's username. */
    @FXML
    private TextField usernameField;

    /** Button to continue the process after entering the username. */
    @FXML
    private Button continuerButton;

    /** ImageView for the back button image. */
    @FXML 
    private ImageView backImage;

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
     * Validates the username and checks if it exists in the database.
     * If the username is found, navigates to the next page; otherwise, shows an error alert.
     */
    @FXML
    private void handleContinuer(ActionEvent event) {
        String username = this.usernameField.getText();

        if (username.isEmpty()) {
            this.showAlert(AlertType.ERROR, "Erreur de validation", "Veuillez remplir tous les champs.");
            this.usernameField.setStyle("-fx-border-color: red;");
            return;
        }

        UserDAO userDao = new UserDAO();
        List<User> uList = userDao.findAll();
        for (User u : uList) {
            if (u.getLogin().equals(username)) {
                this.showAlert(AlertType.INFORMATION, "Utilisateur trouve", "Nom d'utilisateur trouve.");
                this.switchToPageWithUser("/motdepasseoublie2.fxml", u);
                return;
            }
        }
        this.showAlert(AlertType.ERROR, "Utilisateur inexistant", "L'identifiant est associe a aucun utilisateur.");
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
            MotDePasseOublie2Controller controller = loader.getController();
            controller.setUser(user);

            Stage stage = (Stage) continuerButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
