package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ResourceBundle;

/**
 * Controller class that provides logic for the Main Screen of the application.
 *
 *<br><br>
 * RUNTIME ERROR occurred if an object (part or product) was not selected prior to hitting the Modify or Delete button.
 * This issue has been addressed in the following methods via "try-catch" blocks; onActionDeletePart, onActionDeleteProduct,
 * onActionModifyPart, onActionModifyProduct. If nothing is selected prior to hitting the Modify or Delete button, an alert
 * will appear notifying you of the failure to select a product.
 *
 *
 * @author Colin Roberson
 */

public class MainForm implements Initializable {

    /**
     * Static method that will switch scene's when called.
     *
     * @param event Action that will trigger scene change.
     * @param fxmlPath Path to the scene that will be displayed.
     */
    public static void switchStage(ActionEvent event, String fxmlPath) {
        try {
            Parent scene;
            Stage stage;
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(MainForm.class.getResource(fxmlPath));
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * When the Exit button is clicked, exit the application.
     *
     * @param event Exit button action
     */
    @FXML
    void onActionExit(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you would like to exit?", ButtonType.YES, ButtonType.CANCEL);
        alert.showAndWait();

        if (alert.getResult() == ButtonType.YES) {
            System.exit(0);
        }
    }

    /**
     * When the Add button under the Parts Pane, switch to the Add Parts scene.
     *
     * @param event Add part button action
     */
    @FXML
    void onActionAddPart(ActionEvent event) {
        switchStage(event, "/view/AddPartForm.fxml");
    }

    /**
     * When the Add button under the Products Pane, switch to the Add Product scene.
     *
     * @param event Add product button action
     */
    @FXML
    void onActionAddProduct(ActionEvent event) {
        switchStage(event, "/view/AddProductForm.fxml");
    }

    /**
     * Delete the selected part. If no part is selected, throw an exception.
     *
     * @param event Delete part button action
     */
    @FXML
    void onActionDeletePart(ActionEvent event) {
        try {
            if (PartsTable.getSelectionModel().isEmpty()) {
                throw new RuntimeException();
            }
            Part part = PartsTable.getSelectionModel().getSelectedItem();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + part.getName() + " ?", ButtonType.YES, ButtonType.NO);
            alert.showAndWait();

            if(alert.getResult() == ButtonType.YES) {
                Inventory.deletePart(part);
            }
        } catch (RuntimeException e) {
            Alert alert = new Alert(Alert.AlertType.NONE, "No part selected. Please select a part and then delete.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * Delete the selected product. If no product is selected, throw an exception.
     *
     * @param event Delete product button action
     */
    @FXML
    void onActionDeleteProduct(ActionEvent event) {
        try {
            if (ProductTable.getSelectionModel().isEmpty()) {
                throw new RuntimeException();
            }

            Product product = ProductTable.getSelectionModel().getSelectedItem();

            if (!(product.getAllAssociatedParts().isEmpty())) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Cannot delete Product with associated Parts.");
                alert.showAndWait();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + product.getName() + " ?", ButtonType.YES, ButtonType.NO);
                alert.showAndWait();

                if(alert.getResult() == ButtonType.YES) {
                    Inventory.deleteProduct(product);
                }
            }
        } catch (RuntimeException e) {
            Alert alert = new Alert(Alert.AlertType.NONE, "No product selected. Please select a product and then delete.", ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * Take the selected Part, it's index, and then pass it to the modify part form.
     * If no part is selected, throw an exception.
     *
     * @param event Modify part button action
     */
    @FXML
    void onActionModifyPart(ActionEvent event) {
        try {
            if (PartsTable.getSelectionModel().isEmpty()) {
                throw new RuntimeException();
            }

            Part tempPart = PartsTable.getSelectionModel().getSelectedItem();
            int index = Inventory.getAllParts().indexOf(tempPart);

            if (tempPart instanceof Outsourced) {
                ModifyPartForm.passPart((Outsourced) tempPart, index);
            }

            else if (tempPart instanceof InHouse) {
                ModifyPartForm.passPart((InHouse) tempPart, index);
            }

            switchStage(event, "/view/ModifyPartForm.fxml");

        } catch (RuntimeException e) {
            Alert alert = new Alert(Alert.AlertType.NONE, "No part selected. Please select a part to modify", ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * Take the selected Product, it's index, and then pass it to the modify product form.
     * If no product is selected, throw an exception.
     *
     * @param event Modify product button action
     */
    @FXML
    void onActionModifyProduct(ActionEvent event) {
        try {
            if (ProductTable.getSelectionModel().isEmpty()) {
                throw new RuntimeException();
            }
            Product tempProduct = ProductTable.getSelectionModel().getSelectedItem();
            int index = Inventory.getAllProducts().indexOf(tempProduct);

            ModifyProductForm.passProduct(tempProduct, index);

            switchStage(event, "/view/ModifyProductForm.fxml");

        } catch (RuntimeException e) {
            Alert alert = new Alert(Alert.AlertType.NONE, "No product selected. Please select a product to modify", ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * Search for Parts by Name (partial matches are acceptable) or by ID (exact match required).
     * Method first checks if the input is an integer - if so, searches for an exact matching ID.
     * If the integer check fails, searches for a name containing the provided input.
     *
     *
     * @param event Search box action - must press "Enter" or "Return" to execute.
     */
    @FXML
    void searchPart(ActionEvent event) {
        try {
            // First try and cast the search input as an Integer - this will throw exception if a string has been input
            // if this succeeds, lookup the part (must be exact match)
            Part tempPart = Inventory.lookupPart(Integer.parseInt(searchPartTextField.getText()));
            if (tempPart != null) {
                // take the found product and add to the observable list
                Inventory.addFilteredParts(tempPart);

                // set the filtered observable list as main display
                PartsTable.setItems(Inventory.getFilteredParts());
                // select the sole part returned
                PartsTable.getSelectionModel().select(tempPart);
            }
            // if the filter observable list is empty, throw up a dialog box because there were no matches.
            if (Inventory.getFilteredParts().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.NONE, "Part ID " + searchPartTextField.getText() + " not found", ButtonType.OK);
                alert.showAndWait();
            }
        }
        catch (Exception exception) {
            // If a string is searched, it will get passed here where a lookup is performed.
            // exact match not necessary, a partial match will suffice
            // once the entire list of parts has been filtered, set this new list as main display
            PartsTable.setItems(Inventory.lookupPart(searchPartTextField.getText()));

            // if this filtered list is empty, throw up a dialog box because there were no matches.
            if (Inventory.getFilteredParts().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.NONE, "Part Name " + searchPartTextField.getText() + " not found", ButtonType.OK);
                alert.showAndWait();
            }
        }
    }

    /**
     * Search for Products by Name (partial matches are acceptable) or by ID (exact match required).
     * Method first checks if the input is an integer - if so, searches for an exact matching ID.
     * If the integer check fails, searches for a name containing the provided input.
     *
     *
     * @param event Search box action - must press "Enter" or "Return" to execute.
     */
    @FXML
    void searchProduct(ActionEvent event) {
        try {
            // First try and cast the search input as an Integer - this will throw exception if a string has been input
            // if this succeeds, lookup the product (must be exact match)
            Product tempProd = Inventory.lookupProduct(Integer.parseInt(searchProductTextField.getText()));
            if (tempProd != null) {
                // take the found product and add to the observable list
                Inventory.addFilteredProducts(tempProd);

                // set the filtered observable list as main display
                ProductTable.setItems(Inventory.getFilteredProducts());
                // select the sole product returned
                ProductTable.getSelectionModel().select(tempProd);
            }

            // if the filter observable list is empty, throw up a dialog box because there were no matches.
            if (Inventory.getFilteredProducts().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.NONE, "Product ID " + searchProductTextField.getText() + " not found", ButtonType.OK);
                alert.showAndWait();
            }
        }
        catch (Exception exception) {
            // If a string is searched, it will get passed here where a lookup is performed.
            // exact match not necessary, a partial match will suffice
            // once the entire list of products has been filtered, set this new list as main display
            ProductTable.setItems(Inventory.lookupProduct(searchProductTextField.getText()));

            // if this filtered list is empty, throw up a dialog box because there were no matches.
            if (Inventory.getFilteredProducts().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.NONE, "Product name " + searchProductTextField.getText() + " not found", ButtonType.OK);
                alert.showAndWait();
            }
        }
    }

    /**
     * Element of the MainForm GUI
     */
    @FXML
    private TableColumn<Part, Integer> PartIdCol;

    /**
     * Element of the MainForm GUI
     */
    @FXML
    private TableColumn<Part, Integer> PartInvCol;

    /**
     * Element of the MainForm GUI
     */
    @FXML
    private TableColumn<Part, String> PartNameCol;

    /**
     * Element of the MainForm GUI
     */
    @FXML
    private TableColumn<Part, Double> PartPriceCol;

    /**
     * Element of the MainForm GUI
     */
    @FXML
    private TableView<Part> PartsTable;

    /**
     * Element of the MainForm GUI
     */
    @FXML
    private TableColumn<Product, Integer> ProductIdCol;

    /**
     * Element of the MainForm GUI
     */
    @FXML
    private TableColumn<Product, Integer> ProductInvCol;

    /**
     * Element of the MainForm GUI
     */
    @FXML
    private TableColumn<Product, String> ProductNameCol;

    /**
     * Element of the MainForm GUI
     */
    @FXML
    private TableColumn<Product, Double> ProductPriceCol;

    /**
     * Element of the MainForm GUI
     */
    @FXML
    private TableView<Product> ProductTable;

    /**
     * Element of the MainForm GUI
     */
    @FXML
    private TextField searchPartTextField;

    /**
     * Element of the MainForm GUI
     */
    @FXML
    private TextField searchProductTextField;

    /**
     * Initializes controller and populates TableView's
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // instantiate a currencyFormat object for formatting the price column of the below TableViews
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();

        // Populate Parts table and format the price column as $X.XX
        PartsTable.setItems(Inventory.getAllParts());
        PartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        PartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        PartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        PartPriceCol.setCellFactory(tc -> {
                    return new TableCell<>() {

                        @Override
                        protected void updateItem(Double price, boolean empty) {
                            super.updateItem(price, empty);
                            if (empty) {
                                setText(null);
                            } else {
                                setText(currencyFormat.format(price));
                            }
                        }
                    };
                }
        );

        // Populate Products table and format the price column as $X.XX
        ProductTable.setItems(Inventory.getAllProducts());
        ProductIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        ProductInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ProductNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        ProductPriceCol.setCellFactory(tc -> {
                    return new TableCell<>() {

                        @Override
                        protected void updateItem(Double price, boolean empty) {
                            super.updateItem(price, empty);
                            if (empty) {
                                setText(null);
                            } else {
                                setText(currencyFormat.format(price));
                            }
                        }
                    };
                }
        );
    }
}
