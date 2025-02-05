package controleur;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import modele.dao.CommuneDAO;
import modele.dao.DonneesAnnuellesDAO;
import modele.data.Commune;
import modele.data.DonneesAnnuelles;
import modele.data.User;


/**
 * The ComparaisonController class manages the comparison view of the application.
 * @author Eliot ZIEGELMEYER, Iann VION, Ilias BELOUAHRANI
 * @version 1.0
 */
public class ComparaisonController {

    /** ComboBox to select the type of data to display. */
    @FXML
    private ComboBox<String> displayBox;

    /** Button to trigger the display of the selected data. */
    @FXML
    private Button displayButton;

    /** ComboBox to select the first town for comparison. */
    @FXML
    private ComboBox<String> townNameComboBox;

    /** ComboBox to select the second town for comparison. */
    @FXML
    private ComboBox<String> townNameComboBox1;

    /** GridPane to display the first chart. */
    @FXML
    private GridPane gridPane;

    /** GridPane to display the second chart. */
    @FXML
    private GridPane gridPane1;

    /** The home image used to go to the home  */
    @FXML
    private ImageView homeImage;

    /** GridPane to display additional charts or information. */
    @FXML
    private GridPane gridPane2;

    /** The user using the application */
    private User user;

    /** The commune Data Object Access */
    private CommuneDAO communeDAO;

    /** The annual Data Data Object Access */
    private DonneesAnnuellesDAO donneesAnnuellesDAO;

