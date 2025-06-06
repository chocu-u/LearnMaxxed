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
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;


public class IctDashController {
@FXML
    private JFXButton AnimationButton;


    @FXML
    private JFXButton ComProgButton;


    @FXML
    private JFXButton ComSysButton;


    @FXML
    private JFXButton CreateButton112;


    @FXML
    private JFXButton WebDevButton;


    @FXML
    private Label date;


    @FXML
    private Button logoutButton;


    @FXML
    private ScrollPane scrollPane;


    @FXML
    private JFXComboBox<String> subjectComboBox;


    @FXML
    private Label usernameSidePanel;


    @FXML
    private Label welcomebackUsername;


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
        subjectComboBox.getItems().addAll("Computer Programming", "Computer Systems", "Web Development", "Database Management");
    }


    @FXML
    public void logoutButtonHandler(javafx.event.ActionEvent event) throws IOException {
        try {
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.close();
            Parent root = FXMLLoader.load(getClass().getResource("/Login/FXML/LoginPage.fxml"));
            Stage newStage = new Stage();
            newStage.setTitle("Login");
            newStage.setScene(new Scene(root, 1000, 600));
            newStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


@FXML
    public void ComProgButtonHandler(javafx.event.ActionEvent event) throws IOException {
        try {
            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.close();
            Parent root = FXMLLoader.load(getClass().getResource("/LearningMaterials/ICT/COMPUTERPROGRAMMING/FXML/ComProgIntro.fxml"));
            Stage newStage = new Stage();
            newStage.setTitle("Computer Programming");
            newStage.setScene(new Scene(root, 1000, 600));
            newStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


 @FXML
public void handleSubjectSelection() {
       String selected = subjectComboBox.getSelectionModel().getSelectedItem();
    if ("Computer Programming".equals(selected)) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/LearningMaterials/ICT/COMPUTERPROGRAMMING/FXML/ComProgIntro.fxml"));
            Stage stage = (Stage) subjectComboBox.getScene().getWindow();
            stage.setScene(new Scene(root, 1000, 600));
            stage.setTitle("Computer Programming Introduction");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


}
