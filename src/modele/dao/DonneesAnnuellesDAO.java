package modele.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modele.data.*;

/**
 * The DonneesAnnuellesDAO class provides CRUD operations for the DonneesAnnuelles entity.
 * It uses JDBC to interact with the database.
 * @author Eliot ZIEGELMEYER, Iann VION, Ilias BELOUAHRANI
 * @version 1.0
 */
public class DonneesAnnuellesDAO extends Dao<DonneesAnnuelles> {

    /**
     * Inserts a new annual data record into the database.
     * @param donneesAnnuelles the annual data to be inserted
     * @return the number of rows affected, or -1 if an error occurs
     */
    public int create(DonneesAnnuelles donneesAnnuelles) {
        String query = "INSERT INTO DONNEESANNUELLES(lAnnee, laCommune, nbMaison, nbAppart, prixMoyen, prixM2Moyen, surfaceMoy, depensesCulturellesTotales, budgetTotal, population) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(query)) {
            st.setInt(1, donneesAnnuelles.getYear().getYear());
            st.setInt(2, donneesAnnuelles.getCommune().getCommuneId());
            st.setInt(3, donneesAnnuelles.getNbHouses());
            st.setInt(4, donneesAnnuelles.getNbAppart());
            st.setFloat(5, donneesAnnuelles.getAveragePrice());
            st.setFloat(6, donneesAnnuelles.getAverageM2Price());
            st.setFloat(7, donneesAnnuelles.getAverageSurfaceArea());
            st.setFloat(8, donneesAnnuelles.getTotalCulturalExpenses());
            st.setFloat(9, donneesAnnuelles.getTotalBudget());
            st.setFloat(10, donneesAnnuelles.getPopulation());
            return st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    /**
     * Updates an existing annual data record in the database.
     * @param donneesAnnuelles the annual data to be updated
     * @return the number of rows affected, or -1 if an error occurs
     */
    public int update(DonneesAnnuelles donneesAnnuelles) {
        String query = "UPDATE DONNEESANNUELLES SET nbMaison = ?, nbAppart = ?, prixMoyen = ?, prixM2Moyen = ?, surfaceMoy = ?, depensesCulturellesTotales = ?, budgetTotal = ?, population = ? WHERE lAnnee = ? AND laCommune = ?";
        try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(query)) {
            st.setInt(1, donneesAnnuelles.getNbHouses());
            st.setInt(2, donneesAnnuelles.getNbAppart());
            st.setFloat(3, donneesAnnuelles.getAveragePrice());
            st.setFloat(4, donneesAnnuelles.getAverageM2Price());
            st.setFloat(5, donneesAnnuelles.getAverageSurfaceArea());
            st.setFloat(6, donneesAnnuelles.getTotalCulturalExpenses());
            st.setFloat(7, donneesAnnuelles.getTotalBudget());
            st.setFloat(8, donneesAnnuelles.getPopulation());
            st.setInt(9, donneesAnnuelles.getYear().getYear());
            st.setInt(10, donneesAnnuelles.getCommune().getCommuneId());
            return st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    /**
     * Deletes an annual data record from the database.
     * @param donneesAnnuelles the annual data to be deleted
     * @return the number of rows affected, or -1 if an error occurs
     */
    public int delete(DonneesAnnuelles donneesAnnuelles) {
        String query = "DELETE FROM DONNEESANNUELLES WHERE lAnnee = ? AND laCommune = ?";
        try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(query)) {
            st.setInt(1, donneesAnnuelles.getYear().getYear());
            st.setInt(2, donneesAnnuelles.getCommune().getCommuneId());
            return st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    /**
     * Retrieves all annual data records from the database.
     * @return a list of annual data records
     */
    public List<DonneesAnnuelles> findAll() {
        List<DonneesAnnuelles> donneesAnnuellesList = new ArrayList<>();
        String query = "SELECT * FROM DONNEESANNUELLES";
        CommuneDAO comDAO = new CommuneDAO();
        AnneeDAO anneeDAO = new AnneeDAO();
        try (Connection con = getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                int year = rs.getInt("lAnnee");
                int communeId = rs.getInt("laCommune");
                int nbHouses = rs.getInt("nbMaison");
                int nbAppart = rs.getInt("nbAppart");
                float averagePrice = rs.getFloat("prixMoyen");
                float averageM2Price = rs.getFloat("prixM2Moyen");
                float averageSurfaceArea = rs.getFloat("surfaceMoy");
                float totalCulturalExpenses = rs.getFloat("depensesCulturellesTotales");
                float totalBudget = rs.getFloat("budgetTotal");
                float population = rs.getFloat("population");
                Annee annee = anneeDAO.findByYear(year);
                Commune commune = comDAO.findById(communeId);
                donneesAnnuellesList.add(new DonneesAnnuelles(annee, commune, nbHouses, nbAppart, averagePrice, averageM2Price, averageSurfaceArea, totalCulturalExpenses, totalBudget, population));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return donneesAnnuellesList;
    }

    /**
     * Finds an annual data record by year and commune ID.
     * @param year the year of the annual data
     * @param communeId the ID of the commune
     * @return the annual data record if found, or null if not found
     */
    public DonneesAnnuelles findByYearAndCommune(int year, int communeId) {
        String query = "SELECT * FROM DONNEESANNUELLES WHERE lAnnee = ? AND laCommune = ?";
        CommuneDAO comDAO = new CommuneDAO();
        AnneeDAO anneeDAO = new AnneeDAO();
        try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(query)) {
            st.setInt(1, year);
            st.setInt(2, communeId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                int nbHouses = rs.getInt("nbMaison");
                int nbAppart = rs.getInt("nbAppart");
                float averagePrice = rs.getFloat("prixMoyen");
                float averageM2Price = rs.getFloat("prixM2Moyen");
                float averageSurfaceArea = rs.getFloat("surfaceMoy");
                float totalCulturalExpenses = rs.getFloat("depensesCulturellesTotales");
                float totalBudget = rs.getFloat("budgetTotal");
                float population = rs.getFloat("population");
                Annee annee = anneeDAO.findByYear(year);
                Commune commune = comDAO.findById(communeId);
                return new DonneesAnnuelles(annee, commune, nbHouses, nbAppart, averagePrice, averageM2Price, averageSurfaceArea, totalCulturalExpenses, totalBudget, population);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Finds the annual data for a given Commune.
     * @param communeId the commune ID
     * @return The list of annual Data for the given commune.
     */
    public List<DonneesAnnuelles> findByCommune(int communeId) {
        List<DonneesAnnuelles> donneesAnnuellesList = new ArrayList<>();
        String query = "SELECT * FROM DONNEESANNUELLES WHERE laCommune = ?";
        CommuneDAO comDAO = new CommuneDAO();
        AnneeDAO anneeDAO = new AnneeDAO();
        try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(query)) {
            st.setInt(1, communeId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int year = rs.getInt("lAnnee");
                int nbHouses = rs.getInt("nbMaison");
                int nbAppart = rs.getInt("nbAppart");
                float averagePrice = rs.getFloat("prixMoyen");
                float averageM2Price = rs.getFloat("prixM2Moyen");
                float averageSurfaceArea = rs.getFloat("surfaceMoy");
                float totalCulturalExpenses = rs.getFloat("depensesCulturellesTotales");
                float totalBudget = rs.getFloat("budgetTotal");
                int population = rs.getInt("population");
                Annee annee = anneeDAO.findByYear(year);
                Commune commune = comDAO.findById(communeId);
                donneesAnnuellesList.add(new DonneesAnnuelles(annee, commune, nbHouses, nbAppart, averagePrice, averageM2Price, averageSurfaceArea, totalCulturalExpenses, totalBudget, population));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return donneesAnnuellesList;
    }
}
