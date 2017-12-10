package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.Profile;
import model.Util;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterController implements Initializable {

    /**
     *  Error label.
     */
    @FXML Label errorLabel;
    /**
     * The Username.
     */
    @FXML TextField username;
    /**
     * The First name.
     */
    @FXML TextField firstName;
    /**
     * The Last name.
     */
    @FXML TextField lastName;
    /**
     * The Contact number.
     */
    @FXML TextField contactNumber;
    /**
     * Address line one.
     */
    @FXML TextField addressLineOne;
    /**
     * Address line two.
     */
    @FXML TextField addressLineTwo;
    /**
     * City.
     */
    @FXML TextField city;
    /**
     * Country.
     */
    @FXML TextField country;
    /**
     * Post code.
     */
    @FXML TextField postCode;
    /**
     * Back button.
     */
    @FXML Button backButton;
    /**
     * Register button.
     */
    @FXML Button registerButton;

    /**
     * Variables pulled from text fields.
     */
    private String usernamePulled;
    private String firstNamePulled;
    private String lastNamePulled;
    private String addressLineOnePulled;
    private String addressLineTwoPulled;
    private String postCodePulled;
    private String cityPulled;
    private String countryPulled;
    private String contactNumberPulled;
    private String profileImagePath;
    private boolean choseImg = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /**
         * When register button is clicked
         */
        registerButton.setOnAction(e -> {

            //If all values entered correctly
            if(getTextFieldValues()) {

                //If user chose an image (required)
                if(choseImg) {
                    if (validCharacterInput(usernamePulled)) {
                        if (!Util.checkAndSetUser(usernamePulled)) {
                            Profile temp = Profile.createNewProfile(usernamePulled, firstNamePulled, lastNamePulled, contactNumberPulled,
                                    addressLineOnePulled, addressLineTwoPulled, cityPulled, countryPulled, postCodePulled, profileImagePath);
                            Util.saveNewProfileToFile(temp);
                            backAction();
                        } else {
                            errorLabel.setVisible(true);
                            errorLabel.setText("This username is already taken!");
                        }
                    } else {
                        errorLabel.setVisible(true);
                        errorLabel.setText("Only alphanumerics allowed!");
                    }
                } else {
                    errorLabel.setVisible(true);
                    errorLabel.setText("Please choose a profile picture!");
                }
            }
        });
    }

    @FXML
    private void setDefaultImage1() {
        changeDefaultImage("images/profile/male4.png");
    }
    @FXML
    private void setDefaultImage2() {
        changeDefaultImage("images/profile/male3.png");
    }
    @FXML
    private void setDefaultImage3() {
        changeDefaultImage("images/profile/female3.png");
    }
    @FXML
    private void setDefaultImage4() {
        changeDefaultImage("images/profile/female2.png");
    }

    private void changeDefaultImage(String defImagePath) {
        profileImagePath = defImagePath;
        choseImg = true;
    }

    @FXML
    private void backAction() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/layouts/login_layout.fxml"));
            Scene login = new Scene(root);
            Stage stage = Util.getMainStage();
            stage.setScene(login);
            root.getStylesheets().add(Main.class.getResource("/css/login.css").toExternalForm());
            stage.setMinHeight(519);
            stage.setMinWidth(656);
            stage.setHeight(519);
            stage.setWidth(656);
            //TODO: Magic numbers - Mike
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean getTextFieldValues(){
        try {
            usernamePulled = username.getText();
            firstNamePulled = firstName.getText();
            lastNamePulled = lastName.getText();
            addressLineOnePulled = addressLineOne.getText();
            addressLineTwoPulled = addressLineTwo.getText();
            postCodePulled = postCode.getText();
            cityPulled = city.getText();
            countryPulled = country.getText();
            contactNumberPulled = contactNumber.getText();

            if (firstNamePulled == null || Objects.equals(firstNamePulled, "")) {
                throw new IllegalArgumentException();
            }
            if (lastNamePulled == null || Objects.equals(lastNamePulled, "")) {
                throw new IllegalArgumentException();
            }
            if (addressLineOnePulled == null || Objects.equals(addressLineOnePulled, "")) {
                throw new IllegalArgumentException();
            }
            if (addressLineTwoPulled == null || Objects.equals(addressLineTwoPulled, "")) {
                throw new IllegalArgumentException();
            }
            if (postCodePulled == null || Objects.equals(postCodePulled, "")) {
                throw new IllegalArgumentException();
            }
            if (cityPulled == null || Objects.equals(cityPulled, "")) {
                throw new IllegalArgumentException();
            }
            if (countryPulled == null || Objects.equals(countryPulled, "")) {
                throw new IllegalArgumentException();
            }
            if (contactNumberPulled == null || Objects.equals(contactNumberPulled, "")) {
                throw new IllegalArgumentException();
            } else {
                return true;
            }

        } catch (IllegalArgumentException t) {
            errorLabel.setVisible(true);
            errorLabel.setTextFill(Color.RED);
            errorLabel.setText("Please check all fields are filled in correctly!");
            return false;
        }
    }

    private boolean validCharacterInput(String input) {
        //check if there are characters which are not alphanumerical
        Pattern pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);
        return !matcher.find();
    }

}

