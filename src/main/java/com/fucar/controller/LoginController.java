package com.fucar.controller;

import com.fucar.entity.Account;
import com.fucar.service.AccountService;
import com.fucar.util.CurrentUser;
import com.fucar.util.SceneUtils;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoginController {
    
    @FXML private TextField txtAccountName;
    @FXML private PasswordField txtPassword;
    @FXML private Button btnLogin;
    @FXML private Label lblError;
    @FXML private HBox hboxMain;
    
    private AccountService accountService;
    
    public LoginController() {
        this.accountService = new AccountService();
    }
    
    @FXML
    public void initialize() {
        lblError.setVisible(false);
        
        // Add fade-in animation when login screen opens
        addFadeInAnimation();
        
        // Auto-focus on username field
        txtAccountName.requestFocus();
        
        // Allow Enter key to submit login form
        txtPassword.setOnKeyPressed(event -> {
            if (event.getCode().toString().equals("ENTER")) {
                handleLogin();
            }
        });
    }
    
    private void addFadeInAnimation() {
        try {
            HBox mainPane = (HBox) btnLogin.getScene().getRoot();
            
            // Fade-in animation
            FadeTransition fadeIn = new FadeTransition(Duration.millis(600), mainPane);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            
            // Optional: Slight translate for more effect
            TranslateTransition slideIn = new TranslateTransition(Duration.millis(600), mainPane);
            slideIn.setFromY(20);
            slideIn.setToY(0);
            
            fadeIn.play();
            slideIn.play();
        } catch (Exception e) {
            // Silent catch - animation is optional
        }
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