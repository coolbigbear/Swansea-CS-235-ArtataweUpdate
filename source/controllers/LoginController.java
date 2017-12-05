package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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
    private void loginButtonAction() {
        if (validate(loginTextField.getText())) {
            successfulLogin();
        }
    }

    private void successfulLogin() {

    }

    private boolean validate(String input) {
        if (!validCharacterInput(input)) {
            loginUserPrompt.setText("Only alphanumerical values are allowed");
            return false;
        } else {
            if (!validLengthInput(input)) {
                loginUserPrompt.setText("Username too long");
                return false;
            } else {
                if (!validUser(input)) {
                    loginUserPrompt.setText("User not found");
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
        return false;
    }

}