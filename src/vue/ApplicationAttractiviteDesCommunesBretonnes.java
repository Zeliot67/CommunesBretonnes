package vue;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main application class for "Attractivite des communes bretonnes".
 * This class launches the JavaFX application and sets up the initial stage and scene.
 * @author Eliot ZIEGELMEYER, Iann VION, Ilias BELOUAHRANI
 * @version 1.0
 */
public class ApplicationAttractiviteDesCommunesBretonnes extends Application {

    /**
     * The main entry point for all JavaFX applications.
     * This method is called after the application is launched and sets up the primary stage.
     * @param stage the primary stage for this application
     * @throws Exception if an error occurs during loading the FXML file
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/choixconnexion.fxml"));
        stage.setTitle("Attractivite des communes bretonnes");
        stage.setScene(new Scene(root, 1280, 800));
        stage.show();
    }

    /**
     * The main method which serves as the entry point for the application.
     * It launches the JavaFX application.
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(ApplicationAttractiviteDesCommunesBretonnes.class, args);
    }
}
