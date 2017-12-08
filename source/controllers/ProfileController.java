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
    private Label address;
    @FXML
    private Label lastLogin;
    private Profile selectedProfile;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void initProfile(Profile profile) {
        selectedProfile = profile;
        setUserSpecificButtons();
        setLabels();
        //setImage();
    }

    @FXML
    private void chooseProfileImg() {
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
    private void createCustomImg() {

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
        postCode.setText(selectedProfile.getPostcode());
        contactNumber.setText(selectedProfile.getPhoneNumber());
        firstName.setText(selectedProfile.getFirstName());
        lastName.setText(selectedProfile.getLastName());
        address.setText(selectedProfile.getAddressLine1() + selectedProfile.getAddressLine2());
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

    //------------------------------------------------------------------------------------------------------------------
//    public void initProfile(Profile profile) {
//        selectedProfile = profile;
//        System.out.println(selectedProfile.getUsername());
//        if (validate(selectedProfile.getUsername())) {
//            setLastLogin();
//            getProfileImg();
//           // Image
//            //setProfileImg(Stage stage);
//
//            getAddress();
//            getContactNumber();
//            getFirstName();
//            getLastName();
//            getPostCode();
//            favouriteUser.setVisible(false);
//        } else { //If validation doesn't work it sets both buttons to invisible for that profile
//            setLastLogin();
//            getAddress();
//            getContactNumber();
//            getFirstName();
//            getLastName();
//            getPostCode();
//            browseDefault.setVisible(false);
//            createCustom.setVisible(false);
//            favouriteUser.setVisible(true);
//        }
//    }
//
//    //TODO: Remove this temp method so it works when linked with Login Controller
//    //Temp testing method. Uses lastLogin to read the username (bigbear1) to validate and test.
//
//    private boolean validate(String string) {
//        if (!validUser(string)) {
//            selectedProfile.checkUsername("");
//            return false;
//        } else {
//            return true;
//        }
//    }
//
//    private boolean validUser(String string) {
//        return true;
//    }
//
//    @FXML
//    private void setFavouriteUser() {
//
//
//    }
//
//    @FXML
//    private void chooseProfileImg() {
//
//
//
///*        AnchorPane root = new AnchorPane();
//        ImageView background = new ImageView(new Image.setProfileImg(("images/ProfileImage.png")));
//        root.getChildren().add(background);*/
//
///*        AnchorPane img = new AnchorPane();
//        ImageView image = new ImageView(new Image(getClass().getResourceAsStream("\\images\\ProfileImage.png")));
//        img.getChildren().add(image);
//
//        Stage stage = new Stage();
//        stage.setScene(new Scene(img, 640, 480));
//        stage.show();*/
//
////Loading image from URL
////Image image = new Image(new FileInputStream("url for the image));
//
///*
//        AnchorPane img = new AnchorPane();
//        ImageView image = new ImageView(getClass().getResourceAsStream("\\images\\ProfileImage.png"));
//        img.getChildren().add(image);
//
//        Stage stage = new Stage();
//        stage.setScene(new Scene(img, 640, 480));
//        stage.show();*/
//
//
//       // Button button = new Button(" ",imageView);
//   //    button.setOnAction(ev -> ().showDocument(pathToOpen));
//
//
//
//     /*  ImageView input = getClass().getResourceAsStream("\\images\\ProfileImage.png");
//       Image image = new Image(input);
//       ImageView profileImg = new ImageView(image);
//        FlowPane root = new FlowPane();
//        root.setPadding(new Insets(20));
//
//        root.getChildren().addAll(profileImg);
//
//        Stage stage = new Stage();
//
//        stage.setScene(new Scene(root));
//       stage.show();
//
//*/
//    }
//
//    @FXML
//    private void createCustomImg() {
//        try {
//            Parent root = FXMLLoader.load(getClass().getResource("/CustomDrawing/sample.fxml"));
//            Stage stage = new Stage();
//            stage.setScene(new Scene(root));
//            stage.show();
//        } catch(Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//
//
//    }
//
//    private void setProfileImg( /*ImageView profileImg*/) {
//
//        Image image = new Image("\\images\\ProfileImage.png");
//        profileImg.setImage(image);
//
//
//       /* String path = "\\images\\ProfileImage.png";
//        //   String pathToOpen = "\\images\\ProfileImage.png";
//
//        Image image = new Image(path);
//        ImageView imageView = new ImageView(image);
//*/
//
////
////         AnchorPane img = new AnchorPane();
////         Stage stage = new Stage();
//
//         //ImageView profileImg = new ImageView(new URL());
////             @Override
////             public int read() throws IOException {
////                 return 0;
////             }
//        // })//getClass().getResourceAsStream("\\images\\ProfileImage.png")));
//
////         img.getChildren().add(profileImg);
////         stage.setScene(new Scene(profileImg))/;
////
////       File file = niewew File("\\images\\ProfileImage.png");
////        Image image = new Image(profileImg.setImage("\\images\\ProfileImage.png").toString());
//      //  profileImg.setImage(image);
//     //   Image img = new Image("\\images\\ProfileImage.png");
//        //TODO: Might set profile from current path? Doesnt actually work/cant test due to no 'getter'
//      /*  new img(getClass().getResource("Snake/Images/background_options.png"));*/
//
///*
//        StackPane sp = new StackPane();
//        Image img = new Image("\\images\\ProfileImage.png"); //TODO: Change this to a local path to save in src, images
//        ImageView profileimg = new ImageView(img);
//*/
//
//
//    /*  Image img = new Image(imagePath);
//        try {
//            profileImg.setImage(img);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }*/
//    }
//
//   private void getProfileImg(){ //TODO: Wont take String as an image (profile)
//
//       profileImg.getImage();
//
//
//       //profileImg.setImage(getProfileImg());
//      // return profileImg.setImage(getProfileImg());
////profileImg.setImage(selectedProfile.setProfileImagePath("\\images\\ProfileImage.png"));
//      //  profileImg.getImage(selectedProfile.getProfileImagePath());
//    }
//
//    private void setLastLogin() {
//        lastLogin.setText("Last Login: " + selectedProfile.getLastLogInTime());
//
//
//    }
//
//    private void getFirstName(){
//        firstName.setText(selectedProfile.getFirstName());
//
//    }
//
//    private void getLastName(){
//        lastName.setText(selectedProfile.getLastName());
//
//    }
//
//    private void getPostCode(){
//        postCode.setText(selectedProfile.getPostcode());
//
//    }
//
//    private void getContactNumber(){
//        contactNumber.setText(selectedProfile.getPhoneNumber());
//
//    }
//
//    private void getAddress(){
//        address.setText(selectedProfile.getAddressLine1() + "\n" + selectedProfile.getAddressLine2());
//
//    }
//

}
