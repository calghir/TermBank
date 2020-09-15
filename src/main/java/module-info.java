module TermBank {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;

    opens termbank to javafx.fxml, javafx.graphics;
    opens termbank.view;
    exports termbank.view to javafx.fxml;
    exports termbank to javafx.fxml, javafx.graphics;
}