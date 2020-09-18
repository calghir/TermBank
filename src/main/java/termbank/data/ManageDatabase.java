package termbank.data;

import com.opencsv.exceptions.CsvValidationException;
import termbank.utils.FileUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.opencsv.CSVReader;
import java.util.Properties;

public class ManageDatabase {
    private static Properties properties;

    public ManageDatabase(String filename) {
        ManageDatabase.properties = FileUtils.loadPropertyFile(filename);
    }

    // todo - test out method - Risbah
    public static void initDatabase(Connection dbConnection){
        String scriptFilePath = ManageDatabase.properties.getProperty("db.dummydata");
        String insertQuery = "INSERT INTO databanktable VALUES (?,?,?)";

        try(dbConnection; CSVReader reader = new CSVReader(new BufferedReader(new FileReader(FileUtils.fileFromRelativePath(scriptFilePath))))){
            PreparedStatement statement = dbConnection.prepareStatement(insertQuery);
            String[] line = null;
            reader.readNext(); // skip past the csv header row
            while((line = reader.readNext()) != null){
                statement.setString(1, line[0]);
                statement.setString(2, line[1]);
                statement.setString(3, line[2]);
                statement.addBatch();
            }
            statement.executeBatch();
            System.out.println("Database initialize with data from " + scriptFilePath);
        } catch (SQLException | IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }

}

