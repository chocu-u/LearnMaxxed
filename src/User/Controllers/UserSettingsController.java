package User.Controllers;

import java.io.IOException;
import java.util.Optional;

import Data.Session;
import Data.Students;
import Database.DatabaseHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

public class UserSettingsController {
    @FXML
    private Label usernameSidePanel;

    @FXML
    private Button logoutButton, emailchangeButton, passchangeButton, nameChangeButton, deleteButton, badgeButton;

    @FXML
    public void initialize() {
        Students student = Session.getLoggedInStudent();
        if (student != null) {
            usernameSidePanel.setText(student.getFirstName());
        } else {
            usernameSidePanel.setText("");
        }
    }

    @FXML
    public void logoutButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent logoutRoot = javafx.fxml.FXMLLoader.load(getClass().getResource("/Login/FXML/LoginPage.fxml"));
        Stage stage;
        if (event != null) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        } else {
            stage = (Stage) logoutButton.getScene().getWindow();
        }
        stage.setScene(new Scene(logoutRoot, 1000, 600));
    }

    @FXML
    public void badgeButtonHandler() {
        Students student = Session.getLoggedInStudent();
        if (student == null) return;

        try {
            Parent badgeRoot = javafx.fxml.FXMLLoader.load(getClass().getResource("/User/FXML/UserBadges.fxml"));
            Stage stage = (Stage) badgeButton.getScene().getWindow();
            stage.setScene(new Scene(badgeRoot, 1000, 600));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void emailchangeButtonHandler() {
        Students student = Session.getLoggedInStudent();
        if (student == null) return;

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Change Email");
        dialog.setHeaderText("Change your email address");
        dialog.setContentText("Enter new email:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newEmail -> {
            if (newEmail.trim().isEmpty()) {
                showAlert("Field is empty", "Email field cannot be empty.");
                return;
            }
            if (!newEmail.matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$")) {
                showAlert("Invalid Email", "Please enter a valid email address.");
                return;
            }
            if (DatabaseHandler.isEmailTaken(newEmail)) {
                showAlert("Email In Use", "This email is already registered to another user.");
                return;
            }
            boolean success = DatabaseHandler.changeEmail(student.getUsername(), newEmail);
            if (success) {
                showAlert("Success", "Email updated successfully!");
            } else {
                showAlert("Error", "Failed to update email.");
            }
        });
    }

    @FXML
    public void passchangeButtonHandler() {
        Students student = Session.getLoggedInStudent();
        if (student == null) return;

        javafx.scene.control.Dialog<String[]> dialog = new javafx.scene.control.Dialog<>();
        dialog.setTitle("Change Password");
        dialog.setHeaderText("Enter your new password");

        javafx.scene.control.ButtonType okButtonType = new javafx.scene.control.ButtonType("OK", javafx.scene.control.ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, javafx.scene.control.ButtonType.CANCEL);

        javafx.scene.control.PasswordField newPasswordField = new javafx.scene.control.PasswordField();
        javafx.scene.control.PasswordField confirmPasswordField = new javafx.scene.control.PasswordField();
        javafx.scene.control.TextField newPasswordTextField = new javafx.scene.control.TextField();
        javafx.scene.control.TextField confirmPasswordTextField = new javafx.scene.control.TextField();

        newPasswordTextField.managedProperty().bind(newPasswordTextField.visibleProperty());
        newPasswordField.managedProperty().bind(newPasswordField.visibleProperty());
        newPasswordTextField.visibleProperty().set(false);
        newPasswordField.visibleProperty().set(true);

        confirmPasswordTextField.managedProperty().bind(confirmPasswordTextField.visibleProperty());
        confirmPasswordField.managedProperty().bind(confirmPasswordField.visibleProperty());
        confirmPasswordTextField.visibleProperty().set(false);
        confirmPasswordField.visibleProperty().set(true);

        newPasswordTextField.textProperty().bindBidirectional(newPasswordField.textProperty());
        confirmPasswordTextField.textProperty().bindBidirectional(confirmPasswordField.textProperty());

        javafx.scene.control.CheckBox showPasswordCheckBox = new javafx.scene.control.CheckBox("Show Passwords");
        showPasswordCheckBox.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
            newPasswordField.setVisible(!isSelected);
            newPasswordTextField.setVisible(isSelected);
            confirmPasswordField.setVisible(!isSelected);
            confirmPasswordTextField.setVisible(isSelected);
        });

        javafx.scene.layout.GridPane grid = new javafx.scene.layout.GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new javafx.scene.control.Label("New Password:"), 0, 0);
        grid.add(newPasswordField, 1, 0);
        grid.add(newPasswordTextField, 1, 0);
        grid.add(new javafx.scene.control.Label("Confirm Password:"), 0, 1);
        grid.add(confirmPasswordField, 1, 1);
        grid.add(confirmPasswordTextField, 1, 1);
        grid.add(showPasswordCheckBox, 1, 2);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                return new String[]{
                    newPasswordField.isVisible() ? newPasswordField.getText() : newPasswordTextField.getText(),
                    confirmPasswordField.isVisible() ? confirmPasswordField.getText() : confirmPasswordTextField.getText()
                };
            }
            return null;
        });

        dialog.showAndWait().ifPresent(passwords -> {
            String newPassword = passwords[0];
            String confirmPassword = passwords[1];

            if (newPassword.trim().isEmpty() || confirmPassword.trim().isEmpty()) {
                showAlert("Field is empty", "Password fields cannot be empty.");
                return;
            }
            if (newPassword.length() < 6) {
                showAlert("Invalid Password", "Password must be at least 6 characters long.");
                return;
            }
            if (!newPassword.equals(confirmPassword)) {
                showAlert("Password Mismatch", "Passwords do not match.");
                return;
            }
            boolean success = DatabaseHandler.changePassword(student.getUsername(), newPassword);
            if (success) {
                showAlert("Success", "Password updated successfully!");
            } else {
                showAlert("Error", "Failed to update password.");
            }
        });
    }

    @FXML
    private void nameChangeButtonHandler() {
        Students student = Session.getLoggedInStudent();
        if (student == null) return;

        javafx.scene.control.Dialog<String[]> dialog = new javafx.scene.control.Dialog<>();
        dialog.setTitle("Change Name");
        dialog.setHeaderText("Change your name");

        javafx.scene.control.ButtonType okButtonType = new javafx.scene.control.ButtonType("OK", javafx.scene.control.ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, javafx.scene.control.ButtonType.CANCEL);

        javafx.scene.control.TextField firstNameField = new javafx.scene.control.TextField(student.getFirstName());
        javafx.scene.control.TextField lastNameField = new javafx.scene.control.TextField(student.getLastName());

        javafx.scene.layout.GridPane grid = new javafx.scene.layout.GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new javafx.scene.control.Label("First Name:"), 0, 0);
        grid.add(firstNameField, 1, 0);
        grid.add(new javafx.scene.control.Label("Last Name:"), 0, 1);
        grid.add(lastNameField, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                return new String[]{firstNameField.getText(), lastNameField.getText()};
            }
            return null;
        });

        dialog.showAndWait().ifPresent(names -> {
            String newFirstName = names[0].trim();
            String newLastName = names[1].trim();

            if (newFirstName.isEmpty() && newLastName.isEmpty()) {
                showAlert("Field is empty", "At least one field must be filled.");
                return;
            }
            if (!newFirstName.isEmpty() && !newFirstName.matches("^[a-zA-Z ]{2,}$")) {
                showAlert("Invalid First Name", "First name must be at least 2 letters and contain only letters and spaces.");
                return;
            }
            if (!newLastName.isEmpty() && !newLastName.matches("^[a-zA-Z]{2,}$")) {
                showAlert("Invalid Last Name", "Last name must be at least 2 letters and contain only letters.");
                return;
            }

            String finalFirstName = newFirstName.isEmpty() ? student.getFirstName() : newFirstName;
            String finalLastName = newLastName.isEmpty() ? student.getLastName() : newLastName;

            boolean success = DatabaseHandler.changeName(student.getUsername(), finalFirstName, finalLastName);
            if (success) {
                showAlert("Success", "Name updated successfully!");
                try {
                    logoutButtonHandler(null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                showAlert("Error", "Failed to update name.");
            }
        });
    }

    @FXML
    private void deleteButtonHandler() {
        Students student = Session.getLoggedInStudent();
        if (student == null) return;

        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
        alert.setTitle("Delete Account");
        alert.setHeaderText("Are you sure you want to delete your account?");
        alert.setContentText("Deleting your account is permanent and cannot be undone. All your data, progress, and settings will be lost. Are you sure you want to continue?");

        javafx.scene.control.ButtonType confirmButton = new javafx.scene.control.ButtonType("Delete", javafx.scene.control.ButtonBar.ButtonData.OK_DONE);
        javafx.scene.control.ButtonType cancelButton = new javafx.scene.control.ButtonType("Cancel", javafx.scene.control.ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(confirmButton, cancelButton);

        javafx.scene.control.CheckBox understandCheckBox = new javafx.scene.control.CheckBox("Yes, I Understand.");
        javafx.scene.layout.VBox vbox = new javafx.scene.layout.VBox();
        vbox.setSpacing(10);
        vbox.getChildren().addAll(new javafx.scene.control.Label(
            "Deleting your account is permanent and cannot be undone.\nAll your data, progress, and settings will be lost.\nAre you sure you want to continue?"),
            understandCheckBox);
        alert.getDialogPane().setContent(vbox);

        javafx.scene.control.Button deleteBtn = (javafx.scene.control.Button) alert.getDialogPane().lookupButton(confirmButton);
        deleteBtn.setDisable(true);
        understandCheckBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
            deleteBtn.setDisable(!newVal);
        });

        Optional<javafx.scene.control.ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == confirmButton && understandCheckBox.isSelected()) {
            boolean success = DatabaseHandler.deleteAccount(student.getUsername());
            if (success) {
                showAlert("Success", "Account deleted successfully!");
                try {
                    logoutButtonHandler(null);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                showAlert("Error", "Failed to delete account.");
            }
        }
    }

    @FXML
    public void backButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Students student = Session.getLoggedInStudent();
        String fxmlPath = "/User/FXML/PremiumDashboard.fxml"; // default

    if (student != null) {
        int subscriptionID = student.getSubscriptionID();
        if (subscriptionID == 1) {
            fxmlPath = "/User/FXML/PremiumDashboard.fxml"; // Subscribed users
        } else if (subscriptionID == 2) {
            // Free users, you can further check strand if needed
            String strand = student.getStrand();
            if ("ICT".equalsIgnoreCase(strand)) {
                fxmlPath = "/User/FXML/IctDashboard.fxml";
            } else if ("STEM".equalsIgnoreCase(strand)) {
                fxmlPath = "/User/FXML/StemDashboard.fxml";
            }
            // Add more strands if needed
        }
    }

    Parent dashboardRoot = javafx.fxml.FXMLLoader.load(getClass().getResource(fxmlPath));
    Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
    stage.setScene(new Scene(dashboardRoot, 1000, 600));
}

    private void showAlert(String title, String content) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}





