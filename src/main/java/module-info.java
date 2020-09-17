module TermBank {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;
    requires java.sql;

    opens termbank to javafx.fxml, javafx.graphics;
    opens termbank.view;
    opens termbank.model to javafx.base;

    exports termbank.view to javafx.fxml;
    exports termbank to javafx.fxml, javafx.graphics;

}