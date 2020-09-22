/*
 * File: BankSheetController.java
 *
 * This file controls the FXML properties
 */

package termbank.controller;
import java.util.function.Consumer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import termbank.service.DataBankService;
import termbank.utils.AlertHelper;
import termbank.TermBankApp;
import termbank.model.DataBank;

public class BankSheetController {

    @FXML private TableView<DataBank> clientDataTable; // TableView that contains all of the client's data

    @FXML private TableColumn termColumn;
    @FXML private TableColumn definitionColumn;

    @FXML private TextField termField;
    @FXML private TextField createCategoryField;
    @FXML private TextArea definitionField;

    @FXML private Label currentCategorySelected;

    @FXML private ChoiceBox selectViewingCategory;  // A ChoiceBox of possible categories the client can choose to view
    @FXML private ChoiceBox availableCategories;    // A collection of available categories

    private TermBankApp termBankApp = new TermBankApp();

    /* ObservableList for all DataBank objects */
    private ObservableList<DataBank> dataRepository;
    private ObservableList<DataBank> viewingList = dataRepository;

    public BankSheetController() {

    }

    public ObservableList<DataBank> returnRepository() {
        return this.dataRepository;
    }

    /*
     * This function adds to an ObservableList of DataBank objects
     *
     * - ALL DataBank objects will be stored in an ObservableList
     * - For each new category created, add to an ObservableList, which when updated,
     *   will reflect changes to both ChoiceBoxes
     */

    @FXML
    protected void addData() {
        String chosenCategory = (String) selectViewingCategory.getSelectionModel().getSelectedItem();

        /* Selected category choice is anything other than 'Create new category' */

        if((selectViewingCategory.getSelectionModel().getSelectedIndex() != 0)) {
            validateInput(chosenCategory, termField.getText(), definitionField.getText(), collection -> {
                if(currentCategorySelected.getText().equals("All categories") ||
                        (!chosenCategory.equals(currentCategorySelected.getText())))
                    dataRepository.add(new DataBank(chosenCategory, termField.getText(), definitionField.getText()));
                else {
                    dataRepository.add(new DataBank(chosenCategory, termField.getText(), definitionField.getText()));
                    viewingList.add(new DataBank(chosenCategory, termField.getText(), definitionField.getText()));
                }

                //INSERT INTO DATABASE
                DataBankService.insertToDatabase(chosenCategory, termField.getText(), definitionField.getText());
            });
        }

        else { //User decides to create a new category

            /* User attempted to create a category that already exists */
            if(availableCategories.getItems().contains(createCategoryField.getText()))
                AlertHelper.showAlert(Alert.AlertType.WARNING, termBankApp.returnStage(),
                        "Category already exists", "Choose another");
            else {
                if(validateInput(createCategoryField.getText(), termField.getText(), definitionField.getText(), collection -> {
                    dataRepository.add(new DataBank(createCategoryField.getText(), termField.getText(),
                            definitionField.getText()));

                    //INSERT INTO DATABASE
                    DataBankService.insertToDatabase(createCategoryField.getText(), termField.getText(),
                            definitionField.getText());
                }))

                { // The input has been validated, add new category to list of available category
                    availableCategories.getItems().add(createCategoryField.getText());
                    selectViewingCategory.getItems().add(createCategoryField.getText());
                };
            }
        }

        /* Set all fields back to empty */
        termField.setText("");
        definitionField.setText("");
        createCategoryField.setText("");
    }

    @SuppressWarnings("unchecked")
    @FXML
    private void initialize() {
        createCategoryField.setVisible(true);

        selectViewingCategory.setItems(FXCollections.observableArrayList("Create new category"));
        selectViewingCategory.setValue("Create new category");
        availableCategories.setItems(FXCollections.observableArrayList("All Categories"));
        availableCategories.setValue("All Categories");

        dataRepository = DataBankService.retrieveDataInfo(selectViewingCategory, availableCategories);
        clientDataTable.setItems(dataRepository);

        currentCategorySelected.setId("category-label");
        termField.setId("color-field");

        /* This monitors how categories are created and stored */
        selectViewingCategory.getSelectionModel().selectedIndexProperty().addListener((ov, value, new_value) -> {
            String index = (String) selectViewingCategory.getItems().get((Integer) new_value);
            if(index.equals("Create new category")) {
                createCategoryField.setVisible(true);
            }
            else {
                createCategoryField.setVisible(false);
                createCategoryField.setText("");
            }
        });

        /* Changes viewing category immediately after selecting a new one*/
        availableCategories.getSelectionModel().selectedIndexProperty().addListener((ov, value, new_value) -> {
            String categoryChoice = (String) availableCategories.getItems().get((Integer) new_value);
            currentCategorySelected.setText(categoryChoice);
            viewingList = changeViewingCategory(categoryChoice);
            clientDataTable.setItems(viewingList);
        });

        /* TableView cell editing */
        clientDataTable.setEditable(true);
        termColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        termColumn.setOnEditCommit(
                (EventHandler<CellEditEvent<DataBank, String>>) t -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setTerm(t.getNewValue())
        );

        definitionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        definitionColumn.setOnEditCommit(
                (EventHandler<CellEditEvent<DataBank, String>>) t -> t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setDefinition(t.getNewValue())
        );
    }

    /* This function allows the user to select and view one category at a time (as opposed to 'All Categories') */
    // TODO: Add this method to the 'View' class for MVC - Caleb
    private ObservableList<DataBank> changeViewingCategory(String category) {

        ObservableList<DataBank> newDataBank = FXCollections.observableArrayList();

        if(!category.equals("All Categories")) {

            for(DataBank d : dataRepository){
                if(d.getCategory().equals(category))
                    newDataBank.add(d);
            }
            return newDataBank;
        }
        else
            return dataRepository;
    }

    private boolean validateInput(String category, String term, String definition, Consumer<DataBank> operation) {
        if(term.isEmpty() || definition.isEmpty() || category.isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.WARNING, termBankApp.returnStage(),
                    "One or more fields is empty", "Please correct this");
            return false;
        }
        else
            operation.accept(new DataBank());
        return true;
    }
}
