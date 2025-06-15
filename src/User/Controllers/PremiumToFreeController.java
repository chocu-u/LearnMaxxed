package User.Controllers;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import Database.DatabaseHandler;
import Data.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class PremiumToFreeController {

    @FXML
    private JFXButton cancelButton;

    @FXML
    private ComboBox<String> subscriptionCombo;

    @FXML
    private JFXButton updateSubscriptionButton;

    @FXML
    void initialize() {
        subscriptionCombo.getItems().add("Free");
        subscriptionCombo.getSelectionModel().selectFirst();
    }

    @FXML
    void changetoFreeButtonHandler(ActionEvent event) {
        String selectedPlan = subscriptionCombo.getValue();

        if (!"Free".equalsIgnoreCase(selectedPlan)) {
            showAlert(AlertType.WARNING, "Please select the Free plan to continue.");
            return;
        }

        int userId = Session.getLoggedInStudent().getUserID();

        boolean success = DatabaseHandler.updateUserSubscriptionStatus(userId, "Free");

        if (success) {
            Session.clearSession();

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Subscription Cancellation");
            alert.setHeaderText(null);
            alert.setContentText("Your subscription has been changed to Free. Please Re-login.");
            alert.showAndWait();

            redirectToLogin();
        } else {
            showAlert(AlertType.ERROR, "Downgrade failed. Please try again.");
        }
    }

    private void redirectToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login/FXML/LoginPage.fxml"));
            Parent root = loader.load();

            // Close all open stages
            for (Stage stage : Utils.StageHelper.getStages()) {
                stage.close();
            }

            Stage loginStage = new Stage();
            loginStage.setScene(new Scene(root, 1000, 600));
            loginStage.setTitle("Login");
            loginStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Failed to load login page.");
        }
    }

    @FXML
    private void handleCancelButton() {
        ((Stage) cancelButton.getScene().getWindow()).close();
    }

    private void showAlert(AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(type.name());
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
