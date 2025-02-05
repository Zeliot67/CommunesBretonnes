package modele.data;

/**
 * The DonneesAnnuelles class represents annual data for a town, including housing, prices, and cultural expenses.
 * @author ZIEGELMEYER Eliot, VION Iann, BELOUAHRANI Ilias
 * @version 1.0
 */
public class DonneesAnnuelles {

    /**
     * The number of houses.
     */
    private int nbHouses;

    /**
     * The number of apartments.
     */
    private int nbAppart;

    /**
     * The average price.
     */
    private float averagePrice;

    /**
     * The average price per square meter.
     */
    private float averageM2Price;

    /**
     * The average surface area.
     */
    private float averageSurfaceArea;

    /**
     * The total cultural expenses.
     */
    private float totalCulturalExpenses;

    /**
     * The total budget.
     */
    private float totalBudget;

    /**
     * The population.
     */
    private float population;

    /**
     * The year associated with the data.
     */
    private final Annee year;

    /**
     * The commune associated with the data.
     */
    private final Commune commune;

    /**
     * Default constructor.
     * @param year the year (must not be null)
     * @param commune the commune (must not be null)
     * @throws IllegalArgumentException if the year or the commune is null
     */
    public DonneesAnnuelles(Annee year, Commune commune) {
        if (year == null || commune == null) {
            throw new IllegalArgumentException("Error: DonneesAnnuelles: the year and the town cannot be null.");
        }
        this.nbHouses = 0;
        this.nbAppart = 0;
        this.averagePrice = 0.0f;
        this.averageM2Price = 0.0f;
        this.averageSurfaceArea = 0.0f;
        this.totalCulturalExpenses = 0.0f;
        this.totalBudget = 0.0f;
        this.population = 0.0f;
        this.year = year;
        this.commune = commune;
    }

    /**
     * Parameterized constructor with defensive copying and argument validation.
     * @param year the year (must not be null)
     * @param commune the commune (must not be null)
     * @param nbHouses the number of houses (must be non-negative)
     * @param nbAppart the number of apartments (must be non-negative)
     * @param averagePrice the average price (must be non-negative)
     * @param averageM2Price the average price per square meter (must be non-negative)
     * @param averageSurfaceArea the average surface area (must be non-negative)
     * @param totalCulturalExpenses the total cultural expenses (must be non-negative)
     * @param totalBudget the total budget (must be non-negative)
     * @param population the population (must be non-negative)
     * @throws IllegalArgumentException if any value is negative or if the year or the commune is null
     */
    public DonneesAnnuelles(Annee year, Commune commune, int nbHouses, int nbAppart, float averagePrice, float averageM2Price, float averageSurfaceArea, float totalCulturalExpenses, float totalBudget, float population) {
        if (nbHouses < -1 || nbAppart < -1 || averagePrice < -1 || averageM2Price < -1 || averageSurfaceArea < -1 || totalCulturalExpenses < -1 || totalBudget < -1 || population < -1) {
            throw new IllegalArgumentException("Error : DonneesAnnuelles : les valeurs ne peuvent pas etre inferieure a -1");
        }
        if (year == null || commune == null) {
            throw new IllegalArgumentException("Error : DonneesAnnuelles : the year and the town cannot be null.");
        }
        this.nbHouses = nbHouses;
        this.nbAppart = nbAppart;
        this.averagePrice = averagePrice;
        this.averageM2Price = averageM2Price;
        this.averageSurfaceArea = averageSurfaceArea;
        this.totalCulturalExpenses = totalCulturalExpenses;
        this.totalBudget = totalBudget;
        this.population = population;
        this.year = year;
        this.commune = commune;
    }

    /**
     * Returns the number of houses.
     * @return the number of houses
     */
    public int getNbHouses() {
        return this.nbHouses;
    }

    /**
     * Returns the number of apartments.
     * @return the number of apartments
     */
    public int getNbAppart() {
        return this.nbAppart;
    }

    /**
     * Returns the average price.
     * @return the average price
     */
    public float getAveragePrice() {
        return this.averagePrice;
    }

    /**
     * Returns the average price per square meter.
     * @return the average price per square meter
     */
    public float getAverageM2Price() {
        return this.averageM2Price;
    }

