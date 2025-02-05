package controleur;


import java.io.IOException;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.chart.*;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import modele.dao.AnneeDAO;
import modele.dao.CommuneDAO;
import modele.dao.DonneesAnnuellesDAO;
import modele.dao.GareDAO;
import modele.data.Annee;
import modele.data.Commune;
import modele.data.DonneesAnnuelles;
import modele.data.User;

/**
 * Controller class for managing the data display and interactions in the application.
 * @author Eliot ZIEGELMEYER, Iann VION, Ilias BELOUAHRANI
 * @version 1.0
 */
public class DonneesController {

    /** ComboBox to select the type of data to display. */
    @FXML
    private ComboBox<String> displayBox;

    /** Button to trigger the display of the selected data. */
    @FXML
    private Button displayButton;

    /** ComboBox to select the first town for comparison. */
    @FXML
    private ComboBox<String> townNameComboBox;

    /** The town name label */
    @FXML
    private Label townName;

    /** The town population label */
    @FXML
    private Label townPopulation;

    /** The town department label */
    @FXML
    private Label townDepartment;

    /** The town station for people label */
    @FXML
    private Label townTrainPeople;

    /** The town station for freight label */
    @FXML
    private Label townTrainFret;

    /** The town budget label */
    @FXML
    private Label townBudget;

    /** The town m2 price label */
    @FXML
    private Label townPriceM2;

    /** The town sell area label */
    @FXML
    private Label townSellArea;

    /** The town neighbors field */
    @FXML
    private TextArea neighborsField;

    /** GridPane to display the first chart. */
    @FXML
    private GridPane gridPane;

    /** The home image used to go to the home  */
    @FXML
    private ImageView homeImage;

    /** The user using the application */
    private User user;

    /** The Commune Data Object Access */
    private CommuneDAO communeDAO;

    /** The DonneesAnnuelles Data Object Access */
    private DonneesAnnuellesDAO donneesAnnuellesDAO;

    /** The Gare Data Object Access */
    private GareDAO gareDAO;

    /** The Annee Data Object Access */
    private AnneeDAO anneeDAO;

    /**
     * Sets the user and initializes the user interface with the user login.
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Constructor for the DonneesController class.
     * Initializes the DAO objects.
     */
    public DonneesController() {
        this.communeDAO = new CommuneDAO();
        this.donneesAnnuellesDAO = new DonneesAnnuellesDAO();
        this.gareDAO = new GareDAO();
        this.anneeDAO = new AnneeDAO();
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
        this.displayBox.getItems().addAll("Evolution Population", "Evolution Budget", "Evolution Depenses Culturelles", "Evolution Prix Moyen", "Evolution Prix Moyen M2", "Evolution Surface Moyenne en vente", "Repartition Maisons/Appartements", "Evolution Nombre de Maisons en vente", "Evolution Nombre d'Appartements en vente", "Evolution Taux d'Inflation");
    }

    /**
     * Displays information about the selected town and criteria.
     */
    @FXML
    private void afficherInformations(ActionEvent event) {
        String ville = this.townNameComboBox.getValue();
        String critere = this.displayBox.getValue();
    
        if (ville != null && critere != null) {
            Commune commune = this.communeDAO.findByName(ville);
            int communeId = commune.getCommuneId();
            DonneesAnnuelles donnees = this.donneesAnnuellesDAO.findByYearAndCommune(2020, communeId);
            boolean fret = this.gareDAO.hasFreightGare(communeId);
            boolean voyageurs = this.gareDAO.hasPassengerGare(communeId);
    
            this.townName.setText(commune.getCommuneName());
    
            if (donnees.getPopulation() == -1) {
                this.townPopulation.setText("Donnee non disponible");
            } else {
                this.townPopulation.setText(String.valueOf(donnees.getPopulation()));
            }
    
            this.townDepartment.setText(commune.getCommuneDepartment().getNomDep());
    
            if (fret) {
                this.townTrainFret.setText("Oui");
            } else {
                this.townTrainFret.setText("Non");
            }
    
            if (voyageurs) {
                this.townTrainPeople.setText("Oui");
            } else {
                this.townTrainPeople.setText("Non");
            }
    
            donnees = this.donneesAnnuellesDAO.findByYearAndCommune(2019, communeId);
    
            if (donnees.getTotalBudget() == -1) {
                this.townBudget.setText("Donnee non disponible");
            } else {
                this.townBudget.setText(String.valueOf(donnees.getTotalBudget()));
            }
    
            donnees = this.donneesAnnuellesDAO.findByYearAndCommune(2021, communeId);
    
            if (donnees.getAverageM2Price() == -1) {
                this.townPriceM2.setText("Donnee non disponible");
            } else {
                this.townPriceM2.setText(String.valueOf(donnees.getAverageM2Price()));
            }
    
            if (donnees.getAverageSurfaceArea() == -1) {
                this.townSellArea.setText("Donnee non disponible");
            } else {
                this.townSellArea.setText(String.valueOf(donnees.getAverageSurfaceArea()));
            }
    
            ArrayList<Commune> neighbors = this.communeDAO.findNeighbors(communeId);
            commune.setNeighbors(neighbors);
            ArrayList<String> neighborsNames = new ArrayList<>();
            for (Commune c : commune.getNeighbors()) {
                neighborsNames.add(c.getCommuneName());
            }
            this.neighborsField.setWrapText(true);
            this.neighborsField.setText(neighborsNames.toString());
            this.neighborsField.setEditable(false);
            this.afficherGraphique(critere);
        }
    }

