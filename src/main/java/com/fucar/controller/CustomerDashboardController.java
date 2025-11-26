package com.fucar.controller;

import com.fucar.entity.Account;
import com.fucar.entity.CarRental;
import com.fucar.entity.Customer;
import com.fucar.service.AccountService;
import com.fucar.service.CarRentalService;
import com.fucar.service.CustomerService;
import com.fucar.util.CurrentUser;
import com.fucar.util.SceneUtils;
import com.fucar.util.ValidationUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.util.List;

public class CustomerDashboardController {
    
    // Profile Section
    @FXML private Label lblWelcome;
    @FXML private TextField txtCustomerName;
    @FXML private TextField txtMobile;
    @FXML private DatePicker dpBirthday;
    @FXML private TextField txtIdentityCard;
    @FXML private TextField txtLicenceNumber;
    @FXML private DatePicker dpLicenceDate;
    @FXML private TextField txtEmail;
    @FXML private Button btnUpdateProfile;
    
    // Account Section
    @FXML private TextField txtAccountName;
    @FXML private PasswordField txtOldPassword;
    @FXML private PasswordField txtNewPassword;
    @FXML private PasswordField txtConfirmPassword;
    @FXML private Button btnUpdateAccount;
    
    // Rental History Section
    @FXML private TableView<CarRental> tblRentalHistory;
    @FXML private TableColumn<CarRental, String> colCarName;
    @FXML private TableColumn<CarRental, LocalDate> colPickupDate;
    @FXML private TableColumn<CarRental, LocalDate> colReturnDate;
    @FXML private TableColumn<CarRental, Double> colRentPrice;
    @FXML private TableColumn<CarRental, String> colStatus;
    
    @FXML private Button btnLogout;
    
    private CustomerService customerService;
    private CarRentalService rentalService;
    private AccountService accountService;
    private Customer currentCustomer;
    
    public CustomerDashboardController() {
        this.customerService = new CustomerService();
        this.rentalService = new CarRentalService();
        this.accountService = new AccountService();
    }
    
    @FXML
    public void initialize() {
        setupTable();
        loadCustomerData();
        loadRentalHistory();
    }
    
    private void setupTable() {
        colCarName.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getCar().getCarName()));
        colPickupDate.setCellValueFactory(new PropertyValueFactory<>("pickupDate"));
        colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        colRentPrice.setCellValueFactory(new PropertyValueFactory<>("rentPrice"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
    }
    
    private void loadCustomerData() {
        if (CurrentUser.isLoggedIn()) {
            currentCustomer = customerService.findByAccountId(
                CurrentUser.getCurrentAccount().getAccountId()
            );
            
            if (currentCustomer != null) {
                lblWelcome.setText("Welcome, " + currentCustomer.getCustomerName());
                
                // Profile info
                txtCustomerName.setText(currentCustomer.getCustomerName());
                txtMobile.setText(currentCustomer.getMobile());
                dpBirthday.setValue(currentCustomer.getBirthday());
                txtIdentityCard.setText(currentCustomer.getIdentityCard());
                txtLicenceNumber.setText(currentCustomer.getLicenceNumber());
                dpLicenceDate.setValue(currentCustomer.getLicenceDate());
                txtEmail.setText(currentCustomer.getEmail());
                
                // Account info
                txtAccountName.setText(currentCustomer.getAccount().getAccountName());
            }
        }
    }
    
    private void loadRentalHistory() {
        if (currentCustomer != null) {
            List<CarRental> rentals = rentalService.findByCustomerId(currentCustomer.getCustomerId());
            ObservableList<CarRental> rentalList = FXCollections.observableArrayList(rentals);
            tblRentalHistory.setItems(rentalList);
        }
    }
    
    @FXML
    private void handleUpdateProfile() {
        if (!validateProfileInput()) return;
        
        try {
            currentCustomer.setCustomerName(txtCustomerName.getText().trim());
            currentCustomer.setMobile(txtMobile.getText().trim());
            currentCustomer.setBirthday(dpBirthday.getValue());
            currentCustomer.setIdentityCard(txtIdentityCard.getText().trim());
            currentCustomer.setLicenceNumber(txtLicenceNumber.getText().trim());
            currentCustomer.setLicenceDate(dpLicenceDate.getValue());
            currentCustomer.setEmail(txtEmail.getText().trim());
            
            customerService.update(currentCustomer);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Profile updated successfully");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }
    
    @FXML
    private void handleUpdateAccount() {
        String oldPassword = txtOldPassword.getText();
        String newPassword = txtNewPassword.getText();
        String confirmPassword = txtConfirmPassword.getText();
        
        // Validation
        if (ValidationUtils.isNullOrEmpty(oldPassword)) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Old password is required");
            return;
        }
        
        if (!currentCustomer.getAccount().getPassword().equals(oldPassword)) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Old password is incorrect");
            return;
        }
        
        if (ValidationUtils.isNullOrEmpty(newPassword)) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "New password is required");
            return;
        }
        
        if (newPassword.length() < 6) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "New password must be at least 6 characters");
            return;
        }
        
        if (!newPassword.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "New password and confirm password do not match");
            return;
        }
        
        try {
            Account account = currentCustomer.getAccount();
            account.setPassword(newPassword);
            accountService.update(account);
            
            // Clear password fields
            txtOldPassword.clear();
            txtNewPassword.clear();
            txtConfirmPassword.clear();
            
            showAlert(Alert.AlertType.INFORMATION, "Success", "Password updated successfully");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }
    
    @FXML
    private void handleLogout() {
        try {
            CurrentUser.logout();
            Stage stage = (Stage) btnLogout.getScene().getWindow();
            SceneUtils.switchScene(stage, "/fxml/Login.fxml", "FU Car Renting System - Login");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private boolean validateProfileInput() {
        if (ValidationUtils.isNullOrEmpty(txtCustomerName.getText())) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Customer name is required");
            return false;
        }
        
        if (!ValidationUtils.isValidMobile(txtMobile.getText().trim())) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Invalid mobile number");
            return false;
        }
        
        if (dpBirthday.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Birthday is required");
            return false;
        }
        
        if (!ValidationUtils.isValidIdentityCard(txtIdentityCard.getText().trim())) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Invalid identity card");
            return false;
        }
        
        if (ValidationUtils.isNullOrEmpty(txtLicenceNumber.getText())) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Licence number is required");
            return false;
        }
        
        if (dpLicenceDate.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Licence date is required");
            return false;
        }
        
        if (!ValidationUtils.isValidEmail(txtEmail.getText().trim())) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Invalid email format");
            return false;
        }
        
        return true;
    }
    
    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}