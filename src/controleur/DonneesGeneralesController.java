package controleur;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.layout.GridPane;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent; 
import javafx.stage.Stage;
import javafx.scene.Scene; 
import modele.data.*;
import modele.dao.*;

/**
 * The general data page controller
 * @author Eliot ZIEGELMEYER, Iann VION, Ilias BELOUAHRANI
 * @version 1.0
 */
public class DonneesGeneralesController {

    /** The table display button */
    @FXML
    private Button displayButton;

    /** The table display comboBox */
    @FXML
    private ComboBox<String> displayComboBox;
    
    /** The CSV export button */
    @FXML
    private Button exportButton;

    /** The gridPane where is displayed the tableView */
    @FXML
    private GridPane gridPane;

    /** The home image used to go to the home */
    @FXML
    private ImageView homeImage;

    /** The current displayed tableView (we use here an widcard so we can change the tableView type) */
    private TableView<?> currentTableView;

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
     * Method used to the initialize the displayComboBox.
     */
    @FXML
    private void initialize() {
        this.displayComboBox.getItems().addAll("Departement", "Aeroport", "Commune", "Gare", "Annee", "DonneesAnnuelles", "Voisinage");
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
     * Exports the table information into an CSV file.
     */
    @FXML
    private void handleExport(ActionEvent event) {
        if (this.currentTableView == null) {
            System.out.println("Aucune table a exporter");
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer en tant que CSV");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers CSV", "*.csv"));
        File file = fileChooser.showSaveDialog(this.exportButton.getScene().getWindow());

        if (file != null) {
            this.exportTableViewToCSV(this.currentTableView, file);
        }
    }

    /**
     * Method used to display the choosen table from the Data Base.
     */
    @FXML
    private void afficherInformations(ActionEvent event) {
        String selectedTable = this.displayComboBox.getSelectionModel().getSelectedItem();
        this.showAlert(AlertType.INFORMATION, "Chargement de la table", "Chargement en cours cela peut prendre quelques instants... Appuyer sur OK pour continuer");
        if (selectedTable != null) {
            switch (selectedTable) {
                case "Departement":
                    this.afficherDepartements();
                    break;
                case "Aeroport":
                    this.afficherAeroports();
                    break;
                case "Commune":
                    this.afficherCommunes();
                    break;
                case "Gare":
                    this.afficherGares();
                    break;
                case "Annee":
                    this.afficherAnnees();
                    break;
                case "DonneesAnnuelles":
                    this.afficherDonneesAnnuelles();
                    break;
                case "Voisinage":
                    this.afficherVoisinages();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Method used to display the department table.
     */
    private void afficherDepartements() {
        // Create a new TableView for Departement objects
        TableView<Departement> tableView = new TableView<>();
        DepartementDAO departementDAO = new DepartementDAO();

        // Retrieve all departments from the DAO and convert them to an ObservableList
        List<Departement> departements = departementDAO.findAll();
        ObservableList<Departement> data = FXCollections.observableArrayList(departements);
    
        // Clear any existing columns in the TableView
        tableView.getColumns().clear();
    
        // Create a column for the department ID
        TableColumn<Departement, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("idDep")); // Extracts the idDep value from a tableView row reflectively
    
        // Create a column for the department name
        TableColumn<Departement, String> nomColumn = new TableColumn<>("Nom");
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nomDep")); // Extracts the nomDep value from a tableView row reflectively
    
        // Create a column for the cultural investment in 2019
        TableColumn<Departement, Float> investissementColumn = new TableColumn<>("Investissement Culturel 2019");
        investissementColumn.setCellValueFactory(new PropertyValueFactory<>("investissementCulturel2019")); // Extracts the investissementCulturel2019 value from a tableView row reflectively
    
        // Add all columns to the TableView
        tableView.getColumns().addAll(idColumn, nomColumn, investissementColumn);
    
        // Set the data for the TableView
        tableView.setItems(data);
    
        // Make the TableView visible and editable
        tableView.setVisible(true);
    
        // Set the column resize policy to take all the TableView space
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        this.exportButton.setVisible(true);
    
        // Clear any existing children in the gridPane and add the TableView
        this.gridPane.getChildren().clear();
        this.gridPane.add(tableView, 0, 0);
    
        // Store the current TableView reference
        this.currentTableView = tableView;
    }

    /**
     * Method used to display the Aeroport table.
     */
    private void afficherAeroports() {
        TableView<Aeroport> tableView = new TableView<>();
        AeroportDAO aeroportDAO = new AeroportDAO();
        List<Aeroport> aeroports = aeroportDAO.findAll();
        ObservableList<Aeroport> data = FXCollections.observableArrayList(aeroports);

        tableView.getColumns().clear();

        TableColumn<Aeroport, String> nomColumn = new TableColumn<>("Nom");
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Aeroport, String> adresseColumn = new TableColumn<>("Adresse");
        adresseColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn<Aeroport, Integer> departementColumn = new TableColumn<>("Departement");
        departementColumn.setCellValueFactory(cellData -> {
            // Using a CellValueFactory with an SimpleIntegerProperty to retrive only the department ID and not the complete object.
            return new SimpleIntegerProperty(cellData.getValue().getDepartment().getIdDep()).asObject();
        });

        tableView.getColumns().addAll(nomColumn, adresseColumn, departementColumn);
        tableView.setItems(data);
        tableView.setVisible(true);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        this.exportButton.setVisible(true);

        gridPane.getChildren().clear();
        gridPane.add(tableView, 0, 0);
        this.currentTableView = tableView;
    }

    /**
     * Method used to display the Commune table.
     */
    private void afficherCommunes() {
        TableView<Commune> tableView = new TableView<>();
        CommuneDAO communeDAO = new CommuneDAO();
        List<Commune> communes = communeDAO.findAll();
        ObservableList<Commune> data = FXCollections.observableArrayList(communes);

        tableView.getColumns().clear();

        TableColumn<Commune, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("communeId"));

        TableColumn<Commune, String> nomColumn = new TableColumn<>("Nom");
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("communeName"));

        TableColumn<Commune, Integer> departementColumn = new TableColumn<>("Departement");
        departementColumn.setCellValueFactory(cellData -> {
            return new SimpleIntegerProperty(cellData.getValue().getCommuneDepartment().getIdDep()).asObject();
        });

        tableView.getColumns().addAll(idColumn, nomColumn, departementColumn);
        tableView.setItems(data);
        tableView.setVisible(true);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        this.exportButton.setVisible(true);

        gridPane.getChildren().clear();
        gridPane.add(tableView, 0, 0);
        this.currentTableView = tableView;
    }

