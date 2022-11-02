package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Product;

import java.io.IOException;

/**
 * <p>This Inventory Management application implements the requested business requirements for a solution that effectively
 * manages an inventory of parts and products made up of parts.</p>
 *
 *<br> The JavaDocs are located in the JavaDocs folder (./QKM2/JavaDoc)<br>
 *
 * <p>FUTURE ENHANCEMENT : add the ability to increment/decrement a part or product's inventory without having to modify the object.
 * <br>FUTURE ENHANCEMENT : add alerts to notify of low inventory levels as you approach the set minimum value.
 * <br>FUTURE ENHANCEMENT : store inventory in a relational database for better long term storage and increased data integrity.</p>
 *
 *
 * @author Colin Roberson
 */

public class Main extends Application{
    /**
     * The start method creates the stage that will be used for the life of the application and loads the initial scene.
     *
     * @param primaryStage Primary stage for this application.
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
            primaryStage.setTitle("Inventory Management System");
            primaryStage.setScene(new Scene(root, 1000, 400));
            primaryStage.show();
            primaryStage.setResizable(false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * The main method is the entry point for the application.
     * Main method creates test data to work with and is the entry point for the application.
     *
     * @param args args an array of command-line arguments for the application
     */
    public static void main(String[] args) {

        // Test Data
        InHouse obj1 = new InHouse(1, "Bolt", 1.99, 100, 25, 1000, 123);
        Outsourced obj2 = new Outsourced(2, "Nut", .99, 49, 25, 1000,"TestCo");
        InHouse obj3 = new InHouse(3, "Screw", 1.00, 123, 40, 1000, 456);
        Outsourced obj4 = new Outsourced(4, "Washer", .10, 512, 200, 1000,"MoreTestingCo");
        InHouse obj5 = new InHouse(5, "Thread Locker", 4.99, 19, 3, 50, 789);
        Outsourced obj6 = new Outsourced(6, "Wing Nut", .49, 42, 7, 1000,"BetterTestingCo");

        Product prod1 = new Product(1, "Robot", 65536.00, 2, 1, 3);
        Product prod2 = new Product(2, "Chicken", 27.56, 19, 14, 42);
        Product prod3 = new Product(3, "Space Ship", 16777216.00, 1, 1, 2);
        Product prod4 = new Product(4, "Fishing Rod", 25.6, 7, 1, 99);

        Inventory.addPart(obj1);
        Inventory.addPart(obj2);
        Inventory.addPart(obj3);
        Inventory.addPart(obj4);
        Inventory.addPart(obj5);
        Inventory.addPart(obj6);
        Inventory.addProduct(prod1);
        Inventory.addProduct(prod2);
        Inventory.addProduct(prod3);
        Inventory.addProduct(prod4);


        launch(args);

    }
}