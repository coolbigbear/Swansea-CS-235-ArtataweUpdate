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

/**
 * The Controller for the Login layout, this is in charge of <code>layouts.login_layout.fxml</code>.
 *
 * This is the Controller and Layout pair in charge of the login page.
 *
 * @author Bezhan Kodomani
 * @version 1.5
 * @see Initializable
 */
public class LoginController implements Initializable {

    @FXML
    private TextField loginTextField;
    @FXML
    private TextField loginTextFieldPassword;
    @FXML
    private Label loginUserPrompt;
    @FXML
    private ImageView loginImages;
    private Thread cycleImageThread;

    /**
     * Method which initializes the Longin Controller
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cycleImageThread = new Thread(cycleImagesRunnable());
        cycleImageThread.start();
        Util.getMainStage().setOnCloseRequest(e -> cycleImageThread.interrupt());
    }

    /**
     * Method which logins the user
     * @param e event of the button
     * @throws IOException
     * @throws InterruptedException
     */
    @FXML
    private void loginButtonAction(ActionEvent e) throws IOException, InterruptedException {
        if (validate(loginTextField.getText())) {
            successfulLogin(e);
        }
    }

    /**
     * Button method which registers the user
     * @param e The button event
     * @throws IOException
     */
    @FXML
    private void registerButtonAction(ActionEvent e) throws IOException {
        cycleImageThread.interrupt();
        Parent root = FXMLLoader.load(getClass().getResource("/layouts/register_layout.fxml"));
        //root.getStylesheets().add(Main.class.getResource("/css/home_layout.css").toExternalForm());
        Scene registerScene = new Scene(root);
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(registerScene);
        stage.setResizable(false);
        stage.setMinWidth(680);
        stage.setMinHeight(468);
        stage.show();
    }

    /**
     * Method to go to the main program (home layout)
     * @param e Button event
     * @throws IOException
     */
    private void successfulLogin(ActionEvent e) throws IOException {
        cycleImageThread.interrupt();
        loginUserPrompt.setText("Welcome to Artatawe" + ", " + Util.getCurrentUser().getFirstName() + "!");
        Parent root = FXMLLoader.load(getClass().getResource("/layouts/home_layout.fxml"));
        root.getStylesheets().add(Main.class.getResource("/css/home_layout.css").toExternalForm());
        Scene homeScene = new Scene(root);
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(homeScene);
        stage.setResizable(true);
        stage.setMinHeight(700);
        stage.setMinWidth(1250);
        stage.show();
    }

    /**
     * Method to validate user input
     * @param input The user's input in login text field
     * @return
     * @throws InterruptedException
     */
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
                    loginUserPrompt.setText("User not found!");
                    return false;
                } else {
                    return true;
                }
            }
        }
    }

    /**
     * Helper method to check if there are characters which are not alphanumerical
     * @param input String input to be checked
     * @return true if it is valid, false if invalid
     */
    private boolean validCharacterInput(String input) {
        Pattern pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);
        return !matcher.find();
    }

    /**
     * Helper method on validating the length of the user's input
     * @param input String input to be checked
     * @return true of the input is of valid length, false if not
     */
    private boolean validLengthInput(String input) {
        return input.length() < 15;
    }

    /**
     * Method to verify user if he is the database or not
     * @param input The string to be checked
     * @return true if the user is in the JSON file (registered), false if not
     */
    private boolean validUser(String input) {
        return Util.checkAndSetUser(input);
    }

    /**
     * Runnable to cycle through images and create fading in and out effect, animation, this is passed to the thread
     * @return The runnable created
     */
    private Runnable cycleImagesRunnable() {
        Runnable r = () -> {
            try {
                while (true) {
                    loginImages.setImage(new Image("images/login/login1.png"));
                    fading(loginImages);
                    TimeUnit.MILLISECONDS.sleep(3000);
                    fadingOut(loginImages);
                    TimeUnit.MILLISECONDS.sleep(1000);
                    loginImages.setImage(new Image("images/login/login2.png"));
                    fading(loginImages);
                    TimeUnit.MILLISECONDS.sleep(3000);
                    fadingOut(loginImages);
                    TimeUnit.MILLISECONDS.sleep(1000);
                    loginImages.setImage(new Image("images/login/login3.png"));
                    fading(loginImages);
                    TimeUnit.MILLISECONDS.sleep(3000);
                    fadingOut(loginImages);
                    TimeUnit.MILLISECONDS.sleep(1000);
                    loginImages.setImage(new Image("images/login/login4.png"));
                    fading(loginImages);
                    TimeUnit.MILLISECONDS.sleep(3000);
                    fadingOut(loginImages);
                    TimeUnit.MILLISECONDS.sleep(1000);
                }
            } catch (InterruptedException e) {
                System.out.println("time unit interrupted");
            }
        };
        return r;
    }

    /**
     * Helper method which does fading in effect of an image
     * @param image the image to add the effect to
     */
    private void fading(ImageView image) {
        FadeTransition t = new FadeTransition(Duration.seconds(2), image);
        t.setFromValue(0.0);
        t.setToValue(1.0);
        t.play();
    }

    /**
     * Helper method which does fading out effect of an image
     * @param image the image to add the effect to
     */
    private void fadingOut(ImageView image) {
        FadeTransition t = new FadeTransition(Duration.seconds(1), image);
        t.setFromValue(1.0);
        t.setToValue(0.0);
        t.play();
    }
}