    /**
     * Method used to display the Gare table.
     */
    private void afficherGares() {
        TableView<Gare> tableView = new TableView<>();
        GareDAO gareDAO = new GareDAO();
        List<Gare> gares = gareDAO.findAll();
        ObservableList<Gare> data = FXCollections.observableArrayList(gares);

        tableView.getColumns().clear();

        TableColumn<Gare, Integer> codeGareColumn = new TableColumn<>("Code Gare");
        codeGareColumn.setCellValueFactory(new PropertyValueFactory<>("stationCode"));

        TableColumn<Gare, String> nomGareColumn = new TableColumn<>("Nom Gare");
        nomGareColumn.setCellValueFactory(new PropertyValueFactory<>("stationName"));

        TableColumn<Gare, Boolean> estFretColumn = new TableColumn<>("Est Fret");
        estFretColumn.setCellValueFactory(new PropertyValueFactory<>("freight"));

        TableColumn<Gare, Boolean> estVoyageurColumn = new TableColumn<>("Est Voyageur");
        estVoyageurColumn.setCellValueFactory(new PropertyValueFactory<>("passenger"));

        TableColumn<Gare, Integer> communeColumn = new TableColumn<>("Commune");
        communeColumn.setCellValueFactory(cellData -> {
            return new SimpleIntegerProperty(cellData.getValue().getTown().getCommuneId()).asObject();
        });

        tableView.getColumns().addAll(codeGareColumn, nomGareColumn, estFretColumn, estVoyageurColumn, communeColumn);
        tableView.setItems(data);
        tableView.setVisible(true);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        this.exportButton.setVisible(true);

        gridPane.getChildren().clear();
        gridPane.add(tableView, 0, 0);
        this.currentTableView = tableView;
    }

    /**
     * Method used to display the Annee table.
     */
    private void afficherAnnees() {
        TableView<Annee> tableView = new TableView<>();
        AnneeDAO anneeDAO = new AnneeDAO();
        List<Annee> annees = anneeDAO.findAll();
        ObservableList<Annee> data = FXCollections.observableArrayList(annees);

        tableView.getColumns().clear();

        TableColumn<Annee, Integer> anneeColumn = new TableColumn<>("Annee");
        anneeColumn.setCellValueFactory(new PropertyValueFactory<>("year"));

        TableColumn<Annee, Float> tauxInflationColumn = new TableColumn<>("Taux d'Inflation");
        tauxInflationColumn.setCellValueFactory(new PropertyValueFactory<>("inflationRate"));

        tableView.getColumns().addAll(anneeColumn, tauxInflationColumn);
        tableView.setItems(data);
        tableView.setVisible(true);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        this.exportButton.setVisible(true);

        gridPane.getChildren().clear();
        gridPane.add(tableView, 0, 0);
        this.currentTableView = tableView;
    }