    /**
     * Returns the average surface area.
     * @return the average surface area
     */
    public float getAverageSurfaceArea() {
        return this.averageSurfaceArea;
    }

    /**
     * Returns the total cultural expenses.
     * @return the total cultural expenses
     */
    public float getTotalCulturalExpenses() {
        return this.totalCulturalExpenses;
    }

    /**
     * Returns the total budget.
     * @return the total budget
     */
    public float getTotalBudget() {
        return this.totalBudget;
    }

    /**
     * Returns the population.
     * @return the population
     */
    public float getPopulation() {
        return this.population;
    }

    /**
     * Returns the year.
     * @return the year
     */
    public Annee getYear() {
        return new Annee(this.year.getYear(), this.year.getInflationRate());
    }

    /**
     * Returns the town.
     * @return the town
     */
    public Commune getCommune() {
        return new Commune(this.commune.getCommuneId(), this.commune.getCommuneName(), this.commune.getCommuneDepartment(), this.commune.getNeighbors());
    }

    /**
     * Sets the number of houses.
     * @param nbHouses the number of houses (must be non-negative)
     * @throws IllegalArgumentException if the number of houses is negative
     */
    public void setNbHouses(int nbHouses) {
        if (nbHouses < -1) {
            throw new IllegalArgumentException("Error : setnbHouses : le nombre de maisons en vente ne peut pas etre inferieur a -1.");
        }
        this.nbHouses = nbHouses;
    }

    /**
     * Sets the number of apartments.
     * @param nbAppart the number of apartments (must be non-negative)
     * @throws IllegalArgumentException if the number of apartments is negative
     */
    public void setNbAppart(int nbAppart) {
        if (nbAppart < -1) {
            throw new IllegalArgumentException("Error : setNbAppart : le nombre d'appartements ne peut pas etre inferieur a -1.");
        }
        this.nbAppart = nbAppart;
    }

    /**
     * Sets the average price.
     * @param averagePrice the average price (must be non-negative)
     * @throws IllegalArgumentException if the average price is negative
     */
    public void setAveragePrice(float averagePrice) {
        if (averagePrice < -1) {
            throw new IllegalArgumentException("Error : setAveragePrice : le prix moyen ne peut pas etre inferieur a -1.");
        }
        this.averagePrice = averagePrice;
    }

    /**
     * Sets the average price per square meter.
     * @param averageM2Price the average price per square meter (must be non-negative)
     * @throws IllegalArgumentException if the average price per square meter is negative
     */
    public void setAverageM2Price(float averageM2Price) {
        if (averageM2Price < -1) {
            throw new IllegalArgumentException("Error : setaverageM2Price : le prix au m2 ne peut pas etre inferieur a -1.");
        }
        this.averageM2Price = averageM2Price;
    }

    /**
     * Sets the average surface area.
     * @param averageSurfaceArea the average surface area (must be non-negative)
     * @throws IllegalArgumentException if the average surface area is negative
     */
    public void setAverageSurfaceArea(float averageSurfaceArea) {
        if (averageSurfaceArea < -1) {
            throw new IllegalArgumentException("Error : setAverageSurfaceArea : la surface ne peut pas etre inferieure a -1.");
        }
        this.averageSurfaceArea = averageSurfaceArea;
    }

    /**
     * Sets the total cultural expenses.
     * @param totalCulturalExpenses the total cultural expenses (must be non-negative)
     * @throws IllegalArgumentException if the total cultural expenses are negative
     */
    public void setTotalCulturalExpenses(float totalCulturalExpenses) {
        if (totalCulturalExpenses < -1) {
            throw new IllegalArgumentException("Error : setTotalCulturalExpenses : les depenses culturelles ne peuvent pas etre inferieure a -1.");
        }
        this.totalCulturalExpenses = totalCulturalExpenses;
    }

    /**
     * Sets the total budget.
     * @param totalBudget the total budget (must be non-negative)
     * @throws IllegalArgumentException if the total budget is negative
     */
    public void setTotalBudget(float totalBudget) {
        if (totalBudget < -1) {
            throw new IllegalArgumentException("Error : setTotalBudget : le budget ne peut pas etre inferieur a -1.");
        }
        this.totalBudget = totalBudget;
    }

