package model;

/**
 * Class for instantiating Outsourced Part objects.
 * Class derived from Part class.
 *
 *
 * @author Colin Roberson
 */

public class Outsourced extends Part {
    /**
     * Company Name for part.
     */
    private String companyName;

    /**
     * Constructor for new instances of Outsourced Parts.
     *
     * @param id The ID for the part.
     * @param name The name for the part.
     * @param price The price for the part.
     * @param stock The stock level for the part.
     * @param min The minimum stock level for the part.
     * @param max The maximum stock level for the part.
     * @param companyName The machine id for the part.
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        // super calls the constructor for the parent class - Part
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Set the Part's Company Name.
     *
     * @param companyName The company name to set.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Get the Part's Company Name.
     *
     * @return The Part's Company Name.
     */
    public String getCompanyName() {
        return companyName;
    }
}
