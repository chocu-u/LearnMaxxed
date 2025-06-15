package Admin.Controllers.AdminCRUD;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import org.controlsfx.control.textfield.CustomTextField;

import Admin.Controllers.AddAdminController;
import Data.Admin;
import Database.DatabaseHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AdminCreateController implements Initializable {

    private AddAdminController parentController;

    @FXML
    private CustomTextField adminCreatefield;

    @FXML
    private CustomTextField adminPasswordfield;

    @FXML
    private JFXButton cancelButton;

    @FXML
    private JFXButton createAccountButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setParentController(AddAdminController parentController) {
        this.parentController = parentController;
    }

    @FXML
    private void handleCancelButton() {
        ((Stage) cancelButton.getScene().getWindow()).close();
    }

    @FXML
    private void handleCreateAccountButton() {
        String username = adminCreatefield.getText().trim();
        String password = adminPasswordfield.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            showAlert(AlertType.ERROR, "Please fill in all fields.");
            return;
        }

        Admin admin = new Admin(0, username, password);
        boolean success = DatabaseHandler.createAdmin(admin);

        if (success) {
            showAlert(AlertType.INFORMATION, "Admin account created successfully!");
            ((Stage) createAccountButton.getScene().getWindow()).close();
            if (parentController != null) {
                parentController.displayAdmins();
            }
        } else {
            showAlert(AlertType.ERROR, "Failed to create admin account. Please try again.");
        }
    }

    private void showAlert(AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setContentText(message);
        alert.showAndWait();
    }
}