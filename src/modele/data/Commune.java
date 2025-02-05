package modele.data;

import java.util.ArrayList;

/**
 * The Commune class represents a town with an ID, name, department, and a list of neighboring towns.
 * @author ZIEGELMEYER Eliot, VION Iann, BELOUAHRANI Ilias
 * @version 1.0
 */
public class Commune {

    /**
     * Town's ID.
     */
    private final int communeId;

    /**
     * Town's name.
     */
    private String communeName;

    /**
     * Town's department.
     */
    private final Departement communeDepartment;

    /**
     * Town's neighbors.
     */
    private ArrayList<Commune> neighbors;

    /**
     * Constructs a new Commune with the specified ID, name, and department.
     * @param communeId the ID of the commune, must be non-negative
     * @param communeName the name of the commune, must not be null
     * @param department the department of the commune, must not be null
     * @throws IllegalArgumentException if communeId is negative, or if communeName or department is null
     */
    public Commune(int communeId, String communeName, Departement department) {
        if (communeId < 0) {
            throw new IllegalArgumentException("Error : Commune : l'id ne peut pas etre negatif.");
        }
        if (communeName == null) {
            throw new IllegalArgumentException("Error : Commune : the name cannot be null.");
        }
        if (department == null) {
            throw new IllegalArgumentException("Error : Commune : the department cannot be null");
        }
        this.communeId = communeId;
        this.communeName = communeName;
        this.communeDepartment = department;
        this.neighbors = new ArrayList<Commune>();
    }

    /**
     * Constructs a new Commune with the specified ID, name, department, and list of neighbors.
     * @param communeId the ID of the commune, must be non-negative
     * @param communeName the name of the commune, must not be null
     * @param department the department of the commune, must not be null
     * @param neighborList the list of neighboring communes, must not be null
     * @throws IllegalArgumentException if communeId is negative, or if communeName, department, or neighborList is null
     */
    public Commune(int communeId, String communeName, Departement department, ArrayList<Commune> neighborList) {
        if (communeId < 0) {
            throw new IllegalArgumentException("Error : Commune : l'id ne peut pas etre negatif.");
        }
        if (communeName == null) {
            throw new IllegalArgumentException("Error : Commune : the name cannot be null.");
        }
        if (department == null) {
            throw new IllegalArgumentException("Error : Commune : the department cannot be null");
        }
        if (neighborList == null) {
            throw new IllegalArgumentException("Error : Commune : the neighbor list cannot be null.");
        }
        this.communeId = communeId;
        this.communeName = communeName;
        this.communeDepartment = department;
        this.neighbors = new ArrayList<Commune>(neighborList);
    }

    /**
     * Returns the ID of the commune.
     * @return the ID of the commune
     */
    public int getCommuneId() {
        return this.communeId;
    }

    /**
     * Returns the name of the commune.
     * @return the name of the commune
     */
    public String getCommuneName() {
        return this.communeName;
    }

    /**
     * Returns the department of the commune.
     * @return the department of the commune
     */
    public Departement getCommuneDepartment() {
        return new Departement(this.communeDepartment.getIdDep(), this.communeDepartment.getInvestissementCulturel2019());
    }

    /**
     * Returns a list of neighboring communes.
     * @return a list of neighboring communes
     */
    public ArrayList<Commune> getNeighbors() {
        return new ArrayList<Commune>(this.neighbors);
    }

    /**
     * Sets the name of the commune.
     * @param communeName the new name of the commune, must not be null
     * @throws IllegalArgumentException if communeName is null
     */
    public void setCommuneName(String communeName) {
        if (communeName == null) {
            throw new IllegalArgumentException("Error : setCommuneName : the name cannot be null.");
        }
        this.communeName = communeName;
    }

    /**
     * Sets the list of neighboring communes.
     * @param neighbors the new list of neighboring communes, must not be null
     * @throws IllegalArgumentException if neighbors is null
     */
    public void setNeighbors(ArrayList<Commune> neighbors) {
        if (neighbors == null) {
            throw new IllegalArgumentException("Error : setNeighbors : the neighbor list cannot be null.");
        }
        this.neighbors = new ArrayList<Commune>(neighbors);
    }

    /**
     * Adds a neighboring commune.
     * @param commune the neighboring commune to add, must not be null
     * @throws IllegalArgumentException if commune is null
     */
    public void addNeighbor(Commune commune) {
        if (commune == null) {
            throw new IllegalArgumentException("Error : addNeighbor : the neighbor cannot be null.");
        }
        this.neighbors.add(commune);
    }

    /**
     * Removes a neighboring commune.
     * @param commune the neighboring commune to remove, must not be null
     * @throws IllegalArgumentException if commune is null
     */
    public void removeNeighbor(Commune commune) {
        if (commune == null) {
            throw new IllegalArgumentException("Error : removeNeighbor : the neighbor cannot be null.");
        }
        this.neighbors.remove(commune);
    }

    /**
     * Checks if a given commune is a neighbor.
     * @param commune the commune to check
     * @return true if the given commune is a neighbor, false otherwise
     */
    public boolean isNeighbor(Commune commune) {
        if (commune == null) {
            throw new IllegalArgumentException("Error : isNeighbor : the town is null.");
        }
        return this.neighbors.contains(commune);
    }

    /**
     * Returns the number of neighboring communes.
     * @return the number of neighboring communes
     */
    public int numberOfNeighbors() {
        return this.neighbors.size();
    }

    /**
     * Finds a neighboring commune by its ID.
     * @param id the ID of the neighboring commune to find
     * @return the neighboring commune with the specified ID, or null if not found
     */
    public Commune findNeighborById(int id) {
        Commune result = null;
        for (Commune neighbor : this.neighbors) {
            if (neighbor.getCommuneId() == id) {
                result = neighbor;
            }
        }
        return result;
    }

    /**
     * Returns a list of names of neighboring communes.
     * @return a list of names of neighboring communes
     */
    public ArrayList<String> neighborNames() {
        ArrayList<String> names = new ArrayList<>();
        for (Commune neighbor : this.neighbors) {
            names.add(neighbor.getCommuneName());
        }
        return names;
    }

    /**
     * Checks if another commune is in the same department.
     * @param otherCommune the other commune to check
     * @return true if the other commune is in the same department, false otherwise
     */
    public boolean sameDepartment(Commune otherCommune) {
        if (otherCommune == null) {
            throw new IllegalArgumentException("Error : sameDepartment : the other town is null.");
        }
        return this.communeDepartment.equals(otherCommune.getCommuneDepartment());
    }

    /**
 * Checks equality between two communes.
 * @param other the other commune
 * @return true if the communes are equal, false otherwise
 * @throws IllegalArgumentException if the other commune is null
 */
public boolean equals(Commune other) {
    if (other == null) {
        throw new IllegalArgumentException("Error : equals : compared commune cannot be null.");
    }
    return this.communeId == other.communeId
            && this.communeName.equals(other.communeName)
            && this.communeDepartment.equals(other.communeDepartment)
            && this.neighbors.equals(other.neighbors);
}

    /**
     * Returns a string representation of the commune.
     * @return a string representation of the commune
     */
    public String toString() {
        String result = "Town : \n" +
                "\tid : " + this.communeId + "\n" +
                "\tName : " + this.communeName + "\n" +
                "\t" + this.communeDepartment + "\n" +
                "\t #### Neighbors ####\n";
        for (Commune c : this.neighbors) {
            result += "\tTown Name : " + c.getCommuneName() + "\n";
        }
        return result;
    }
}