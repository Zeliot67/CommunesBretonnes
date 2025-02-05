package modele.data;

/**
 * Represents an airport with a name, address, and department.
 * @author ZIEGELMEYER Eliot, VION Iann, BELOUAHRANI Ilias
 * @version 1.0
 */
public class Aeroport {

    /**
     * The airport name.
     */
    private String name;

    /**
     * The airport adress.
     */
    private String address;

    /**
     * The airport department.
     */
    private final Departement department;

    /**
     * Constructs an Aeroport with the specified name, address, and department.
     * @param name the name of the airport
     * @param address the address of the airport
     * @param department the department of the airport
     * @throws IllegalArgumentException if any of the parameters are null or if the address does not contain the department ID
     */
    public Aeroport(String name, String address, Departement department) {
        if (name == null) {
            throw new IllegalArgumentException("Error : Aeroport : the airport name cannot be null.");
        }
        if (address == null) {
            throw new IllegalArgumentException("Error : Aeroport : the airport address cannot be null.");
        }
        if (department == null) {
            throw new IllegalArgumentException("Error : Aeroport : the airport department cannot be null.");
        }
        if (!this.isValidAddress(address, department)) {
            throw new IllegalArgumentException("Error : Aeroport : l'adresse de l'aeroport doit contenir l'id du departement.");
        }
        this.name = name;
        this.address = address;
        this.department = department;
    }

    /**
     * Returns the name of the airport.
     * @return the name of the airport
     */
    public String getName() {
        return this.name;
    }
    
    /**
     * Returns the address of the airport.
     * @return the address of the airport
     */
    public String getAddress() {
        return this.address;
    }

    /**
     * Returns the department of the airport.
     * @return the department of the airport
     */
    public Departement getDepartment() {
        return new Departement(this.department.getIdDep(), this.department.getInvestissementCulturel2019());
    }

    /**
     * Sets the name of the airport.
     * @param name the new name of the airport
     * @throws IllegalArgumentException if the name is null
     */
    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Error : setName : the airport name cannot be null.");
        }
        this.name = name;
    }

    /**
     * Sets the address of the airport.
     * @param address the new address of the airport
     * @throws IllegalArgumentException if the address is null or does not contain the department ID
     */
    public void setAddress(String address) {
        if (address == null) {
            throw new IllegalArgumentException("Error : setAddress : the airport address cannot be null.");
        }
        if (!this.isValidAddress(address, this.department)) {
            throw new IllegalArgumentException("Error : setAddress : l'adresse de l'aeroport doit contenir l'id du departement.");
        }
        this.address = address;
    }

    /**
     * Checks if the airport is in the specified department.
     * @param department the department to check
     * @return true if the airport is in the specified department, false otherwise
     */
    public boolean isInDepartment(Departement department) {
        if (department == null) {
            throw new IllegalArgumentException("Error : isInDepartment : the department to check with cannot be null.");
        }
        return this.department.equals(department);
    }

    /**
     * Checks equality between two airports.
     * @param other the other airport
     * @return true if the airports are equal, false otherwise
     * @throws IllegalArgumentException if the other airport is null
     */
    public boolean equals(Aeroport other) {
        if (other == null) {
            throw new IllegalArgumentException("Error : equals : compared airport cannot be null.");
        }
        return this.name.equals(other.name)
                && this.address.equals(other.address)
                && this.department.equals(other.department);
    }

    /**
     * Returns a string representation of the airport.
     * @return a string representation of the airport
     */
    public String toString() {
        return "Airport : \n" + 
               "\tName : " + this.name + "\n" +
               "\tAddress : " + this.address + "\n" +
               "\tDepartment : " + this.department.getNomDep() + "\n";
    }

    /**
     * Checks if the address is valid for the specified department.
     * @param address the address to check
     * @param department the department to check against
     * @return true if the address contains the department ID, false otherwise
     */
    private boolean isValidAddress(String address, Departement department) {
        if (address == null || department == null) {
            throw new IllegalArgumentException("Error : isValidAddress : the address or department are null.");
        }
        return address.contains(String.valueOf(department.getIdDep()));
    }
}