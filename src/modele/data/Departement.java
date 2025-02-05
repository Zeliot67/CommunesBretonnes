package modele.data;

/**
 * Class that models objects of type Department, defined by an id, name, and cultural investment in 2019.
 * It uses an internal enumeration for the representation of departments.
 * @author ZIEGELMEYER Eliot, VION Iann, BELOUAHRANI Ilias
 * @version 1.0
 */
public class Departement {

    /**
     * Enumeration DepartmentEnum that represents departments with an id and a name.
     */
    public enum DepartementEnum {
        COTES_D_ARMOR(22, "COTES-D-ARMOR"),
        FINISTERE(29, "FINISTERE"),
        ILLE_ET_VILAINE(35, "ILLE-ET-VILAINE"),
        MORBIHAN(56, "MORBIHAN");

        /**
         * The department id.
         */
        private final int id;

        /**
         * The department name.
         */
        private final String name;

        /**
         * Constructor for the enumeration DepartmentEnum.
         * @param id the department's identifier.
         * @param name the department's name.
         */
        DepartementEnum(int id, String name) {
            this.id = id;
            this.name = name;
        }

        /**
         * Returns the department's identifier.
         * @return the department's identifier.
         */
        public int getId() {
            return id;
        }

        /**
         * Returns the department's name.
         * @return the department's name.
         */
        public String getName() {
            return name;
        }

        /**
         * Returns the department corresponding to the given identifier.
         * @param id the department's identifier.
         * @return the corresponding department.
         * @throws IllegalArgumentException if the identifier is invalid.
         */
        public static DepartementEnum fromId(int id) {
            DepartementEnum departement = null;
            for (DepartementEnum dep : values()) {
                if (dep.getId() == id) {
                    departement = dep;
                }
            }
            if (departement == null) {
                throw new IllegalArgumentException("Error : fromID : Id du departement invalide : " + id);
            }
            return departement;
        }
    }

    /**
     * The department id and name.
     */
    private final DepartementEnum department;

    /**
     * The department cultural investment.
     */
    private float culturalInvestment2019;

    /**
     * Constructor for the Department class.
     * @param idDep the department's identifier
     * @param culturalInvestment2019 the cultural investment for the year 2019
     * @throws IllegalArgumentException if the cultural investment is negative
     */
    public Departement(int idDep, float culturalInvestment2019) {
        this.department = DepartementEnum.fromId(idDep);
        if (culturalInvestment2019 < 0) {
            throw new IllegalArgumentException("Error : Department : l'investissement culturel ne peut pas etre negatif.");
        }
        this.culturalInvestment2019 = culturalInvestment2019;
    }

    /**
     * Returns the department's identifier.
     * @return the department's identifier
     */
    public int getIdDep() {
        return this.department.getId();
    }

    /**
     * Returns the department's name.
     * @return the department's name
     */
    public String getNomDep() {
        return this.department.getName();
    }

    /**
     * Returns the cultural investment for the year 2019.
     * @return the cultural investment for the year 2019
     */
    public float getInvestissementCulturel2019() {
        return this.culturalInvestment2019;
    }

    /**
     * Sets the cultural investment for the year 2019.
     * @param culturalInvestment2019 the cultural investment for the year 2019
     * @throws IllegalArgumentException if the cultural investment is negative
     */
    public void setInvestissementCulturel2019(float culturalInvestment2019) {
        if (culturalInvestment2019 < 0) {
            throw new IllegalArgumentException("Error : setCulturalInvestment2019 : l'investissement culturel ne peut pas etre negatif.");
        }
        this.culturalInvestment2019 = culturalInvestment2019;
    }

    /**
     * Modifies the cultural investment based on a given percentage.
     * @param modificationPercentage the modification percentage
     * @throws IllegalArgumentException if the modification results in a negative investment
     */
    public void modifyCulturalInvestment(float modificationPercentage) {
        float modif = modificationPercentage / 100;
        float newInvest = culturalInvestment2019 * (1 + modif);
        if (newInvest < 0) {
            throw new IllegalArgumentException("Error : modifyCulturalInvestment : the modification results in a negative investment.");
        }
        this.culturalInvestment2019 = newInvest;
    }

    /**
     * Compares the cultural investment of this department with another department.
     * @param other the other department
     * @return a negative number, zero, or a positive number if the cultural investment of this department is respectively less than, equal to, or greater than that of the other department
     * @throws IllegalArgumentException if the other department is null
     */
    public int compareCulturalInvestment(Departement other) {
        if (other == null) {
            throw new IllegalArgumentException("Error : compareCulturalInvestment : compared department cannot be null.");
        }
        return Float.compare(this.culturalInvestment2019, other.getInvestissementCulturel2019());
    }

    /**
     * Increases the cultural investment by a given amount.
     * @param amount the increase amount
     * @throws IllegalArgumentException if the amount is negative
     */
    public void increaseCulturalInvestment(float amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Error : increaseCulturalInvestment : the increase amount cannot be negative.");
        }
        this.culturalInvestment2019 += amount;
    }

    /**
     * Reduces the cultural investment by a given amount.
     * @param amount the reduction amount
     * @throws IllegalArgumentException if the amount is negative or if the reduction results in a negative investment
     */
    public void reduceCulturalInvestment(float amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Error : reduceCulturalInvestment : the reduction amount cannot be negative.");
        }
        float newInvest = this.culturalInvestment2019 - amount;
        if (newInvest < 0) {
            throw new IllegalArgumentException("Error : reduceCulturalInvestment : the reduction results in a negative investment.");
        }
        this.culturalInvestment2019 = newInvest;
    }

    /**
     * Calculates the difference in cultural investment between this department and another department.
     * @param other the other department
     * @return the difference in cultural investment
     * @throws IllegalArgumentException if the other department is null
     */
    public float differenceCulturalInvestment(Departement other) {
        if (other == null) {
            throw new IllegalArgumentException("Error : differenceCulturalInvestment : compared department cannot be null.");
        }
        return this.culturalInvestment2019 - other.getInvestissementCulturel2019();
    }
    
    /**
     * Checks equality between two departments.
     * @param other the other department
     * @return true if the departments are equal, false otherwise
     * @throws IllegalArgumentException if the other department is null
     */
    public boolean equals(Departement other) {
        if (other == null) {
            throw new IllegalArgumentException("Error: equals: compared department cannot be null.");
        }
        return this.department.equals(other.department)
                && this.compareCulturalInvestment(other) == 0;
    }

    /**
     * Returns a string representation of this department.
     * @return a string representation of this department
     */
    public String toString() {
        return "Department : \n" +
                "\tID : " + department.getId() + "\n" +
                "\tName : " + department.getName() + '\n' +
                "\tCulturalInvestment2019 : " + culturalInvestment2019 + "\n";
    }
}