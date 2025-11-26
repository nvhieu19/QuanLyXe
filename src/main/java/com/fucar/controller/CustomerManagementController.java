package com.fucar.controller;

import com.fucar.entity.Account;
import com.fucar.entity.Customer;
import com.fucar.service.CustomerService;
import com.fucar.service.AccountService;
import com.fucar.util.ValidationUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;

public class CustomerManagementController {
    
    @FXML private TableView<Customer> tblCustomer;
    @FXML private TableColumn<Customer, Integer> colCustomerId;
    @FXML private TableColumn<Customer, String> colName;
    @FXML private TableColumn<Customer, String> colMobile;
    @FXML private TableColumn<Customer, LocalDate> colBirthday;
    @FXML private TableColumn<Customer, String> colIdentityCard;
    @FXML private TableColumn<Customer, String> colLicence;
    @FXML private TableColumn<Customer, LocalDate> colLicenceDate;
    @FXML private TableColumn<Customer, String> colEmail;
    @FXML private TableColumn<Customer, String> colAccountName;
    @FXML private TableColumn<Customer, String> colRole;
    
    @FXML private TextField txtCustomerName;
    @FXML private TextField txtMobile;
    @FXML private DatePicker dpBirthday;
    @FXML private TextField txtIdentityCard;
    @FXML private TextField txtLicenceNumber;
    @FXML private DatePicker dpLicenceDate;
    @FXML private TextField txtEmail;
    
    // Account fields
    @FXML private TextField txtAccountName;
    @FXML private PasswordField txtPassword;
    @FXML private ComboBox<Account.Role> cmbRole;
    
    @FXML private Button btnAdd;
    @FXML private Button btnUpdate;
    @FXML private Button btnDelete;
    @FXML private Button btnClear;
    
    private CustomerService customerService;
    private AccountService accountService;
    private ObservableList<Customer> customerList;
    private Customer selectedCustomer;
    
    public CustomerManagementController() {
        this.customerService = new CustomerService();
        this.accountService = new AccountService();
    }
    
