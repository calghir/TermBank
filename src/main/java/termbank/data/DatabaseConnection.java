package termbank.data;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import termbank.model.DataBank;

import java.sql.*;

public class DatabaseConnection implements DAO {

    private static Connection conn = null;
//    public static final String DATABASE_NAME = "jdbc:sqlite:/home/risbah/IdeaProjects/TermBank/src/main/resources/db/termbank.db";

    public Connection getDatabaseConnection() {
        return this.conn;
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

}