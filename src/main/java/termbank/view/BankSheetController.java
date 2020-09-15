/*
 * File: BankSheetController.java
 *
 * This file controls the FXML properties
 */

package termbank.view;
import java.util.function.Consumer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import termbank.utils.AlertHelper;
import termbank.TermBankApp;
import termbank.model.DataBank;


public class BankSheetController {

    @FXML private TableView<DataBank> clientDataTable; //TableView that contains all of the client's data

    @FXML private TableColumn termColumn;
    @FXML private TableColumn definitionColumn;

    @FXML private TextField termField;
    @FXML private TextField createGroupField;

    @FXML private TextArea definitionField;

    @FXML private Label currentGroupSelected;

    @FXML private ChoiceBox selectViewingGroup; //A ChoiceBox of possible groups the client can choose to view from
    @FXML private ChoiceBox availableGroups;    //A collection of available groups

    private TermBankApp termBankApp = new TermBankApp();
    String groupChoice = "";

    /* ObservableList for all DataBank objects */
    ObservableList<DataBank> dataRepository;

    /*
     * This function adds to an ObservableList of DataBank objects
     *
     * - ALL DataBank objects will be stored in an ObservableList
     * - For each new group created, add to an ObservableList, which when updated,
     *   will reflect changes to both ChoiceBoxes
     */

    @FXML
    protected void addData(ActionEvent event) {

        String chosenGroup = (String) selectViewingGroup.getSelectionModel().getSelectedItem();

        /* If the user does not want to create a new group and selectGroup is not "Create a new group" */

        if(createGroupField.getText().isEmpty()) { //User has chosen a group that already exists

            if((selectViewingGroup.getSelectionModel().getSelectedIndex() != 0)) {
                validateInput(termField.getText(), definitionField.getText(), chosenGroup, collection -> {
                    dataRepository.add(new DataBank(termField.getText(), definitionField.getText(), chosenGroup));
                });

            }
        }



        else { //User decides to create a new group

            if(availableGroups.getItems().contains(createGroupField.getText()))
                AlertHelper.showAlert(Alert.AlertType.WARNING, termBankApp.returnStage(),
                        "Group already exists", "Choose another");
            else {

                validateInput(termField.getText(), definitionField.getText(), chosenGroup, collection -> {
                    dataRepository.add(new DataBank(termField.getText(), definitionField.getText(), createGroupField.getText()));
                });

                availableGroups.getItems().add(createGroupField.getText());
                selectViewingGroup.getItems().add(createGroupField.getText());

            }
        }

        /* Set all fields back to empty */
        termField.setText("");
        definitionField.setText("");
        createGroupField.setText("");

    }

    @SuppressWarnings("unchecked")
    @FXML
    private void initialize() {
        createGroupField.setVisible(true);
        dataRepository = clientDataTable.getItems(); //Equal to the underlying TableView data
        selectViewingGroup.setItems(FXCollections.observableArrayList("Create new group"));
        selectViewingGroup.setValue("Create new group");

        availableGroups.setItems(FXCollections.observableArrayList("All Groups"));
        availableGroups.setValue("All Groups");

        currentGroupSelected.setId("group-label");
        termField.setId("color-field");


        /* This monitors how groups are created and stored */
        selectViewingGroup.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue ov, Number value, Number new_value) {
                String index = (String) selectViewingGroup.getItems().get((Integer) new_value);
                if(index.equals("Create new group")) {
                    createGroupField.setVisible(true);
                }
                else {
                    createGroupField.setVisible(false);
                    createGroupField.setText("");
                }
            }
        });

        /* TableView cell editing */
        clientDataTable.setEditable(true);
        termColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        termColumn.setOnEditCommit(
                new EventHandler<CellEditEvent<DataBank, String>>() {
                    @Override
                    public void handle(CellEditEvent<DataBank, String> t) {
                        ((DataBank) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setTerm(t.getNewValue());
                    }
                }
        );

        definitionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        definitionColumn.setOnEditCommit(
                new EventHandler<CellEditEvent<DataBank, String>>() {
                    @Override
                    public void handle(CellEditEvent<DataBank, String> t) {
                        ((DataBank) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setDefinition(t.getNewValue());
                    }
                }
        );
    }

    /*
     * This function allows the user to view a certain group at a time
     */

    @FXML
    protected void updateDataView() {

        ObservableList<DataBank> newData = FXCollections.observableArrayList();

        String selectedGroup = (String) availableGroups.getSelectionModel().getSelectedItem();

        if(selectedGroup.equals("All Groups")) {
            currentGroupSelected.setText(selectedGroup);
            clientDataTable.setItems(dataRepository);
        }

        for(DataBank d : dataRepository) {
            if(d.getGroup().equals(selectedGroup)) {
                newData.add(d);
            }
        }

        if(!newData.isEmpty()) {
            currentGroupSelected.setText(selectedGroup);
            clientDataTable.setItems(newData);
        }

    }

    private void validateInput(String term, String definition, String group, Consumer<DataBank> operation) {
        if(term.isEmpty() || definition.isEmpty() || group.isEmpty())
            AlertHelper.showAlert(Alert.AlertType.WARNING, termBankApp.returnStage(),
                    "One or more fields is empty", "Please correct this");
        else
            operation.accept(null);
    }

}