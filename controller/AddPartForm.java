package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller class that provides logic for the Add Part Form of the application.
 *
 *<br><br>
 * RUNTIME ERROR occurred in onActionSave. If a string was input for one of the fields expecting a number
 * (min, max, price, inv, machine id), an exception was thrown. This was addressed via "try-catch" blocks to catch
 * NumberFormatException's as well as a number of other checks on input values such as min &lt; inv &lt; max and checking
 * for negative numbers.
 *
 *
 * @author Colin Roberson
 */

public class AddPartForm implements Initializable {

    /**
     * Element of the AddPartForm GUI
     */
    @FXML
    private Label PartFieldLabel;

    /**
     * Element of the AddPartForm GUI
     */
    @FXML
    private Label PartIDLabel;

    /**
     * Element of the AddPartForm GUI
     */
    @FXML
    private Label PartInvLabel;

    /**
     * Element of the AddPartForm GUI
     */
    @FXML
    private Label PartMaxLabel;

    /**
     * Element of the AddPartForm GUI
     */
    @FXML
    private Label PartMinLabel;

    /**
     * Element of the AddPartForm GUI
     */
    @FXML
    private Label PartNameLabel;

    /**
     * Element of the AddPartForm GUI
     */
    @FXML
    private Label PartPriceLabel;

    /**
     * Element of the AddPartForm GUI
     */
    @FXML
    private TextField invTextField;

    /**
     * Element of the AddPartForm GUI
     */
    @FXML
    private ToggleGroup manufactureTypeTG;

    /**
     * Element of the AddPartForm GUI
     */
    @FXML
    private TextField maxTextField;

    /**
     * Element of the AddPartForm GUI
     */
    @FXML
    private TextField minTextField;

    /**
     * Element of the AddPartForm GUI
     */
    @FXML
    private TextField nameTextField;

    /**
     * Element of the AddPartForm GUI
     */
    @FXML
    private TextField optionalTextField;

    /**
     * Element of the AddPartForm GUI
     */
    @FXML
    private TextField priceTextField;

    /**
     * Element of the AddPartForm GUI
     */
    @FXML
    private RadioButton OutsourcedRadioBtn;

    /**
     * Element of the AddPartForm GUI
     */
    @FXML
    private RadioButton inHouseRadioBtn;

    /**
     * Set the Part type to InHouse - modifies scene label's to properly represent InHouse parts.
     *
     * @param event In House radio button action
     */
    @FXML
    void onInHouse(ActionEvent event) {
        PartFieldLabel.setText("Machine ID");
    }

    /**
     * Set the Part type to Outsourced - modifies scene label's to properly represent Outsourced parts.
     *
     * @param event Outsourced radio button action
     */
    @FXML
    void onOutsourced(ActionEvent event) {
        PartFieldLabel.setText("Company Name");
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
     * Creates a new Part with the values that have been input into the form.
     * Will throw an error for invalid inputs and display a corresponding dialog window.
     *
     * @param event Save button action
     */
    @FXML
    void onActionSave(ActionEvent event) {
        try {
            int id = Inventory.generatePartNum();
            String name = nameTextField.getText();
            int inv = Integer.parseInt(invTextField.getText());
            double price = Double.parseDouble(priceTextField.getText());
            int max = Integer.parseInt(maxTextField.getText());
            int min = Integer.parseInt(minTextField.getText());
            if (min > max) {
                throw new RuntimeException("Min must be less that Max");
            }
            if (inv > max || inv < min) {
                throw new RuntimeException("Inv cannot be more than max or less than min");
            }
            if ((inv < 0) || (price < 0.0) || (max < 0) || (min < 0)) {
                throw new RuntimeException("Please input a valid number, negative numbers are invalid.");
            }
            else {
                if (inHouseRadioBtn.isSelected()) {
                    int machineID = Integer.parseInt(optionalTextField.getText());
                    InHouse inHousePart = new InHouse(id, name, price, inv, min, max, machineID);
                    Inventory.addPart(inHousePart);
                }

                else if (OutsourcedRadioBtn.isSelected()) {
                    String companyName = optionalTextField.getText();
                    Outsourced outsourcedPart = new Outsourced(id, name, price, inv, min, max, companyName);
                    Inventory.addPart(outsourcedPart);
                }

                MainForm.switchStage(event, "/view/MainForm.fxml");
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Please input a valid number", ButtonType.OK);
            alert.showAndWait();
        } catch (RuntimeException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    /**
     * Initializes controller.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
