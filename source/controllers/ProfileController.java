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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Profile;
import model.Util;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;


public class ProfileController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ImageView profileImg; //TODO Once a get has been made this should be used
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
    private Label address;
    @FXML
    private Label lastLogin;

    private Profile selectedProfile;

    public void initProfile(Profile profile) {
        selectedProfile = profile;
        if (validate(selectedProfile.getUsername())) {
            setLastLogin();
           // Image
            //setProfileImg(Stage stage);

            getAddress();
            getContactNumber();
            getFirstName();
            getLastName();
            getPostCode();
            favouriteUser.setVisible(false);
        } else { //If validation doesn't work it sets both buttons to invisible for that profile
            setLastLogin();
            getAddress();
            getContactNumber();
            getFirstName();
            getLastName();
            getPostCode();
            browseDefault.setVisible(false);
            createCustom.setVisible(false);
            favouriteUser.setVisible(true);
        }
    }

    //TODO: Remove this temp method so it works when linked with Login Controller
    //Temp testing method. Uses lastLogin to read the username (bigbear1) to validate and test.

    private boolean validate(String string) {
        if (!validUser(string)) {
            selectedProfile.checkUsername("User not found");
            return false;
        } else {
            return true;
        }
    }

    private boolean validUser(String string) {
        return Util.checkAndSetUser(string);
    }

    @FXML
    private void setFavouriteUser() {

    }

    @FXML
    private void chooseProfileImg() {

/*        AnchorPane img = new AnchorPane();
        ImageView image = new ImageView(new Image(getClass().getResourceAsStream("\\images\\ProfileImage.png")));
        img.getChildren().add(image);

        Stage stage = new Stage();
        stage.setScene(new Scene(img, 640, 480));
        stage.show();*/

//Loading image from URL
//Image image = new Image(new FileInputStream("url for the image));

/*
        AnchorPane img = new AnchorPane();
        ImageView image = new ImageView(getClass().getResourceAsStream("\\images\\ProfileImage.png"));
        img.getChildren().add(image);

        Stage stage = new Stage();
        stage.setScene(new Scene(img, 640, 480));
        stage.show();*/


       // Button button = new Button(" ",imageView);
   //    button.setOnAction(ev -> ().showDocument(pathToOpen));



     /*  ImageView input = getClass().getResourceAsStream("\\images\\ProfileImage.png");
       Image image = new Image(input);
       ImageView profileImg = new ImageView(image);
        FlowPane root = new FlowPane();
        root.setPadding(new Insets(20));

        root.getChildren().addAll(profileImg);

        Stage stage = new Stage();

        stage.setScene(new Scene(root));
       stage.show();

*/
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

    private void setProfileImg( /*ImageView profileImg*/) {


       /* String path = "\\images\\ProfileImage.png";
        //   String pathToOpen = "\\images\\ProfileImage.png";

        Image image = new Image(path);
        ImageView imageView = new ImageView(image);
*/

//
//         AnchorPane img = new AnchorPane();
//         Stage stage = new Stage();

         //ImageView profileImg = new ImageView(new URL());
//             @Override
//             public int read() throws IOException {
//                 return 0;
//             }
        // })//getClass().getResourceAsStream("\\images\\ProfileImage.png")));

//         img.getChildren().add(profileImg);
//         stage.setScene(new Scene(profileImg))/;
//
//       File file = niewew File("\\images\\ProfileImage.png");
//        Image image = new Image(profileImg.setImage("\\images\\ProfileImage.png").toString());
      //  profileImg.setImage(image);
     //   Image img = new Image("\\images\\ProfileImage.png");
        //TODO: Might set profile from current path? Doesnt actually work/cant test due to no 'getter'
      /*  new img(getClass().getResource("Snake/Images/background_options.png"));*/

/*
        StackPane sp = new StackPane();
        Image img = new Image("\\images\\ProfileImage.png"); //TODO: Change this to a local path to save in src, images
        ImageView profileimg = new ImageView(img);
*/


    /*  Image img = new Image(imagePath);
        try {
            profileImg.setImage(img);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

   private void getProfileImg(){ //TODO: Wont take String as an image (profile)
       //profileImg.setImage(getProfileImg());
      // return profileImg.setImage(getProfileImg());
//profileImg.setImage(selectedProfile.setProfileImagePath("\\images\\ProfileImage.png"));
      //  profileImg.getImage(selectedProfile.getProfileImagePath());
    }

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


}
