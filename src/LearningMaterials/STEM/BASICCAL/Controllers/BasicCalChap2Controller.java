package LearningMaterials.STEM.BASICCAL.Controllers;

import java.io.IOException;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;

import Data.Session;
import Data.Students;
import VideoMaterials.STEM.Controllers.BasicCalChap2VidController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class BasicCalChap2Controller {
    @FXML
    private Button dashboardButton;

    @FXML
    private Button logoutButton;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private JFXButton nextButton;

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
        subjectComboBox.getItems().addAll("Chemistry", "Physics", "Biology", "Pre Calculus", "Basic Calculus");
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
            } else if ("Pre Calculus".equals(selected)) {
                Parent pcalRoot = FXMLLoader.load(getClass().getResource("/LearningMaterials/STEM/Precal/FXML/PCalIntro.fxml"));
                stage.setScene(new Scene(pcalRoot, 1000, 600));
            } else if ("Basic Calculus".equals(selected)) {
                Parent basiccalRoot = FXMLLoader.load(getClass().getResource("/LearningMaterials/STEM/Basiccal/FXML/BasicCalIntro.fxml"));
                stage.setScene(new Scene(basiccalRoot, 1000, 600));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void premhandleSubjectSelection() {
        String selected = subjectComboBox.getSelectionModel().getSelectedItem();
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
            } else if ("Pre Calculus".equals(selected)) {
                Parent pcalRoot = FXMLLoader.load(getClass().getResource("/LearningMaterials/Premium/PCalIntro.fxml"));
                stage.setScene(new Scene(pcalRoot, 1000, 600));
            } else if ("Basic Calculus".equals(selected)) {
                Parent basiccalRoot = FXMLLoader.load(getClass().getResource("/LearningMaterials/Premium/BasicCalIntro.fxml"));
                stage.setScene(new Scene(basiccalRoot, 1000, 600));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void BasicCalIntroButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent BasicCalIntroRoot = FXMLLoader.load(getClass().getResource("/LearningMaterials/STEM/Basiccal/FXML/BasicCalIntro.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(BasicCalIntroRoot, 1000, 600));
    }

    @FXML
    public void BasicCal1ButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent BasicCal1Root = FXMLLoader.load(getClass().getResource("/LearningMaterials/STEM/Basiccal/FXML/BasicCalChap1.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(BasicCal1Root, 1000, 600));
    }

    @FXML
    public void BasicCal3ButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent BasicCal3Root = FXMLLoader.load(getClass().getResource("/LearningMaterials/STEM/Basiccal/FXML/BasicCalChap3.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(BasicCal3Root, 1000, 600));
    }

    @FXML
    public void nextButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent nextRoot = FXMLLoader.load(getClass().getResource("/LearningMaterials/STEM/Basiccal/FXML/BasicCalChap3.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(nextRoot, 1000, 600));
    }

    @FXML
    public void premDashboardButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent dashboardRoot = FXMLLoader.load(getClass().getResource("/User/FXML/PremiumDashboard.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(dashboardRoot, 1000, 600));
    }

    @FXML
    public void premnextButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent nextRoot = FXMLLoader.load(getClass().getResource("/LearningMaterials/Premium/BasicCalChap3.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(nextRoot, 1000, 600));
    }

    @FXML
    public void premBasicCalIntroButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent premBasicCalIntroRoot = FXMLLoader.load(getClass().getResource("/LearningMaterials/Premium/BasicCalIntro.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(premBasicCalIntroRoot, 1000, 600));
    }

    @FXML
    public void premBasicCal1ButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent premBasicCal1Root = FXMLLoader.load(getClass().getResource("/LearningMaterials/Premium/BasicCalChap1.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(premBasicCal1Root, 1000, 600));
    }

    @FXML
    public void premBasicCal3ButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent premBasicCal3Root = FXMLLoader.load(getClass().getResource("/LearningMaterials/Premium/BasicCalChap3.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(premBasicCal3Root, 1000, 600));
    }

    @FXML
    public void playVidHandler(javafx.event.ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/VideoMaterials/STEM/FXML/BasicCalChap2Vid.fxml"));
        Parent root = loader.load();
        BasicCalChap2VidController controller = loader.getController();

        Stage vidStage = new Stage();
        vidStage.setTitle("Basic Calculus Chapter 2 Video");
        vidStage.setScene(new Scene(root, 1280, 800));
        vidStage.setResizable(false);

        vidStage.setOnCloseRequest(e -> {
            if (controller != null && controller.getMediaPlayer() != null) {
                controller.getMediaPlayer().stop();
            }
        });

        vidStage.show();
    }

    public void scrollToTop() {
        if (scrollPane != null) {
            scrollPane.setVvalue(0);
        }
    }   
}
