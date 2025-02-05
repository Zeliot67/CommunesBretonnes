package modele.data;

/**
 * The Voisinage class represents a neighborhood relationship between two communes.
 * @author Eliot ZIEGELMEYER, Iann VION, Ilias BELOUAHRANI
 * @version 1.0
 */
public class Voisinage {

    /**
     * The main commune.
     */
    private Commune commune;

    /**
     * The neighboring commune.
     */
    private Commune communeVoisine;

    /**
     * Constructor for the Voisinage class.
     * @param commune the main commune, must not be null
     * @param communeVoisine the neighboring commune, must not be null
     * @throws IllegalArgumentException if either commune is null
     */
    public Voisinage(Commune commune, Commune communeVoisine) {
        if (commune == null || communeVoisine == null) {
            throw new IllegalArgumentException("Error : Voisinage : communes cannot be null.");
        }
        this.commune = commune;
        this.communeVoisine = communeVoisine;
    }

    /**
     * Gets the main commune.
     * @return the main commune
     */
    public Commune getCommune() {
        return commune;
    }

    /**
     * Sets the main commune.
     * @param commune the new main commune, must not be null
     * @throws IllegalArgumentException if the commune is null
     */
    public void setCommune(Commune commune) {
        if (commune == null) {
            throw new IllegalArgumentException("Error : setCommune : commune cannot be null.");
        }
        this.commune = commune;
    }

    /**
     * Gets the neighboring commune.
     * @return the neighboring commune
     */
    public Commune getCommuneVoisine() {
        return communeVoisine;
    }

    /**
     * Sets the neighboring commune.
     * @param communeVoisine the new neighboring commune, must not be null
     * @throws IllegalArgumentException if the neighboring commune is null
     */
    public void setCommuneVoisine(Commune communeVoisine) {
        if (communeVoisine == null) {
            throw new IllegalArgumentException("Error : setCommuneVoisine : neighboring commune cannot be null.");
        }
        this.communeVoisine = communeVoisine;
    }
}