package termbank.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import termbank.controller.BankSheetController;
import termbank.model.DataBank;

// TODO: Communicate with BankSheetController effectively - Caleb
public class ViewHandler {

    public static BankSheetController bankSheetController = new BankSheetController();

    public static ObservableList<DataBank> changeViewingCategory(String category) {

        ObservableList<DataBank> newDataBank = FXCollections.observableArrayList();

        if(!category.equals("All Categories")) {

            for(DataBank d : bankSheetController.returnRepository()){
                if(d.getCategory().equals(category))
                    newDataBank.add(d);
            }
            return newDataBank;
        }
        else
            return bankSheetController.returnRepository();
    }

}