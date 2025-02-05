package modele.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modele.data.Commune;
import modele.data.Voisinage;

/**
 * The VoisinageDao class provides CRUD operations for the Voisinage entity.
 * It uses JDBC to interact with the database.
 * @author Eliot ZIEGELMEYER, Iann VION, Ilias BELOUAHRANI
 * @version 1.0
 */
public class VoisinageDAO extends Dao<Voisinage> {

    /**
     * Inserts a new voisinage record into the database.
     * @param voisinage the voisinage to be inserted
     * @return the number of rows affected, or -1 if an error occurs
     */
    public int create(Voisinage voisinage) {
        String query = "INSERT INTO Voisinage(commune, communeVoisine) VALUES (?, ?)";
        try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(query)) {
            st.setInt(1, voisinage.getCommune().getCommuneId());
            st.setInt(2, voisinage.getCommuneVoisine().getCommuneId());
            return st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    /**
     * Updates an existing voisinage record in the database.
     * @param oldVoisinage the existing voisinage to be updated
     * @param newVoisinage the new voisinage data
     * @return the number of rows affected, or -1 if an error occurs
     */
    public int update(Voisinage voisinage) {
        String query = "UPDATE Voisinage SET communeVoisine = ? WHERE commune = ?";
        try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(query)) {
            st.setInt(1, voisinage.getCommuneVoisine().getCommuneId());
            st.setInt(2, voisinage.getCommune().getCommuneId());
            return st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    /**
     * Deletes a voisinage record from the database.
     * @param voisinage the voisinage to be deleted
     * @return the number of rows affected, or -1 if an error occurs
     */
    public int delete(Voisinage voisinage) {
        String query = "DELETE FROM Voisinage WHERE commune = ? AND communeVoisine = ?";
        try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(query)) {
            st.setInt(1, voisinage.getCommune().getCommuneId());
            st.setInt(2, voisinage.getCommuneVoisine().getCommuneId());
            return st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    /**
     * Retrieves all voisinage records from the database.
     * @return a list of voisinage records
     */
    public List<Voisinage> findAll() {
        List<Voisinage> voisinages = new ArrayList<>();
        String query = "SELECT * FROM Voisinage";
        CommuneDAO communeDAO = new CommuneDAO();

        try (Connection con = getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                int communeId = rs.getInt("commune");
                int communeVoisineId = rs.getInt("communeVoisine");

                Commune commune = communeDAO.findById(communeId);
                Commune communeVoisine = communeDAO.findById(communeVoisineId);

                voisinages.add(new Voisinage(commune, communeVoisine));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return voisinages;
    }
}