package modele.data;

/**
 * The Annee class represents a year with an associated inflation rate.
 * It allows manipulation and calculation of inflation rates for different years.
 * @author ZIEGELMEYER Eliot, VION Iann, BELOUAHRANI Ilias
 * @version 1.0
 */
public class Annee {

    /**
     * The year.
     */
    private final int year;

    /**
     * The inflation rate for the year.
     */
    private float inflationRate;

    /**
     * Constructor to create an instance of the Annee class.
     * @param year the year (must be non-negative)
     * @param inflationRate the inflation rate for the year
     * @throws IllegalArgumentException if the year is negative
     */
    public Annee(int year, float inflationRate) {
        if (year < 0) {
            throw new IllegalArgumentException("Error : Annee : l'annee ne peut pas etre negative.");
        }
        this.year = year;
        this.inflationRate = inflationRate;
    }

    /**
     * Returns the year.
     * @return the year
     */
    public int getYear() {
        return this.year;
    }

    /**
     * Returns the inflation rate.
     * @return the inflation rate
     */
    public float getInflationRate() {
        return this.inflationRate;
    }

    /**
     * Sets the inflation rate.
     * @param rate the inflation rate
     */
    public void setInflationRate(float rate) {
        this.inflationRate = rate;
    }

    /**
     * Calculates the cumulative inflation between this year and another year.
     * @param other the other instance of Annee
     * @return the cumulative inflation
     * @throws IllegalArgumentException if the year of this instance is greater than that of the other instance
     */
    public float calculateCumulativeInflation(Annee other) {
        if (this.year > other.year) {
            throw new IllegalArgumentException("Error : calculateCumulativeInflation : Start year must be before end year.");
        }
        return this.inflationRate + other.inflationRate;
    }

    /**
     * Adjusts the inflation rate by a given percentage.
     * @param percentage the adjustment percentage (cannot be less than -100)
     */
    public void adjustInflationRate(float percentage) {
        this.inflationRate += this.inflationRate * (percentage / 100);
    }

    /**
     * Checks equality between two years.
     * @param other the other year
     * @return true if the years are equal, false otherwise
     * @throws IllegalArgumentException if the other year is null
     */
    public boolean equals(Annee other) {
        if (other == null) {
            throw new IllegalArgumentException("Error : equals : compared year cannot be null.");
        }
        return this.year == other.year
                && Float.compare(this.inflationRate, other.inflationRate) == 0;
    }

    /**
     * Returns a string representation of the Annee instance.
     * @return a string representation of the year and inflation rate
     */
    public String toString() {
        return "Year : " + this.year + "\n" +
               "\tInflation rate : " + this.inflationRate + "\n";
    }
}