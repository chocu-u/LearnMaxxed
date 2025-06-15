package Admin.Controllers.StudentsCRUD;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.CustomTextField;
import com.jfoenix.controls.JFXButton;

import Admin.Controllers.AdminStudentsController;
import Admin.Controllers.AdminStudentsICTController;
import Admin.Controllers.AdminStudentsSTEMController;
import Data.Students;
import Database.DatabaseHandler;
import Utils.InputValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

public class StudentsCreateController implements Initializable {

    private AdminStudentsController parentController;
    private AdminStudentsICTController parentICTController;
    private AdminStudentsSTEMController parentSTEMController;

    @FXML
    private JFXButton cancelButton, createAccountButton;

    @FXML
    private CustomTextField Createfield, EmailField, FnameField, LnameField, Passwordfield;

    @FXML
    private ComboBox<String> subscriptionCombo, strandCombo, paymentCombo;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadPaymentMethods();
        loadStrands();
        loadPlanTypes();

        subscriptionCombo.valueProperty().addListener((obs, oldVal, newVal) -> {
            if ("Free".equalsIgnoreCase(newVal)) {
                paymentCombo.setDisable(true);
                paymentCombo.getSelectionModel().clearSelection();
            } else {
                paymentCombo.setDisable(false);
            }
        });
    }

    private void loadPaymentMethods() {
        ObservableList<String> paymentMethods = FXCollections.observableArrayList();
        List<String> methods = DatabaseHandler.getPaymentMethods();
        paymentMethods.addAll(methods);
        paymentCombo.setItems(paymentMethods);
    }

    private void loadStrands() {
        ObservableList<String> strands = FXCollections.observableArrayList();
        List<String> strandList = DatabaseHandler.getStrands();
        strands.addAll(strandList);
        strandCombo.setItems(strands);
    }

    private void loadPlanTypes() {
        ObservableList<String> planTypes = FXCollections.observableArrayList();
        List<String> types = DatabaseHandler.getSubscriptionTypes();
        planTypes.addAll(types);
        subscriptionCombo.setItems(planTypes);
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
    private void handleCreateAccountButton() {
        String username = Createfield.getText().trim();
        String fname = FnameField.getText().trim();
        String lname = LnameField.getText().trim();
        String email = EmailField.getText().trim();
        String password = Passwordfield.getText().trim();
        String paymentMethod = paymentCombo.getValue() != null ? paymentCombo.getValue().trim() : "";
        String planType = subscriptionCombo.getValue() != null ? subscriptionCombo.getValue().trim() : "";
        String strand = strandCombo.getValue() != null ? strandCombo.getValue().trim() : "";

        if (username.isEmpty() || fname.isEmpty() || lname.isEmpty() || email.isEmpty() || password.isEmpty() || planType.isEmpty() || strand.isEmpty()) {
            showAlert(AlertType.ERROR, "Please fill in all fields.");
            return;
        }

        int paymentID = -1;
        if (planType.equalsIgnoreCase("Free")) {
            if (!paymentMethod.isEmpty()) {
                showAlert(AlertType.ERROR, "You have chosen Free Plan Type. Payment method should not be selected.");
                paymentCombo.getSelectionModel().clearSelection();
                loadPaymentMethods();
                return;
            }
            paymentID = -1;
        } else {
            if (paymentMethod.isEmpty()) {
                showAlert(AlertType.ERROR, "Please select a payment method for paid plans.");
                return;
            }
            paymentID = DatabaseHandler.getPaymentIDByMethod(paymentMethod);
            if (paymentID == -1) {
                showAlert(AlertType.ERROR, "Invalid payment method selected.");
                return;
            }
        }

        if (DatabaseHandler.isUsernameTaken(username)) {
            showAlert(AlertType.ERROR, "Username already exists. Please choose another.");
            return;
        }
        if (DatabaseHandler.isEmailTaken(email)) {
            showAlert(AlertType.ERROR, "Email already exists. Please use another.");
            return;
        }

        String validationError = InputValidator.validateStudentFields(username, fname, lname, email, password);
        if (validationError != null) {
            showAlert(AlertType.ERROR, validationError);
            return;
        }

        int subscriptionID = DatabaseHandler.getSubscriptionIDByPlanType(planType);
        String strandID = DatabaseHandler.getStrandIDByName(strand);

        Students student = new Students(0, fname, lname, email, username, password, strandID, subscriptionID, paymentID, "");

        boolean success = DatabaseHandler.createStudent(student);

        if (success) {
            showAlert(AlertType.INFORMATION, "Student account created successfully!");
            ((Stage) createAccountButton.getScene().getWindow()).close();
            if (parentController != null) {
                parentController.displayStudents();
            }
            if (parentICTController != null) {
                parentICTController.displayStudents();
            }
            if (parentSTEMController != null) {
                parentSTEMController.displayStudents();
            }
        } else {
            showAlert(AlertType.ERROR, "Failed to create student account. Please try again.");
        }
    }

    public void setParentController(AdminStudentsController controller) {
        this.parentController = controller;
    }

    public void setParentController(AdminStudentsICTController controller) {
        this.parentICTController = controller;
    }

    public void setParentController(AdminStudentsSTEMController controller) {
        this.parentSTEMController = controller;
    }

    
}