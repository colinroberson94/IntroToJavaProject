package model;

/**
 * Class for instantiating InHouse Part objects.
 * Class derived from Part class.
 *
 * @author Colin Roberson
 */

public class InHouse extends Part {
    /**
     * Machine ID for part.
     */
    private int machineId;

    /**
     * Constructor for new instances of InHouse Parts.
     *
     * @param id The ID for the part
     * @param name The name for the part
     * @param price The price for the part
     * @param stock The stock level for the part
     * @param min The minimum stock level for the part
     * @param max The maximum stock level for the part
     * @param machineId The machine id for the part
     */
    public InHouse (int id, String name, double price, int stock, int min, int max, int machineId) {
        // super calls the constructor for the parent class - Part
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * Set the part's Machine ID.
     *
     * @param machineId the machine id to set.
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * Return the part's machine id.
     *
     * @return the machine ID.
     */
    public int getMachineId() {
        return machineId;
    }
}
