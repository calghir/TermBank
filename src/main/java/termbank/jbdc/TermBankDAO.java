package termbank.jbdc;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import termbank.model.DataBank;

import java.sql.*;

/*
    This class manages the JBDC-MySQL connection
 */
public class TermBankDAO implements DAOImpl {

    public static ObservableList<DataBank> buildData() {

        ObservableList<DataBank> existingData = FXCollections.observableArrayList();

        try (Connection connection = DriverManager
                .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);

             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM databanktable")) {

            System.out.println("A connection to MySQL has been established");

            ResultSet resultSet = preparedStatement.executeQuery("SELECT * FROM databanktable");

            while(resultSet.next()) {
                DataBank dataBank = createDataBank(resultSet);
                existingData.add(dataBank);
            }


        } catch (SQLException e) {
            // print SQL exception information
            printSQLException(e);
        }
        return existingData;
    }

    public static DataBank createDataBank(ResultSet resultSet) {
        DataBank dataBank = new DataBank();
        try {
            dataBank.setCategory(resultSet.getString("category"));
            dataBank.setTerm(resultSet.getString("term"));
            dataBank.setDefinition(resultSet.getString("definition"));

        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return dataBank;
    }

    public static void insertDataBank(String category, String term, String definition) throws SQLException {

        // Step 1: Establishing a Connection and
        // try-with-resource statement will auto close the connection.
        try (Connection connection = DriverManager
                .getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);

             // Step 2:Create a statement using connection object
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {

            String SQL = "SELECT * FROM databanktable WHERE category='" + category + "'";
            ResultSet resultSet = preparedStatement.executeQuery(SQL);

            if(!resultSet.next()) { // Category does not already exist in database - good to go

                preparedStatement.setString(1, category);
                preparedStatement.setString(2, term);
                preparedStatement.setString(3, definition);

                System.out.println(preparedStatement);
                // Step 3: Execute the query or update query
                preparedStatement.executeUpdate();
            }
            else { // Category already exists

                Alert alert = new Alert(Alert.AlertType.NONE);
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("This category already exists!");
                alert.setHeaderText("Please create a new one");
                alert.show();
//                AlertHandler.showAlert(Alert.AlertType.ERROR, Window, "The category already exists!", "Try again");
            }
        } catch (SQLException e) {
            // print SQL exception information
            printSQLException(e);
        }
    }

    public static void printSQLException(SQLException ex) {
        for(Throwable e : ex) {
            if(e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

}