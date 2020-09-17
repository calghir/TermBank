package termbank.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import termbank.model.DataBank;

import java.sql.*;

public class DatabaseConnection {

    private static Connection conn = null;
    private static final String DATABASE_NAME = "jdbc:sqlite:E:/TermBank/src/main/resources/db/termbank.db";

    public DatabaseConnection() {
    }

    public static boolean isConnected() {
        try {
            conn = DriverManager.getConnection(DATABASE_NAME);
            return true;
        } catch (SQLException e) {
            System.out.println("Exception found!");
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static void disconnect() {
        try {
            if(conn != null) {
               conn.close();
               System.out.println("Connection to " + DATABASE_NAME + " has been terminated.");
            }
        } catch (SQLException e) {
            System.out.println("Couldn't close connection: " + e.getMessage());
        }
    }

    /* This method creates and returns a DataBank object from existing SQL data*/
    public DataBank createDataBank(ResultSet resultSet) {
        DataBank dataBank = new DataBank();
        try {
            dataBank.setGroup(resultSet.getString("category"));
            dataBank.setTerm(resultSet.getString("term"));
            dataBank.setDefinition(resultSet.getString("definition"));

        } catch (SQLException ex){
            System.out.println(ex.getMessage());
        }

        return dataBank;
    }

    /* A method that allows us to read existing data from termbank.db */
    public ObservableList<DataBank> viewDatabaseData() {

        ObservableList<DataBank> existingSQLData = FXCollections.observableArrayList();

        try {
            String sqlQuery = "Select * from databanktable";

            conn = DriverManager.getConnection(DATABASE_NAME);

            System.out.println("Connection to SQLite has been established");

            Statement queryStatement = conn.createStatement();
            ResultSet resultSet = queryStatement.executeQuery(sqlQuery);

            while(resultSet.next()) {
                DataBank dataBank = createDataBank(resultSet);
                existingSQLData.add(dataBank);
            }
            resultSet.close();
            conn.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return existingSQLData;
    }
}