    /**
     * Displays the graph based on the selected criterion.
     * @param critere the criterion to display the graph for
     */
    private void afficherGraphique(String critere) {
        this.gridPane.getChildren().clear();

        // Initialize axes and charts for the commune
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        PieChart pieChart = new PieChart();

        // Create data series for the commune
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Evolution des donnees");
        
        // Retrieve annual data for the commune
        Commune commune = this.communeDAO.findByName(this.townNameComboBox.getValue());
        List<DonneesAnnuelles> donneesList = this.donneesAnnuellesDAO.findByCommune(commune.getCommuneId());
        List<Annee> annees = this.anneeDAO.findAll();
        
        // Handle different comparison criteria
        switch (critere) {
            case "Evolution Population":
                for (DonneesAnnuelles donnees : donneesList) {
                    if (donnees.getPopulation() != -1) {
                        series.getData().add(new XYChart.Data<>(String.valueOf(donnees.getYear().getYear()), donnees.getPopulation()));
                    }
                }
                lineChart.getData().add(series);
                this.gridPane.add(lineChart, 0, 0);
                break;
            case "Evolution Budget":
                for (DonneesAnnuelles donnees : donneesList) {
                    if (donnees.getTotalBudget() != -1) {
                        series.getData().add(new XYChart.Data<>(String.valueOf(donnees.getYear().getYear()), donnees.getTotalBudget()));
                    }
                }
                lineChart.getData().add(series);
                this.gridPane.add(lineChart, 0, 0);
                break;
            case "Evolution Depenses Culturelles":
                for (DonneesAnnuelles donnees : donneesList) {
                    if (donnees.getTotalCulturalExpenses() != -1) {
                        series.getData().add(new XYChart.Data<>(String.valueOf(donnees.getYear().getYear()), donnees.getTotalCulturalExpenses()));
                    }
                }
                lineChart.getData().add(series);
                this.gridPane.add(lineChart, 0, 0);
                break;
            case "Evolution Prix Moyen":
                for (DonneesAnnuelles donnees : donneesList) {
                    if (donnees.getAveragePrice() != -1) {
                        series.getData().add(new XYChart.Data<>(String.valueOf(donnees.getYear().getYear()), donnees.getAveragePrice()));
                    }
                }
                lineChart.getData().add(series);
                this.gridPane.add(lineChart, 0, 0);
                break;
            case "Evolution Prix Moyen M2":
                for (DonneesAnnuelles donnees : donneesList) {
                    if (donnees.getAverageM2Price() != -1) {
                        series.getData().add(new XYChart.Data<>(String.valueOf(donnees.getYear().getYear()), donnees.getAverageM2Price()));
                    }
                }
                lineChart.getData().add(series);
                this.gridPane.add(lineChart, 0, 0);
                break;
            case "Evolution Surface Moyenne en vente":
                for (DonneesAnnuelles donnees : donneesList) {
                    if (donnees.getAverageSurfaceArea() != -1) {
                        series.getData().add(new XYChart.Data<>(String.valueOf(donnees.getYear().getYear()), donnees.getAverageSurfaceArea()));
                    }
                }
                lineChart.getData().add(series);
                this.gridPane.add(lineChart, 0, 0);
                break;
            case "Repartition Maisons/Appartements":
                int totalMaisons = 0;
                int totalAppartements = 0;
                for (DonneesAnnuelles donnees : donneesList) {
                    if (donnees.getNbHouses() != -1) {
                        totalMaisons += donnees.getNbHouses();
                    }
                    if (donnees.getNbAppart() != -1) {
                        totalAppartements += donnees.getNbAppart();
                    }
                }
                PieChart.Data slice1 = new PieChart.Data("Maisons", totalMaisons);
                PieChart.Data slice2 = new PieChart.Data("Appartements", totalAppartements);
                pieChart.getData().add(slice1);
                pieChart.getData().add(slice2);
                gridPane.add(pieChart, 0, 0);
                break;
            case "Evolution Nombre de Maisons en vente":
                for (DonneesAnnuelles donnees : donneesList) {
                    if (donnees.getNbHouses() != -1) {
                        series.getData().add(new XYChart.Data<>(String.valueOf(donnees.getYear().getYear()), donnees.getNbHouses()));
                    }
                }
                lineChart.getData().add(series);
                this.gridPane.add(lineChart, 0, 0);
                break;
            case "Evolution Nombre d'Appartements en vente":
                for (DonneesAnnuelles donnees : donneesList) {
                    if (donnees.getNbAppart() != -1) {
                        series.getData().add(new XYChart.Data<>(String.valueOf(donnees.getYear().getYear()), donnees.getNbAppart()));
                    }
                }
                lineChart.getData().add(series);
                this.gridPane.add(lineChart, 0, 0);
                break;
            case "Evolution Taux d'Inflation":
                for (Annee annee : annees) {
                    if (annee.getInflationRate() != -1) {
                        series.getData().add(new XYChart.Data<>(String.valueOf(annee.getYear()), annee.getInflationRate()));
                    }
                }
                lineChart.getData().add(series);
                this.gridPane.add(lineChart, 0, 0);
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
            controller.setUser(user);

            Stage stage = (Stage) this.homeImage.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}