package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Profile;
import model.Util;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


public class ProfileController implements Initializable {

    @FXML
    private ImageView profileImg;
    @FXML
    private Button browseDefault;
    @FXML
    private Button createCustom;
    @FXML
    private Button favouriteUser;
    @FXML
    private Label postCode;
    @FXML
    private Label contactNumber;
    @FXML
    private Label firstName;
    @FXML
    private Label lastName;
    @FXML
    private Label addressLine1;
    @FXML
    private Label addressLine2;
    @FXML
    private Label city;
    @FXML
    private Label country;
    @FXML
    private Label lastLogin;
    @FXML
    private Label usernameLabelProfile;
    private Profile selectedProfile;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void initProfile(Profile profile) {
        selectedProfile = profile;
        setUserSpecificButtons();
        setLabels();
        try {
            setImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void chooseProfileImg() {

    }

    @FXML
    private void createCustomImg() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/CustomDrawing/sample.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void setFavouriteUser() {
        if (favouriteUser.getText().equalsIgnoreCase("Remove favorite")) {
            for (int i = 0; i < Util.getCurrentUser().getFavouriteUsers().size(); i++) {
                if (Util.getCurrentUser().getFavouriteUsers().get(i).equalsIgnoreCase(selectedProfile.getUsername())) {
                    //TODO GSON NEEDS TO BE UPDATED!!!
                    Util.getCurrentUser().getFavouriteUsers().remove(i);
                }
            }
            favouriteUser.setText("Add to favorites");
        } else {
            Util.getCurrentUser().getFavouriteUsers().add(selectedProfile.getUsername());
            favouriteUser.setText("Remove favorite");
        }
    }

    private boolean isFavorited() {
        boolean favorite = false;
        for (String elem : Util.getCurrentUser().getFavouriteUsers()) {
            if (elem.equalsIgnoreCase(selectedProfile.getUsername())) {
                favorite = true;
            }
        }
        return favorite;
    }

    private void setLabels() {
        usernameLabelProfile.setText(selectedProfile.getUsername());
        postCode.setText(selectedProfile.getPostcode());
        contactNumber.setText(selectedProfile.getPhoneNumber());
        firstName.setText(selectedProfile.getFirstName());
        lastName.setText(selectedProfile.getLastName());
        addressLine1.setText(selectedProfile.getAddressLine1());
        addressLine2.setText(selectedProfile.getAddressLine1());
        city.setText(selectedProfile.getCity());
        country.setText(selectedProfile.getCountry());
        lastLogin.setText(selectedProfile.getLastLogInTime().getHour() + ":" + selectedProfile.getLastLogInTime().getMinute() +
                                " " + selectedProfile.getLastLogInTime().getDayOfMonth() + "." + selectedProfile.getLastLogInTime().getMonthValue() +
                                    "." + selectedProfile.getLastLogInTime().getYear());
        if (!isSignedInUser()) {
            if (isFavorited()) {
                favouriteUser.setText("Remove favorite");
            } else {
                favouriteUser.setText("Add to favorites");
            }
        }
    }

    private void setImage() {
        Image profileImage = new Image(selectedProfile.getProfileImagePath());
        this.profileImg.setImage(profileImage);
    }

    private void setUserSpecificButtons() {
        if (isSignedInUser()) {
            browseDefault.setDisable(false);
            createCustom.setDisable(false);
            favouriteUser.setDisable(true);
            browseDefault.setVisible(true);
            createCustom.setVisible(true);
            favouriteUser.setVisible(false);
        } else {
            browseDefault.setDisable(true);
            createCustom.setDisable(true);
            favouriteUser.setDisable(false);
            browseDefault.setVisible(false);
            createCustom.setVisible(false);
            favouriteUser.setVisible(true);
        }
    }

    private boolean isSignedInUser() {
        return selectedProfile.getUsername().equalsIgnoreCase(Util.getCurrentUser().getUsername());
    }

}
