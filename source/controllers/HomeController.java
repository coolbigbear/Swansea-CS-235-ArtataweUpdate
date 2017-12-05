package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.Util;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private Label welcomeLabel;
    @FXML
    private ImageView profileImageView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        welcomeLabel.setText(Util.getCurrentUser().getFirstName() + Util.getCurrentUser().getLastName());
        setProfileImageView(Util.getCurrentUser().getProfileImagePath());
    }

    private void setProfileImageView(String imagePath) {
        Image img = new Image(imagePath);
        try {
            profileImageView.setImage(img);
        } catch (Exception e) {
            e.printStackTrace();
        }
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