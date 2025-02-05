package modele.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modele.data.Annee;

/**
 * The AnneeDAO class provides CRUD operations for the Annee entity.
 * It uses JDBC to interact with the database.
 * @author Eliot ZIEGELMEYER, Iann VION, Ilias BELOUAHRANI
 * @version 1.0
 */
public class AnneeDAO extends Dao<Annee> {

    /**
     * Inserts a new year into the database.
     * @param annee the year to be inserted
     * @return the number of rows affected, or -1 if an error occurs
     */
    public int create(Annee annee) {
        String query = "INSERT INTO Annee(annee, tauxInflation) VALUES (?, ?)";
        try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(query)) {
            st.setInt(1, annee.getYear());
            st.setFloat(2, annee.getInflationRate());
            return st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    /**
     * Updates an existing year in the database.
     * @param annee the year to be updated
     * @return the number of rows affected, or -1 if an error occurs
     */
    public int update(Annee annee) {
        String query = "UPDATE Annee SET tauxInflation = ? WHERE annee = ?";
        try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(query)) {
            st.setFloat(1, annee.getInflationRate());
            st.setInt(2, annee.getYear());
            return st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    /**
     * Deletes a year from the database.
     * @param annee the year to be deleted
     * @return the number of rows affected, or -1 if an error occurs
     */
    public int delete(Annee annee) {
        String query = "DELETE FROM Annee WHERE annee = ?";
        try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(query)) {
            st.setInt(1, annee.getYear());
            return st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    /**
     * Retrieves all years from the database.
     * @return a list of years
     */
    public List<Annee> findAll() {
        List<Annee> annees = new ArrayList<>();
        String query = "SELECT * FROM Annee";
        try (Connection con = getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                int year = rs.getInt("annee");
                float tauxInflation = rs.getFloat("tauxInflation");
                annees.add(new Annee(year, tauxInflation));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return annees;
    }

    /**
     * Finds a year by its year value.
     * @param year the year value
     * @return the year if found, or null if not found
     */
    public Annee findByYear(int year) {
        String query = "SELECT * FROM Annee WHERE annee = ?";
        try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(query)) {
            st.setInt(1, year);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                float tauxInflation = rs.getFloat("tauxInflation");
                return new Annee(year, tauxInflation);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}