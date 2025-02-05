package controleur;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.event.ActionEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.BooleanStringConverter;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.scene.control.Alert.AlertType;
import modele.dao.*;
import modele.data.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The administration page controller
 * @author Eliot ZIEGELMEYER, Iann VION, Ilias BELOUAHRANI
 * @version 1.0
 */
public class AdministrateurController {

    /** The table display button */
    @FXML 
    private Button displayButton;

    /** The table display comboBox */
    @FXML 
    private ComboBox<String> displayComboBox;

    /** The gridPane where is displayed the tableView */
    @FXML 
    private GridPane gridPane;

    /** The home image used to go to the home  */
    @FXML 
    private ImageView homeImage;

    /** The scrollPane used to display the textFields */
    @FXML 
    private ScrollPane scrollPane;

    /** Button used to add a row to the table */
    @FXML 
    private Button addButton;

    /** Button used to delete a row in the table */
    @FXML 
    private Button supprimerButton;
    
    /** The current displayed tableView (we use here an widcard so we can change the tableView type) */
    private TableView<?> currentTableView;

    /** The text fields used to add a row to a table */
    private List<TextField> textFields = new ArrayList<>();
    
    /** The user using the application */
    private User user;

    /** The commune Data Object Access */
    private CommuneDAO communeDAO = new CommuneDAO();

    /** The department Data Object Access */
    private DepartementDAO departementDAO = new DepartementDAO();

    /** The airport Data Object Access */
    private AeroportDAO aeroportDAO = new AeroportDAO();

    /** The train station Data Object Access */
    private GareDAO gareDAO = new GareDAO();

    /** The year Data Object Access */
    private AnneeDAO anneeDAO = new AnneeDAO();

    /** The annual Data Data Object Access */
    private DonneesAnnuellesDAO donneesAnnuellesDAO = new DonneesAnnuellesDAO();

    /** The neighbors Data Object Access */
    private VoisinageDAO voisinageDAO = new VoisinageDAO();

    /** The user Data Object Access */
    private UserDAO userDAO = new UserDAO();

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
        this.displayComboBox.getItems().addAll("Departement", "Aeroport", "Commune", "Gare", "Annee", "DonneesAnnuelles", "Voisinage", "User");
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
                case "User":
                    this.afficherUsers();
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
    