    /**
     * Sets the user and initializes the user interface with the user login.
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Constructor for the ComparaisonController class.
     * Initializes the DAO objects.
     */
    public ComparaisonController() {
        this.communeDAO = new CommuneDAO();
        this.donneesAnnuellesDAO = new DonneesAnnuellesDAO();
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
     * Initializes the controller class. This method is automatically called after the FXML file has been loaded.
     * Sets up the ComboBoxes by sorting the commune names.
     */
    @FXML
    private void initialize() {
        List<String> communeNames = communeDAO.findAllCommuneNames();
        Collections.sort(communeNames);

        this.townNameComboBox.getItems().addAll(communeNames);
        this.townNameComboBox1.getItems().addAll(communeNames);
        this.displayBox.getItems().addAll("Evolution Population", "Evolution Budget", "Evolution Depenses Culturelles", "Evolution Prix Moyen", "Evolution Prix Moyen M2", "Evolution Surface Moyenne", "Repartition Maisons/Appartements", "Evolution Nombre de Maisons en vente", "Evolution Nombre d'Appartements en vente");
    }

    /**
     * Displays the information based on the selected towns and criteria.
     */
    @FXML
    private void afficherInformations() {
        String ville1 = townNameComboBox.getValue();
        String ville2 = townNameComboBox1.getValue();
        String critere = displayBox.getValue();

        if (ville1 != null && ville2 != null && critere != null) {
            Commune commune1 = communeDAO.findByName(ville1);
            Commune commune2 = communeDAO.findByName(ville2);

            this.afficherGraphique(commune1, commune2, critere);
        }
    }

    /**
     * Displays the chart based on the selected criteria for the two towns.
     *
     * @param commune1 the first town
     * @param commune2 the second town
     * @param critere the criteria for comparison
     */
    private void afficherGraphique(Commune commune1, Commune commune2, String critere) {
        this.gridPane.getChildren().clear();
        this.gridPane1.getChildren().clear();

        // Initialize axes and charts for the first commune
        CategoryAxis xAxis1 = new CategoryAxis();
        NumberAxis yAxis1 = new NumberAxis();
        LineChart<String, Number> lineChart1 = new LineChart<>(xAxis1, yAxis1);
        lineChart1.setTitle("Graphique de " + commune1.getCommuneName());

        // Initialize axes and charts for the second commune
        CategoryAxis xAxis2 = new CategoryAxis();
        NumberAxis yAxis2 = new NumberAxis();
        LineChart<String, Number> lineChart2 = new LineChart<>(xAxis2, yAxis2);
        lineChart2.setTitle("Graphique de " + commune2.getCommuneName());

        // Create data series for each commune
        XYChart.Series<String, Number> series1 = new XYChart.Series<>();
        XYChart.Series<String, Number> series2 = new XYChart.Series<>();
        series1.setName(commune1.getCommuneName());
        series2.setName(commune2.getCommuneName());

        // Retrieve annual data for each commune
        List<DonneesAnnuelles> donneesList1 = this.donneesAnnuellesDAO.findByCommune(commune1.getCommuneId());
        List<DonneesAnnuelles> donneesList2 = this.donneesAnnuellesDAO.findByCommune(commune2.getCommuneId());

        // Handle different comparison criteria
        switch (critere) {
            case "Evolution Population":
                for (DonneesAnnuelles donnees : donneesList1) {
                    if (donnees.getPopulation() != -1) {
                        series1.getData().add(new XYChart.Data<>(String.valueOf(donnees.getYear().getYear()), donnees.getPopulation()));
                    }
                }
                for (DonneesAnnuelles donnees : donneesList2) {
                    if (donnees.getPopulation() != -1) {
                        series2.getData().add(new XYChart.Data<>(String.valueOf(donnees.getYear().getYear()), donnees.getPopulation()));
                    }
                }
                lineChart1.getData().add(series1);
                lineChart2.getData().add(series2);
                this.gridPane.add(lineChart1, 0, 0);
                this.gridPane1.add(lineChart2, 0, 0);
                break;
            case "Evolution Budget":
                for (DonneesAnnuelles donnees : donneesList1) {
                    if (donnees.getTotalBudget() != -1) {
                        series1.getData().add(new XYChart.Data<>(String.valueOf(donnees.getYear().getYear()), donnees.getTotalBudget()));
                    }
                }
                for (DonneesAnnuelles donnees : donneesList2) {
                    if (donnees.getTotalBudget() != -1) {
                        series2.getData().add(new XYChart.Data<>(String.valueOf(donnees.getYear().getYear()), donnees.getTotalBudget()));
                    }
                }
                lineChart1.getData().add(series1);
                lineChart2.getData().add(series2);
                this.gridPane.add(lineChart1, 0, 0);
                this.gridPane1.add(lineChart2, 0, 0);
                break;
            case "Evolution Depenses Culturelles":
                for (DonneesAnnuelles donnees : donneesList1) {
                    if (donnees.getTotalCulturalExpenses() != -1) {
                        series1.getData().add(new XYChart.Data<>(String.valueOf(donnees.getYear().getYear()), donnees.getTotalCulturalExpenses()));
                    }
                }
                for (DonneesAnnuelles donnees : donneesList2) {
                    if (donnees.getTotalCulturalExpenses() != -1) {
                        series2.getData().add(new XYChart.Data<>(String.valueOf(donnees.getYear().getYear()), donnees.getTotalCulturalExpenses()));
                    }
                }
                lineChart1.getData().add(series1);
                lineChart2.getData().add(series2);
                this.gridPane.add(lineChart1, 0, 0);
                this.gridPane1.add(lineChart2, 0, 0);
                break;
            case "Evolution Prix Moyen":
                for (DonneesAnnuelles donnees : donneesList1) {
                    if (donnees.getAveragePrice() != -1) {
                        series1.getData().add(new XYChart.Data<>(String.valueOf(donnees.getYear().getYear()), donnees.getAveragePrice()));
                    }
                }
                for (DonneesAnnuelles donnees : donneesList2) {
                    if (donnees.getAveragePrice() != -1) {
                        series2.getData().add(new XYChart.Data<>(String.valueOf(donnees.getYear().getYear()), donnees.getAveragePrice()));
                    }
                }
                lineChart1.getData().add(series1);
                lineChart2.getData().add(series2);
                this.gridPane.add(lineChart1, 0, 0);
                this.gridPane1.add(lineChart2, 0, 0);
                break;
            case "Evolution Prix Moyen M2":
                for (DonneesAnnuelles donnees : donneesList1) {
                    if (donnees.getAverageM2Price() != -1) {
                        series1.getData().add(new XYChart.Data<>(String.valueOf(donnees.getYear().getYear()), donnees.getAverageM2Price()));
                    }
                }
                for (DonneesAnnuelles donnees : donneesList2) {
                    if (donnees.getAverageM2Price() != -1) {
                        series2.getData().add(new XYChart.Data<>(String.valueOf(donnees.getYear().getYear()), donnees.getAverageM2Price()));
                    }
                }
                lineChart1.getData().add(series1);
                lineChart2.getData().add(series2);
                this.gridPane.add(lineChart1, 0, 0);
                this.gridPane1.add(lineChart2, 0, 0);
                break;
            case "Evolution Surface Moyenne":
                for (DonneesAnnuelles donnees : donneesList1) {
                    if (donnees.getAverageSurfaceArea() != -1) {
                        series1.getData().add(new XYChart.Data<>(String.valueOf(donnees.getYear().getYear()), donnees.getAverageSurfaceArea()));
                    }
                }
                for (DonneesAnnuelles donnees : donneesList2) {
                    if (donnees.getAverageSurfaceArea() != -1) {
                        series2.getData().add(new XYChart.Data<>(String.valueOf(donnees.getYear().getYear()), donnees.getAverageSurfaceArea()));
                    }
                }
                lineChart1.getData().add(series1);
                lineChart2.getData().add(series2);
                this.gridPane.add(lineChart1, 0, 0);
                this.gridPane1.add(lineChart2, 0, 0);
                break;
            case "Repartition Maisons/Appartements":
                int totalMaisons1 = 0;
                int totalAppartements1 = 0;
                int totalMaisons2 = 0;
                int totalAppartements2 = 0;
                for (DonneesAnnuelles donnees : donneesList1) {
                    if (donnees.getNbHouses() != -1) {
                        totalMaisons1 += donnees.getNbHouses();
                    }
                    if (donnees.getNbAppart() != -1) {
                        totalAppartements1 += donnees.getNbAppart();
                    }
                }
                for (DonneesAnnuelles donnees : donneesList2) {
                    if (donnees.getNbHouses() != -1) {
                        totalMaisons2 += donnees.getNbHouses();
                    }
                    if (donnees.getNbAppart() != -1) {
                        totalAppartements2 += donnees.getNbAppart();
                    }
                }
                // PieChart creation for this one
                PieChart pieChart1 = new PieChart();
                PieChart pieChart2 = new PieChart();
                PieChart.Data slice1_1 = new PieChart.Data("Maisons", totalMaisons1);
                PieChart.Data slice1_2 = new PieChart.Data("Appartements", totalAppartements1);
                PieChart.Data slice2_1 = new PieChart.Data("Maisons", totalMaisons2);
                PieChart.Data slice2_2 = new PieChart.Data("Appartements", totalAppartements2);
                pieChart1.getData().add(slice1_1);
                pieChart1.getData().add(slice1_2);
                pieChart2.getData().add(slice2_1);
                pieChart2.getData().add(slice2_2);
                this.gridPane.add(pieChart1, 0, 0);
                this.gridPane1.add(pieChart2, 0, 0);
                break;
            case "Evolution Nombre de Maisons en vente":
                for (DonneesAnnuelles donnees : donneesList1) {
                    if (donnees.getNbHouses() != -1) {
                        series1.getData().add(new XYChart.Data<>(String.valueOf(donnees.getYear().getYear()), donnees.getNbHouses()));
                    }
                }
                for (DonneesAnnuelles donnees : donneesList2) {
                    if (donnees.getNbHouses() != -1) {
                        series2.getData().add(new XYChart.Data<>(String.valueOf(donnees.getYear().getYear()), donnees.getNbHouses()));
                    }
                }
                lineChart1.getData().add(series1);
                lineChart2.getData().add(series2);
                this.gridPane.add(lineChart1, 0, 0);
                this.gridPane1.add(lineChart2, 0, 0);
                break;
            case "Evolution Nombre d'Appartements en vente":
                for (DonneesAnnuelles donnees : donneesList1) {
                    if (donnees.getNbAppart() != -1) {
                        series1.getData().add(new XYChart.Data<>(String.valueOf(donnees.getYear().getYear()), donnees.getNbAppart()));
                    }
                }
                for (DonneesAnnuelles donnees : donneesList2) {
                    if (donnees.getNbAppart() != -1) {
                        series2.getData().add(new XYChart.Data<>(String.valueOf(donnees.getYear().getYear()), donnees.getNbAppart()));
                    }
                }
                lineChart1.getData().add(series1);
                lineChart2.getData().add(series2);
                this.gridPane.add(lineChart1, 0, 0);
                this.gridPane1.add(lineChart2, 0, 0);
                break;
            default:
                break;
        }
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
            controller.setUser(this.user);

            Stage stage = (Stage) this.homeImage.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}