package User.Controllers;

import java.io.IOException;

import Data.Session;
import Data.Students;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class UserBadgesController {
    @FXML
    private Label usernameSidePanel;

    @FXML
    private Button logoutButton;

    @FXML
    private ImageView basiccalculus, biology, chemistry, physics, precalculus, animation, computerprog, computersys, illustration, webdev;

    public void setBasicCalculusBadge() {
        Image badge = new Image(getClass().getResourceAsStream("/Badges/STEM/BasicCalculus.png"));
        basiccalculus.setImage(badge);
    }
    public void setBiologyBadge() {
        Image badge = new Image(getClass().getResourceAsStream("/Badges/STEM/Biology.png"));
        biology.setImage(badge);
    }
    public void setChemistryBadge() {
        Image badge = new Image(getClass().getResourceAsStream("/Badges/STEM/Chemistry.png"));
        chemistry.setImage(badge);
    }
    public void setPhysicsBadge() {
        Image badge = new Image(getClass().getResourceAsStream("/Badges/STEM/Physics.png"));
        physics.setImage(badge);
    }
    public void setPreCalculusBadge() {
        Image badge = new Image(getClass().getResourceAsStream("/Badges/STEM/PreCalculus.png"));
        precalculus.setImage(badge);
    }

    public void setAnimationBadge() {
        Image badge = new Image(getClass().getResourceAsStream("/Badges/ICT/Animation.png"));
        animation.setImage(badge);
    }
    public void setComputerProgrammingBadge() {
        Image badge = new Image(getClass().getResourceAsStream("/Badges/ICT/ComputerProgramming.png"));
        computerprog.setImage(badge);
    }
    public void setComputerSystemsBadge() {
        Image badge = new Image(getClass().getResourceAsStream("/Badges/ICT/ComputerSystems.png"));
        computersys.setImage(badge);
    }
    public void setIllustrationBadge() {
        Image badge = new Image(getClass().getResourceAsStream("/Badges/ICT/Illustration.png"));
        illustration.setImage(badge);
    }
    public void setWebDevelopmentBadge() {
        Image badge = new Image(getClass().getResourceAsStream("/Badges/ICT/WebDevelopment.png"));
        webdev.setImage(badge);
    }

    @FXML
    public void initialize() {
        Students student = Session.getLoggedInStudent();
        if (student != null) {
            usernameSidePanel.setText(student.getFirstName());
        } else {
            usernameSidePanel.setText("");
        }

        if (Data.Session.getLoggedInStudent() != null) {
            int userID = Data.Session.getLoggedInStudent().getUserID();
            if (Database.DatabaseHandler.hasBadge(userID, "Basic Calculus")) {
                setBasicCalculusBadge();
            }
            if (Database.DatabaseHandler.hasBadge(userID, "Biology")) {
                setBiologyBadge();
            }
            if (Database.DatabaseHandler.hasBadge(userID, "Chemistry")) {
                setChemistryBadge();
            }
            if (Database.DatabaseHandler.hasBadge(userID, "Physics")) {
                setPhysicsBadge();
            }
            if (Database.DatabaseHandler.hasBadge(userID, "Pre-Calculus")) {
                setPreCalculusBadge();
            }
            if (Database.DatabaseHandler.hasBadge(userID, "Animation")) {
                setAnimationBadge();
            }
            if (Database.DatabaseHandler.hasBadge(userID, "Computer Programming")) {
                setComputerProgrammingBadge();
            }
            if (Database.DatabaseHandler.hasBadge(userID, "Computer Systems")) {
                setComputerSystemsBadge();
            }
            if (Database.DatabaseHandler.hasBadge(userID, "Illustration")) {
                setIllustrationBadge();
            }
            if (Database.DatabaseHandler.hasBadge(userID, "Web Development")) {
                setWebDevelopmentBadge();
            }
        }
    }

    @FXML
    public void logoutButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent logoutRoot = javafx.fxml.FXMLLoader.load(getClass().getResource("/Login/FXML/LoginPage.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(logoutRoot, 1000, 600));
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
