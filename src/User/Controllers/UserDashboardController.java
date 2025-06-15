package User.Controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import Data.Session;
import Data.Students;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class UserDashboardController {

    @FXML
    private Button logoutButton, badgesButton, settingsButton, backButton;

    @FXML
    private Label date;

    @FXML
    private Label usernameSidePanel;

    @FXML
    private Label welcomebackUsername;

    @FXML
    public void initialize() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd yyyy");
        date.setText(LocalDate.now().format(formatter));

        Students student = Session.getLoggedInStudent();
        if (student != null) {
            usernameSidePanel.setText(student.getFirstName());
            welcomebackUsername.setText("Welcome back, " + student.getFirstName() + "!");
        } else {
            welcomebackUsername.setText("Welcome back!");
            usernameSidePanel.setText("");
        }
    }

    @FXML
    public void logoutButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent logoutRoot = javafx.fxml.FXMLLoader.load(getClass().getResource("/Login/FXML/LoginPage.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(logoutRoot, 1000, 600));
    }

    @FXML
    public void badgesButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent badgesRoot = javafx.fxml.FXMLLoader.load(getClass().getResource("/User/FXML/UserBadges.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(badgesRoot, 1000, 600));
    }

    @FXML
    public void settingsButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent settingsRoot = javafx.fxml.FXMLLoader.load(getClass().getResource("/User/FXML/UserSettings.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(settingsRoot, 1000, 600));
    }

    @FXML
    public void backButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Students student = Session.getLoggedInStudent();
        String fxmlPath = "/User/FXML/PremiumDashboard.fxml";

        if (student != null) {
            int subscriptionID = student.getSubscriptionID();
            if (subscriptionID == 1) {
                fxmlPath = "/User/FXML/PremiumDashboard.fxml"; // Subscribed users
            } else if (subscriptionID == 2) {
                // Free users
                String strand = student.getStrand();
                if ("ICT".equalsIgnoreCase(strand)) {
                    fxmlPath = "/User/FXML/IctDashboard.fxml";
                } else if ("STEM".equalsIgnoreCase(strand)) {
                    fxmlPath = "/User/FXML/StemDashboard.fxml";
                }
            }
        }

        Parent dashboardRoot = javafx.fxml.FXMLLoader.load(getClass().getResource(fxmlPath));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(dashboardRoot, 1000, 600));
    }

}
