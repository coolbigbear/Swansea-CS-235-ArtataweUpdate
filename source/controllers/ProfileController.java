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
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import model.Profile;
import model.Util;
import java.net.URL;
import java.util.ResourceBundle;


public class ProfileController implements Initializable {

    @FXML
    private ImageView profileImg; //TODO Once a get has been made this should be used
    @FXML
    private Button browseDefault;
    @FXML
    private Button createCustom;
    @FXML
    private Label postCode;
    @FXML
    private Label contactNumber;
    @FXML
    private Label firstName;
    @FXML
    private Label lastName;
    @FXML
    private Label address;
    @FXML
    private Label lastLogin;
    private Profile selectedProfile;

    public void initProfile(Profile profile) {
        selectedProfile = profile;
        // MOVE STUFF TO LOAD HERE
        setLastLogin();
    }

    @FXML
    private void chooseProfileImg() {
        //TODO: Move this statement somewhere else to load the profile
        //Temp test method. Uses lastLogin to read the username (bigbear1) to validate and test. Wont be in a button when live
        if (validate(lastLogin.getText())) {
            setLastLogin();
            getAddress();
            getContactNumber();
            getFirstName();
            getLastName();
            getPostCode();
        } else { //If validation doesn't work it sets both buttons to invisible for that profile
            browseDefault.setVisible(false);
            createCustom.setVisible(false);
        }

    }

    @FXML
    private void createCustomImg() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/CustomDrawing/sample.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private void setProfileImg(Stage imagePath) {
        //TODO: Might set profile from current path? Doesnt actually work/cant test due to no 'getter'
        StackPane sp = new StackPane();
        Image img = new Image("C:\\Users\\Ben\\Documents\\University 2nd Year\\CS-230\\cs-230-assignment-3-artatawe\\source\\images\\test.png"); //TODO: Change this to a local path to save in src, images
        ImageView profileImg = new ImageView(img);
        Scene scene = new Scene(sp);
        imagePath.setScene(scene);
        imagePath.show();

    /*  Image img = new Image(imagePath);
        try {
            profileImg.setImage(img);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    /*private void getProfileImg(){ //TODO: Wont take String as an image (profile)
        profileImg.setImage(Util.getCurrentUser().getProfileImagePath());

    }*/

    private void setLastLogin() {
        lastLogin.setText("Last Login: " + selectedProfile.getLastLogInTime());

    }

    private void getFirstName(){
        firstName.setText(selectedProfile.getFirstName());

    }

    private void getLastName(){
        lastName.setText(selectedProfile.getLastName());

    }

    private void getPostCode(){
        postCode.setText(selectedProfile.getPostcode());

    }

    private void getContactNumber(){
        contactNumber.setText(selectedProfile.getPhoneNumber());

    }

    private void getAddress(){
        address.setText(selectedProfile.getAddressLine1() + "\n" + selectedProfile.getAddressLine2());

    }

    //TODO: Remove this temp method so it works when linked with Login Controller
    //Temp testing method. Uses lastLogin to read the username (bigbear1) to validate and test.

    private boolean validate(String string) {
                if (!validUser(string)) {
                    lastLogin.setText("User not found");
                    return false;
                } else {
                    return true;
                }
            }

    private boolean validUser(String string) {
        return Util.checkAndSetUser(string);

    }

}
