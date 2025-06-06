package LearningMaterials.STEM.CHEMISTRY.Controllers;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class Chem1Chap3Controller {
    @FXML
    private Button dashboardButton;

    @FXML
    private Button logoutButton;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private JFXButton nextButton;

    @FXML
    private JFXComboBox<String> subjectComboBox;

    @FXML
    public void initialize() {
        Platform.runLater(() -> scrollPane.setVvalue(0));
        subjectComboBox.getItems().clear();
        subjectComboBox.getItems().addAll("Chemistry", "Physics", "Biology");
    }

    @FXML
    public void logoutButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent logoutRoot = FXMLLoader.load(getClass().getResource("/Login/FXML/LoginPage.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(logoutRoot, 1000, 600));
    }

    @FXML
    public void dashboardButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent dashboardRoot = FXMLLoader.load(getClass().getResource("/User/FXML/StemDashboard.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(dashboardRoot, 1000, 600));
    }

    public void chem1ButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent chem1Root = FXMLLoader.load(getClass().getResource("/LearningMaterials/STEM/Chemistry/FXML/ChemChapter1.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(chem1Root, 1000, 600));
    }

    @FXML
    public void chem2ButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent chem2Root = FXMLLoader.load(getClass().getResource("/LearningMaterials/STEM/Chemistry/FXML/ChemChapter2.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(chem2Root, 1000, 600));
    }

    @FXML
    public void chem3ButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent chem3Root = FXMLLoader.load(getClass().getResource("/LearningMaterials/STEM/Chemistry/FXML/ChemChapter4.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(chem3Root, 1000, 600));
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
                // Add your Biology FXML path here if you have one
                // Parent biologyRoot = FXMLLoader.load(getClass().getResource("/LearningMaterials/STEM/Biology/FXML/BioIntro.fxml"));
                // stage.setScene(new Scene(biologyRoot, 1000, 600));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void nextButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent nextRoot = FXMLLoader.load(getClass().getResource("/LearningMaterials/STEM/Chemistry/FXML/ChemChapter4.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(nextRoot, 1000, 600));
    }

    public void scrollToTop() {
        if (scrollPane != null) {
        scrollPane.setVvalue(0);
        }
    }   
}
