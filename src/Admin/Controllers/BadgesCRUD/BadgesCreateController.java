package Admin.Controllers.BadgesCRUD;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

import Database.DatabaseHandler;

public class BadgesCreateController implements Initializable {

    @FXML
    private JFXButton addbadgeButton;

    @FXML
    private ComboBox<String> badgescombo;

    @FXML
    private JFXButton cancelButton;

    private final HashMap<String, Integer> badgeMap = new HashMap<>();

    private int selectedUserID;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadBadges();
    }

    public void setSelectedUserID(int userID) {
        this.selectedUserID = userID;
    }

    private void loadBadges() {
        try {
            ResultSet rs = DatabaseHandler.getAllBadges();
            if (rs == null) {
                showAlert(AlertType.ERROR, "Failed to connect to the database.");
                return;
            }
            while (rs.next()) {
                int badgeID = rs.getInt("BadgeID");
                String badgeName = rs.getString("BadgeName");
                badgeMap.put(badgeName, badgeID);
                badgescombo.getItems().add(badgeName);
            }
        } catch (SQLException e) {
            showAlert(AlertType.ERROR, "Failed to load badges.");
        }
    }

    @FXML
    private void handleAddBadgeButton() {
        String selectedBadgeName = badgescombo.getValue();
        if (selectedBadgeName == null) {
            showAlert(AlertType.ERROR, "Please select a badge.");
            return;
        }
        Integer badgeID = badgeMap.get(selectedBadgeName);
        if (badgeID == null) {
            showAlert(AlertType.ERROR, "Invalid badge selection.");
            return;
        }

        boolean success = DatabaseHandler.awardBadgeToUser(selectedUserID, badgeID);
        if (success) {
            showAlert(AlertType.INFORMATION, "Badge awarded successfully!");
            closePopup();
        } else {
            showAlert(AlertType.ERROR, "Failed to award badge. Check badge limits or duplicates.");
        }
    }

    @FXML
    private void handleCancelButton() {
        closePopup();
    }

    private void closePopup() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private void showAlert(AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
