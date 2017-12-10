package controllers;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Util;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginController implements Initializable {

    @FXML
    private TextField loginTextField;
    @FXML
    private Label loginUserPrompt;
    @FXML
    private ImageView loginImages;
    private Thread cycleImageThread;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cycleImageThread = new Thread(cycleImagesRunnable());
        cycleImageThread.start();
        Util.getMainStage().setOnCloseRequest(e -> cycleImageThread.interrupt());
    }

    @FXML
    private void loginButtonAction(ActionEvent e) throws IOException, InterruptedException {
        if (validate(loginTextField.getText())) {
            successfulLogin(e);
        }
    }

    @FXML
    private void registerButtonAction(ActionEvent e) throws IOException {
        cycleImageThread.interrupt();
        Parent root = FXMLLoader.load(getClass().getResource("/layouts/register_layout.fxml"));
        //root.getStylesheets().add(ArtataweMain.class.getResource("/css/home_layout.css").toExternalForm());
        Scene registerScene = new Scene(root);
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(registerScene);
        stage.setResizable(false);
        stage.setMinWidth(680);
        stage.setMinHeight(468);
        stage.show();
    }

    private void successfulLogin(ActionEvent e) throws IOException {
        cycleImageThread.interrupt();
        loginUserPrompt.setText("Welcome to Artatawe" + ", " + Util.getCurrentUser().getFirstName() + "!");
        Parent root = FXMLLoader.load(getClass().getResource("/layouts/home_layout.fxml"));
        root.getStylesheets().add(ArtataweMain.class.getResource("/css/home_layout.css").toExternalForm());
        Scene homeScene = new Scene(root);
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(homeScene);
        stage.setResizable(true);
        stage.setMinHeight(700);
        stage.setMinWidth(1250);
        stage.show();
    }

    private boolean validate(String input) throws InterruptedException {
        if (!validCharacterInput(input)) {
            loginUserPrompt.setText("   ");
            loginUserPrompt.setText("Only alphanumerical values are allowed!");
            return false;
        } else {
            if (!validLengthInput(input)) {
                loginUserPrompt.setText(" ");
                loginUserPrompt.setText("Username too long!");
                return false;
            } else {
                if (!validUser(input)) {
                    loginUserPrompt.setText(" ");
                    Thread.sleep(500);
                    loginUserPrompt.setText("User not found!");
                    return false;
                } else {
                    return true;
                }
            }
        }
    }

    private boolean validCharacterInput(String input) {
        //check if there are characters which are not alphanumerical
        Pattern pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);
        return !matcher.find();
    }

    private boolean validLengthInput(String input) {
        return input.length() < 15;
    }

    private boolean validUser(String input) {
        return Util.checkAndSetUser(input);
    }

    private Runnable cycleImagesRunnable() {
        Runnable r = () -> {
            try {
                while (true) {
                    loginImages.setImage(new Image("images/login/login1.png"));
                    fading(loginImages);
                    TimeUnit.MILLISECONDS.sleep(3000);
                    fadingOut(loginImages);
                    TimeUnit.MILLISECONDS.sleep(1000);
                    System.out.println("IMAGE THREAD RUNNING");
                    loginImages.setImage(new Image("images/login/login2.png"));
                    fading(loginImages);
                    TimeUnit.MILLISECONDS.sleep(3000);
                    fadingOut(loginImages);
                    TimeUnit.MILLISECONDS.sleep(1000);
                    System.out.println("IMAGE THREAD RUNNING");
                    loginImages.setImage(new Image("images/login/login3.png"));
                    fading(loginImages);
                    TimeUnit.MILLISECONDS.sleep(3000);
                    fadingOut(loginImages);
                    TimeUnit.MILLISECONDS.sleep(1000);
                    System.out.println("IMAGE THREAD RUNNING");
                    loginImages.setImage(new Image("images/login/login4.png"));
                    fading(loginImages);
                    TimeUnit.MILLISECONDS.sleep(3000);
                    fadingOut(loginImages);
                    TimeUnit.MILLISECONDS.sleep(1000);
                    System.out.println("IMAGE THREAD RUNNING");
                }
            } catch (InterruptedException e) {
                System.out.println("time unit interrupted");
            }
        };
        return r;
    }

    private void fading(ImageView image) {
        FadeTransition t = new FadeTransition(Duration.seconds(2), image);
        t.setFromValue(0.0);
        t.setToValue(1.0);
        t.play();
    }

    private void fadingOut(ImageView image) {
        FadeTransition t = new FadeTransition(Duration.seconds(1), image);
        t.setFromValue(1.0);
        t.setToValue(0.0);
        t.play();
    }
}