package termbank.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import termbank.jbdc.TermBankDAO;

import java.sql.SQLException;

public class RegisterController {

    @FXML
    private TextField category_field;

    @FXML
    private TextField term_field;

    @FXML
    private TextField definition_field;

    @FXML
    private Button submitButton;

    @FXML
    public void registerInput(ActionEvent event) throws SQLException {

        Window owner = submitButton.getScene().getWindow();

        if(category_field.getText().isEmpty() || term_field.getText().isEmpty() || definition_field.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!", "Do not leave any fields blank");
        }
        else {

            // Input is assumed to be valid
            String category = category_field.getText();
            String term = term_field.getText();
            String definition = definition_field.getText();

            TermBankDAO termBankDAO = new TermBankDAO();
            termBankDAO.insertDataBank(category, term, definition);

            // Registration successful
            showAlert(Alert.AlertType.CONFIRMATION, owner, "Registration Successful!",
                    "Thank you for using TermBank!");
        }

    }

    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

}
