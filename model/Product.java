package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class for instantiating Product objects.
 *
 * @author Colin Roberson
 */
public class Product {
    /**
     * List of Part objects to associated with a Product
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * The ID for the product.
     */
    private int id;

    /**
     * The name of the product.
     */
    private String name;

    /**
     * The price of the product
     */
    private double price;

    /**
     * The current stock level of a Product.
     */
    private int stock;

    /**
     * The minimum stock level of a Product.
     */
    private int min;

    /**
     * The maximum stock level of a Product.
     */
    private int max;

    /**
     * Default constructor for Product objects.
     */
    public Product() {
        this.id = -1;
        this.name = "";
        this.price = -1;
        this.stock = -1;
        this.min = -1;
        this.max = -1;
    }

    /**
     * Constructor for new instances of Product objects.
     *
     * @param id The ID for the product.
     * @param name The name for the product.
     * @param price The price for the product.
     * @param stock The stock level for the product.
     * @param min The minimum stock level for the product.
     * @param max The maximum stock level for the product.
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Set the Product ID
     *
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Set the Product name
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the Product price
     *
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Set the Product stock level
     *
     * @param stock the stock level to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Set the Min stock level
     *
     * @param min the min stock level to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Set the Max stock level
     *
     * @param max the max stock level to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Get Product ID
     *
     * @return Product ID
     */
    public int getId() {
        return id;
    }

    /**
     * Get Product Name
     *
     * @return Product name
     */
    public String getName() {
        return name;
    }

    /**
     * Get Product Price
     *
     * @return Product price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Get Product stock
     *
     * @return Product stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * Get Product Min
     *
     * @return Product min
     */
    public int getMin() {
        return min;
    }

    /**
     * Get Product max
     *
     * @return Product max
     */
    public int getMax() {
        return max;
    }

    /**
     * Associate parts with a Product.
     *
     * @param part the part to associate with a Product.
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * Delete an associated part from the list
     *
     * Method will cycle through associatedParts Observable list and remove any matches.
     *
     * @param selectedAssociatedPart Associated part to remove from list
     * @return True if part was successfully removed, otherwise returns false.
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        for (Part part : getAllAssociatedParts()) {
            if (part == selectedAssociatedPart) {
                return associatedParts.remove(part);
            }
        }
        return false;
    }

    /**
     * Get all parts associated with a Product.
     *
     * @return associatedParts list.
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }


}
