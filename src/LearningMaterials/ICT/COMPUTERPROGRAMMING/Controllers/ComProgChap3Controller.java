package LearningMaterials.ICT.COMPUTERPROGRAMMING.Controllers;

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

public class ComProgChap3Controller {
    
    @FXML
    private Button ComProg1Button;

    @FXML
    private Button ComProg2Button;

    @FXML
    private Button ComProg3Button;

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
void comprog1ButtonHandler(ActionEvent event) {
    try {
        Parent root = FXMLLoader.load(getClass().getResource("/LearningMaterials/ICT/COMPUTERPROGRAMMING/FXML/ComProgChapter1.fxml"));
        Stage stage = (Stage) ComProg1Button.getScene().getWindow();
        stage.setScene(new Scene(root, 1000, 600));
        stage.setTitle("Computer Programming - Chapter 1");
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    @FXML
    void comprog2ButtonHandler(ActionEvent event) {
    try {
        Parent root = FXMLLoader.load(getClass().getResource("/LearningMaterials/ICT/COMPUTERPROGRAMMING/FXML/ComProgChapter2.fxml"));
        Stage stage = (Stage) ComProg2Button.getScene().getWindow();
        stage.setScene(new Scene(root, 1000, 600));
        stage.setTitle("Computer Programming - Chapter 2");
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    @FXML
    void comprog3ButtonHandler(ActionEvent event) {
    try {
        Parent root = FXMLLoader.load(getClass().getResource("/LearningMaterials/ICT/COMPUTERPROGRAMMING/FXML/ComProgChapter3.fxml"));
        Stage stage = (Stage) ComProg3Button.getScene().getWindow();
        stage.setScene(new Scene(root, 1000, 600));
        stage.setTitle("Computer Programming - Chapter 3");
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    @FXML
    public void dashboardButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent dashboardRoot = javafx.fxml.FXMLLoader.load(getClass().getResource("/User/FXML/IctDashboard.fxml"));
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
        Parent logoutRoot = javafx.fxml.FXMLLoader.load(getClass().getResource("/Login/FXML/LoginPage.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(logoutRoot, 1000, 600));
    }

    @FXML
public void nextButtonHandler(ActionEvent event) {
    }

    @FXML
    public void premDashboardButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent dashboardRoot = javafx.fxml.FXMLLoader.load(getClass().getResource("/User/FXML/PremiumDashboard.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(dashboardRoot, 1000, 600));
    }

    @FXML
    public void premnextButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent nextRoot = javafx.fxml.FXMLLoader.load(getClass().getResource("/LearningMaterials/Premium/ComProgChapter2.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(nextRoot, 1000, 600));
    }

    @FXML
    public void premcomprogIntroButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent comprogIntroRoot = javafx.fxml.FXMLLoader.load(getClass().getResource("/LearningMaterials/Premium/ComProgChapterIntro.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(comprogIntroRoot, 1000, 600));
    }

    @FXML
    public void premcomprog2ButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent comprog2Root = javafx.fxml.FXMLLoader.load(getClass().getResource("/LearningMaterials/Premium/ComProgChapter2.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(comprog2Root, 1000, 600));
    }

    @FXML
    public void premcomprog1ButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent comprog1Root = javafx.fxml.FXMLLoader.load(getClass().getResource("/LearningMaterials/Premium/ComProgChapter1.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(comprog1Root, 1000, 600));
    }

    @FXML
    private void finishButtonHandler(javafx.event.ActionEvent event) throws IOException {
        boolean badgeAdded = false;
        boolean alreadyHasBadge = false;
        if (Data.Session.getLoggedInStudent() != null) {
            int userID = Data.Session.getLoggedInStudent().getUserID();
            badgeAdded = Database.DatabaseHandler.addBadgeIfAllowed(userID, "Computer Programming");
            // Check if the user already has the badge
            if (!badgeAdded && Database.DatabaseHandler.hasBadge(userID, "Computer Programming")) {
                alreadyHasBadge = true;
            }
        }

        String fxmlPath;
        if (badgeAdded) {
            fxmlPath = "/User/FXML/Congratulations.fxml";
        } else if (alreadyHasBadge) {
            fxmlPath = "/User/FXML/AlreadyHasBadge.fxml"; // Create this FXML for a custom message
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

