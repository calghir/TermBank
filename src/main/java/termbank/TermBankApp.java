/*
 * Programmer: Caleb Ghirmai
 *
 * File: TermBankApp.java
 *
 * This file runs the JavaFX application
 */
package termbank;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

import java.io.IOException;

public class TermBankApp extends Application {

    private Stage primaryStage;
    private static Scene primaryScene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.primaryStage = stage;
        this.primaryStage.setTitle("TermBank 1.0");

        String bankSheetLayout = "/fxml/BankSheetLayout"; // FXML code that describes the graphics

        primaryScene = new Scene(loadFXML(bankSheetLayout), 500, 500);

        String stylesheet = TermBankApp.class.getResource("/stylesheets/stylesheet.css").toExternalForm();
        primaryScene.getStylesheets().clear();
        primaryScene.getStylesheets().add(stylesheet);


        this.primaryStage.setScene(this.primaryScene);
        this.primaryStage.setResizable(false);
        this.primaryStage.show();
    }

    // This is for switching screens/ views
    public static void setRoot(String fxml) throws IOException{
        primaryScene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TermBankApp.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public Stage returnStage() {
        return this.primaryStage;
    }

    public Scene returnScene() {
        return this.primaryScene;
    }

}