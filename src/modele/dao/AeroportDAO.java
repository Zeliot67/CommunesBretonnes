package modele.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import modele.data.*;

/**
 * The AeroportDAO class provides CRUD operations for the Aeroport entity.
 * It uses JDBC to interact with the database.
 * @author Eliot ZIEGELMEYER, Iann VION, Ilias BELOUAHRANI
 * @version 1.0
 */
public class AeroportDAO extends Dao<Aeroport> {

    /**
     * Inserts a new airport into the database.
     * @param aeroport the airport to be inserted
     * @return the number of rows affected, or -1 if an error occurs
     */
    public int create(Aeroport aeroport) {
        String query = "INSERT INTO AEROPORT(nom, adresse, leDepartement) VALUES (?, ?, ?)";
        try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(query)) {
            st.setString(1, aeroport.getName());
            st.setString(2, aeroport.getAddress());
            st.setInt(3, aeroport.getDepartment().getIdDep());
            return st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    /**
     * Updates an existing airport in the database.
     * @param aeroport the airport to be updated
     * @return the number of rows affected, or -1 if an error occurs
     */
    public int update(Aeroport aeroport) {
        String query = "UPDATE AEROPORT SET nom = ?, adresse = ?, leDepartement = ? WHERE nom = ?";
        try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(query)) {
            st.setString(1, aeroport.getName());
            st.setString(2, aeroport.getAddress());
            st.setInt(3, aeroport.getDepartment().getIdDep());
            st.setString(4, aeroport.getName());
            return st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    /**
     * Deletes an airport from the database.
     * @param aeroport the airport to be deleted
     * @return the number of rows affected, or -1 if an error occurs
     */
    public int delete(Aeroport aeroport) {
        String query = "DELETE FROM AEROPORT WHERE nom = ?";
        try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(query)) {
            st.setString(1, aeroport.getName());
            return st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    /**
     * Retrieves all airports from the database.
     * @return a list of airports
     */
    public List<Aeroport> findAll() {
        List<Aeroport> aeroports = new LinkedList<>();
        String query = "SELECT * FROM AEROPORT";
        DepartementDAO depDAO = new DepartementDAO();
        try (Connection con = getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                String name = rs.getString("nom");
                String address = rs.getString("adresse");
                int departmentId = rs.getInt("leDepartement");
                Departement department = depDAO.findById(departmentId);
                aeroports.add(new Aeroport(name, address, department));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return aeroports;
    }

    /**
     * Finds an airport by name.
     * @param name the name of the airport
     * @return the airport if found, or null if not found
     */
    public Aeroport findByName(String name) {
        String query = "SELECT * FROM AEROPORT WHERE nom = ?";
        DepartementDAO depDAO = new DepartementDAO();
        try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(query)) {
            st.setString(1, name);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                String address = rs.getString("adresse");
                int departmentId = rs.getInt("leDepartement");
                Departement department = depDAO.findById(departmentId);
                return new Aeroport(name, address, department);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}