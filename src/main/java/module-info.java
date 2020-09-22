module TermBank {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires java.sql;
    requires opencsv;

    opens termbank to javafx.fxml, javafx.graphics;
    opens termbank.controller;
    opens termbank.view;
    opens termbank.model to javafx.base;

    exports termbank.controller to javafx.fxml;
    exports termbank to javafx.fxml, javafx.graphics;

}