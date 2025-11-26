package com.fucar;

import com.fucar.config.HibernateConfig;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
//hieuaaaa
public class App extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Test Hibernate connection
        try {
            HibernateConfig.getSessionFactory();
            System.out.println("✅ Hibernate connection successful!");
        } catch (Exception e) {
            System.err.println("❌ Hibernate connection failed!");
            e.printStackTrace();
            return;
        }
        
        // Load Login screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
        Parent root = loader.load();
        
        Scene scene = new Scene(root, 900, 600);
        String css = getClass().getResource("/css/style.css").toExternalForm();
        scene.getStylesheets().add(css);
        //aa
        primaryStage.setTitle("FU Car Renting System");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(900);
        primaryStage.setMinHeight(600);
        primaryStage.setMaximized(false); // Start normal size
        primaryStage.show();
    }
    
    @Override
    public void stop() {
        // Shutdown Hibernate when application closes
        HibernateConfig.shutdown();
        System.out.println("Application closed. Hibernate session factory shutdown.");
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}