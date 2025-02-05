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
 * The UserDao class provides CRUD operations for the User entity.
 * It uses JDBC to interact with the database.
 * @author Eliot ZIEGELMEYER, Iann VION, Ilias BELOUAHRANI
 * @version 1.0
 */
public class UserDAO extends Dao<User> {

    /**
     * Inserts a new user into the database.
     *
     * @param user the user to be inserted
     * @return the number of rows affected, or -1 if an error occurs
     */
    public int create(User user) {
        String query = "INSERT INTO USER(LOGIN, PWD, EMAIL) VALUES (?, ?, ?)";
        try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(query)) {
            st.setString(1, user.getLogin());
            st.setString(2, user.getPwd());
            st.setString(3, user.getEmail());
            return st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    /**
     * Updates an existing user in the database.
     *
     * @param user the user to be updated
     * @return the number of rows affected, or -1 if an error occurs
     */
    public int update(User user) {
        String query = "UPDATE USER SET LOGIN = ?, PWD = ?, EMAIL = ? WHERE LOGIN = ?";
        try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(query)) {
            st.setString(1, user.getLogin());
            st.setString(2, user.getPwd());
            st.setString(3, user.getEmail());
            st.setString(4, user.getLogin());
            return st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    /**
     * Deletes a user from the database.
     *
     * @param user the user to be deleted
     * @return the number of rows affected, or -1 if an error occurs
     */
    public int delete(User user) {
        String query = "DELETE FROM USER WHERE LOGIN = ?";
        try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(query)) {
            st.setString(1, user.getLogin());
            return st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    /**
     * Retrieves all users from the database.
     *
     * @return a list of users
     */
    public List<User> findAll() {
        List<User> users = new LinkedList<>();
        String query = "SELECT * FROM USER";
        try (Connection con = getConnection(); Statement st = con.createStatement(); ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                String login = rs.getString("LOGIN");
                String pwd = rs.getString("PWD");
                String email = rs.getString("EMAIL");
                users.add(new User(login, pwd, email));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return users;
    }

    /**
     * Finds a user by login and password.
     *
     * @param login the login of the user
     * @param pwd the password of the user
     * @return the user if found, or null if not found
     */
    public User findByLoginPwd(String login, String pwd) {
        String query = "SELECT * FROM USER WHERE LOGIN = ? AND PWD = ?";
        try (Connection con = getConnection(); PreparedStatement st = con.prepareStatement(query)) {
            st.setString(1, login);
            st.setString(2, pwd);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                String l = rs.getString("LOGIN");
                String p = rs.getString("PWD");
                String e = rs.getString("EMAIL");
                return new User(l, p, e);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}