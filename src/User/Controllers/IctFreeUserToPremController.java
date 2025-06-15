package User.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import Utils.StageHelper;
import Database.DatabaseHandler;
import javafx.scene.Node;
import Data.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.application.Platform;

public class IctFreeUserToPremController implements Initializable {

    @FXML
    private JFXButton cancelButton;

    @FXML
    private ComboBox<String> paymentCombo;

    @FXML
    private ComboBox<String> subscriptionCombo;

    @FXML
    private JFXButton updateSubscriptionButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadPaymentMethods();
        loadPlanTypes();
    }

    private void loadPaymentMethods() {
        ObservableList<String> paymentMethods = FXCollections.observableArrayList();
        List<String> methods = DatabaseHandler.getPaymentMethods();
        paymentMethods.addAll(methods);
        paymentCombo.setItems(paymentMethods);
    }

    private void loadPlanTypes() {
        ObservableList<String> planTypes = FXCollections.observableArrayList("Subscribed"); 
        subscriptionCombo.setItems(planTypes);
        subscriptionCombo.getSelectionModel().selectFirst(); 
    }

@FXML
private void changetoPremiumButtonHandler(ActionEvent event) {
    String selectedPlan = subscriptionCombo.getValue();
    String selectedPayment = paymentCombo.getValue();

    if (!"Subscribed".equalsIgnoreCase(selectedPlan)) {
        showAlert(Alert.AlertType.ERROR, "Only the 'Subscribed' plan is available.");
        return;
    }

    if (selectedPayment == null || selectedPayment.trim().isEmpty()) {
        showAlert(Alert.AlertType.WARNING, "Please select a payment method.");
        return;
    }

    int userId = Session.getLoggedInStudent().getUserID();
    boolean success = DatabaseHandler.updateUserSubscriptionStatus(userId, "Subscribed", selectedPayment);

    if (success) {
        Session.clearSession();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Subscription Updated");
        alert.setHeaderText(null);
        alert.setContentText("Your account has been upgraded. Please re-login.");
        alert.showAndWait();

        redirectToLogin(event); 
    } else {
        showAlert(Alert.AlertType.ERROR, "Upgrade failed. Please try again.");
    }
}

@FXML
private void redirectToLogin(ActionEvent event) {
    try {
        
        for (Stage stage : StageHelper.getStages()) {
            stage.close();
        }

       
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login/FXML/LoginPage.fxml"));
        Parent root = loader.load();

        
        Stage loginStage = new Stage();
        loginStage.setTitle("Login");
        loginStage.setScene(new Scene(root, 1000, 600));
        loginStage.show();

    } catch (IOException e) {
        e.printStackTrace();
        showAlert(Alert.AlertType.ERROR, "Could not load login page. Please restart the application.");
    }
}

private void showAlert(Alert.AlertType type, String message) {
    Alert alert = new Alert(type);
    alert.setTitle(type.name());
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}
@FXML
    private void handleCancelButton() {
        ((Stage) cancelButton.getScene().getWindow()).close();
    }

}
