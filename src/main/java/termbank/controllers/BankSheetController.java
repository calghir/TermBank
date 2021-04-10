/*
 * File: BankSheetController.java
 *
 * This file controls the FXML properties
 */

package termbank.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.*;
import termbank.jbdc.TermBankDAO;
import termbank.model.DataBank;

import java.sql.SQLException;

public class BankSheetController {

    @FXML private TableView<DataBank> tableView; // TableView that contains all of the client's data

    @FXML private TableColumn termColumn;
    @FXML private TableColumn defColumn;

    @FXML private TextField term_field;
    @FXML private TextField category_field;
    @FXML private TextArea definition_field;

    @FXML private ChoiceBox available_categories;    // A collection of available categories


    /* ObservableList for all DataBank objects */
    private ObservableList<DataBank> data_repository;

    ObservableList<String> category_options = FXCollections.observableArrayList(
            "All categories"
    );

    @FXML
    protected void addData() throws SQLException {
        System.out.println("Add Term button activated");
    }

    @SuppressWarnings("unchecked")
    @FXML
    private void initialize() {
       try {
           data_repository = TermBankDAO.buildData();
           tableView.setItems(data_repository);
           for(DataBank dataBank : data_repository) {
               if(!category_options.contains(dataBank.getCategory()))
                   category_options.add(dataBank.getCategory());
           }

       } catch (Exception e) {
           e.getMessage();
       }

       String category_selected = "";
       available_categories.getSelectionModel().selectedItemProperty().addListener
               ((observableValue, o, t1) -> {
                   System.out.println(t1);
                   data_repository = t1.toString().equals("All categories")? TermBankDAO.buildData()
                           : TermBankDAO.buildDataForCategory(t1.toString());
                   tableView.setItems(data_repository);

               });

       available_categories.getItems().addAll(category_options);
       ObservableList list = available_categories.getItems();
       Separator separator = new Separator();
       separator.setMaxWidth(80);
       separator.setHalignment(HPos.CENTER);
       list.add(1, separator);
    }

}
