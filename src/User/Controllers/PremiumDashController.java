package User.Controllers;

import Data.Session;
import Data.Students;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PremiumDashController {

    @FXML
    private JFXButton AnimationButton, ComProgButton, ComSysButton, IllustrationButton, WebDevButton, BasicCalButton, preCalButton, BiologyButton, ChemistryButton, PhysicsButton;

    @FXML
    private Label date;

    @FXML
    private Label usernameSidePanel;

    @FXML
    private Label welcomebackUsername;

    @FXML
    private Button logoutButton;

     @FXML
    private Button cancelButton;

    @FXML
    private JFXComboBox<String> ictSubjectComboBox;

    @FXML
    private JFXComboBox<String> stemSubjectComboBox;

    @FXML
    private Button changetoFreeButton;

    public void displayName(String name) {
        usernameSidePanel.setText(name);
    }

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

        stemSubjectComboBox.getItems().clear();
        stemSubjectComboBox.getItems().addAll("Chemistry", "Physics", "Biology", "Pre Calculus", "Basic Calculus");

        ictSubjectComboBox.getItems().clear();
        ictSubjectComboBox.getItems().addAll("Computer Programming", "Computer Systems", "Web Development", "Animation", "Illustration");

    }

    @FXML
    public void handleStemSubjectSelection() {
        String selected = stemSubjectComboBox.getSelectionModel().getSelectedItem();
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        try {
            if ("Chemistry".equals(selected)) {
                Parent chemistryRoot = FXMLLoader.load(getClass().getResource("/LearningMaterials/Premium/ChemChapter1.fxml"));
                stage.setScene(new Scene(chemistryRoot, 1000, 600));
            } else if ("Physics".equals(selected)) {
                Parent physicsRoot = FXMLLoader.load(getClass().getResource("/LearningMaterials/Premium/PhysicsIntro.fxml"));
                stage.setScene(new Scene(physicsRoot, 1000, 600));
            } else if ("Biology".equals(selected)) {
                Parent biologyRoot = FXMLLoader.load(getClass().getResource("/LearningMaterials/Premium/BioIntro.fxml"));
                stage.setScene(new Scene(biologyRoot, 1000, 600));
            }else if ("Pre Calculus".equals(selected)) {
                Parent biologyRoot = FXMLLoader.load(getClass().getResource("/LearningMaterials/Premium/PCalIntro.fxml"));
                stage.setScene(new Scene(biologyRoot, 1000, 600));
            }else if ("Basic Calculus".equals(selected)) {
                Parent biologyRoot = FXMLLoader.load(getClass().getResource("/LearningMaterials/Premium/BasicCalIntro.fxml"));
                stage.setScene(new Scene(biologyRoot, 1000, 600));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleIctSubjectSelection() {
        String selected = ictSubjectComboBox.getSelectionModel().getSelectedItem();
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        try {
            if ("Computer Programming".equals(selected)) {
                Parent root = FXMLLoader.load(getClass().getResource("/LearningMaterials/Premium/ComProgIntro.fxml"));
                stage.setScene(new Scene(root, 1000, 600));
            } else if ("Computer Systems".equals(selected)) {
                Parent root = FXMLLoader.load(getClass().getResource("/LearningMaterials/Premium/ComSysIntro.fxml"));
                stage.setScene(new Scene(root, 1000, 600));
            } else if ("Web Development".equals(selected)) {
                Parent root = FXMLLoader.load(getClass().getResource("/LearningMaterials/Premium/WebDevIntro.fxml"));
                stage.setScene(new Scene(root, 1000, 600));
            } else if ("Animation".equals(selected)) {
                Parent root = FXMLLoader.load(getClass().getResource("/LearningMaterials/Premium/AnimationIntro.fxml"));
                stage.setScene(new Scene(root, 1000, 600));
            } else if ("Illustration".equals(selected)) {
                Parent root = FXMLLoader.load(getClass().getResource("/LearningMaterials/Premium/IllustrationIntro.fxml"));
                stage.setScene(new Scene(root, 1000, 600));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void chemistryButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent chemistryRoot = javafx.fxml.FXMLLoader.load(getClass().getResource("/LearningMaterials/Premium/ChemChapter1.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(chemistryRoot, 1000, 600));
    }

    @FXML
    public void physicsButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent physicsRoot = javafx.fxml.FXMLLoader.load(getClass().getResource("/LearningMaterials/Premium/PhysicsIntro.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(physicsRoot, 1000, 600));
    }

    @FXML
    public void biologyButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent physicsRoot = javafx.fxml.FXMLLoader.load(getClass().getResource("/LearningMaterials/Premium/BioIntro.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(physicsRoot, 1000, 600));
    }

    @FXML
    public void precalButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent physicsRoot = javafx.fxml.FXMLLoader.load(getClass().getResource("/LearningMaterials/Premium/PCalIntro.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(physicsRoot, 1000, 600));
    }

    @FXML
    public void basicCalButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent physicsRoot = javafx.fxml.FXMLLoader.load(getClass().getResource("/LearningMaterials/Premium/BasicCalIntro.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(physicsRoot, 1000, 600));
    }

    @FXML
    public void ComProgButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/LearningMaterials/Premium/ComProgIntro.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 1000, 600));
    }

    @FXML
    public void ComSysButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/LearningMaterials/Premium/ComSysIntro.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 1000, 600));
    }

    @FXML
    public void AnimationButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/LearningMaterials/Premium/AnimationIntro.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 1000, 600));
    }

    @FXML
    public void WebDevButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/LearningMaterials/Premium/WebDevIntro.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 1000, 600));
    }

    @FXML
    public void illustrationButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/LearningMaterials/Premium/IllustrationIntro.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 1000, 600));
    }


    @FXML
    public void logoutButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent logoutRoot = javafx.fxml.FXMLLoader.load(getClass().getResource("/Login/FXML/LoginPage.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(logoutRoot, 1000, 600));
    }

    @FXML
    public void changetoFreeButtonHandler(javafx.event.ActionEvent event) {
    try {
        Parent root = FXMLLoader.load(getClass().getResource("/User/FXML/PremiumToFreePopup.fxml"));
        Stage popupStage = new Stage();
        popupStage.setTitle("Back To Free Version?");
        popupStage.initStyle(javafx.stage.StageStyle.UNDECORATED);
        popupStage.setScene(new Scene(root));
        popupStage.initOwner(changetoFreeButton.getScene().getWindow());
        popupStage.showAndWait();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    @FXML
    public void userdashboardButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent userDashboardRoot = FXMLLoader.load(getClass().getResource("/User/FXML/UserDashboard.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(userDashboardRoot, 1000, 600));
    }
    
    @FXML
    private void handleCancelButton() {
        ((Stage) cancelButton.getScene().getWindow()).close();
    }
}

