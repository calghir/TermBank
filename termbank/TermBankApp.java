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

public class TermBankApp extends Application {

	private Stage primaryStage;
	private Scene primaryScene;
	
	public TermBankApp() {
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		this.primaryStage = stage;
		this.primaryStage.setTitle("TermBank 1.0");
		
		Parent root = FXMLLoader.load(getClass().
				getResource("view/BankSheetLayout.fxml"));
		
		this.primaryScene = new Scene(root, 500, 500);
		this.primaryScene.getStylesheets().clear();
		this.primaryScene.getStylesheets().add("termbank/stylesheet.css");
			
		
		this.primaryStage.setScene(this.primaryScene);
		this.primaryStage.setResizable(false);
		this.primaryStage.show();
	}
	
	public Stage returnStage() {
		return this.primaryStage;
	}
	
	public Scene returnScene() {
		return this.primaryScene;
	}

}
