package Admin.Controllers;

import com.jfoenix.controls.JFXButton;
import Admin.Controllers.BadgesCRUD.BadgesCreateController;
import Data.Badges;
import Database.DatabaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import org.controlsfx.control.textfield.CustomTextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class BadgesAdminController implements Initializable {

    @FXML
    private JFXButton BillingsDeletebutton;

    @FXML
    private JFXButton CreateButton;

    @FXML
    private Text adminName;

    @FXML
    private Button adminbutton, badgebutton;

    @FXML
    private Button adminstudentbutton;

    @FXML
    private TableColumn<Badges, String> badgenameCol;

    @FXML
    private TableView<Badges> badgesTable;

    @FXML
    private Button billingsbutton;

    @FXML
    private Button dashboardButton;

    @FXML
    private TableColumn<Badges, String> dateCol;

    @FXML
    private TableColumn<Badges, String> emailCol;

    @FXML
    private TableColumn<Badges, String> firstnameCol;

    @FXML
    private TableColumn<Badges, String> lastnameCol;

    @FXML
    private Button logoutButton;

    @FXML
    private TableColumn<Badges, String> plantypeCol;

    @FXML
    private CustomTextField searchField;

    @FXML
    private TableColumn<Badges, String> strandCol;

    @FXML
    private ComboBox<String> strandfilterCombo;

    @FXML
    private TableColumn<Badges, Integer> userbadgesidCol;

    @FXML
    private TableColumn<Badges, Integer> useridCol;

    private ObservableList<Badges> badgesList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Set up table columns
        userbadgesidCol.setCellValueFactory(new PropertyValueFactory<>("userBadgeID"));
        useridCol.setCellValueFactory(new PropertyValueFactory<>("userID"));
        firstnameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastnameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        strandCol.setCellValueFactory(new PropertyValueFactory<>("strand"));
        plantypeCol.setCellValueFactory(new PropertyValueFactory<>("planType"));
        badgenameCol.setCellValueFactory(new PropertyValueFactory<>("badgeName"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("dateAwarded"));

        displayBadges();
        loadStrands();

        strandfilterCombo.setOnAction(event -> filterUsersByStrand());
        searchField.textProperty().addListener((observable, oldValue, newValue) -> searchFieldHandler());
    }

    public void displayBadges() {
        badgesList.clear();

        ResultSet result = DatabaseHandler.getBadges(); 
        if (result == null) {
            System.err.println("Error: ResultSet is null. Check database connection.");
            return;
        }
        try {
            while (result.next()) {
                int userBadgeID = result.getInt("UserBadgeID");
                int userID = result.getInt("UserID");
                String firstName = result.getString("FirstName");
                String lastName = result.getString("LastName");
                String email = result.getString("EmailAddress");
                String strand = result.getString("StrandName");
                String planType = result.getString("PlanType");
                String badgeName = result.getString("BadgeName");
                String dateAwarded = result.getString("DateAwarded");

                badgesList.add(new Badges(userBadgeID, userID, firstName, lastName, email, strand, planType, badgeName, dateAwarded));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        badgesTable.setItems(badgesList);
    }

    @FXML
    void createButtonHandler(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/FXML/BadgesCreatePopup.fxml"));
            Parent root = loader.load();
            Stage popupStage = new Stage();
            popupStage.setTitle("Create Badge");
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.initStyle(StageStyle.UNDECORATED);
            popupStage.setScene(new Scene(root));
            popupStage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAddBadgePopup() {
        Badges selectedBadge = badgesTable.getSelectionModel().getSelectedItem();
        if (selectedBadge == null) {
            showAlert(Alert.AlertType.WARNING, "Please select a user first.");
            return;
        }
        int selectedUserID = selectedBadge.getUserID();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/FXML/BadgesCreatePopup.fxml"));
            Parent root = loader.load();
            BadgesCreateController controller = loader.getController();
            controller.setSelectedUserID(selectedUserID);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    private void dashboardButtonHandler() {
        try {
            Stage stage = (Stage) dashboardButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/Admin/FXML/AdminPage.fxml"));
            stage.setTitle("Students STEM");
            stage.setScene(new Scene(root, 1000, 600));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void adminButtonHandler(ActionEvent event) throws IOException {
        Parent adminRoot = FXMLLoader.load(getClass().getResource("/Admin/FXML/addAdmin.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(adminRoot, 1000, 600));
    }

    @FXML
    public void adminbillingsButtonHandler(ActionEvent event) throws IOException {
        Parent billingsRoot = FXMLLoader.load(getClass().getResource("/Admin/FXML/BillingsAdmin.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(billingsRoot, 1000, 600));
    }

    @FXML
    private void goToStudentsButtonHandler() {
        try {
            Stage stage = (Stage) dashboardButton.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/Admin/FXML/Students.fxml"));
            stage.setTitle("Students");
            stage.setScene(new Scene(root, 1000, 600));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML 
    private void deleteBadgeButtonHandler(ActionEvent event) {
        Badges selectedBadge = badgesTable.getSelectionModel().getSelectedItem();
        if (selectedBadge != null) {
            boolean success = DatabaseHandler.deleteBadgeFromUser(selectedBadge.getUserBadgeID()); // Implement this method to return boolean
            if (success) {
                badgesList.remove(selectedBadge);
                badgesTable.refresh();
                showAlert(Alert.AlertType.INFORMATION, "Badge deleted successfully.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed to delete badge.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "No badge selected for deletion.");
        }
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void loadStrands() {
        try {
            java.util.List<String> strands = DatabaseHandler.getStrands(); 
            strandfilterCombo.getItems().add("All"); 
            if (strands != null) {
                strandfilterCombo.getItems().addAll(strands);
            }
            strandfilterCombo.getSelectionModel().selectFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void filterUsersByStrand() {
        String selectedStrand = strandfilterCombo.getValue();
        ObservableList<Badges> filteredList = FXCollections.observableArrayList();

        if ("All".equals(selectedStrand)) {
            filteredList.addAll(badgesList); 
        } else {
            for (Badges badge : badgesList) {
                if (selectedStrand.equals(badge.getStrand())) {
                    filteredList.add(badge);
                }
            }
        }
        badgesTable.setItems(filteredList); 
    }

    @FXML
    private void searchFieldHandler() {
        String searchText = searchField.getText().toLowerCase();
        ObservableList<Badges> filteredList = FXCollections.observableArrayList();

        for (Badges badge : badgesList) {
            if (badge.getFirstName().toLowerCase().contains(searchText) ||
                badge.getLastName().toLowerCase().contains(searchText) ||
                badge.getEmail().toLowerCase().contains(searchText) ||
                badge.getBadgeName().toLowerCase().contains(searchText) ||
                badge.getPlanType().toLowerCase().contains(searchText) ||
                badge.getStrand().toLowerCase().contains(searchText) ||
                badge.getDateAwarded().toLowerCase().contains(searchText)) {
                filteredList.add(badge);
            }
        }

        badgesTable.setItems(filteredList);
    }

    @FXML
    public void goTobadgesHandler(javafx.event.ActionEvent event) throws IOException {
        Parent badgesRoot = javafx.fxml.FXMLLoader.load(getClass().getResource("/Admin/FXML/BadgesAdmin.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(badgesRoot, 1000, 600));
    }
}
