/*
    File: AlertHandler.java
    This class controls how alerts are sent
 */

package termbank.utilities;

import javafx.scene.control.Alert;
import javafx.stage.Window;

public class AlertHandler {

    public static void showAlert(Alert.AlertType alertType, Window window,
                                 String title, String message) {

        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(window);
        alert.show();
    }

}
