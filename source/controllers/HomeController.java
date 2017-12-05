package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private Label welcomeLabel;
    @FXML
    private ImageView profileImageView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //setProfileImageView();
    }

    //when user changed profile in his profile settings, this needs to be updated
    public void setProfileImageView(String imagePath) {
        Image img = new Image(imagePath);
        profileImageView.setImage(img);
    }

    @FXML
    private void currentAuctionsButtonAction() {

    }

    @FXML
    private void myProfileMenuItemAction() {

    }

    @FXML
    private void logoutMenuItemAction() {

    }

    @FXML
    private void bidsPlacedMenuItemAction() {

    }

    @FXML
    private void bidsWonMenuItemAction() {

    }

    @FXML
    private void allBidsMenuItemAction() {

    }

    @FXML
    private void currentlySellingMenuItemAction() {

    }

    @FXML
    private void auctionsSoldMenuItemAction() {

    }

    @FXML
    private void allSellingSoldMenuItemAction() {

    }
}
