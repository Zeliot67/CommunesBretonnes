package controleur;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

import javafx.event.ActionEvent;
import java.io.IOException;

import javafx.scene.image.ImageView;
import modele.data.User;

/**
 * The CartographieController class manages the cartography view of the application.
 * @author Eliot ZIEGELMEYER, Iann VION, Ilias BELOUAHRANI
 * @version 1.0
 */
public class CartographieController {

    /** Button to display the degree map. */
    @FXML
    private Button degreButton;

    /** Button to display the naive map. */
    @FXML
    private Button naifButton;

    /** Button to display the Welsh map. */
    @FXML
    private Button welshButton;

    /** Button to display the eccentricity map. */
    @FXML
    private Button exentriciteButton;

    /** ImageView to display the Brittany map. */
    @FXML
    private ImageView bretagneImage;

    /** ImageView to display the naive map. */
    @FXML
    private ImageView naifImage;

    /** ImageView to display the Welsh map. */
    @FXML
    private ImageView welshImage;

    /** ImageView to display the eccentricity map. */
    @FXML
    private ImageView exentriciteImage;

    /** The home image used to go to the home  */
    @FXML
    private ImageView homeImage;

    /** The user using the application */
    private User user;

    /**
     * Sets the user and initializes the user interface with the user login.
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Method used to go back to the home menu of the application, by clicking on the home image.
     * @param event the mouse clicked event
     */
    @FXML
    private void handleHome(MouseEvent event) {
        this.switchToPageWithUser("/accueil.fxml", this.user);
    }

    /**
     * Handles the event of clicking the degree button to display the degree map.
     * @param event the action event
     */
    @FXML
    private void handleDegre(ActionEvent event) {
        this.resetImage();
        this.bretagneImage.setVisible(true);
    }

    /**
     * Handles the event of clicking the eccentricity button to display the eccentricity map.
     * @param event the action event
     */
    @FXML
    private void handleExentricite(ActionEvent event) {
        this.resetImage();
        this.exentriciteImage.setVisible(true);
    }

    /**
     * Handles the event of clicking the naive button to display the naive map.
     * @param event the action event
     */
    @FXML
    private void handleNaif(ActionEvent event) {
        this.resetImage();
        this.naifImage.setVisible(true);
    }

    /**
     * Handles the event of clicking the Welsh button to display the Welsh map.
     * @param event the action event
     */
    @FXML
    private void handleWelsh(ActionEvent event) {
        this.resetImage();
        this.welshImage.setVisible(true);
    }

    /**
     * Resets the visibility of all map images.
     */
    private void resetImage() {
        this.bretagneImage.setVisible(false);
        this.exentriciteImage.setVisible(false);
        this.naifImage.setVisible(false);
        this.welshImage.setVisible(false);
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

            AccueilController controller = loader.getController();
            controller.setUser(user);

            Stage stage = (Stage) this.homeImage.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
