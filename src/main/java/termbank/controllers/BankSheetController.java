///*
// * File: BankSheetController.java
// *
// * This file controls the FXML properties
// */
//
//package termbank.controllers;
//import java.util.function.Consumer;
//
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.EventHandler;
//import javafx.fxml.FXML;
//import javafx.scene.control.*;
//import javafx.scene.control.TableColumn.CellEditEvent;
//import javafx.scene.control.cell.TextFieldTableCell;
//import javafx.scene.input.MouseButton;
//import javafx.scene.input.MouseEvent;
//import termbank.TermBankApp;
//import termbank.model.DataBank;
//
//public class BankSheetController {
//
//    @FXML private TableView<DataBank> clientDataTable; // TableView that contains all of the client's data
//
//    @FXML private TableColumn termColumn;
//    @FXML private TableColumn definitionColumn;
//
//    @FXML private TextField termField;
//    @FXML private TextField createCategoryField;
//    @FXML private TextArea definitionField;
//
//    @FXML private Label currentCategorySelected;
//
//    @FXML private ChoiceBox selectViewingCategory;  // A ChoiceBox of possible categories the client can choose to view
//    @FXML private ChoiceBox availableCategories;    // A collection of available categories
//
//    private TermBankApp termBankApp = new TermBankApp();
//
//    /* ObservableList for all DataBank objects */
//    private ObservableList<DataBank> dataRepository;
//    private ObservableList<DataBank> viewingList = dataRepository;
//
//    public ObservableList<DataBank> returnRepository() {
//        return this.dataRepository;
//    }
//
//    @FXML
//    protected void addData() {
//        String chosenCategory = (String) selectViewingCategory.getSelectionModel().getSelectedItem();
//
//        /* Selected category choice is anything other than 'Create new category' */
//
//        if((selectViewingCategory.getSelectionModel().getSelectedIndex() != 0)) {
//            validateInput(chosenCategory, termField.getText(), definitionField.getText(), collection -> {
//                if(currentCategorySelected.getText().equals("All categories") ||
//                        (!chosenCategory.equals(currentCategorySelected.getText())))
//                    dataRepository.add(new DataBank(chosenCategory, termField.getText(), definitionField.getText()));
//                else {
//                    dataRepository.add(new DataBank(chosenCategory, termField.getText(), definitionField.getText()));
//                    viewingList.add(new DataBank(chosenCategory, termField.getText(), definitionField.getText()));
//                }
//
//                //INSERT INTO DATABASE
//                DataBankService.insertToDatabase(chosenCategory, termField.getText(), definitionField.getText());
//            });
//        }
//
//        else { //User decides to create a new category
//
//            /* User attempted to create a category that already exists */
//            if(availableCategories.getItems().contains(createCategoryField.getText()))
//                AlertHelper.showAlert(Alert.AlertType.WARNING, termBankApp.returnStage(),
//                        "Category already exists", "Choose another");
//            else {
//                if(validateInput(createCategoryField.getText(), termField.getText(), definitionField.getText(), collection -> {
//                    dataRepository.add(new DataBank(createCategoryField.getText(), termField.getText(),
//                            definitionField.getText()));
//
//                    //INSERT INTO DATABASE
//                    DataBankService.insertToDatabase(createCategoryField.getText(), termField.getText(),
//                            definitionField.getText());
//                }))
//
//                { // The input has been validated, add new category to list of available category
//                    availableCategories.getItems().add(createCategoryField.getText());
//                    selectViewingCategory.getItems().add(createCategoryField.getText());
//                };
//            }
//        }
//
//        /* Set all fields back to empty */
//        termField.setText("");
//        definitionField.setText("");
//        createCategoryField.setText("");
//    }
//
//    @SuppressWarnings("unchecked")
//    @FXML
//    private void initialize() {
//        createCategoryField.setVisible(true);
//
//        selectViewingCategory.setItems(FXCollections.observableArrayList("Create new category"));
//        selectViewingCategory.setValue("Create new category");
//        availableCategories.setItems(FXCollections.observableArrayList("All Categories"));
//        availableCategories.setValue("All Categories");
//
//        dataRepository = DataBankService.getDatabaseContent(selectViewingCategory, availableCategories);
//        clientDataTable.setItems(dataRepository);
//
//        currentCategorySelected.setId("category-label");
//        termField.setId("color-field");
//
//        /* This monitors how categories are created and stored */
//        selectViewingCategory.getSelectionModel().selectedIndexProperty().addListener((ov, value, new_value) -> {
//            String index = (String) selectViewingCategory.getItems().get((Integer) new_value);
//            if(index.equals("Create new category")) {
//                createCategoryField.setVisible(true);
//            }
//            else {
//                createCategoryField.setVisible(false);
//                createCategoryField.setText("");
//            }
//        });
//
//        /* Changes viewing category immediately after selecting a new one*/
//        availableCategories.getSelectionModel().selectedIndexProperty().addListener((ov, value, new_value) -> {
//            String categoryChoice = (String) availableCategories.getItems().get((Integer) new_value);
//            currentCategorySelected.setText(categoryChoice);
//            viewingList = changeViewingCategory(categoryChoice);
//            clientDataTable.setItems(viewingList);
//        });
//
//        /* Edit handling */
//        clientDataTable.setEditable(true);
//        termColumn.setCellFactory(TextFieldTableCell.forTableColumn());
//        termColumn.setOnEditCommit(
//                (EventHandler<CellEditEvent<DataBank, String>>) t -> {
//
//                    String cellBefore = t.getOldValue();
//                    String cellAfter = t.getNewValue();
//
//                    if(!t.getNewValue().isBlank()) {
//
//                        t.getTableView().getItems().get(t.getTablePosition()
//                                .getRow()).setTerm(t.getNewValue());
//
//                        //UPDATE DATABASE
//                        DataBankService.updateDatabaseTerm(cellBefore, cellAfter);
//                    }
//                    else {
//                        t.getTableView().getItems().get(t.getTablePosition().getRow()).setTerm(cellBefore);
//                        AlertHelper.showAlert(Alert.AlertType.WARNING, termBankApp.returnStage(),
//                                "You cannot enter an empty term", "Please correct this");
//                    }
//                });
//
//        definitionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
//        definitionColumn.setOnEditCommit(
//                (EventHandler<CellEditEvent<DataBank, String>>) t -> {
//
//                    String cellBefore = t.getOldValue();
//                    String cellAfter = t.getNewValue();
//
//                    if(!t.getNewValue().isBlank()) {
//                        t.getTableView().getItems().get(t.getTablePosition()
//                                .getRow()).setDefinition(t.getNewValue());
//
//                        //UPDATE DATABASE
//                        DataBankService.updateDatabaseDefinition(cellBefore, cellAfter);
//                    }
//                    else {
//                        t.getTableView().getItems().get(t.getTablePosition().getRow()).setDefinition(cellBefore);
//                        AlertHelper.showAlert(Alert.AlertType.WARNING, termBankApp.returnStage(),
//                                "You cannot enter an empty definition", "Please correct this");
//                    }
//                });
//
//        ContextMenu contextMenu = new ContextMenu();
//        MenuItem deleteOption = new MenuItem("Delete");
//        contextMenu.getItems().add(deleteOption);
//
//        clientDataTable.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
//
//            @Override
//            public void handle(MouseEvent t) {
//                if(t.getButton() == MouseButton.SECONDARY) {
//                    contextMenu.show(clientDataTable, t.getScreenX(), t.getScreenY());
//
//                    deleteOption.setOnAction(e -> {
//                        DataBank selectedItem = clientDataTable.getSelectionModel().getSelectedItem();
//                        clientDataTable.getItems().remove(selectedItem);
//                        dataRepository.remove(selectedItem);
//
//                        if(isCategoryListEmpty(selectedItem.getCategory())) {
//                            /* This removes the term AND the group */
//                            DataBankService.deleteTermAndGroup(selectedItem);
//
//                            /* Reset to original data viewing */
//                            clientDataTable.setItems(dataRepository);
//                            availableCategories.setValue("All Categories");
//                            currentCategorySelected.setText("All Categories");
//                            availableCategories.getItems().remove(selectedItem.getCategory());
//                        }
//                        else {
//                            /* This removes a term from the group */
//                            DataBankService.deleteTerm(selectedItem);
//                        }
//                    });
//
//                }
//            }
//        });
//    }
//
//    /* This function allows the user to select and view one category at a time (as opposed to 'All Categories') */
//    // TODO: Add this method to the 'View' class for MVC - Caleb
//    private ObservableList<DataBank> changeViewingCategory(String category) {
//
//        ObservableList<DataBank> newDataBank = FXCollections.observableArrayList();
//
//        if(!category.equals("All Categories")) {
//
//            for(DataBank d : dataRepository){
//                if(d.getCategory().equals(category))
//                    newDataBank.add(d);
//            }
//            return newDataBank;
//        }
//        else
//            return dataRepository;
//    }
//
//    private boolean isCategoryListEmpty(String category) {
//        for(DataBank dataBank : dataRepository) {
//            if(dataBank.getCategory().equals(category))
//                return false;
//        }
//        return true;
//    }
//
//    private boolean validateInput(String category, String term, String definition, Consumer<DataBank> operation) {
//        if(term.isEmpty() || definition.isEmpty() || category.isEmpty()) {
//            AlertHelper.showAlert(Alert.AlertType.WARNING, termBankApp.returnStage(),
//                    "One or more fields is empty", "Please correct this");
//            return false;
//        }
//        else
//            operation.accept(new DataBank());
//        return true;
//    }
//}
