package com.fucar.controller;

import com.fucar.entity.Account;
import com.fucar.service.AccountService;
import com.fucar.util.CurrentUser;
import com.fucar.util.SceneUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginController {
    
    @FXML private TextField txtAccountName;
    @FXML private PasswordField txtPassword;
    @FXML private Button btnLogin;
    @FXML private Label lblError;
    
    private AccountService accountService;
    
    public LoginController() {
        this.accountService = new AccountService();
    }
    
    @FXML
    public void initialize() {
        lblError.setVisible(false);
    }
    
    @FXML
    private void handleLogin() {
        String accountName = txtAccountName.getText().trim();
        String password = txtPassword.getText();
        
        if (accountName.isEmpty() || password.isEmpty()) {
            showError("Please enter both account name and password");
            return;
        }
        
        try {
            Account account = accountService.login(accountName, password);
            
            if (account == null) {
                showError("Invalid account name or password");
                return;
            }
            
            // Set current user
            CurrentUser.setCurrentAccount(account);
            
            // Navigate based on role
            Stage stage = (Stage) btnLogin.getScene().getWindow();
            
            if (account.getRole() == Account.Role.Admin) {
                SceneUtils.switchScene(stage, "/fxml/AdminDashboard.fxml", "Admin Dashboard");
            } else {
                SceneUtils.switchScene(stage, "/fxml/CustomerDashboard.fxml", "Customer Dashboard");
            }
            
        } catch (Exception e) {
            showError("Login error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void showError(String message) {
        lblError.setText(message);
        lblError.setVisible(true);
    }
}