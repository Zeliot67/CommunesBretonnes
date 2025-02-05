package modele.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modele.data.Gare;
import modele.data.Commune;

/**
 * The GareDAO class provides CRUD operations for the Gare entity.
 * It uses JDBC to interact with the database.
 * @author Eliot ZIEGELMEYER, Iann VION, Ilias BELOUAHRANI
 * @version 1.0
 */
public class GareDAO extends Dao<Gare> {

    /**
     * Inserts a new train station into the database.
     * @param gare the train station to be inserted
     * @return the number of rows affected, or -1 if an error occurs
     */
    public int create(Gare gare) {
        String query = "INSERT INTO Gare (codeGare, nomGare, estFret, estVoyageur, laCommune) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(query)) {
            st.setInt(1, gare.getStationCode());
            st.setString(2, gare.getStationName());
            st.setBoolean(3, gare.isFreight());
            st.setBoolean(4, gare.isPassenger());
            st.setInt(5, gare.getTown().getCommuneId());
            return st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    /**
     * Updates an existing train station in the database.
     * @param gare the train station to be updated
     * @return the number of rows affected, or -1 if an error occurs
     */
    public int update(Gare gare) {
        String query = "UPDATE Gare SET nomGare = ?, estFret = ?, estVoyageur = ?, laCommune = ? WHERE codeGare = ?";
        try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(query)) {
            st.setString(1, gare.getStationName());
            st.setBoolean(2, gare.isFreight());
            st.setBoolean(3, gare.isPassenger());
            st.setInt(4, gare.getTown().getCommuneId());
            st.setInt(5, gare.getStationCode());
            return st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    /**
     * Deletes a train station from the database.
     * @param departement the train station to be deleted
     * @return the number of rows affected, or -1 if an error occurs
     */
    public int delete(Gare gare) {
        String query = "DELETE FROM Gare WHERE codeGare = ?";
        try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(query)) {
            st.setInt(1, gare.getStationCode());
            return st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    /**
     * Retrieves all train stations from the database.
     * @return a list of train stations
     */
    public List<Gare> findAll() {
        List<Gare> gares = new ArrayList<>();
        String query = "SELECT * FROM Gare";
        CommuneDAO comDAO = new CommuneDAO();
        try (Connection con = getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                int codeGare = rs.getInt("codeGare");
                String nomGare = rs.getString("nomGare");
                boolean estFret = rs.getBoolean("estFret");
                boolean estVoyageur = rs.getBoolean("estVoyageur");
                int laCommune = rs.getInt("laCommune");
                Commune commune = comDAO.findById(laCommune);
                gares.add(new Gare(codeGare, nomGare, estFret, estVoyageur, commune));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return gares;
    }

    /**
     * Finds a train station by its code.
     * @param id the code of the train station
     * @return the train station if found, or null if not found
     */
    public Gare findByCode(int codeGare) {
        String query = "SELECT * FROM Gare WHERE codeGare = ?";
        CommuneDAO comDAO = new CommuneDAO();
        try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(query)) {
            st.setInt(1, codeGare);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                String nomGare = rs.getString("nomGare");
                boolean estFret = rs.getBoolean("estFret");
                boolean estVoyageur = rs.getBoolean("estVoyageur");
                int laCommune = rs.getInt("laCommune");
                Commune commune = comDAO.findById(laCommune);
                return new Gare(codeGare, nomGare, estFret, estVoyageur, commune);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Checks if a commune has a freight train station.
     * @param communeId the ID of the commune
     * @return true if the commune has a freight train station, false otherwise
     */
    public boolean hasFreightGare(int communeId) {
        String query = "SELECT COUNT(*) FROM Gare WHERE laCommune = ? AND estFret = TRUE";
        try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(query)) {
            st.setInt(1, communeId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Checks if a commune has a passenger train station.
     * @param communeId the ID of the commune
     * @return true if the commune has a passenger train station, false otherwise
     */
    public boolean hasPassengerGare(int communeId) {
        String query = "SELECT COUNT(*) FROM Gare WHERE laCommune = ? AND estVoyageur = TRUE";
        try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(query)) {
            st.setInt(1, communeId);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}