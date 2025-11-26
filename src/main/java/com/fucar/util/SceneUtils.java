package com.fucar.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class SceneUtils {
    
    public static void switchScene(Stage stage, String fxmlPath, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneUtils.class.getResource(fxmlPath));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        // Add CSS
        String css = SceneUtils.class.getResource("/css/style.css").toExternalForm();
        scene.getStylesheets().add(css);
        
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
    }
    
    public static void switchScene(Stage stage, String fxmlPath, String title, Object controller) throws IOException {
        FXMLLoader loader = new FXMLLoader(SceneUtils.class.getResource(fxmlPath));
        loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        // Add CSS
        String css = SceneUtils.class.getResource("/css/style.css").toExternalForm();
        scene.getStylesheets().add(css);
        
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();
    }
}