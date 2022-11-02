package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Inventory;
import model.Part;
import model.Product;

import java.net.URL;
import java.text.NumberFormat;
import java.util.ResourceBundle;

/**
 * Controller class that provides logic for the Add Product Form of the application.
 *
 *<br><br>
 * RUNTIME ERROR occurred when implementing onActionRemovePart. If no part was selected prior to hitting the Remove
 * Associated Part button an exception was thrown. This was corrected by implementing "try-catch" blocks - if the
 * selectionModel is empty when the Remove Associated Part button is selected an exception is thrown and a corresponding
 * dialog window is shown.
 *
 *
 * @author Colin Roberson
 */

public class AddProductForm implements Initializable {
    /**
     * instantiates a new product with the default constructor
     */
    Product product = new Product();

    /**
     * Element of the AddProductForm GUI
     */
    @FXML
    private TableView<Part> AssociatedPartsTable;

    /**
     * Element of the AddProductForm GUI
     */
    @FXML
    private TableColumn<Part, Integer> AssocPartIdCol;

    /**
     * Element of the AddProductForm GUI
     */
    @FXML
    private TableColumn<Part, Integer> AssocPartInvCol;

    /**
     * Element of the AddProductForm GUI
     */
    @FXML
    private TableColumn<Part, String> AssocPartNameCol;

    /**
     * Element of the AddProductForm GUI
     */
    @FXML
    private TableColumn<Part, Double> AssocPartPriceCol;

    /**
     * Element of the AddProductForm GUI
     */
    @FXML
    private TableView<Part> PartsTable;

    /**
     * Element of the AddProductForm GUI
     */
    @FXML
    private TableColumn<Part, Integer> PartIdCol;

    /**
     * Element of the AddProductForm GUI
     */
    @FXML
    private TableColumn<Part, Integer> PartInvCol;

    /**
     * Element of the AddProductForm GUI
     */
    @FXML
    private TableColumn<Part, String> PartNameCol;

    /**
     * Element of the AddProductForm GUI
     */
    @FXML
    private TableColumn<Part, Double> PartPriceCol;

    /**
     * Element of the AddProductForm GUI
     */
    @FXML
    private Label AddIdLabel;

    /**
     * Element of the AddProductForm GUI
     */
    @FXML
    private TextField AddIdTextBox;

    /**
     * Element of the AddProductForm GUI
     */
    @FXML
    private Label AddInvLabel;

    /**
     * Element of the AddProductForm GUI
     */
    @FXML
    private TextField AddInvTextBox;

    /**
     * Element of the AddProductForm GUI
     */
    @FXML
    private Label AddMaxLabel;

    /**
     * Element of the AddProductForm GUI
     */
    @FXML
    private TextField AddMaxTextBox;

    /**
     * Element of the AddProductForm GUI
     */
    @FXML
    private Label AddMinLabel;

    /**
     * Element of the AddProductForm GUI
     */
    @FXML
    private TextField AddMinTextBox;

    /**
     * Element of the AddProductForm GUI
     */
    @FXML
    private Label AddNameLabel;

    /**
     * Element of the AddProductForm GUI
     */
    @FXML
    private TextField AddNameTextBox;

    /**
     * Element of the AddProductForm GUI
     */
    @FXML
    private Label AddPriceLabel;

    /**
     * Element of the AddProductForm GUI
     */
    @FXML
    private TextField AddPriceTextBox;

    /**
     * Element of the AddProductForm GUI
     */
    @FXML
    private TextField SearchPart;

    /**
     * If selection is not empty, add the selected part as an AssociatedPart to the current Product.
     *
     * @param event Add part button action
     */
    @FXML
    void onActionAddPart(ActionEvent event) {
        if(!(PartsTable.getSelectionModel().isEmpty())) {
            product.addAssociatedPart(PartsTable.getSelectionModel().getSelectedItem());
        }
    }

    /**
     * Cancel the current operation and return to the main form scene
     *
     * @param event Cancel button action
     */
    @FXML
    void onActionCancel(ActionEvent event) {
        MainForm.switchStage(event, "/view/MainForm.fxml");
    }

    /**
     * Remove the currently selected part from the Associated Parts list.
     *
     * @param event Remove Associated Part button action
     */
    @FXML
    void onActionRemovePart(ActionEvent event) {
        try {
            if (AssociatedPartsTable.getSelectionModel().isEmpty()) {
                throw new RuntimeException("No part selected. Please select a part to remove");
            }
            else {
                Part part = AssociatedPartsTable.getSelectionModel().getSelectedItem();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Remove " + part.getName() + " as an associated part?", ButtonType.YES, ButtonType.NO);
                alert.showAndWait();

                if (alert.getResult() == ButtonType.YES) {
                    product.deleteAssociatedPart(part);
                }
            }
        } catch (RuntimeException e) {
            Alert alert = new Alert(Alert.AlertType.NONE, e.getLocalizedMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * Adds a new Product with the new values that have been input into the form.
     * Will throw an error for invalid inputs and display a dialog window.
     *
     * @param event Save button action
     */
    @FXML
    void onActionSave(ActionEvent event) {
        try {
            int stock = Integer.parseInt(AddInvTextBox.getText());
            double price = Double.parseDouble(AddPriceTextBox.getText());
            int max = Integer.parseInt(AddMaxTextBox.getText());
            int min = Integer.parseInt(AddMinTextBox.getText());
            if (min > max) {
                throw new RuntimeException("Min must be less that Max");
            }
            if (stock > max || stock < min) {
                throw new RuntimeException("Inv cannot be more than max or less than min");
            }
            if ((stock < 0) || (min < 0) || (max < 0) || (price < 0.0)) {
                throw new RuntimeException("Please input a valid number, negative numbers are invalid.");
            }
            product.setId(Inventory.generateProductNum());
            product.setName(AddNameTextBox.getText());
            product.setStock(stock);
            product.setPrice(price);
            product.setMax(max);
            product.setMin(min);
            Inventory.addProduct(product);

            MainForm.switchStage(event, "/view/MainForm.fxml");
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please input a valid number.", ButtonType.OK);
            alert.showAndWait();
        } catch (RuntimeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage(), ButtonType.OK);
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
            Part tempPart = Inventory.lookupPart(Integer.parseInt(SearchPart.getText()));
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
                Alert alert = new Alert(Alert.AlertType.NONE, "Part ID " + SearchPart.getText() + " not found", ButtonType.OK);
                alert.showAndWait();
            }
        }
        catch (Exception exception) {
            // If a string is searched, it will get passed here where a lookup is performed.
            // exact match not necessary, a partial match will suffice
            // once the entire list of parts has been filtered, set this new list as main display
            PartsTable.setItems(Inventory.lookupPart(SearchPart.getText()));

            // if this filtered list is empty, throw up a dialog box because there were no matches.
            if (Inventory.getFilteredParts().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.NONE, "Part Name " + SearchPart.getText() + " not found", ButtonType.OK);
                alert.showAndWait();
            }
        }
    }

    /**
     * Initializes controller, and populates TableView's.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // instantiate a currencyFormat object for formatting the price column of the below TableViews
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();

        // Populate Associated Parts table and format the price column as $X.XX
        AssociatedPartsTable.setItems(product.getAllAssociatedParts());
        AssocPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        AssocPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        AssocPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        AssocPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        AssocPartPriceCol.setCellFactory(tc -> {
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

    }
}
