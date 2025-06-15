package Admin.Controllers;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AdminDashboardController {

    @FXML
    private Button addadminbutton, adminbutton, badgebutton;

    @FXML
    private Button addstudentbutton;

    @FXML
    private Button adminbillingsbutton;

    @FXML
    private Button adminstudentbutton;

    @FXML
    private Button dashboardadminbutton;

    @FXML
    private Button logoutadminbutton;

    @FXML
    public void addstudentButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent studentsRoot = FXMLLoader.load(getClass().getResource("/Admin/FXML/Students.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(studentsRoot, 1000, 600));
    }

    @FXML
    public void addadminButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent addAdminRoot = FXMLLoader.load(getClass().getResource("/Admin/FXML/addAdmin.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(addAdminRoot, 1000, 600));
    }

    @FXML
    public void adminbillingsButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent billingsRoot = FXMLLoader.load(getClass().getResource("/Admin/FXML/BillingsAdmin.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(billingsRoot, 1000, 600));
    }

    @FXML
    public void adminButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent adminRoot = FXMLLoader.load(getClass().getResource("/Admin/FXML/addAdmin.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(adminRoot, 1000, 600));
    }

    @FXML
    public void logoutButtonHandler(javafx.event.ActionEvent event) throws IOException {
        try {
            Stage stage = (Stage) logoutadminbutton.getScene().getWindow();
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
    public void goTobadgesHandler(javafx.event.ActionEvent event) throws IOException {
        Parent badgesRoot = FXMLLoader.load(getClass().getResource("/Admin/FXML/BadgesAdmin.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(badgesRoot, 1000, 600));
    }
}
