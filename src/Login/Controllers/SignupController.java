package Login.Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import Data.Students;
import Database.DatabaseHandler;
import Utils.InputValidator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SignupController implements Initializable {

    @FXML
    private JFXButton signupButton;

    @FXML
    private Button backButton;

    @FXML
    private TextField usernameField, emailField, fnameField, lnameField, passwordField, passwordText;

    @FXML
    private ComboBox<String> strandCombo, paymentCombo, planTypeCombo;

    @FXML
    private CheckBox checkBox;

    @FXML
    private ImageView eyeIcon;

    private Image eyeoff, eyeon;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadStrands();
        loadPaymentMethods();
        loadPlanTypes();

        eyeoff = new Image("file:Resources/Icons/eyeoff.png");
        eyeon = new Image("file:Resources/Icons/eyeon.png");

        if (eyeIcon != null && eyeoff != null) {
            eyeIcon.setImage(eyeoff);
        }

        planTypeCombo.valueProperty().addListener((obs, oldVal, newVal) -> {
            if ("Free".equalsIgnoreCase(newVal)) {
                paymentCombo.setDisable(true);
                paymentCombo.getSelectionModel().clearSelection();
            } else {
                paymentCombo.setDisable(false);
            }
        });

        if ("Free".equalsIgnoreCase(planTypeCombo.getValue())) {
            paymentCombo.setDisable(true);
        } else {
            paymentCombo.setDisable(false);
        }
    }

    private void loadStrands() {
        ObservableList<String> strands = FXCollections.observableArrayList();
        strands.addAll(DatabaseHandler.getStrands());
        strandCombo.setItems(strands);
    }

    private void loadPaymentMethods() {
        ObservableList<String> paymentMethods = FXCollections.observableArrayList();
        paymentMethods.addAll(DatabaseHandler.getPaymentMethods());
        paymentCombo.setItems(paymentMethods);
    }

    private void loadPlanTypes() {
        ObservableList<String> planTypes = FXCollections.observableArrayList();
        planTypes.addAll(DatabaseHandler.getSubscriptionTypes());
        planTypeCombo.setItems(planTypes);
    }

    private void showAlert(AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void handlebackButton() {
        try {
            Stage stage = (Stage) backButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/Login/FXML/LoginPage.fxml"));
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Unable to go back to login screen.");
        }
    }

    @FXML
    private void changeVisibility() {
        if (checkBox.isSelected()) {
            passwordText.setText(passwordField.getText());
            passwordText.setVisible(true);
            passwordText.setManaged(true);
            passwordField.setVisible(false);
            passwordField.setManaged(false);
            passwordText.requestFocus();
            passwordText.positionCaret(passwordText.getText().length());

            if (eyeIcon != null && eyeon != null) {
                eyeIcon.setImage(eyeon);
            }
        } else {
            passwordField.setText(passwordText.getText());
            passwordField.setVisible(true);
            passwordField.setManaged(true);
            passwordText.setVisible(false);
            passwordText.setManaged(false);
            passwordField.requestFocus();
            passwordField.positionCaret(passwordField.getText().length());

            if (eyeIcon != null && eyeoff != null) {
                eyeIcon.setImage(eyeoff);
            }
        }
    }

    @FXML
    private void handleSignupButton() {
        String username = usernameField.getText().trim();
        String fname = fnameField.getText().trim();
        String lname = lnameField.getText().trim();
        String email = emailField.getText().trim();
        String password = passwordField.isVisible() ? passwordField.getText().trim() : passwordText.getText().trim();
        String strand = strandCombo.getValue() != null ? strandCombo.getValue().trim() : "";
        String paymentMethod = paymentCombo.getValue() != null ? paymentCombo.getValue().trim() : "";
        String planType = planTypeCombo.getValue() != null ? planTypeCombo.getValue().trim() : "";

        if (username.isEmpty() || fname.isEmpty() || lname.isEmpty() || email.isEmpty() || password.isEmpty() || strand.isEmpty() || planType.isEmpty()) {
            showAlert(AlertType.ERROR, "Please fill in all fields.");
            return;
        }

        // Plan type logic
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
            showAlert(AlertType.INFORMATION, "Account created successfully!");
            try {
                Stage stage = (Stage) signupButton.getScene().getWindow();
                Parent root = FXMLLoader.load(getClass().getResource("/Login/FXML/LoginPage.fxml"));
                stage.setScene(new Scene(root));
            } catch (Exception e) {
                showAlert(AlertType.ERROR, "Unable to redirect to login screen.");
            }
        } else {
            showAlert(AlertType.ERROR, "Failed to create account. Please try again.");
        }
    }
}
