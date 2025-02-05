package modele.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import modele.data.Departement;

/**
 * The DepartementDAO class provides CRUD operations for the Departement entity.
 * It uses JDBC to interact with the database.
 * @author Eliot ZIEGELMEYER, Iann VION, Ilias BELOUAHRANI
 * @version 1.0
 */
public class DepartementDAO extends Dao<Departement> {

    /**
     * Inserts a new department into the database.
     * @param departement the department to be inserted
     * @return the number of rows affected, or -1 if an error occurs
     */
    public int create(Departement departement) {
        String query = "INSERT INTO Departement(idDep, nomDep, investissementCulturel2019) VALUES (?, ?, ?)";
        try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(query)) {
            st.setInt(1, departement.getIdDep());
            st.setString(2, departement.getNomDep());
            st.setFloat(3, departement.getInvestissementCulturel2019());
            return st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    /**
     * Updates an existing department in the database.
     * @param departement the department to be updated
     * @return the number of rows affected, or -1 if an error occurs
     */
    public int update(Departement departement) {
        String query = "UPDATE Departement SET nomDep = ?, investissementCulturel2019 = ? WHERE idDep = ?";
        try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(query)) {
            st.setString(1, departement.getNomDep());
            st.setFloat(2, departement.getInvestissementCulturel2019());
            st.setInt(3, departement.getIdDep());
            return st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    /**
     * Deletes a department from the database.
     * @param departement the department to be deleted
     * @return the number of rows affected, or -1 if an error occurs
     */
    public int delete(Departement departement) {
        String query = "DELETE FROM Departement WHERE idDep = ?";
        try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(query)) {
            st.setInt(1, departement.getIdDep());
            return st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    /**
     * Retrieves all departments from the database.
     * @return a list of departments
     */
    public List<Departement> findAll() {
        List<Departement> departements = new LinkedList<>();
        String query = "SELECT * FROM Departement";
        try (Connection con = getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                int id = rs.getInt("idDep");
                String nom = rs.getString("nomDep");
                float investissementCulturel2019 = rs.getFloat("investissementCulturel2019");
                departements.add(new Departement(id, investissementCulturel2019));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return departements;
    }

    /**
     * Finds a department by its ID.
     * @param id the ID of the department
     * @return the department if found, or null if not found
     */
    public Departement findById(int id) {
        String query = "SELECT * FROM Departement WHERE idDep = ?";
        try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(query)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                String nom = rs.getString("nomDep");
                float investissementCulturel2019 = rs.getFloat("investissementCulturel2019");
                return new Departement(id, investissementCulturel2019);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}