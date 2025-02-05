package modele.data;

/**
 * The Gare class represents a train station with information such as the station code, station name, whether it is used for freight, for passengers, and the town where it is located.
 * @author ZIEGELMEYER Eliot, VION Iann, BELOUAHRANI Ilias
 * @version 1.0
 */
public class Gare {
    
    /**
     * The train station id.
     */
    private final int stationCode;

    /**
     * The train station name.
     */
    private String stationName;

    /**
     * If it is for freight.
     */
    private boolean isFreight;

    /**
     * If it is for passengers.
     */
    private boolean isPassenger;

    /**
     * The train station's town.
     */
    private final Commune town;

    /**
     * Constructs a new Gare instance.
     * @param stationCode the station code, must be non-negative
     * @param stationName the station name, must not be null
     * @param isFreight whether the station is used for freight
     * @param isPassenger whether the station is used for passengers
     * @param town the commune where the station is located, must not be null
     * @throws IllegalArgumentException if stationCode is negative, stationName is null, or town is null
     */
    public Gare(int stationCode, String stationName, boolean isFreight, boolean isPassenger, Commune town) {
        if (stationCode < 0) {
            throw new IllegalArgumentException("Error : Gare : l'id ne peut pas etre negatif.");
        }
        if (stationName == null) {
            throw new IllegalArgumentException("Error : Gare : the train station's name cannot be null.");
        }
        if (town == null) {
            throw new IllegalArgumentException("Error : Gare : the town cannot be null.");
        }
        this.stationCode = stationCode;
        this.stationName = stationName;
        this.isFreight = isFreight;
        this.isPassenger = isPassenger;
        this.town = town;
    }

    /**
     * Gets the station code.
     * @return the station code
     */
    public int getStationCode() {
        return this.stationCode;
    }

    /**
     * Gets the station name.
     * @return the station name
     */
    public String getStationName() {
        return this.stationName;
    }

    /**
     * Checks if the station is used for freight.
     * @return true if the station is used for freight, false otherwise
     */
    public boolean isFreight() {
        return this.isFreight;
    }

    /**
     * Checks if the station is used for passengers.
     * @return true if the station is used for passengers, false otherwise
     */
    public boolean isPassenger() {
        return this.isPassenger;
    }

    /**
     * Gets the commune where the station is located.
     * @return a clone of the commune
     */
    public Commune getTown() {
        return new Commune(this.town.getCommuneId(), this.town.getCommuneName(), this.town.getCommuneDepartment(), this.town.getNeighbors());
    }

    /**
     * Sets the station name.
     * @param stationName the new station name, must not be null
     * @throws IllegalArgumentException if stationName is null
     */
    public void setStationName(String stationName) {
        if (stationName == null) {
            throw new IllegalArgumentException("Error : setStationName : the train station's name cannot be null.");
        }
        this.stationName = stationName;
    }
    
    /**
     * Sets whether the station is used for freight.
     * @param isFreight true if the station is used for freight, false otherwise
     */
    public void setFreight(boolean isFreight) {
        this.isFreight = isFreight;
    }

    /**
     * Sets whether the station is used for passengers.
     * @param isPassenger true if the station is used for passengers, false otherwise
     */
    public void setPassenger(boolean isPassenger) {
        this.isPassenger = isPassenger;
    }

    /**
     * Checks if the station is in a given commune.
     * @param townName the name of the commune
     * @return true if the station is in the given commune, false otherwise
     */
    public boolean isInTown(String townName) {
        if (townName == null) {
            throw new IllegalArgumentException("Error : isinTown : the given town is null.");
        }
        return this.town.getCommuneName().equalsIgnoreCase(townName);
    }

    /**
     * Checks if the station is operational (used for either freight or passengers).
     * @return true if the station is operational, false otherwise
     */
    public boolean isOperational() {
        return this.isFreight || this.isPassenger;
    }

    /**
     * Checks equality between two train stations.
     * @param other the other train station
     * @return true if the train stations are equal, false otherwise
     * @throws IllegalArgumentException if the other train station is null
     */
    public boolean equals(Gare other) {
        if (other == null) {
            throw new IllegalArgumentException("Error: equals: compared train station cannot be null.");
        }
        return this.stationCode == other.stationCode
                 && this.stationName.equals(other.stationName)
                 && this.isFreight == other.isFreight
                 && this.isPassenger == other.isPassenger
                 && this.town.equals(other.town);
    }

    /**
     * Returns a string representation of the Gare instance.
     * @return a string representation of the Gare instance
     */
    public String toString() {
        return "Train Station : \n"+
                "\tId : " + this.stationCode + "\n" +
                "\tName : " + this.stationName + "\n" +
                "\tFreight : " + this.isFreight + "\n" +
                "\tTravelers : " + this.isPassenger + "\n" +
                "\tTown : " + this.town.getCommuneName() + "\n";
    }
}