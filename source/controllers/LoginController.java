package controllers;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.BCrypt;
import model.Profile;
import model.Util;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

/**
 * The Controller for the Login layout, this is in charge of <code>layouts.login_layout.fxml</code>.
 * <p>
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
     *
     * @param e event of the button
     * @throws IOException
     * @throws InterruptedException
     */
    @FXML
    private void loginButtonAction(ActionEvent e) throws IOException {
        if (validate(loginTextField.getText()) && validatePassword(loginTextFieldPassword.getText())) {
            stopImageThread();
            successfulLogin(e);
        }
    }

    /**
     * Button method which registers the user
     *
     * @param e The button event
     * @throws IOException
     */
    @FXML
    private void registerButtonAction(ActionEvent e) throws IOException {
        cycleImageThread.interrupt();
        Parent root = FXMLLoader.load(getClass().getResource("/layouts/register_layout.fxml"));
        //root.getStylesheets().add(Main.class.getResource("/css/home_layout.css").toExternalForm());
        Scene registerScene = new Scene(root);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(registerScene);
        stage.setResizable(false);
        stage.setMinWidth(680);
        stage.setMinHeight(468);
        stage.show();
    }

    @FXML
    private void forgotPassword() {
        Dialog dialog = new Dialog();
        dialog.setTitle("Reset your password");
        dialog.setHeaderText("Please enter your current username.\n" +
                "Then please enter your personal details");
        dialog.setResizable(false);

        final Label usernameLabel = new Label("Username: ");
        final Label firstNameLabel = new Label("First Name: ");
        final Label lastNameLabel = new Label("Last Name: ");
        final Label phoneNumberLabel = new Label("Phone Number: ");
        final Label errorLabel = new Label("Your old password doesn't match");
        final TextField usernameText = new TextField();
        final TextField firstNameText = new TextField();
        final TextField lastNameText = new TextField();
        final TextField phoneNumberText = new TextField();
        errorLabel.setVisible(false);

        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.add(usernameLabel, 1, 1);
        grid.add(usernameText, 2, 1);
        grid.add(firstNameLabel, 1, 2);
        grid.add(firstNameText, 2, 2);
        grid.add(lastNameLabel, 1, 3);
        grid.add(lastNameText, 2, 3);
        grid.add(phoneNumberLabel, 1, 4);
        grid.add(phoneNumberText, 2, 4);
        grid.add(errorLabel, 1, 5,2,2);
        dialog.getDialogPane().setContent(grid);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);
        final Button btOK = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        btOK.addEventFilter(
                ActionEvent.ACTION,
                event -> {
                    String usrname = usernameText.getText();
                    String firstName = firstNameText.getText();
                    String lastName = lastNameText.getText();
                    String phoneNumber = phoneNumberText.getText();
                    if (Util.checkAndSetUser(usrname)) {
                        Profile tempProfile = Util.getProfileByUsername(usrname);
                        if (tempProfile.getFirstName().equalsIgnoreCase(firstName)) {
                            if (tempProfile.getLastName().equalsIgnoreCase(lastName)) {
                                if (tempProfile.getPhoneNumber().equalsIgnoreCase(phoneNumber)) {
                                    errorLabel.setVisible(false);
                                } else {
                                    errorMsg(event,errorLabel);
                                }
                            } else {
                                errorMsg(event,errorLabel);
                            }
                        } else {
                            errorMsg(event,errorLabel);
                        }
                    } else {
                        errorMsg(event,errorLabel);
                    }
                });
        Optional result = dialog.showAndWait();
        if (result.isPresent()) {
            String newPassword = "bread";
            String newHash = BCrypt.hashpw(newPassword, BCrypt.gensalt());
            Util.updatePasswordOfUser(Util.getCurrentUser().getUsername(), newHash);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("Your password has been reset!\n" +
                    "Your new password is \"bread\" !\n" +
                    "Please change your password as soon as you log in!");
            alert.showAndWait();
        }
    }

    private void errorMsg(ActionEvent event, Label errorLabel) {
        event.consume();
        errorLabel.setVisible(true);
        errorLabel.setTextFill(Color.RED);
        errorLabel.setText("Those details don't match our records");
    }

    private void stopImageThread() {
        cycleImageThread.interrupt();
    }

    /**
     * Method to go to the main program (home layout)
     *
     * @param e Button event
     * @throws IOException
     */
    public void successfulLogin(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/layouts/home_layout.fxml"));
        root.getStylesheets().add(Main.class.getResource("/css/home_layout.css").toExternalForm());
        Scene homeScene = new Scene(root);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(homeScene);
        stage.setResizable(true);
        stage.setMinHeight(70);
        stage.setMinWidth(1250);
        stage.show();
    }

    /**
     * Method to validate user input
     *
     * @param input The user's input in login text field
     * @return
     */
    private boolean validate(String input) {
        if (!validLengthInput(input)) {
            loginUserPrompt.setText("Username too long!");
            return false;
        } else {
            if (!validUser(input)) {
                loginUserPrompt.setText("Username or password is incorrect");
                return false;
            } else {
                return true;
            }
        }
    }

    private boolean validatePassword(String password) {
        String hash = Util.getHashByUsername(loginTextField.getText());
        if (hash.equals("")) {
            return true;
        } else {
            if (BCrypt.checkpw(password, hash)) {
                return true;
            } else {
                loginUserPrompt.setText("Username or password is incorrect");
                return false;
            }
        }
    }

    /**
     * Helper method on validating the length of the user's input
     *
     * @param input String input to be checked
     * @return true of the input is of valid length, false if not
     */
    private boolean validLengthInput(String input) {
        return input.length() < 15;
    }

    /**
     * Method to verify user if he is the database or not
     *
     * @param input The string to be checked
     * @return true if the user is in the JSON file (registered), false if not
     */
    private boolean validUser(String input) {
        return Util.checkAndSetUser(input);
    }

    /**
     * Runnable to cycle through images and create fading in and out effect, animation, this is passed to the thread
     *
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
     *
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
     *
     * @param image the image to add the effect to
     */
    private void fadingOut(ImageView image) {
        FadeTransition t = new FadeTransition(Duration.seconds(1), image);
        t.setFromValue(1.0);
        t.setToValue(0.0);
        t.play();
    }
}