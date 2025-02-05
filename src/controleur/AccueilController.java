package controleur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import modele.data.User;

import java.io.IOException;

/**
 * The AccueilController class manages the main view of the application.
 * @author Eliot ZIEGELMEYER, Iann VION, Ilias BELOUAHRANI
 * @version 1.0
 */
public class AccueilController {

    /** Button to navigate to the cartography page. */
    @FXML
    private Button cartographieButton;

    /** Button to navigate to the data page. */
    @FXML
    private Button donneesButton;

    /** Button to navigate to the comparison page. */
    @FXML
    private Button comparaisonButton;

    /** Button to navigate to the general data page. */
    @FXML
    private Button donneesGButton;

    /** Button to log out the user. */
    @FXML
    private Button meDeconnecterButton;

    /** Button to navigate to the account modification page. */
    @FXML
    private Button modifierMonCompteButton;

    /** Button to navigate to the administration page. */
    @FXML
    private Button administrationButton;

    /** Label to display the user's login. */
    @FXML
    private Label userLabel;

    /** The user */
    private User user;

    /**
     * Sets the user and initializes the user interface with the user login.
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
        this.initializeUser();
    }

    /**
     * Initializes the user interface with the user's login information.
     */
    private void initializeUser() {
        if (user != null) {
            this.userLabel.setText(user.getLogin());
        }
    }

    /**
     * Initializes the controller class. This method is automatically called after the FXML file has been loaded.
     */
    @FXML
    private void initialize() {
        this.cartographieButton.setOnAction(event -> this.switchToPageWithUser("/cartographie.fxml", this.user));
        this.donneesButton.setOnAction(event -> this.switchToPageWithUser("/donnees.fxml", this.user));
        this.comparaisonButton.setOnAction(event -> this.switchToPageWithUser("/comparaison.fxml", this.user));
        this.meDeconnecterButton.setOnAction(event -> this.switchToPageWithUser("/pageconnexion.fxml", this.user));
        this.modifierMonCompteButton.setOnAction(event -> this.switchToPageWithUser("/moncompte.fxml", this.user));
        this.administrationButton.setOnAction(event -> this.switchToPageAdmin("/administration.fxml", this.user));
        this.donneesGButton.setOnAction(event -> this.switchToPageWithUser("/donneesGenerales.fxml", this.user));

        this.addHoverEffect(cartographieButton);
        this.addHoverEffect(donneesButton);
        this.addHoverEffect(comparaisonButton);
        this.addHoverEffect(donneesGButton);
    }

    /**
     * Adds a hover effect to the specified button.
     * @param button the button to which the hover effect is added
     */
    private void addHoverEffect(Button button) {
        button.setOnMouseEntered(event -> button.setStyle("-fx-font-size: 18px; -fx-background-color: lightblue; -fx-font-weight: bold; -fx-text-fill: white;"));
        button.setOnMouseExited(event -> button.setStyle("-fx-font-size: 18px; -fx-background-color: transparent; -fx-font-weight: bold; -fx-text-fill: white;"));
    }

    /**
     * Switches to a new page and passes the user information to the new controller.
     * @param fxmlFile the FXML file of the new page
     * @param user the user to pass to the new controller
     */
    private void switchToPageWithUser(String fxmlFile, User user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent root = loader.load();

            if (fxmlFile.contains("cartographie")) {
                CartographieController controller = loader.getController();
                controller.setUser(this.user);
            } else if (fxmlFile.contains("moncompte")) {
                MonCompteController controller = loader.getController();
                controller.setUser(this.user);
            } else if (fxmlFile.contains("donneesGenerales")) {
                DonneesGeneralesController controller = loader.getController();
                controller.setUser(this.user);
            } else if (fxmlFile.contains("donnees")) {
                DonneesController controller = loader.getController();
                controller.setUser(this.user);
            } else if (fxmlFile.contains("comparaison")) {
                ComparaisonController controller = loader.getController();
                controller.setUser(this.user);
            }

            Stage stage = (Stage) this.cartographieButton.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Switches to the admin page if the user has admin privileges.
     * @param fxmlFile the FXML file of the admin page
     * @param user the user to pass to the new controller
     */
    private void switchToPageAdmin(String fxmlFile, User user) {
        try {
            if (this.user.getLogin().equals("root")) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
                Parent root = loader.load();

                AdministrateurController controller = loader.getController();
                controller.setUser(this.user);

                Stage stage = (Stage) this.cartographieButton.getScene().getWindow();
                stage.setScene(new Scene(root));
            } else {
                this.showAlert(AlertType.ERROR, "Acces interdit", "Vous devez etre connecte en tant qu'administrateur pour acceder a cette page.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays an alert with the specified type, title, and message.
     * @param alertType the type of alert
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