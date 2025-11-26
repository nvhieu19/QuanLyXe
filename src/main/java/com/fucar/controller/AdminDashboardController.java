package com.fucar.controller;

import com.fucar.util.CurrentUser;
import com.fucar.util.SceneUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AdminDashboardController {
    
    @FXML private BorderPane mainPane;
    @FXML private Label lblWelcome;
    
    @FXML
    public void initialize() {
        if (CurrentUser.isLoggedIn()) {
            lblWelcome.setText("Welcome, " + CurrentUser.getCurrentAccount().getAccountName());
        }
        
        // Load default tab (Car Management)
        loadCarManagement();
    }
    
    @FXML
    private void loadCarManagement() {
        loadView("/fxml/CarManagement.fxml");
    }
    
    @FXML
    private void loadCustomerManagement() {
        loadView("/fxml/CustomerManagement.fxml");
    }
    
    @FXML
    private void loadRentalManagement() {
        loadView("/fxml/RentalManagement.fxml");
    }
    
    @FXML
    private void loadReport() {
        loadView("/fxml/Report.fxml");
    }
    
    @FXML
    private void handleLogout() {
        try {
            CurrentUser.logout();
            Stage stage = (Stage) mainPane.getScene().getWindow();
            SceneUtils.switchScene(stage, "/fxml/Login.fxml", "FU Car Renting System - Login");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void loadView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent view = loader.load();
            mainPane.setCenter(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}