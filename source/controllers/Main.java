package controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Util;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Util.setMainStage(primaryStage);
        Parent root = FXMLLoader.load(getClass().getResource("/layouts/login_layout.fxml"));
        primaryStage.setTitle("Artatawe v3.0");
        primaryStage.setScene(new Scene(root));
        //root.getStylesheets().add(Main.class.getResource("/css/login.css").toExternalForm());
        primaryStage.setResizable(false);
        primaryStage.show();
        Util.setMainStage(primaryStage);
    }
}