    @FXML
    public void initialize() {
        setupTable();
        loadRoles();
        loadCustomers();
        
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        
        tblCustomer.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                selectCustomer(newVal);
            }
        });
    }
    
    private void setupTable() {
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        colMobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));
        colBirthday.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        colIdentityCard.setCellValueFactory(new PropertyValueFactory<>("identityCard"));
        colLicence.setCellValueFactory(new PropertyValueFactory<>("licenceNumber"));
        colLicenceDate.setCellValueFactory(new PropertyValueFactory<>("licenceDate"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colAccountName.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getAccount().getAccountName()));
        colRole.setCellValueFactory(cellData -> 
            new javafx.beans.property.SimpleStringProperty(
                cellData.getValue().getAccount().getRole().toString()));
    }
    
    private void loadRoles() {
        cmbRole.setItems(FXCollections.observableArrayList(Account.Role.values()));
    }
    
    private void loadCustomers() {
        customerList = FXCollections.observableArrayList(customerService.findAll());
        tblCustomer.setItems(customerList);
    }
    
    private void selectCustomer(Customer customer) {
        selectedCustomer = customer;
        txtCustomerName.setText(customer.getCustomerName());
        txtMobile.setText(customer.getMobile());
        dpBirthday.setValue(customer.getBirthday());
        txtIdentityCard.setText(customer.getIdentityCard());
        txtLicenceNumber.setText(customer.getLicenceNumber());
        dpLicenceDate.setValue(customer.getLicenceDate());
        txtEmail.setText(customer.getEmail());
        
        // Account info
        txtAccountName.setText(customer.getAccount().getAccountName());
        txtPassword.setText(customer.getAccount().getPassword());
        cmbRole.setValue(customer.getAccount().getRole());
        
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);
    }
    
    @FXML
    private void handleAdd() {
        if (!validateInput()) return;
        
        try {
            // Check if account name already exists
            Account existingAccount = accountService.findByAccountName(txtAccountName.getText().trim());
            if (existingAccount != null) {
                showAlert(Alert.AlertType.ERROR, "Error", "Account name already exists!");
                return;
            }
            
            // Create new account
            Account account = new Account();
            account.setAccountName(txtAccountName.getText().trim());
            account.setPassword(txtPassword.getText());
            account.setRole(cmbRole.getValue());
            accountService.save(account);
            
            // Create customer
            Customer customer = new Customer();
            customer.setCustomerName(txtCustomerName.getText().trim());
            customer.setMobile(txtMobile.getText().trim());
            customer.setBirthday(dpBirthday.getValue());
            customer.setIdentityCard(txtIdentityCard.getText().trim());
            customer.setLicenceNumber(txtLicenceNumber.getText().trim());
            customer.setLicenceDate(dpLicenceDate.getValue());
            customer.setEmail(txtEmail.getText().trim());
            customer.setAccount(account);
            
            customerService.save(customer);
            loadCustomers();
            handleClear();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Customer and Account created successfully");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }
    
    @FXML
    private void handleUpdate() {
        if (selectedCustomer == null || !validateInput()) return;
        
        try {
            // Update customer info
            selectedCustomer.setCustomerName(txtCustomerName.getText().trim());
            selectedCustomer.setMobile(txtMobile.getText().trim());
            selectedCustomer.setBirthday(dpBirthday.getValue());
            selectedCustomer.setIdentityCard(txtIdentityCard.getText().trim());
            selectedCustomer.setLicenceNumber(txtLicenceNumber.getText().trim());
            selectedCustomer.setLicenceDate(dpLicenceDate.getValue());
            selectedCustomer.setEmail(txtEmail.getText().trim());
            
            // Update account info
            Account account = selectedCustomer.getAccount();
            
            // Check if changing account name to existing one
            if (!account.getAccountName().equals(txtAccountName.getText().trim())) {
                Account existingAccount = accountService.findByAccountName(txtAccountName.getText().trim());
                if (existingAccount != null) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Account name already exists!");
                    return;
                }
            }
            
            account.setAccountName(txtAccountName.getText().trim());
            account.setPassword(txtPassword.getText());
            account.setRole(cmbRole.getValue());
            
            accountService.update(account);
            customerService.update(selectedCustomer);
            
            loadCustomers();
            handleClear();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Customer and Account updated successfully");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
        }
    }
    
    @FXML
    private void handleDelete() {
        if (selectedCustomer == null) return;
        
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Delete");
        confirm.setHeaderText("Delete Customer and Account");
        confirm.setContentText("Are you sure? This will delete both customer and account data!");
        
        if (confirm.showAndWait().get() == ButtonType.OK) {
            try {
                customerService.delete(selectedCustomer.getCustomerId());
                loadCustomers();
                handleClear();
                showAlert(Alert.AlertType.INFORMATION, "Success", "Customer deleted successfully");
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
            }
        }
    }
    
    @FXML
    private void handleClear() {
        selectedCustomer = null;
        txtCustomerName.clear();
        txtMobile.clear();
        dpBirthday.setValue(null);
        txtIdentityCard.clear();
        txtLicenceNumber.clear();
        dpLicenceDate.setValue(null);
        txtEmail.clear();
        txtAccountName.clear();
        txtPassword.clear();
        cmbRole.setValue(null);
        
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        tblCustomer.getSelectionModel().clearSelection();
    }
    
    private boolean validateInput() {
        if (ValidationUtils.isNullOrEmpty(txtCustomerName.getText())) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Customer name is required");
            return false;
        }
        
        if (!ValidationUtils.isValidMobile(txtMobile.getText().trim())) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Invalid mobile number (must be 10-11 digits starting with 0)");
            return false;
        }
        
        if (dpBirthday.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Birthday is required");
            return false;
        }
        
        if (!ValidationUtils.isValidIdentityCard(txtIdentityCard.getText().trim())) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Invalid identity card (must be 9-12 digits)");
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
        
        // Validate account fields
        if (ValidationUtils.isNullOrEmpty(txtAccountName.getText())) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Account name is required");
            return false;
        }
        
        if (ValidationUtils.isNullOrEmpty(txtPassword.getText())) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Password is required");
            return false;
        }
        
        if (txtPassword.getText().length() < 6) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Password must be at least 6 characters");
            return false;
        }
        
        if (cmbRole.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Role is required");
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