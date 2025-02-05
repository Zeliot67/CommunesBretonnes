package modele.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

/**
 * Abstract Data Access Object (DAO) class that provides basic CRUD operations.
 * This class is designed to be extended by specific DAO implementations for different entities.
 * @author Eliot ZIEGELMEYER, Iann VION, Ilias BELOUAHRANI
 * @version 1.0
 */
public abstract class Dao<T> {

    private static String driverClassName = "com.mysql.cj.jdbc.Driver";
    private static String url = "jdbc:mysql:///bd_sae";
    private static String user = "Eliot";
    private static String password = "Eliot2006!mysql";

    /**
     * Gets a connection to the database.
     * @return a Connection object
     * @throws SQLException if a database access error occurs
     */
    protected Connection getConnection() throws SQLException {
        // Charger la classe du pilote
        try {
            Class.forName(driverClassName);
        } 
        catch (ClassNotFoundException ex) {
            ex.printStackTrace ();
            return null;
        }
        // Obtenir la connection
        return DriverManager.getConnection(url , user , password);
    }
    public abstract List<T> findAll();
    public abstract int update(T element);
    public abstract int delete(T element);
    public abstract int create(T element);
}
