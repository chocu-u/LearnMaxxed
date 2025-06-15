package LearningMaterials.ICT.ANIMATION.Controllers;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import Data.Session;
import Data.Students;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class AnimationChap3Controller {

    @FXML
    private Button ComSysButton1;

    @FXML
    private Button ComSysButton2;

    @FXML
    private Button ComSysButton3;

    @FXML
    private Button dashboardButton;

    @FXML
    private Button logoutButton;

    @FXML
    private JFXButton nextButton;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Button premdashboardButton;

    @FXML
    private JFXButton premnextButton;

    @FXML
    private JFXComboBox<String> subjectComboBox;

    @FXML
    private Label usernameSidePanel;

    @FXML
    public void initialize() {
        Students student = Session.getLoggedInStudent();
        if (student != null) {
            usernameSidePanel.setText(student.getFirstName());
        } else {
            usernameSidePanel.setText("");
        }
        Platform.runLater(() -> scrollPane.setVvalue(0));
        subjectComboBox.getItems().clear();
        subjectComboBox.getItems().addAll("Computer Programming", "Computer Systems", "Animation", "Web Development", "Illustration");
    }

    @FXML
    void comsys1ButtonHandler(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/LearningMaterials/ICT/ANIMATION/FXML/AnimationChap1.fxml"));
            Stage stage = (Stage) ComSysButton1.getScene().getWindow();
            stage.setScene(new Scene(root, 1000, 600));
            stage.setTitle("Computer Systems - Chapter 1");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void comsys2ButtonHandler(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/LearningMaterials/ICT/ANIMATION/FXML/AnimationChap2.fxml"));
            Stage stage = (Stage) ComSysButton2.getScene().getWindow();
            stage.setScene(new Scene(root, 1000, 600));
            stage.setTitle("Computer Systems - Chapter 2");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void comsys3ButtonHandler(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/LearningMaterials/ICT/ANIMATION/FXML/AnimationChap3.fxml"));
            Stage stage = (Stage) ComSysButton3.getScene().getWindow();
            stage.setScene(new Scene(root, 1000, 600));
            stage.setTitle("Computer Systems - Chapter 3");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void dashboardButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent dashboardRoot = FXMLLoader.load(getClass().getResource("/User/FXML/IctDashboard.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(dashboardRoot, 1000, 600));
    }

    @FXML
    public void handleSubjectSelection() {
        String selected = subjectComboBox.getSelectionModel().getSelectedItem();
        try {
            if ("Computer Programming".equals(selected)) {
                Parent root = FXMLLoader.load(getClass().getResource("/LearningMaterials/ICT/COMPUTERPROGRAMMING/FXML/ComProgIntro.fxml"));
                Stage stage = (Stage) subjectComboBox.getScene().getWindow();
                stage.setScene(new Scene(root, 1000, 600));
                stage.setTitle("Computer Programming Introduction");
                stage.show();
            } else if ("Computer Systems".equals(selected)) {
                Parent root = FXMLLoader.load(getClass().getResource("/LearningMaterials/ICT/COMPUTERSYSTEMS/FXML/ComSysIntro.fxml"));
                Stage stage = (Stage) subjectComboBox.getScene().getWindow();
                stage.setScene(new Scene(root, 1000, 600));
                stage.setTitle("Computer Systems Introduction");
                stage.show();
            } else if ("Web Development".equals(selected)) {
                Parent root = FXMLLoader.load(getClass().getResource("/LearningMaterials/ICT/WEBDEVELOPMENT/FXML/WebDevIntro.fxml"));
                Stage stage = (Stage) subjectComboBox.getScene().getWindow();
                stage.setScene(new Scene(root, 1000, 600));
                stage.setTitle("Web Development Introduction");
                stage.show();
            } else if ("Animation".equals(selected)) {
                Parent root = FXMLLoader.load(getClass().getResource("/LearningMaterials/ICT/ANIMATION/FXML/AnimationIntro.fxml"));
                Stage stage = (Stage) subjectComboBox.getScene().getWindow();
                stage.setScene(new Scene(root, 1000, 600));
                stage.setTitle("Animation Introduction");
                stage.show();
            } else if ("Illustration".equals(selected)) {
                Parent root = FXMLLoader.load(getClass().getResource("/LearningMaterials/ICT/ILLUSTRATION/FXML/IllustrationIntro.fxml"));
                Stage stage = (Stage) subjectComboBox.getScene().getWindow();
                stage.setScene(new Scene(root, 1000, 600));
                stage.setTitle("Illustration Introduction");
                stage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void logoutButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent logoutRoot = FXMLLoader.load(getClass().getResource("/Login/FXML/LoginPage.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(logoutRoot, 1000, 600));
    }

    @FXML
    void nextButtonHandler(ActionEvent event) {
        // No next chapter, so do nothing or show a message if needed
    }

    @FXML
    public void premDashboardButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent dashboardRoot = FXMLLoader.load(getClass().getResource("/User/FXML/PremiumDashboard.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(dashboardRoot, 1000, 600));
    }

    @FXML
    public void premnextButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent nextRoot = FXMLLoader.load(getClass().getResource("/LearningMaterials/Premium/AnimationChap1.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(nextRoot, 1000, 600));
    }

    @FXML
    public void premanimation1ButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent animation1Root = FXMLLoader.load(getClass().getResource("/LearningMaterials/Premium/AnimationChap1.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(animation1Root, 1000, 600));
    }

    @FXML
    public void premanimation2ButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent animation2Root = FXMLLoader.load(getClass().getResource("/LearningMaterials/Premium/AnimationChap2.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(animation2Root, 1000, 600));
    }

    @FXML
    public void premanimation3ButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent animation3Root = FXMLLoader.load(getClass().getResource("/LearningMaterials/Premium/AnimationChap3.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(animation3Root, 1000, 600));
    }

    @FXML
    private void finishButtonHandler(javafx.event.ActionEvent event) throws IOException {
        boolean badgeAdded = false;
        boolean alreadyHasBadge = false;
        if (Data.Session.getLoggedInStudent() != null) {
            int userID = Data.Session.getLoggedInStudent().getUserID();
            badgeAdded = Database.DatabaseHandler.addBadgeIfAllowed(userID, "Animation");
            // Check if the user already has the badge
            if (!badgeAdded && Database.DatabaseHandler.hasBadge(userID, "Animation")) {
                alreadyHasBadge = true;
            }
        }

        String fxmlPath;
        if (badgeAdded) {
            fxmlPath = "/User/FXML/Congratulations.fxml";
        } else if (alreadyHasBadge) {
            fxmlPath = "/User/FXML/AlreadyHasBadge.fxml"; 
        } else {
            fxmlPath = "/User/FXML/CongratulationsNoBadge.fxml";
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();

        Stage popupStage = new Stage();
        popupStage.setTitle("Congratulations!");
        popupStage.initStyle(javafx.stage.StageStyle.UNDECORATED);
        popupStage.setScene(new Scene(root, 555, 333));
        popupStage.initOwner(((JFXButton) event.getSource()).getScene().getWindow());
        popupStage.initModality(javafx.stage.Modality.WINDOW_MODAL);
        popupStage.setResizable(false);
        popupStage.showAndWait();
    }

}
