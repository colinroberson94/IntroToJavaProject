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
 * Controller class that provides logic for the Modify Part Form of the application.
 *
 *<br><br>
 * RUNTIME ERROR occurred when implementing initialize. When Part objects were passed from MainForm to ModifyPartForm
 * the correct radio buttons were not being selected. If the correct radio button was not selected after opening a Part,
 * an exception would be thrown after hitting the save button as the wrong constructor would then be called. This was
 * addressed by calling the setSelected() method while initializing.
 *
 *
 * @author Colin Roberson
 */

public class ModifyPartForm implements Initializable {

    /**
     * Product object reference used for passing an object from the MainForm to the Modify Part Form.
     */
    private static InHouse inHouse = null;

    /**
     * Product object reference used for passing an object from the MainForm to the Modify Part Form.
     */
    private static Outsourced outsourced = null;

    /**
     * Index of the product that is to be modified
     */
    private static int index;

    /**
     * Static method that allows the main scene to pass a selected object to the modify part scene.
     *
     * @param inHouse The InHouse Part that is to be modified
     * @param index The index of the part that is to be modified.
     */
    public static void passPart(InHouse inHouse, int index) {
        ModifyPartForm.inHouse = inHouse;
        ModifyPartForm.index = index;
    }

    /**
     * Static method that allows the main scene to pass a selected object to the modify part scene.
     *
     * @param outsourced The Outsourced Part that is to be modified
     * @param index The index of the part that is to be modified.
     */
    public static void passPart(Outsourced outsourced, int index) {
        ModifyPartForm.outsourced = outsourced;
        ModifyPartForm.index = index;
    }

    /**
     * Element of the ModifyPartForm GUI
     */
    @FXML
    private RadioButton OutsourcedRadioBtn;

    /**
     * Element of the ModifyPartForm GUI
     */
    @FXML
    private Label PartFieldLabel;

    /**
     * Element of the ModifyPartForm GUI
     */
    @FXML
    private Label PartIDLabel;

    /**
     * Element of the ModifyPartForm GUI
     */
    @FXML
    private Label PartInvLabel;

    /**
     * Element of the ModifyPartForm GUI
     */
    @FXML
    private Label PartMaxLabel;

    /**
     * Element of the ModifyPartForm GUI
     */
    @FXML
    private Label PartMinLabel;

    /**
     * Element of the ModifyPartForm GUI
     */
    @FXML
    private Label PartNameLabel;

    /**
     * Element of the ModifyPartForm GUI
     */
    @FXML
    private Label PartPriceLabel;

    /**
     * Element of the ModifyPartForm GUI
     */
    @FXML
    private TextField idTextField;

    /**
     * Element of the ModifyPartForm GUI
     */
    @FXML
    private RadioButton inHouseRadioBtn;

    /**
     * Element of the ModifyPartForm GUI
     */
    @FXML
    private TextField invTextField;

    /**
     * Element of the ModifyPartForm GUI
     */
    @FXML
    private ToggleGroup manufactureTypeTG;

    /**
     * Element of the ModifyPartForm GUI
     */
    @FXML
    private TextField maxTextField;

    /**
     * Element of the ModifyPartForm GUI
     */
    @FXML
    private TextField minTextField;

    /**
     * Element of the ModifyPartForm GUI
     */
    @FXML
    private TextField nameTextField;

    /**
     * Element of the ModifyPartForm GUI
     */
    @FXML
    private TextField optionalTextField;

    /**
     * Element of the ModifyPartForm GUI
     */
    @FXML
    private TextField priceTextField;

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
        inHouse = null;
        outsourced = null;
        MainForm.switchStage(event, "/view/MainForm.fxml");

    }

    /**
     * Updates the currently selected Part with the new values that have been input into the form.
     * Will throw an error for invalid inputs and display a corresponding dialog window.
     *
     * @param event Save button action
     */
    @FXML
    void onActionSave(ActionEvent event) {
        try {
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
                    int id = inHouse.getId();
                    int machineID = Integer.parseInt(optionalTextField.getText());
                    InHouse inHousePart = new InHouse(id, name, price, inv, min, max, machineID);
                    Inventory.updatePart(index, inHousePart);
                }
                else if (OutsourcedRadioBtn.isSelected()) {
                    int id = outsourced.getId();
                    String companyName = optionalTextField.getText();
                    Outsourced outsourcedPart = new Outsourced(id, name, price, inv, min, max, companyName);
                    Inventory.updatePart(index, outsourcedPart);
                }
                inHouse = null;
                outsourced = null;
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
     * Initializes controller and populates the text fields with the values of the selected Part.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (inHouse != null) {
            idTextField.setText(String.valueOf(inHouse.getId()));
            nameTextField.setText(inHouse.getName());
            invTextField.setText(String.valueOf(inHouse.getStock()));
            priceTextField.setText(String.valueOf(inHouse.getPrice()));
            maxTextField.setText(String.valueOf(inHouse.getMax()));
            minTextField.setText(String.valueOf(inHouse.getMin()));
            optionalTextField.setText(String.valueOf((inHouse).getMachineId()));
            inHouseRadioBtn.setSelected(true);
            PartFieldLabel.setText("Machine ID");
        }
        else if (outsourced != null) {
            idTextField.setText(String.valueOf(outsourced.getId()));
            nameTextField.setText(outsourced.getName());
            invTextField.setText(String.valueOf(outsourced.getStock()));
            priceTextField.setText(String.valueOf(outsourced.getPrice()));
            maxTextField.setText(String.valueOf(outsourced.getMax()));
            minTextField.setText(String.valueOf(outsourced.getMin()));
            optionalTextField.setText(outsourced.getCompanyName());
            OutsourcedRadioBtn.setSelected(true);
            PartFieldLabel.setText("Company Name");
        }


    }
}
