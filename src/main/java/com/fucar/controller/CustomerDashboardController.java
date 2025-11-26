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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CustomerDashboardController {
    
    // --- CÁC VIEW (MÀN HÌNH CON) ---
    @FXML private VBox viewProfile;
    @FXML private VBox viewAccount;
    @FXML private VBox viewHistory;

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
        showProfile(); // Mặc định hiển thị màn hình Hồ sơ
        setupTable();
        loadCustomerData();
        loadRentalHistory();
        
        // Định dạng ngày tháng dd/MM/yyyy
        String pattern = "dd/MM/yyyy";
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
        StringConverter<LocalDate> dateConverter = new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                return (date != null) ? dateFormatter.format(date) : "";
            }
            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    try {
                        return LocalDate.parse(string, dateFormatter);
                    } catch (Exception e) { return null; }
                }
                return null;
            }
        };
        dpBirthday.setConverter(dateConverter);
        dpLicenceDate.setConverter(dateConverter);
    }
    
    // --- LOGIC CHUYỂN ĐỔI MÀN HÌNH ---
    @FXML
    private void showProfile() {
        viewProfile.setVisible(true);
        viewAccount.setVisible(false);
        viewHistory.setVisible(false);
    }
    
    @FXML
    private void showAccount() {
        viewProfile.setVisible(false);
        viewAccount.setVisible(true);
        viewHistory.setVisible(false);
    }
    
    @FXML
    private void showHistory() {
        viewProfile.setVisible(false);
        viewAccount.setVisible(false);
        viewHistory.setVisible(true);
    }
    // ---------------------------------

    private void setupTable() {
        colCarName.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getCar().getCarName()));
        
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Callback<TableColumn<CarRental, LocalDate>, TableCell<CarRental, LocalDate>> cellFactory = column -> new TableCell<CarRental, LocalDate>() {
            @Override
            protected void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (empty || date == null) {
                    setText(null);
                } else {
                    setText(dateFormatter.format(date));
                }
            }
        };

        colPickupDate.setCellValueFactory(new PropertyValueFactory<>("pickupDate"));
        colPickupDate.setCellFactory(cellFactory);
        
        colReturnDate.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        colReturnDate.setCellFactory(cellFactory);
        
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
                txtCustomerName.setText(currentCustomer.getCustomerName());
                txtMobile.setText(currentCustomer.getMobile());
                dpBirthday.setValue(currentCustomer.getBirthday());
                txtIdentityCard.setText(currentCustomer.getIdentityCard());
                txtLicenceNumber.setText(currentCustomer.getLicenceNumber());
                dpLicenceDate.setValue(currentCustomer.getLicenceDate());
                txtEmail.setText(currentCustomer.getEmail());
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
        
        if (ValidationUtils.isNullOrEmpty(oldPassword) || ValidationUtils.isNullOrEmpty(newPassword)) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Please fill all password fields");
            return;
        }
        
        if (!currentCustomer.getAccount().getPassword().equals(oldPassword)) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Old password is incorrect");
            return;
        }
        
        if (newPassword.length() < 6) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "New password must be at least 6 characters");
            return;
        }
        
        if (!newPassword.equals(confirmPassword)) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Passwords do not match");
            return;
        }
        
        try {
            Account account = currentCustomer.getAccount();
            account.setPassword(newPassword);
            accountService.update(account);
            txtOldPassword.clear(); txtNewPassword.clear(); txtConfirmPassword.clear();
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
        // ... validation logic khác giữ nguyên
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