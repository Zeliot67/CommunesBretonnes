package modele.data;

import java.util.regex.Pattern;

/**
 * The User class represents a user with a login, password, and email address.
 * It includes methods to access and modify these attributes, as well as to validate inputs.
 * @author ZIEGELMEYER Eliot
 * @author VION Iann
 * @author BELOUAHRANI Ilias
 * @version 1.0
 */
public class User {

    /** The user's login. */
    private String login;

    /** The user's password. */
    private String pwd;

    /** The user's email. */
    private String email;

    /**
     * Constructor for the User class.
     * @param login the user's login
     * @param pwd the user's password
     * @param email the user's email address
     * @throws IllegalArgumentException if the login, password, or email are not valid
     */
    public User(String login , String pwd, String email) {
        setLogin(login);
        setPwd(pwd);
        setEmail(email);
    }

    /**
     * Returns the user's login.
     * @return the user's login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Sets the user's login.
     * @param login the new login for the user
     * @throws IllegalArgumentException if the login is null or empty
     */
    public void setLogin(String login) {
        if (login == null || login.trim().isEmpty()) {
            throw new IllegalArgumentException("Erreur : setLogin : Le login ne peut pas etre vide.");
        }
        this.login = login;
    }

    /**
     * Returns the user's password.
     * @return the user's password
     */
    public String getPwd() {
        
        return pwd;
    }

    /**
     * Sets the user's password.
     * @param pwd the new password for the user
     * @throws IllegalArgumentException if the password is null or empty
     */
    public void setPwd(String pwd) {
        if (pwd == null || pwd.trim().isEmpty()) {
            throw new IllegalArgumentException("Erreur : setPwd : Le mot de passe ne peut pas etre vide.");
        }
        this.pwd = pwd;
    }

    /**
     * Returns the user's email address.
     * @return the user's email address
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Sets the user's email address.
     * @param email the new email address for the user
     * @throws IllegalArgumentException if the email is null or invalid
     */
    public void setEmail(String email) {
        if (email == null || !isValidEmail(email)) {
            throw new IllegalArgumentException("Erreur : setEmail : L'adresse email n'est pas valide.");
        }
        this.email = email;
    }

     /**
     * Validates the email address.
     * @param email the email address to validate
     * @return true if the email address is valid, false otherwise
     */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }
}
