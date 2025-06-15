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

public class StemDashController {

    @FXML
    private Label date;

    @FXML
    private Label usernameSidePanel;

    @FXML
    private Label welcomebackUsername;

    @FXML
    private JFXButton BiologyButton;

    @FXML
    private JFXButton ChemistryButton;

    @FXML
    private JFXButton PhysicsButton;

    @FXML
    private JFXComboBox<String> subjectComboBox;
    
    @FXML
    private Button logoutButton;

    @FXML
    private Button changetoPremiumButton, userdashboardbutton;

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

        subjectComboBox.getItems().clear();
        subjectComboBox.getItems().addAll("Chemistry", "Physics", "Biology", "Pre Calculus", "Basic Calculus");
    }

    @FXML
    public void logoutButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent logoutRoot = FXMLLoader.load(getClass().getResource("/Login/FXML/LoginPage.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(logoutRoot, 1000, 600));
    }

    @FXML
    public void chemistryButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent chemistryRoot = FXMLLoader.load(getClass().getResource("/LearningMaterials/STEM/Chemistry/FXML/ChemChapter1.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(chemistryRoot, 1000, 600));
    }

    @FXML
    public void physicsButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent physicsRoot = FXMLLoader.load(getClass().getResource("/LearningMaterials/STEM/Physics/FXML/PhysicsIntro.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(physicsRoot, 1000, 600));
    }

    @FXML
    public void biologyButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent biologyRoot = FXMLLoader.load(getClass().getResource("/LearningMaterials/STEM/Biology/FXML/BioIntro.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(biologyRoot, 1000, 600));
    }

    @FXML
    public void precalButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent precalRoot = FXMLLoader.load(getClass().getResource("/LearningMaterials/STEM/Precal/FXML/PCalIntro.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(precalRoot, 1000, 600));
    }

    @FXML
    public void basicCalButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent basicCalRoot = FXMLLoader.load(getClass().getResource("/LearningMaterials/STEM/Basiccal/FXML/BasicCalIntro.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(basicCalRoot, 1000, 600));
    }

    @FXML
    public void handleSubjectSelection() {
        String selected = subjectComboBox.getSelectionModel().getSelectedItem();
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        try {
            if ("Chemistry".equals(selected)) {
                Parent chemistryRoot = FXMLLoader.load(getClass().getResource("/LearningMaterials/STEM/Chemistry/FXML/ChemChapter1.fxml"));
                stage.setScene(new Scene(chemistryRoot, 1000, 600));
            } else if ("Physics".equals(selected)) {
                Parent physicsRoot = FXMLLoader.load(getClass().getResource("/LearningMaterials/STEM/Physics/FXML/PhysicsIntro.fxml"));
                stage.setScene(new Scene(physicsRoot, 1000, 600));
            } else if ("Biology".equals(selected)) {
                Parent biologyRoot = FXMLLoader.load(getClass().getResource("/LearningMaterials/STEM/Biology/FXML/BioIntro.fxml"));
                stage.setScene(new Scene(biologyRoot, 1000, 600));
            }else if ("Pre Calculus".equals(selected)) {
                Parent biologyRoot = FXMLLoader.load(getClass().getResource("/LearningMaterials/STEM/Precal/FXML/PCalIntro.fxml"));
                stage.setScene(new Scene(biologyRoot, 1000, 600));
            }else if ("Basic Calculus".equals(selected)) {
                Parent biologyRoot = FXMLLoader.load(getClass().getResource("/LearningMaterials/STEM/Basiccal/FXML/BasicCalIntro.fxml"));
                stage.setScene(new Scene(biologyRoot, 1000, 600));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void changetoPremiumButtonHandler(javafx.event.ActionEvent event) {
    try {
        Parent root = FXMLLoader.load(getClass().getResource("/User/FXML/IctToPremiumPopup.fxml"));
        Stage popupStage = new Stage();
        popupStage.setTitle("Upgrade to Premium");
        popupStage.initStyle(javafx.stage.StageStyle.UNDECORATED);
        popupStage.setScene(new Scene(root));
        popupStage.initOwner(changetoPremiumButton.getScene().getWindow());
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
}

