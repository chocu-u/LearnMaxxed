package Admin.Controllers;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.CustomTextField;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.ResultSet;

import Data.Billings;
import Admin.Controllers.BillingsCRUD.BillingsUpdateController;

public class BillingsAdminController implements Initializable {

    @FXML
    private JFXButton BillingsDeletebutton;

    @FXML
    private JFXButton BillingsUpdateButton;

    @FXML
    private TableView<Billings> billingAdminTable;

    @FXML
    private TableColumn<Billings, Integer> billingTransactColumn;

    @FXML
    private TableColumn<Billings, String> billingStrandColumn;

    @FXML
    private TableColumn<Billings, Integer> billingSubColumn;

    @FXML
    private TableColumn<Billings, String> billingFirstNameColumn;

    @FXML
    private TableColumn<Billings, String> billingLastNameColumn;

    @FXML
    private TableColumn<Billings, String> billingEmailColumn;

    @FXML
    private TableColumn<Billings, String> billingTransactionDateColumn;

    @FXML
    private TableColumn<Billings, Integer> billingUserIdColumn;

    @FXML
    private TableColumn<Billings, String> PaymentDetailsColumn;

    @FXML
    private TableColumn<Billings, String> PaymentMethodColumn;

    @FXML
    private TableColumn<Billings, String> paymentDetailsColumn;

    @FXML
    private ComboBox<String> strandfilterCombo;

    @FXML
    private Button adminstudentbutton, adminbutton, badgebutton;

    @FXML
    private Button logoutButton, dashboardButton;

    @FXML
    private CustomTextField searchField;

    private ObservableList<Billings> billingList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        billingTransactColumn.setCellValueFactory(new PropertyValueFactory<>("transactionID"));
        billingStrandColumn.setCellValueFactory(new PropertyValueFactory<>("strand"));
        billingSubColumn.setCellValueFactory(new PropertyValueFactory<>("subscriptionID"));
        PaymentMethodColumn.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        PaymentDetailsColumn.setCellValueFactory(new PropertyValueFactory<>("paymentDetails"));
        billingSubColumn.setCellFactory(column -> new TableCell<Billings, Integer>() {
            @Override
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item == 1 ? "Subscribed" : "Free");
                }
            }
        });
        billingFirstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        billingLastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        billingEmailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        billingTransactionDateColumn.setCellValueFactory(new PropertyValueFactory<>("transactionDate"));
        billingUserIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));

        strandfilterCombo.setItems(FXCollections.observableArrayList("All Students", "STEM", "ICT"));
        strandfilterCombo.getSelectionModel().selectFirst();
        strandfilterCombo.setOnAction(event -> filterBillings());

        loadBillings();
    }

    private void loadBillings() {
        billingList.clear();
        try {
            ResultSet rs = Database.DatabaseHandler.getBillings();
            while (rs.next()) {
                Billings billing = new Billings(
                    rs.getInt("UserID"),
                    rs.getInt("TransactionID"),
                    rs.getString("TransactionDate"),
                    rs.getString("StrandName"),
                    rs.getInt("SubscriptionID"),
                    rs.getString("FirstName"),
                    rs.getString("LastName"),
                    rs.getString("EmailAddress"),
                    rs.getString("PaymentMethod"),
                    rs.getString("PaymentDetails")
                );
                billingList.add(billing);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        billingAdminTable.setItems(billingList);
    }

    private void filterBillings() {
        String selected = strandfilterCombo.getSelectionModel().getSelectedItem();
        billingList.clear();
        try {
            ResultSet rs = "All Students".equals(selected)
                ? Database.DatabaseHandler.getBillings()
                : Database.DatabaseHandler.getBillingsByStrand(selected);

            while (rs.next()) {
                Billings billing = new Billings(
                    rs.getInt("UserID"),
                    rs.getInt("TransactionID"),
                    rs.getString("TransactionDate"),
                    rs.getString("StrandName"),
                    rs.getInt("SubscriptionID"),
                    rs.getString("FirstName"),
                    rs.getString("LastName"),
                    rs.getString("EmailAddress"),
                    rs.getString("PaymentMethod"),
                    rs.getString("PaymentDetails")
                );
                billingList.add(billing);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        billingAdminTable.setItems(billingList);
    }

    @FXML
    private void BillingsUpdateButtonHandler() {
        Billings selectedBilling = billingAdminTable.getSelectionModel().getSelectedItem();
        if (selectedBilling == null) {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a user to update.");
            alert.showAndWait();
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/FXML/billingsUpdatePopup.fxml"));
            Parent root = loader.load();

            BillingsUpdateController controller = loader.getController();
            controller.setUserId(selectedBilling.getUserId());

            Stage popupStage = new Stage();
            popupStage.setTitle("Update Subscription Status");
            popupStage.initStyle(javafx.stage.StageStyle.UNDECORATED);
            popupStage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
            popupStage.setScene(new Scene(root));
            popupStage.showAndWait();

            loadBillings();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    private void BillingsDeletebuttonHandler() {
        Billings selectedBilling = billingAdminTable.getSelectionModel().getSelectedItem();
        if (selectedBilling == null) {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
            alert.setTitle("No Selection");
            alert.setHeaderText(null);
            alert.setContentText("Please select a user to delete.");
            alert.showAndWait();
            return;
        }

        javafx.scene.control.Alert confirm = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Deletion");
        confirm.setHeaderText(null);
        confirm.setContentText("Are you sure you want to delete this billing/user?");
        java.util.Optional<javafx.scene.control.ButtonType> result = confirm.showAndWait();
        if (result.isPresent() && result.get() == javafx.scene.control.ButtonType.OK) {
            boolean success = Database.DatabaseHandler.deleteUserById(selectedBilling.getUserId());
            if (success) {
                billingList.remove(selectedBilling);
                javafx.scene.control.Alert info = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
                info.setTitle("Deleted");
                info.setHeaderText(null);
                info.setContentText("User deleted successfully.");
                info.showAndWait();
            } else {
                javafx.scene.control.Alert error = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
                error.setTitle("Error");
                error.setHeaderText(null);
                error.setContentText("Failed to delete user.");
                error.showAndWait();
            }
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
    private void searchFieldHandler() {
        String searchText = searchField.getText().toLowerCase();
        ObservableList<Billings> filteredList = FXCollections.observableArrayList();

        for (Billings billing : billingList) {
            String subscriptionStatus = billing.getSubscriptionID() == 1 ? "subscribed" : "free";

             if (billing.getFirstName().toLowerCase().contains(searchText) ||
            billing.getLastName().toLowerCase().contains(searchText) ||
            billing.getEmail().toLowerCase().contains(searchText) ||
            subscriptionStatus.contains(searchText)) {
            filteredList.add(billing);
            }
        }

        billingAdminTable.setItems(filteredList);
    }

    @FXML
    public void adminButtonHandler(javafx.event.ActionEvent event) throws IOException {
    Parent adminRoot = javafx.fxml.FXMLLoader.load(getClass().getResource("/Admin/FXML/addAdmin.fxml"));
    Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
    stage.setScene(new Scene(adminRoot, 1000, 600));
    }

    @FXML
    public void goTobadgesHandler(javafx.event.ActionEvent event) throws IOException {
        Parent badgesRoot = javafx.fxml.FXMLLoader.load(getClass().getResource("/Admin/FXML/BadgesAdmin.fxml"));
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(badgesRoot, 1000, 600));
    }
}


