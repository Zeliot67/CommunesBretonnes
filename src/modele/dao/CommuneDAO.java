package modele.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modele.data.Commune;
import modele.data.Departement;

/**
 * The CommuneDAO class provides CRUD operations for the Commune entity.
 * It uses JDBC to interact with the database.
 * @author Eliot ZIEGELMEYER, Iann VION, Ilias BELOUAHRANI
 * @version 1.0
 */
public class CommuneDAO extends Dao<Commune> {

    /**
     * Inserts a new commune into the database.
     * @param commune the commune to be inserted
     * @return the number of rows affected, or -1 if an error occurs
     */
    public int create(Commune commune) {
        String query = "INSERT INTO Commune(idCommune, nomCommune, leDepartement) VALUES (?, ?, ?)";
        try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(query)) {
            st.setInt(1, commune.getCommuneId());
            st.setString(2, commune.getCommuneName());
            st.setInt(3, commune.getCommuneDepartment().getIdDep());
            return st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    /**
     * Updates an existing commune in the database.
     * @param commune the commune to be updated
     * @return the number of rows affected, or -1 if an error occurs
     */
    public int update(Commune commune) {
        String query = "UPDATE Commune SET nomCommune = ?, leDepartement = ? WHERE idCommune = ?";
        try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(query)) {
            st.setString(1, commune.getCommuneName());
            st.setInt(2, commune.getCommuneDepartment().getIdDep());
            st.setInt(3, commune.getCommuneId());
            return st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    /**
     * Deletes a commune from the database.
     * @param commune the commune to be deleted
     * @return the number of rows affected, or -1 if an error occurs
     */
    public int delete(Commune commune) {
        String query = "DELETE FROM Commune WHERE idCommune = ?";
        try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(query)) {
            st.setInt(1, commune.getCommuneId());
            return st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    /**
     * Retrieves all communes from the database.
     * @return a list of communes
     */
    public List<Commune> findAll() {
        List<Commune> communes = new ArrayList<>();
        String query = "SELECT * FROM Commune";
        DepartementDAO depDAO = new DepartementDAO();
        try (Connection con = getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                int id = rs.getInt("idCommune");
                String name = rs.getString("nomCommune");
                int departmentId = rs.getInt("leDepartement");
                Departement department = depDAO.findById(departmentId);
                communes.add(new Commune(id, name, department));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return communes;
    }

    /**
     * Finds a commune by its ID.
     * @param id the ID of the commune
     * @return the commune if found, or null if not found
     */
    public Commune findById(int id) {
        String query = "SELECT * FROM Commune WHERE idCommune = ?";
        DepartementDAO depDAO = new DepartementDAO();
        try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(query)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                String name = rs.getString("nomCommune");
                int departmentId = rs.getInt("leDepartement");
                Departement department = depDAO.findById(departmentId);
                return new Commune(id, name, department);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves all commune names from the database.
     * @return a list of commune names
     */
    public List<String> findAllCommuneNames() {
        List<String> communeNames = new ArrayList<>();
        String query = "SELECT nomCommune FROM Commune";
        try (Connection con = getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                communeNames.add(rs.getString("nomCommune"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return communeNames;
    }

    /**
     * Finds a commune by its name.
     * @param name the name of the commune
     * @return the commune if found, or null if not found
     */
    public Commune findByName(String name) {
        String query = "SELECT * FROM Commune WHERE nomCommune = ?";
        try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(query)) {
            st.setString(1, name);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("idCommune");
                String communeName = rs.getString("nomCommune");
                int departmentId = rs.getInt("leDepartement");
                Departement department = new DepartementDAO().findById(departmentId);
                return new Commune(id, communeName, department);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Finds the neighbors of a commune by its ID.
     * @param communeId the ID of the commune
     * @return a list of neighboring communes
     */
    public ArrayList<Commune> findNeighbors(int communeId) {
        ArrayList<Commune> neighbors = new ArrayList<>();
        String query = "SELECT idCommune, nomCommune, leDepartement FROM Voisinage JOIN Commune ON communeVoisine = idCommune WHERE commune = ?";
        try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(query)) {
            st.setInt(1, communeId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("idCommune");
                String name = rs.getString("nomCommune");
                int departmentId = rs.getInt("leDepartement");
                Departement department = new DepartementDAO().findById(departmentId);
                neighbors.add(new Commune(id, name, department));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return neighbors;
    }
}