    /**
     * Sets the population.
     * @param population the population (must be non-negative)
     * @throws IllegalArgumentException if the population is negative
     */
    public void setPopulation(float population) {
        if (population < -1) {
            throw new IllegalArgumentException("Error : setPopulation : la population ne peut pas etre inferieur a -1.");
        }
        this.population = population;
    }

    /**
     * Calculates the cultural expenses per inhabitant.
     * @return the cultural expenses per inhabitant, or 0 if the population is zero
     */
    public float calculateCulturalExpensesPerInhabitant() {
        if (population == 0) {
            return 0.0f;
        } else {
            return this.totalCulturalExpenses / this.population;
        }
    }

    /**
     * Calculates the total price of houses and apartments.
     * @return the total price of houses and apartments
     */
    public float calculateTotalPrice() {
        return (this.nbHouses + this.nbAppart) * this.averagePrice;
    }

    /**
     * Calculates the average price per housing (houses and apartments).
     * @return the average price per housing
     */
    public float calculateAveragePricePerHousing() {
        int totalHousing = this.nbHouses + this.nbAppart;
        if (totalHousing == 0) {
            return 0.0f;
        } else {
            return this.averagePrice / totalHousing;
        }
    }

    /**
     * Calculates the ratio of cultural expenses to the total budget.
     * @return the ratio of cultural expenses to the total budget
     */
    public float calculateCulturalExpensesRatio() {
        if (totalBudget == 0) {
            return 0.0f;
        } else {
            return this.totalCulturalExpenses / this.totalBudget;
        }
    }

    /**
     * Updates multiple fields at once.
     * @param nbHouses the number of houses (must be non-negative)
     * @param nbAppart the number of apartments (must be non-negative)
     * @param averagePrice the average price (must be non-negative)
     * @param averageM2Price the average price per square meter (must be non-negative)
     * @param averageSurfaceArea the average surface area (must be non-negative)
     * @param totalCulturalExpenses the total cultural expenses (must be non-negative)
     * @param totalBudget the total budget (must be non-negative)
     * @param population the population (must be non-negative)
     * @throws IllegalArgumentException if any value is negative
     */
    public void updateData(int nbHouses, int nbAppart, float averagePrice, float averageM2Price, float averageSurfaceArea, float totalCulturalExpenses, float totalBudget, float population) {
        setNbHouses(nbHouses);
        setNbAppart(nbAppart);
        setAveragePrice(averagePrice);
        setAverageM2Price(averageM2Price);
        setAverageSurfaceArea(averageSurfaceArea);
        setTotalCulturalExpenses(totalCulturalExpenses);
        setTotalBudget(totalBudget);
        setPopulation(population);
    }

    /**
     * Checks equality between two annual data instances.
     * @param other the other annual data instance
     * @return true if the annual data instances are equal, false otherwise
     * @throws IllegalArgumentException if the other annual data instance is null
     */
    public boolean equals(DonneesAnnuelles other) {
        if (other == null) {
            throw new IllegalArgumentException("Error: equals: compared annual data cannot be null.");
        }
        return this.nbHouses == other.nbHouses
                && this.nbAppart == other.nbAppart
                && Float.compare(this.averagePrice, other.averagePrice) == 0
                && Float.compare(this.averageM2Price, other.averageM2Price) == 0
                && Float.compare(this.averageSurfaceArea, other.averageSurfaceArea) == 0
                && Float.compare(this.totalCulturalExpenses, other.totalCulturalExpenses) == 0
                && Float.compare(this.totalBudget, other.totalBudget) == 0
                && Float.compare(this.population, other.population) == 0
                && this.year.equals(other.year)
                && this.commune.equals(other.commune);
    }

    /**
     * Returns a string representation of the annual data.
     * @return a string representation of the annual data
     */
    public String toString() {
        return "\nAnnual Data (" + this.year.getYear() + ", " + this.commune.getCommuneName() + ") {" +
               "nbHouses=" + nbHouses +
               ", nbAppart=" + nbAppart +
               ", averagePrice=" + averagePrice +
               ", averageM2Price=" + averageM2Price +
               ", averageSurfaceArea=" + averageSurfaceArea +
               ", totalCulturalExpenses=" + totalCulturalExpenses +
               ", totalBudget=" + totalBudget +
               ", population=" + population +
               "}\n";
    }
}