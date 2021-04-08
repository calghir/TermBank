/*
 * File: BankSheetController.java
 *
 * This file controls the FXML properties
 */

package termbank.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import termbank.jbdc.TermBankDAO;
import termbank.model.DataBank;

public class BankSheetController {

    @FXML private TableView<DataBank> dataTable; // TableView that contains all of the client's data

    @FXML private TableColumn termCol;
    @FXML private TableColumn defCol;

    @FXML private TextField termField;
    @FXML private TextField createCategoryField;
    @FXML private TextArea definitionField;

    @FXML private Label currentCategorySelected;

    @FXML private ChoiceBox selectViewingCategory;  // A ChoiceBox of possible categories the client can choose to view
    @FXML private ChoiceBox availableCategories;    // A collection of available categories


    /* ObservableList for all DataBank objects */
    private ObservableList<DataBank> dataRepository;


    @FXML
    protected void addData() {
        // TODO: work on efficient way to test data handling
    }

    @SuppressWarnings("unchecked")
    @FXML
    private void initialize() {
       try {
           dataTable.setItems(TermBankDAO.buildData());

       } catch (Exception e) {
           e.getMessage();
       }
    }
}