    /**
     * Method used to display the DonneesAnnuelles table.
     */
    private void afficherDonneesAnnuelles() {
        TableView<DonneesAnnuelles> tableView = new TableView<>();
        DonneesAnnuellesDAO donneesAnnuellesDAO = new DonneesAnnuellesDAO();
        List<DonneesAnnuelles> donneesAnnuellesList = donneesAnnuellesDAO.findAll();
        ObservableList<DonneesAnnuelles> data = FXCollections.observableArrayList(donneesAnnuellesList);

        tableView.getColumns().clear();

        TableColumn<DonneesAnnuelles, Integer> anneeColumn = new TableColumn<>("Annee");
        anneeColumn.setCellValueFactory(cellData -> {
            return new SimpleIntegerProperty(cellData.getValue().getYear().getYear()).asObject();
        });

        TableColumn<DonneesAnnuelles, Integer> communeColumn = new TableColumn<>("Commune");
        communeColumn.setCellValueFactory(cellData -> {
            return new SimpleIntegerProperty(cellData.getValue().getCommune().getCommuneId()).asObject();
        });

        TableColumn<DonneesAnnuelles, Integer> nbMaisonColumn = new TableColumn<>("Nombre de Maisons");
        nbMaisonColumn.setCellValueFactory(new PropertyValueFactory<>("nbHouses"));

        TableColumn<DonneesAnnuelles, Integer> nbAppartColumn = new TableColumn<>("Nombre d'Appartements");
        nbAppartColumn.setCellValueFactory(new PropertyValueFactory<>("nbAppart"));

        TableColumn<DonneesAnnuelles, Float> prixMoyenColumn = new TableColumn<>("Prix Moyen");
        prixMoyenColumn.setCellValueFactory(new PropertyValueFactory<>("averagePrice"));

        TableColumn<DonneesAnnuelles, Float> prixM2MoyenColumn = new TableColumn<>("Prix Moyen au m2");
        prixM2MoyenColumn.setCellValueFactory(new PropertyValueFactory<>("averageM2Price"));

        TableColumn<DonneesAnnuelles, Float> surfaceMoyColumn = new TableColumn<>("Surface Moyenne");
        surfaceMoyColumn.setCellValueFactory(new PropertyValueFactory<>("averageSurfaceArea"));

        TableColumn<DonneesAnnuelles, Float> depensesCulturellesTotalesColumn = new TableColumn<>("Depenses Culturelles Totales");
        depensesCulturellesTotalesColumn.setCellValueFactory(new PropertyValueFactory<>("totalCulturalExpenses"));

        TableColumn<DonneesAnnuelles, Float> budgetTotalColumn = new TableColumn<>("Budget Total");
        budgetTotalColumn.setCellValueFactory(new PropertyValueFactory<>("totalBudget"));

        TableColumn<DonneesAnnuelles, Float> populationColumn = new TableColumn<>("Population");
        populationColumn.setCellValueFactory(new PropertyValueFactory<>("population"));

        tableView.getColumns().addAll(anneeColumn, communeColumn, nbMaisonColumn, nbAppartColumn, prixMoyenColumn, prixM2MoyenColumn, surfaceMoyColumn, depensesCulturellesTotalesColumn, budgetTotalColumn, populationColumn);
        tableView.setItems(data);
        tableView.setVisible(true);
        this.exportButton.setVisible(true);

        gridPane.getChildren().clear();
        gridPane.add(tableView, 0, 0);
        this.currentTableView = tableView;
    }

    /**
     * Method used to display the Voisinage table.
     */
    private void afficherVoisinages() {
        TableView<Voisinage> tableView = new TableView<>();
        VoisinageDAO voisinageDAO = new VoisinageDAO();
        List<Voisinage> voisinages = voisinageDAO.findAll();
        ObservableList<Voisinage> data = FXCollections.observableArrayList(voisinages);

        tableView.getColumns().clear();

        TableColumn<Voisinage, Integer> communeColumn = new TableColumn<>("Commune");
        communeColumn.setCellValueFactory(cellData -> {
            return new SimpleIntegerProperty(cellData.getValue().getCommune().getCommuneId()).asObject();
        });

        TableColumn<Voisinage, Integer> communeVoisineColumn = new TableColumn<>("Commune Voisine");
        communeVoisineColumn.setCellValueFactory(cellData -> {
            return new SimpleIntegerProperty(cellData.getValue().getCommuneVoisine().getCommuneId()).asObject();
        });

        tableView.getColumns().addAll(communeColumn, communeVoisineColumn);
        tableView.setItems(data);
        tableView.setVisible(true);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
        this.exportButton.setVisible(true);

        gridPane.getChildren().clear();
        gridPane.add(tableView, 0, 0);
        this.currentTableView = tableView;
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

    /**
     * Exports the data from a TableView to a CSV file.
     *
     * @param <T> the type of the items contained in the TableView
     * @param tableView the TableView containing the data to be exported
     * @param file the file to which the data will be written
     */
    private <T> void exportTableViewToCSV(TableView<T> tableView, File file) {
        try (PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file)))) {
            // Write column headers
            for (TableColumn<T, ?> column : tableView.getColumns()) {
                writer.print(column.getText() + ",");
            }
            writer.println();

            // Write row data
            for (T item : tableView.getItems()) {
                for (TableColumn<T, ?> column : tableView.getColumns()) {
                    Object cellData = column.getCellData(item);
                    if (cellData != null) {
                        writer.print(cellData.toString() + ",");
                    } else {
                        writer.print(",");
                    }
                }
                writer.println();
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