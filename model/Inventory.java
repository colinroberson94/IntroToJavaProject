package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Model for managing the inventory of Part and Product objects.
 * Contains static methods accessible throughout the application.
 *
 *
 * @author Colin Roberson
 */
public class Inventory {
    /**
     * An ObservableList containing all parts in inventory.
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**
     * An ObservableList containing a filtered list of parts.
     *
     * List is populated while searching for parts in the GUI.
     */
    private static ObservableList<Part> filteredParts = FXCollections.observableArrayList();

    /**
     * An ObservableList containing all products in inventory
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * An ObservableList containing a filtered list of parts.
     *
     * List is populated while searching for parts in the GUI.
     */
    private static ObservableList<Product> filteredProducts = FXCollections.observableArrayList();

    /**
     * An ID for a part. Allows for unique part ID's.
     */
    private static int partNum = 10;

    /**
     * An ID for a product. Allows for unique product ID's.
     */
    private static int productNum = 10;

    /**
     * Increments the partNum field and returns a locally unique Part ID.
     *
     * @return a unique part ID.
     */
    public static int generatePartNum() {
        partNum++;
        return partNum;
    }

    /**
     * Increments the productNum field and returns a locally unique Product ID.
     *
     * @return a unique product ID.
     */
    public static int generateProductNum() {
        productNum++;
        return productNum;
    }

    /**
     * Adds a new Part to inventory.
     *
     * @param newPart The part object to add.
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Adds a new Product to inventory.
     *
     * @param newProduct The product object to add.
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Searches the list for a matching Part ID.
     *
     * @param partId The Part ID to search for.
     * @return The part object if found, null if not found.
     */
    public static Part lookupPart(int partId) {
        Inventory.filteredParts.clear();
        for (Part part : Inventory.getAllParts()) {
            if (partId == part.getId()) {
                return part;
            }
        }
        return null;
    }

    /**
     * Searches the list for a matching Product ID.
     *
     * @param productId The Product ID to search for.
     * @return The product object if found, null if not found.
     */
    public static Product lookupProduct(int productId) {
        Inventory.filteredProducts.clear();
        for (Product product : Inventory.getAllProducts()) {
            if (productId == product.getId()) {
                return product;
            }
        }
        return null;
    }

    /**
     * Searches the list for a matching Part Name. Exact match or partial match will work.
     *
     * @param partName The Part Name (or partial name) to search for.
     * @return The part object(s) if found, null if not found.
     */
    public static ObservableList<Part> lookupPart(String partName) {
        Inventory.filteredParts.clear();
        for (Part part : Inventory.getAllParts()) {
            if (part.getName().contains(partName)) {
                Inventory.filteredParts.add(part);
            }
        }
        return Inventory.filteredParts;
    }

    /**
     * Searches the list of a matching Product Name. Exact match or partial match will work.
     *
     * @param productName The Product Name (or partial name) to search for.
     * @return The product object(s) if found, null if not found.
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        Inventory.filteredProducts.clear();
        for (Product product : Inventory.getAllProducts()) {
            if (product.getName().contains(productName)) {
                Inventory.filteredProducts.add(product);
            }
        }
        return Inventory.filteredProducts;
    }

    /**
     * Updates a part object found in the list.
     *
     * @param index index of part object that is to be replaced.
     * @param selectedPart new part object that will replace existing object.
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * Updates a product object found in the list.
     *
     * @param index index of product object that is to be replaced.
     * @param newProduct new product object that will replace existing object.
     */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /**
     * Delete Part object from list.
     *
     * @param selectedPart Part object that is to be deleted from allParts list.
     * @return True if part was successfully removed, false if part was not successfully removed.
     */
    public static boolean deletePart(Part selectedPart) {
        for (Part part : Inventory.getAllParts()) {
            if (part == selectedPart) {
                return Inventory.getAllParts().remove(part);
            }
        }
        return false;
    }

    /**
     * Delete Product object from list.
     *
     * @param selectedProduct Product object that is to be deleted from allProducts list.
     * @return True if product was successfully removed, false if product was not successfully removed.
     */
    public static boolean deleteProduct(Product selectedProduct) {
        for (Product product : Inventory.getAllProducts()) {
            if (product == selectedProduct) {
                return Inventory.getAllProducts().remove(product);
            }
        }
        return false;
    }

    /**
     * Get allParts ObservableList
     *
     * @return allParts ObservableList
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Get allProducts ObservableList
     *
     * @return allProducts ObservableList
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     * Get filteredParts ObservableList
     *
     * @return filteredParts ObservableList
     */
    public static ObservableList<Part> getFilteredParts() {
        return filteredParts;
    }

    /**
     * Get filteredProducts ObservableList
     *
     * @return filteredProducts ObservableList
     */
    public static ObservableList<Product> getFilteredProducts() {
        return filteredProducts;
    }

    /**
     * Adds Product objects to a filtered list of Products. Intended to be used for Searching purposes.
     *
     * @param newProd Product object to be added to list.
     */
    public static void addFilteredProducts(Product newProd) {
        filteredProducts.add(newProd);
    }

    /**
     * Adds Part objects to a filtered list of Parts. Intended to be used for Searching Purposes
     *
     * @param newPart Part object to be added to list.
     */
    public static void addFilteredParts(Part newPart) {
        filteredParts.add(newPart);
    }

}