        // Retrieve all departments from the DAO and convert them to an ObservableList
        List<Departement> departements = this.departementDAO.findAll();
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
        investissementColumn.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter())); // Allows real-time updates in the investment column
    
        // Handle the commit event for editing the investment column
        investissementColumn.setOnEditCommit(event -> {
            Departement departement = event.getRowValue();
            try {
                // Update the department's investment value
                departement.setInvestissementCulturel2019(event.getNewValue());
            } catch (IllegalArgumentException e) {
                // Show an error alert if the new value is invalid
                this.showAlert(AlertType.ERROR, "Erreur", e.getMessage());
            }
            // Update the department in the DAO
            this.departementDAO.update(departement);
        });
    
        // Add all columns to the TableView
        tableView.getColumns().addAll(idColumn, nomColumn, investissementColumn);
    
        // Set the data for the TableView
        tableView.setItems(data);
    
        // Make the TableView visible and editable
        tableView.setVisible(true);
        tableView.setEditable(true);
    
        // Set the column resize policy to take all the TableView space
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
    
        // Clear any existing children in the gridPane and add the TableView
        this.gridPane.getChildren().clear();
        this.gridPane.add(tableView, 0, 0);
    
        // Store the current TableView reference
        this.currentTableView = tableView;
    
        // Initialize text fields for additional functionality
        this.initializeTextFields();
    
        // Make the add and delete buttons visible
        this.addButton.setVisible(true);
        this.supprimerButton.setVisible(true);
    }

    /**
     * Method used to display the Aeroport table.
     */
    private void afficherAeroports() {
        TableView<Aeroport> tableView = new TableView<>();;
        List<Aeroport> aeroports = this.aeroportDAO.findAll();
        ObservableList<Aeroport> data = FXCollections.observableArrayList(aeroports);

        tableView.getColumns().clear();

        TableColumn<Aeroport, String> nomColumn = new TableColumn<>("Nom");
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Aeroport, String> adresseColumn = new TableColumn<>("Adresse");
        adresseColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        adresseColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        adresseColumn.setOnEditCommit(event -> {
            Aeroport aeroport = event.getRowValue();
            try {
                aeroport.setAddress(event.getNewValue());
            } catch (IllegalArgumentException e) {
                this.showAlert(AlertType.ERROR, "Erreur", e.getMessage());
            }
            this.aeroportDAO.update(aeroport);
        });

        TableColumn<Aeroport, Integer> departementColumn = new TableColumn<>("Departement");
        departementColumn.setCellValueFactory(cellData -> {
            // Using a CellValueFactory with an SimpleIntegerProperty to retrive only the department ID and not the complete object.
            return new SimpleIntegerProperty(cellData.getValue().getDepartment().getIdDep()).asObject();
        });

        tableView.getColumns().addAll(nomColumn, adresseColumn, departementColumn);
        tableView.setItems(data);
        tableView.setVisible(true);
        tableView.setEditable(true);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        this.gridPane.getChildren().clear();
        this.gridPane.add(tableView, 0, 0);
        this.currentTableView = tableView;

        this.initializeTextFields();
        this.addButton.setVisible(true);
        this.supprimerButton.setVisible(true);
    }

    /**
     * Method used to display the Commune table.
     */
    private void afficherCommunes() {
        TableView<Commune> tableView = new TableView<>();
        List<Commune> communes = this.communeDAO.findAll();
        ObservableList<Commune> data = FXCollections.observableArrayList(communes);

        tableView.getColumns().clear();

        TableColumn<Commune, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("communeId"));

        TableColumn<Commune, String> nomColumn = new TableColumn<>("Nom");
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("communeName"));
        nomColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nomColumn.setOnEditCommit(event -> {
            Commune commune = event.getRowValue();
            try {
                commune.setCommuneName(event.getNewValue());
            } catch (IllegalArgumentException e) {
                this.showAlert(AlertType.ERROR, "Erreur", e.getMessage());
            }
            this.communeDAO.update(commune); 
        });

        TableColumn<Commune, Integer> departementColumn = new TableColumn<>("Departement");
        departementColumn.setCellValueFactory(cellData -> {
            return new SimpleIntegerProperty(cellData.getValue().getCommuneDepartment().getIdDep()).asObject();
        });

        tableView.getColumns().addAll(idColumn, nomColumn, departementColumn);
        tableView.setItems(data);
        tableView.setVisible(true);
        tableView.setEditable(true);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        this.gridPane.getChildren().clear();
        this.gridPane.add(tableView, 0, 0);
        this.currentTableView = tableView;

        this.initializeTextFields();
        this.addButton.setVisible(true);
        this.supprimerButton.setVisible(true);
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
        nomGareColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nomGareColumn.setOnEditCommit(event -> {
            Gare gare = event.getRowValue();
            try {
                gare.setStationName(event.getNewValue());
            } catch (IllegalArgumentException e) {
                this.showAlert(AlertType.ERROR, "Erreur", e.getMessage());
            }
            this.gareDAO.update(gare);
        });

        TableColumn<Gare, Boolean> estFretColumn = new TableColumn<>("Est Fret");
        estFretColumn.setCellValueFactory(new PropertyValueFactory<>("freight"));
        estFretColumn.setCellFactory(TextFieldTableCell.forTableColumn(new BooleanStringConverter()));
        estFretColumn.setOnEditCommit(event -> {
            Gare gare = event.getRowValue();
            try {
                gare.setFreight(event.getNewValue());
            } catch (IllegalArgumentException e) {
                this.showAlert(AlertType.ERROR, "Erreur", e.getMessage());
            }
            this.gareDAO.update(gare); 
        });

        TableColumn<Gare, Boolean> estVoyageurColumn = new TableColumn<>("Est Voyageur");
        estVoyageurColumn.setCellValueFactory(new PropertyValueFactory<>("passenger"));
        estVoyageurColumn.setCellFactory(TextFieldTableCell.forTableColumn(new BooleanStringConverter()));
        estVoyageurColumn.setOnEditCommit(event -> {
            Gare gare = event.getRowValue();
            try {
                gare.setPassenger(event.getNewValue());
            } catch (IllegalArgumentException e) {
                this.showAlert(AlertType.ERROR, "Erreur", e.getMessage());
            }
            this.gareDAO.update(gare); 
        });

        TableColumn<Gare, Integer> communeColumn = new TableColumn<>("Commune");
        communeColumn.setCellValueFactory(cellData -> {
            return new SimpleIntegerProperty(cellData.getValue().getTown().getCommuneId()).asObject();
        });

        tableView.getColumns().addAll(codeGareColumn, nomGareColumn, estFretColumn, estVoyageurColumn, communeColumn);
        tableView.setItems(data);
        tableView.setVisible(true);
        tableView.setEditable(true);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        this.gridPane.getChildren().clear();
        this.gridPane.add(tableView, 0, 0);
        this.currentTableView = tableView;

        this.initializeTextFields();
        this.addButton.setVisible(true);
        this.supprimerButton.setVisible(true);
    }

    /**
     * Method used to display the Annee table.
     */
    private void afficherAnnees() {
        TableView<Annee> tableView = new TableView<>();
        List<Annee> annees = this.anneeDAO.findAll();
        ObservableList<Annee> data = FXCollections.observableArrayList(annees);

        tableView.getColumns().clear();

        TableColumn<Annee, Integer> anneeColumn = new TableColumn<>("Annee");
        anneeColumn.setCellValueFactory(new PropertyValueFactory<>("year"));

        TableColumn<Annee, Float> tauxInflationColumn = new TableColumn<>("Taux d'Inflation");
        tauxInflationColumn.setCellValueFactory(new PropertyValueFactory<>("inflationRate"));
        tauxInflationColumn.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        tauxInflationColumn.setOnEditCommit(event -> {
            Annee annee = event.getRowValue();
            try {
                annee.setInflationRate(event.getNewValue());
            } catch (IllegalArgumentException e) {
                this.showAlert(AlertType.ERROR, "Erreur", e.getMessage());
            }
            this.anneeDAO.update(annee);
        });

        tableView.getColumns().addAll(anneeColumn, tauxInflationColumn);
        tableView.setItems(data);
        tableView.setVisible(true);
        tableView.setEditable(true);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        this.gridPane.getChildren().clear();
        this.gridPane.add(tableView, 0, 0);
        this.currentTableView = tableView;

        this.initializeTextFields();
        this.addButton.setVisible(true);
        this.supprimerButton.setVisible(true);
    }

    /**
     * Method used to display the DonneesAnnuelles table.
     */
    private void afficherDonneesAnnuelles() {
        TableView<DonneesAnnuelles> tableView = new TableView<>();
        List<DonneesAnnuelles> donneesAnnuellesList = this.donneesAnnuellesDAO.findAll();
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
        nbMaisonColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        nbMaisonColumn.setOnEditCommit(event -> {
            DonneesAnnuelles donneesAnnuelles = event.getRowValue();
            try {
                donneesAnnuelles.setNbHouses(event.getNewValue());
            } catch (IllegalArgumentException e) {
                this.showAlert(AlertType.ERROR, "Erreur", e.getMessage());
            }
            this.donneesAnnuellesDAO.update(donneesAnnuelles);
        });

        TableColumn<DonneesAnnuelles, Integer> nbAppartColumn = new TableColumn<>("Nombre d'Appartements");
        nbAppartColumn.setCellValueFactory(new PropertyValueFactory<>("nbAppart"));
        nbAppartColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        nbAppartColumn.setOnEditCommit(event -> {
            DonneesAnnuelles donneesAnnuelles = event.getRowValue();
            try {
                donneesAnnuelles.setNbAppart(event.getNewValue());
            } catch (IllegalArgumentException e) {
                this.showAlert(AlertType.ERROR, "Erreur", e.getMessage());
            }
            this.donneesAnnuellesDAO.update(donneesAnnuelles);
        });

        TableColumn<DonneesAnnuelles, Float> prixMoyenColumn = new TableColumn<>("Prix Moyen");
        prixMoyenColumn.setCellValueFactory(new PropertyValueFactory<>("averagePrice"));
        prixMoyenColumn.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        prixMoyenColumn.setOnEditCommit(event -> {
            DonneesAnnuelles donneesAnnuelles = event.getRowValue();
            try {
                donneesAnnuelles.setAveragePrice(event.getNewValue());
            } catch (IllegalArgumentException e) {
                this.showAlert(AlertType.ERROR, "Erreur", e.getMessage());
            }
            this.donneesAnnuellesDAO.update(donneesAnnuelles);
        });

        TableColumn<DonneesAnnuelles, Float> prixM2MoyenColumn = new TableColumn<>("Prix Moyen au m2");
        prixM2MoyenColumn.setCellValueFactory(new PropertyValueFactory<>("averageM2Price"));
        prixM2MoyenColumn.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        prixM2MoyenColumn.setOnEditCommit(event -> {
            DonneesAnnuelles donneesAnnuelles = event.getRowValue();
            try {
                donneesAnnuelles.setAverageM2Price(event.getNewValue());
            } catch (IllegalArgumentException e) {
                this.showAlert(AlertType.ERROR, "Erreur", e.getMessage());
            }
            this.donneesAnnuellesDAO.update(donneesAnnuelles);
        });

        TableColumn<DonneesAnnuelles, Float> surfaceMoyColumn = new TableColumn<>("Surface Moyenne");
        surfaceMoyColumn.setCellValueFactory(new PropertyValueFactory<>("averageSurfaceArea"));
        surfaceMoyColumn.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        surfaceMoyColumn.setOnEditCommit(event -> {
            DonneesAnnuelles donneesAnnuelles = event.getRowValue();
            try {
                donneesAnnuelles.setAverageSurfaceArea(event.getNewValue());
            } catch (IllegalArgumentException e) {
                this.showAlert(AlertType.ERROR, "Erreur", e.getMessage());
            }
            this.donneesAnnuellesDAO.update(donneesAnnuelles);
        });

        TableColumn<DonneesAnnuelles, Float> depensesCulturellesTotalesColumn = new TableColumn<>("Depenses Culturelles Totales");
        depensesCulturellesTotalesColumn.setCellValueFactory(new PropertyValueFactory<>("totalCulturalExpenses"));
        depensesCulturellesTotalesColumn.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        depensesCulturellesTotalesColumn.setOnEditCommit(event -> {
            DonneesAnnuelles donneesAnnuelles = event.getRowValue();
            try {
                donneesAnnuelles.setTotalCulturalExpenses(event.getNewValue());
            } catch (IllegalArgumentException e) {
                this.showAlert(AlertType.ERROR, "Erreur", e.getMessage());
            }
            this.donneesAnnuellesDAO.update(donneesAnnuelles);
        });

        TableColumn<DonneesAnnuelles, Float> budgetTotalColumn = new TableColumn<>("Budget Total");
        budgetTotalColumn.setCellValueFactory(new PropertyValueFactory<>("totalBudget"));
        budgetTotalColumn.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        budgetTotalColumn.setOnEditCommit(event -> {
            DonneesAnnuelles donneesAnnuelles = event.getRowValue();
            try {
                donneesAnnuelles.setTotalBudget(event.getNewValue());
            } catch (IllegalArgumentException e) {
                this.showAlert(AlertType.ERROR, "Erreur", e.getMessage());
            }
            this.donneesAnnuellesDAO.update(donneesAnnuelles);
        });

        TableColumn<DonneesAnnuelles, Float> populationColumn = new TableColumn<>("Population");
        populationColumn.setCellValueFactory(new PropertyValueFactory<>("population"));
        populationColumn.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        populationColumn.setOnEditCommit(event -> {
            DonneesAnnuelles donneesAnnuelles = event.getRowValue();
            try {
                donneesAnnuelles.setPopulation(event.getNewValue());
            } catch (IllegalArgumentException e) {
                this.showAlert(AlertType.ERROR, "Erreur", e.getMessage());
            }
            this.donneesAnnuellesDAO.update(donneesAnnuelles);
        });

        tableView.getColumns().addAll(anneeColumn, communeColumn, nbMaisonColumn, nbAppartColumn, prixMoyenColumn, prixM2MoyenColumn, surfaceMoyColumn, depensesCulturellesTotalesColumn, budgetTotalColumn, populationColumn);
        tableView.setItems(data);
        tableView.setVisible(true);
        tableView.setEditable(true);

        this.gridPane.getChildren().clear();
        this.gridPane.add(tableView, 0, 0);
        this.currentTableView = tableView;

        this.initializeTextFields();
        this.addButton.setVisible(true);
        this.supprimerButton.setVisible(true);
    }

    /**
     * Method used to display the Voisinage table.
     */
    private void afficherVoisinages() {
        TableView<Voisinage> tableView = new TableView<>();
        List<Voisinage> voisinages = this.voisinageDAO.findAll();
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

        this.gridPane.getChildren().clear();
        this.gridPane.add(tableView, 0, 0);
        this.currentTableView = tableView;

        this.initializeTextFields();
        this.addButton.setVisible(true);
        this.supprimerButton.setVisible(true);
    }

    /**
     * Method used to display the User table.
     */
    private void afficherUsers() {
        TableView<User> tableView = new TableView<>();
        List<User> users = userDAO.findAll();
        ObservableList<User> data = FXCollections.observableArrayList(users);
    
        tableView.getColumns().clear();
    
        TableColumn<User, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
    
        TableColumn<User, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        emailColumn.setOnEditCommit(event -> {
            User user = event.getRowValue();
            try {
                user.setEmail(event.getNewValue());
            } catch (IllegalArgumentException e) {
                this.showAlert(AlertType.ERROR, "Erreur", e.getMessage());
            }
            this.userDAO.update(user);
        });
    
        TableColumn<User, String> passwordColumn = new TableColumn<>("Mot de Passe");
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("pwd"));
        passwordColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        passwordColumn.setOnEditCommit(event -> {
            User user = event.getRowValue();
            try {
                user.setPwd(event.getNewValue());
            } catch (IllegalArgumentException e) {
                this.showAlert(AlertType.ERROR, "Erreur", e.getMessage());
            }
            this.userDAO.update(user);
        });
    
        tableView.getColumns().addAll(idColumn, emailColumn, passwordColumn);
        tableView.setItems(data);
        tableView.setVisible(true);
        tableView.setEditable(true);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
    
        this.gridPane.getChildren().clear();
        this.gridPane.add(tableView, 0, 0);
        this.currentTableView = tableView;
    
        this.initializeTextFields();
        this.addButton.setVisible(true);
        this.supprimerButton.setVisible(true);
    }

    /**
     * Method used to inialize the different text fields used to add an row.
     */
    private void initializeTextFields() {
        VBox vbox = new VBox();
        textFields.clear();
        for (TableColumn<?, ?> column : currentTableView.getColumns()) {
            TextField textField = new TextField();
            textField.setPromptText(column.getText());
            textField.setPrefSize(254, 30);
            textFields.add(textField);
            vbox.getChildren().add(textField);
        }
        scrollPane.setContent(vbox);
    }

    /** 
     * Method used to add rows in the different tables.
     * It uses the Data Object Access classes to create the new object retrieved from the bd_SAE data base.
     */
    @FXML
    private void addRow(ActionEvent event) {
        if (this.currentTableView != null) {
            for (TextField t : textFields) { 
                if (t.getText().isEmpty()) { // We make sure that the text fields are not empty
                    this.showAlert(AlertType.ERROR, "Erreur", "Veuillez remplir tout les champs.");
                    return;
                }
            }
            String selectedTable = this.displayComboBox.getSelectionModel().getSelectedItem();
            int res;
            switch (selectedTable) {
                case "Commune":
                    try {
                        // We retrieve the informations from the textFields and create the Object
                        int communeId = Integer.parseInt(textFields.get(0).getText());
                        String nomCommune = textFields.get(1).getText();
                        Departement depCommune = this.departementDAO.findById(Integer.parseInt(textFields.get(2).getText()));
                        Commune newCommune = new Commune(communeId, nomCommune, depCommune);
                        res = this.communeDAO.create(newCommune); // Creates the object in the data base
                        if (res == -1) {
                            this.showAlert(AlertType.ERROR, "Erreur", "l'operation n'a pas pu etre realisee, surement un probleme de cle etrangere");
                        } else {
                            ((TableView<Commune>) this.currentTableView).getItems().add(newCommune); // Adds the row into the tableView
                        }
                    } catch (IllegalArgumentException e) {
                        this.showAlert(AlertType.ERROR, "Erreur", e.getMessage());
                    }
                    break;
                case "Departement":
                    try {
                        int idDep = Integer.parseInt(textFields.get(0).getText());
                        float invest = Float.parseFloat(textFields.get(2).getText());
                        Departement newDepartement = new Departement(idDep, invest);
                        res = this.departementDAO.create(newDepartement);
                        if (res == -1) {
                            this.showAlert(AlertType.ERROR, "Erreur", "l'operation n'a pas pu etre realisee, surement un probleme de cle etrangere");
                        } else {
                            ((TableView<Departement>) this.currentTableView).getItems().add(newDepartement);
                        }
                    } catch (IllegalArgumentException e) {
                        this.showAlert(AlertType.ERROR, "Erreur", e.getMessage());
                    }
                    break;
                case "Aeroport":
                    try {
                        String nomAeroport = textFields.get(0).getText();
                        String addresseAeroport = textFields.get(1).getText();
                        Departement depAeroport = this.departementDAO.findById(Integer.parseInt(textFields.get(2).getText()));
                        Aeroport newAeroport = new Aeroport(nomAeroport, addresseAeroport, depAeroport);
                        res = this.aeroportDAO.create(newAeroport);
                        if (res == -1) {
                            this.showAlert(AlertType.ERROR, "Erreur", "l'operation n'a pas pu etre realisee, surement un probleme de cle etrangere");
                        } else {
                            ((TableView<Aeroport>) this.currentTableView).getItems().add(newAeroport);
                        }
                    } catch (IllegalArgumentException e) {
                        this.showAlert(AlertType.ERROR, "Erreur", e.getMessage());
                    }
                    break;
                case "Gare":
                    try {
                        int code = Integer.parseInt(textFields.get(0).getText());
                        String nomGare = textFields.get(1).getText();
                        boolean estFret = Boolean.parseBoolean(textFields.get(2).getText());
                        boolean estVoyageurs = Boolean.parseBoolean(textFields.get(3).getText());
                        Commune comGare = this.communeDAO.findById(Integer.parseInt(textFields.get(4).getText()));
                        Gare newGare = new Gare(code, nomGare, estFret, estVoyageurs, comGare);
                        res = this.gareDAO.create(newGare);
                        if (res == -1) {
                            this.showAlert(AlertType.ERROR, "Erreur", "l'operation n'a pas pu etre realisee, surement un probleme de cle etrangere");
                        } else {
                            ((TableView<Gare>) this.currentTableView).getItems().add(newGare);
                        }
                    } catch (IllegalArgumentException e) {
                        this.showAlert(AlertType.ERROR, "Erreur", e.getMessage());
                    }
                    break;
                case "Annee":
                    try {
                        Annee newAnnee = new Annee(Integer.parseInt(textFields.get(0).getText()), Float.parseFloat(textFields.get(1).getText()));
                        res = this.anneeDAO.create(newAnnee);
                        if (res == -1) {
                            this.showAlert(AlertType.ERROR, "Erreur", "l'operation n'a pas pu etre realisee, surement un probleme de cle etrangere");
                        } else {
                            ((TableView<Annee>) this.currentTableView).getItems().add(newAnnee);
                        }
                    } catch (IllegalArgumentException e) {
                        this.showAlert(AlertType.ERROR, "Erreur", e.getMessage());
                    }
                    break;
                case "DonneesAnnuelles":
                    try {
                        DonneesAnnuelles newDonneesAnnuelles = new DonneesAnnuelles(
                            this.anneeDAO.findByYear(Integer.parseInt(textFields.get(0).getText())),
                            this.communeDAO.findById(Integer.parseInt(textFields.get(1).getText())),
                            Integer.parseInt(textFields.get(2).getText()),
                            Integer.parseInt(textFields.get(3).getText()),
                            Float.parseFloat(textFields.get(4).getText()),
                            Float.parseFloat(textFields.get(5).getText()),
                            Float.parseFloat(textFields.get(6).getText()),
                            Float.parseFloat(textFields.get(7).getText()),
                            Float.parseFloat(textFields.get(8).getText()),
                            Integer.parseInt(textFields.get(9).getText())
                        );
                        res = donneesAnnuellesDAO.create(newDonneesAnnuelles);
                        if (res == -1) {
                            this.showAlert(AlertType.ERROR, "Erreur", "l'operation n'a pas pu etre realisee, surement un probleme de cle etrangere");
                        } else {
                            ((TableView<DonneesAnnuelles>) this.currentTableView).getItems().add(newDonneesAnnuelles);
                        }
                    } catch (IllegalArgumentException e) {
                        this.showAlert(AlertType.ERROR, "Erreur", e.getMessage());
                    }
                    break;
                case "Voisinage":
                    try {
                        Voisinage newVoisinage = new Voisinage(
                            this.communeDAO.findById(Integer.parseInt(textFields.get(0).getText())),
                            this.communeDAO.findById(Integer.parseInt(textFields.get(1).getText()))
                        );
                        res = this.voisinageDAO.create(newVoisinage);
                        if (res == -1) {
                            this.showAlert(AlertType.ERROR, "Erreur", "l'operation n'a pas pu etre realisee, surement un probleme de cle etrangere");
                        } else {
                            ((TableView<Voisinage>) this.currentTableView).getItems().add(newVoisinage);
                        }
                    } catch (IllegalArgumentException e) {
                        this.showAlert(AlertType.ERROR, "Erreur", e.getMessage());
                    }
                    break;
                case "User":
                    try {
                        String id = textFields.get(0).getText();
                        String email = textFields.get(1).getText();
                        String password = textFields.get(2).getText();
                            
                        User newUser = new User(id, password, email);
                        res = this.userDAO.create(newUser);
                            
                        if (res == -1) {
                            this.showAlert(AlertType.ERROR, "Erreur", "L'operation n'a pas pu etre realisee, probablement un probleme de cle etrangere.");
                        } else {
                            ((TableView<User>) this.currentTableView).getItems().add(newUser);
                        }
                    } catch (IllegalArgumentException e) {
                        this.showAlert(AlertType.ERROR, "Erreur", e.getMessage());
                    }
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Method used to delete rows in the table.
     * It uses the Data Object Access classes to delete rows in the the bd_SAE data base.
     */
    @FXML
    private void deleteSelectedRow(ActionEvent event) {
        if (currentTableView != null) {
            String selectedTable = this.displayComboBox.getSelectionModel().getSelectedItem();
            switch (selectedTable) {
                case "Commune":
                    // Retrieves the selected object on the table View
                    Commune selectedCommune = (Commune) this.currentTableView.getSelectionModel().getSelectedItem();
                    if (selectedCommune != null) {
                        int res = this.communeDAO.delete(selectedCommune); // Deletes the row in the data base using DAO
                        if (res == -1) {
                            this.showAlert(AlertType.ERROR, "Erreur", "l'operation n'a pas pu etre realisee, surement un probleme de cle etrangere");
                            break;
                        }
                        ((TableView<Commune>) this.currentTableView).getItems().remove(selectedCommune); // Deletes the row in the table view.
                    }
                    break;
                case "Departement":
                    Departement selectedDepartement = (Departement) this.currentTableView.getSelectionModel().getSelectedItem();
                    if (selectedDepartement != null) {
                        int res = this.departementDAO.delete(selectedDepartement);
                        if (res == -1) {
                            this.showAlert(AlertType.ERROR, "Erreur", "l'operation n'a pas pu etre realisee, surement un probleme de cle etrangere");
                            break;
                        }
                        ((TableView<Departement>) this.currentTableView).getItems().remove(selectedDepartement);
                    }
                    break;
                case "Aeroport":
                    Aeroport selectedAeroport = (Aeroport) this.currentTableView.getSelectionModel().getSelectedItem();
                    if (selectedAeroport != null) {
                        int res = this.aeroportDAO.delete(selectedAeroport);
                        if (res == -1) {
                            this.showAlert(AlertType.ERROR, "Erreur", "l'operation n'a pas pu etre realisee, surement un probleme de cle etrangere");
                            break;
                        }
                        ((TableView<Aeroport>) this.currentTableView).getItems().remove(selectedAeroport);
                    }
                    break;
                case "Gare":
                    Gare selectedGare = (Gare) this.currentTableView.getSelectionModel().getSelectedItem();
                    if (selectedGare != null) {
                        int res = this.gareDAO.delete(selectedGare);
                        if (res == -1) {
                            this.showAlert(AlertType.ERROR, "Erreur", "l'operation n'a pas pu etre realisee, surement un probleme de cle etrangere");
                            break;
                        }
                        ((TableView<Gare>) this.currentTableView).getItems().remove(selectedGare);
                    }
                    break;
                case "Annee":
                    Annee selectedAnnee = (Annee) this.currentTableView.getSelectionModel().getSelectedItem();
                    if (selectedAnnee != null) {
                        int res = this.anneeDAO.delete(selectedAnnee);
                        if (res == -1) {
                            this.showAlert(AlertType.ERROR, "Erreur", "l'operation n'a pas pu etre realisee, surement un probleme de cle etrangere");
                            break;
                        }
                        ((TableView<Annee>) this.currentTableView).getItems().remove(selectedAnnee);
                    }
                    break;
                case "DonneesAnnuelles":
                    DonneesAnnuelles selectedDonneesAnnuelles = (DonneesAnnuelles) this.currentTableView.getSelectionModel().getSelectedItem();
                    if (selectedDonneesAnnuelles != null) {
                        int res = this.donneesAnnuellesDAO.delete(selectedDonneesAnnuelles);
                        if (res == -1) {
                            this.showAlert(AlertType.ERROR, "Erreur", "l'operation n'a pas pu etre realisee, surement un probleme de cle etrangere");
                            break;
                        }
                        ((TableView<DonneesAnnuelles>) this.currentTableView).getItems().remove(selectedDonneesAnnuelles);
                    }
                    break;
                case "Voisinage":
                    Voisinage selectedVoisinage = (Voisinage) this.currentTableView.getSelectionModel().getSelectedItem();
                    if (selectedVoisinage != null) {
                        int res = this.voisinageDAO.delete(selectedVoisinage);
                        if (res == -1) {
                            this.showAlert(AlertType.ERROR, "Erreur", "l'operation n'a pas pu etre realisee, surement un probleme de cle etrangere");
                            break;
                        }
                        ((TableView<Voisinage>) this.currentTableView).getItems().remove(selectedVoisinage);
                    }
                    break;
                case "User":
                    User selectedUser = (User) this.currentTableView.getSelectionModel().getSelectedItem();
                    if (selectedUser != null) {
                        int res = this.userDAO.delete(selectedUser);
                        if (res == -1) {
                            this.showAlert(AlertType.ERROR, "Erreur", "L'opération n'a pas pu être réalisée, probablement un problème de clé étrangère.");
                            break;
                        }
                        ((TableView<User>) currentTableView).getItems().remove(selectedUser);
                    }
                    break;
                default:
                    break;
            }
        }
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