package Admin.Controllers.AdminCRUD;

import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.CustomTextField;
import com.jfoenix.controls.JFXButton;

import Admin.Controllers.AddAdminController;
import Data.Admin;
import Database.DatabaseHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AdminUpdateController implements Initializable {

    private AddAdminController parentController;
    private Admin selectedAdmin;

    @FXML
    private CustomTextField adminUpdatefield;

    @FXML
    private CustomTextField adminPasswordUpdatefield;

    @FXML
    private JFXButton cancelButton;

    @FXML
    private JFXButton updateAdminButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }

    public void setParentController(AddAdminController controller) {
        this.parentController = controller;
    }

    public void setAdminToUpdate(Admin admin) {
        this.selectedAdmin = admin;
        if (admin != null && adminUpdatefield != null && adminPasswordUpdatefield != null) {
            adminUpdatefield.setText(admin.getAdminUserName());
            adminPasswordUpdatefield.setText(admin.getAdminPassword());
        }
    }

    private void showAlert(AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handleCancelButton() {
        ((Stage) cancelButton.getScene().getWindow()).close();
    }

    @FXML
    private void UpdateAdminButtonHandler() {
        if (selectedAdmin == null) {
            showAlert(AlertType.ERROR, "No admin selected for update.");
            return;
        }
        String username = adminUpdatefield != null ? adminUpdatefield.getText().trim() : "";
        String password = adminPasswordUpdatefield != null ? adminPasswordUpdatefield.getText().trim() : "";

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(AlertType.ERROR, "Please fill in all fields.");
            return;
        }

        if (!username.equals(selectedAdmin.getAdminUserName()) && DatabaseHandler.isAdminUsernameTaken(username)) {
            showAlert(AlertType.ERROR, "Username already exists. Please choose another.");
            return;
        }

        Admin updatedAdmin = new Admin(selectedAdmin.getAdminID(), username, password);
        boolean success = DatabaseHandler.updateAdmin(updatedAdmin);

        if (success) {
            showAlert(AlertType.INFORMATION, "Admin account updated successfully!");
            ((Stage) updateAdminButton.getScene().getWindow()).close();
            if (parentController != null) {
                parentController.displayAdmins(); 
            }
        } else {
            showAlert(AlertType.ERROR, "Failed to update admin account. Please try again.");
        }
    }
}