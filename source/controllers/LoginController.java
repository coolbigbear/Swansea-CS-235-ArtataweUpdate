package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Util;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginController implements Initializable {

    @FXML
    private TextField loginTextField;
    @FXML
    private Label loginUserPrompt;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void loginButtonAction(ActionEvent e) throws IOException {
        if (validate(loginTextField.getText())) {
            successfulLogin(e);
        }
    }

    @FXML
    private void registerButtonAction() {

    }

    private void successfulLogin(ActionEvent e) throws IOException {
        loginUserPrompt.setText("Welcome to Artatawe" + ", " + Util.getCurrentUser().getFirstName() + "!");
        Parent root = FXMLLoader.load(getClass().getResource("/layouts/home_layout.fxml"));
        root.getStylesheets().add(ArtataweMain.class.getResource("/css/home_layout.css").toExternalForm());
        Scene homeScene = new Scene(root);
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(homeScene);
        stage.setResizable(true);
        stage.setMinHeight(638);
        stage.setMinWidth(1183);
        stage.show();
    }

    private boolean validate(String input) {
        if (!validCharacterInput(input)) {
            loginUserPrompt.setText("Only alphanumerical values are allowed!");
            return false;
        } else {
            if (!validLengthInput(input)) {
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

}