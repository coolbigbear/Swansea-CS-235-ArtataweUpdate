package controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ArtataweMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/layouts/login_layout.fxml"));
        primaryStage.setTitle("Artatawe Login");
        primaryStage.setScene(new Scene(root));
        root.getStylesheets().add(ArtataweMain.class.getResource("/layouts/login.css").toExternalForm());
        primaryStage.setResizable(false);
        primaryStage.show();

    }
}