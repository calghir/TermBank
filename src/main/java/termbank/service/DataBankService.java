package termbank.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ChoiceBox;
import termbank.data.DAO;
import termbank.model.DataBank;

import java.sql.*;

public class DataBankService implements DAO {

    /* This method creates and returns a DataBank object from existing SQL data*/
    public static DataBank createDataBank(ResultSet resultSet) {
        DataBank dataBank = new DataBank();
        try {
            dataBank.setCategory(resultSet.getString("category"));
            dataBank.setTerm(resultSet.getString("term"));
            dataBank.setDefinition(resultSet.getString("definition"));

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return dataBank;
    }

    /* A method that allows us to read all existing data from termbank.db */
    public static ObservableList<DataBank> retrieveDataInfo(ChoiceBox available, ChoiceBox viewing) {

        ObservableList<DataBank> existingSQLData = FXCollections.observableArrayList();

        try (Connection connection = DriverManager.getConnection(DATABASE_NAME)) {
            String sqlQuery = "Select * from databanktable";

            Statement queryStatement = connection.createStatement();
            ResultSet resultSet = queryStatement.executeQuery(sqlQuery);

            while(resultSet.next()) {
                DataBank dataBank = createDataBank(resultSet);
                existingSQLData.add(dataBank);

                if(!available.getItems().contains(dataBank.getCategory())) {
                    available.getItems().add(dataBank.getCategory());
                    viewing.getItems().add(dataBank.getCategory());
                }
            }
            resultSet.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return existingSQLData;
    }

    /* This method adds a new DataBank object to the database table*/
    public static void insertToDatabase(String category, String term, String definition) {

        String SQL = "INSERT INTO databanktable(category,term,definition)VALUES(?,?,?)";

        try (Connection connection = DriverManager.getConnection(DATABASE_NAME)) {

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);

            preparedStatement.setString(1, category);
            preparedStatement.setString(2, term);
            preparedStatement.setString(3, definition);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /* This method updates terms in the database table */
    public static void updateDatabaseTerm(String valBefore, String valAfter) {

        try (Connection connection = DriverManager.getConnection(DATABASE_NAME)) {

            String sqlUpdate = "UPDATE databanktable "
                    + "SET term =  ? "
                    + "WHERE term = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);
            preparedStatement.setString(1, valAfter);
            preparedStatement.setString(2, valBefore);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /* This method updates definitions in the database table */
    public static void updateDatabaseDefinition(String valBefore, String valAfter) {

        try (Connection connection = DriverManager.getConnection(DATABASE_NAME)) {

            String sqlUpdate = "UPDATE databanktable "
                    + "SET definition =  ? "
                    + "WHERE definition = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);
            preparedStatement.setString(1, valAfter);
            preparedStatement.setString(2, valBefore);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}