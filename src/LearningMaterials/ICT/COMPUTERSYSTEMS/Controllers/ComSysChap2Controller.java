package LearningMaterials.ICT.COMPUTERSYSTEMS.Controllers;

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

public class ComSysChap2Controller {
    @FXML
    private Button ComProg3Button;

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
            Parent root = FXMLLoader.load(getClass().getResource("/LearningMaterials/ICT/COMPUTERSYSTEMS/FXML/ComSysChapter1.fxml"));
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
            Parent root = FXMLLoader.load(getClass().getResource("/LearningMaterials/ICT/COMPUTERSYSTEMS/FXML/ComSysChapter2.fxml"));
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
            Parent root = FXMLLoader.load(getClass().getResource("/LearningMaterials/ICT/COMPUTERSYSTEMS/FXML/ComSysChapter3.fxml"));
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
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/LearningMaterials/ICT/COMPUTERSYSTEMS/FXML/ComSysChapter3.fxml"));
            Stage stage = (Stage) nextButton.getScene().getWindow();
            stage.setScene(new Scene(root, 1000, 600));
            stage.setTitle("Computer Systems - Chapter 2");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void premDashboardButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent dashboardRoot = FXMLLoader.load(getClass().getResource("/User/FXML/PremiumDashboard.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(dashboardRoot, 1000, 600));
    }

    @FXML
    public void premnextButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent nextRoot = FXMLLoader.load(getClass().getResource("/LearningMaterials/Premium/ComSysChapter3.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(nextRoot, 1000, 600));
    }

    @FXML
    public void premcomsysIntroButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent comsys1Root = FXMLLoader.load(getClass().getResource("/LearningMaterials/Premium/ComSysIntro.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(comsys1Root, 1000, 600));
    }

    @FXML
    public void premcomsys1ButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent comsys1Root = FXMLLoader.load(getClass().getResource("/LearningMaterials/Premium/ComSysChapter1.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(comsys1Root, 1000, 600));
    }

    @FXML
    public void premcomsys3ButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent comsys3Root = FXMLLoader.load(getClass().getResource("/LearningMaterials/Premium/ComSysChapter3.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(comsys3Root, 1000, 600));
    }
}

