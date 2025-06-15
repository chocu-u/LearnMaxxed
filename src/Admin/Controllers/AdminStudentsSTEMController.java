package Admin.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.CustomTextField;
import com.jfoenix.controls.JFXButton;

import Admin.Controllers.StudentsCRUD.StudentsCreateController;
import Data.Students;
import Database.DatabaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AdminStudentsSTEMController implements Initializable {

    ObservableList<Students> studentsstemList = FXCollections.observableArrayList();

    @FXML
    private Button ictButton, logoutButton, studentsButton, dashboardadminbutton, adminbillingsbutton, adminbutton, badgebutton;

    @FXML
    private JFXButton createButton, deleteButton;

    @FXML
    private TableView<Students> studentsstemTable;

    @FXML
    private TableColumn<Students, Integer> userIDColumn;

    @FXML
    private CustomTextField searchField;

    @FXML
    private TableColumn<Students, String> firstNameColumn, lastNameColumn, emailColumn, strandColumn, usernameColumn, passwordColumn, createdColumn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userIDColumn.setCellValueFactory(new PropertyValueFactory<>("userID"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        strandColumn.setCellValueFactory(new PropertyValueFactory<>("strand"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        createdColumn.setCellValueFactory(new PropertyValueFactory<>("created"));

        passwordColumn.setCellFactory(column -> new javafx.scene.control.TableCell<Students, String>() {
            @Override
            protected void updateItem(String password, boolean empty) {
                super.updateItem(password, empty);
                if (empty || password == null) {
                    setText(null);
                } else {
                    setText("•".repeat(password.length()));
                }
            }
        });

        displayStudents();
    }

    public void displayStudents() {
        studentsstemList.clear();

        ResultSet result = DatabaseHandler.getStudentsSTEM();
        if (result == null) {
            System.err.println("Error: ResultSet is null. Check database connection.");
            return;
        }
        try {
            while (result.next()) {
                int userID = result.getInt("UserID");
                String firstName = result.getString("FirstName");
                String lastName = result.getString("LastName");
                String email = result.getString("EmailAddress");
                String username = result.getString("Username");
                String password = result.getString("Password");
                String created = result.getString("Created");
                String strand = result.getString("StrandName");
                int subscriptionID = result.getInt("SubscriptionID");
                int paymentID = result.getInt("PaymentID");

                studentsstemList.add(new Students(userID, firstName, lastName, email, username, password, strand, subscriptionID, paymentID, created));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        studentsstemTable.setItems(studentsstemList);
    }

    @FXML
    private void createButtonHandler() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/FXML/CreatePopup.fxml"));
            Parent root = loader.load();

            StudentsCreateController createController = loader.getController();
            createController.setParentController(this);
            Stage popupStage = new Stage();
            popupStage.setTitle("Create Student");
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.initStyle(StageStyle.UNDECORATED);
            popupStage.setScene(new Scene(root));
            popupStage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void updateButtonHandler() {
        Students selectedStudent = studentsstemTable.getSelectionModel().getSelectedItem();
        if (selectedStudent == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("No student selected.");
            alert.showAndWait();
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/FXML/UpdatePopup.fxml"));
            Parent root = loader.load();

            Admin.Controllers.StudentsCRUD.StudentsUpdateController updateController = loader.getController();
            updateController.setParentController(this);
            updateController.setStudentToUpdate(selectedStudent);

            Stage popupStage = new Stage();
            popupStage.setTitle("Update Student");
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.initStyle(StageStyle.UNDECORATED);
            popupStage.setScene(new Scene(root));
            popupStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteButtonHandler() {
        Students selectedStudent = studentsstemTable.getSelectionModel().getSelectedItem();

        if (selectedStudent != null) {
            boolean deleted = DatabaseHandler.deleteStudent(selectedStudent);
            Alert alert;
            if (deleted) {
                studentsstemList.remove(selectedStudent);
                alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Student deleted successfully!");
            } else {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Failed to delete student.");
            }
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("No student selected.");
            alert.showAndWait();
        }
    }

    @FXML
    private void searchFieldHandler() {
        String searchText = searchField.getText().toLowerCase();
        ObservableList<Students> filteredList = FXCollections.observableArrayList();

        for (Students student : studentsstemList) {
            if (student.getFirstName().toLowerCase().contains(searchText) ||
                student.getLastName().toLowerCase().contains(searchText) ||
                student.getEmail().toLowerCase().contains(searchText) ||
                student.getUsername().toLowerCase().contains(searchText)) {
                filteredList.add(student);
            }
        }

        studentsstemTable.setItems(filteredList);
    }

    @FXML
    private void logoutButtonHandler() {
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
    private void ictButtonHandler() {
        try {
            Stage stage = (Stage) ictButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/Admin/FXML/StudentsICT.fxml"));
            stage.setTitle("Students ICT");
            stage.setScene(new Scene(root, 1000, 600));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void studentsButtonHandler() {
        try {
            Stage stage = (Stage) studentsButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/Admin/FXML/Students.fxml"));
            stage.setTitle("All Students");
            stage.setScene(new Scene(root, 1000, 600));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void adminButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent adminRoot = FXMLLoader.load(getClass().getResource("/Admin/FXML/addAdmin.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(adminRoot, 1000, 600));
    }

    @FXML
    public void adminbillingsButtonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent billingsRoot = FXMLLoader.load(getClass().getResource("/Admin/FXML/BillingsAdmin.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(billingsRoot, 1000, 600));
    }

    @FXML
    public void dashboardadminbuttonHandler(javafx.event.ActionEvent event) throws IOException {
        Parent adminRoot = FXMLLoader.load(getClass().getResource("/Admin/FXML/AdminPage.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(adminRoot, 1000, 600));
    }

    @FXML
    public void goTobadgesHandler(javafx.event.ActionEvent event) throws IOException {
        Parent badgesRoot = FXMLLoader.load(getClass().getResource("/Admin/FXML/BadgesAdmin.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(badgesRoot, 1000, 600));
    }
}
