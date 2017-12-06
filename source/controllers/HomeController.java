package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import model.Auction;
import model.Feed;
import model.Profile;
import model.Util;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private Label welcomeLabel;
    @FXML
    private ImageView profileImageView;
    @FXML
    private BorderPane homeLayout;
    @FXML
    private GridPane favoritesGridPane;
    private Feed auctionsFeed;
    private ArrayList<Profile> favoriteUsers;
    private Profile selectedProfile;
    private Auction selectedAuction;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Util.getActiveAuctions();
        auctionsFeed = Feed.getInstance();
        try {
            favoriteUsers = populateFavoriteUsers();
        } catch (IOException e) {
            e.printStackTrace();
        }
        welcomeLabel.setText(Util.getCurrentUser().getFirstName() + " " + Util.getCurrentUser().getLastName());
        //setProfileImageView(Util.getCurrentUser().getProfileImagePath());
        try {
            setAuctionsCenter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        populateFavoritesView();
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
    private void currentAuctionsButtonAction() throws IOException {
        setAuctionsCenter();
    }

    @FXML
    private void myProfileMenuItemAction() throws IOException {
//        BorderPane profileLayout = (BorderPane) FXMLLoader.load(getClass().getResource("/layouts/profile_layout.fxml"));
//        homeLayout.setCenter(profileLayout);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/layouts/profile_layout.fxml"));
        BorderPane profileLayout = (BorderPane) loader.load();
        ProfileController controller = loader.getController();
        controller.initProfile(Util.getCurrentUser());
        homeLayout.setCenter(profileLayout);
    }

    @FXML
    private void logoutMenuItemAction() {

    }
    
    
    //Auctions which you have placed Bids on and also current Auctions
    @FXML
    private void bidsPlacedMenuItemAction() throws IOException {
    	Feed feed = Feed.getInstance();
    	Util.getActiveAuctions();
        setAuctionsCenter();
    }

    //Auctions that you have won and you finished them
    @FXML
    private void bidsWonMenuItemAction() throws IOException {
        setAuctionsCenter();
    }

    
    //All Auctions which you have placed Bids on
    @FXML
    private void allBidsMenuItemAction() throws IOException {
        setAuctionsCenter();
    }

    @FXML
    private void currentlySellingMenuItemAction() throws IOException {
        setAuctionsCenter();
    }

    @FXML
    private void auctionsSoldMenuItemAction() throws IOException {
        setAuctionsCenter();
    }

    @FXML
    private void allSellingSoldMenuItemAction() throws IOException {
        setAuctionsCenter();
    }

    @FXML
    private void createAuctionButtonAction() {

    }

    private BorderPane setAuctionsCenter() throws IOException {
        BorderPane feedLayout = (BorderPane) FXMLLoader.load(getClass().getResource("/layouts/feed_layout.fxml"));
        homeLayout.setCenter(feedLayout);
        return feedLayout;
    }

    private ArrayList<Profile> populateFavoriteUsers() throws IOException {
        ArrayList<Profile> profiles = new ArrayList<>();
        for (String elem : Util.getCurrentUser().getFavouriteUsers()) {
            profiles.add(Util.getProfileByUsername(elem));
        }
        return profiles;
    }



    private void populateFavoritesView() {
        //TODO ADD IMAGES
        int IMAGE_COLUMN = 0;
        int PROFILE_COLUMN = 1;
        int row = 0;
        Hyperlink favoriteUser;
        favoritesGridPane.addRow(favoriteUsers.size());
        for (Profile elem : favoriteUsers) {
            favoriteUser = new Hyperlink();
            favoriteUser.setText(elem.getUsername());
            favoriteUser.setOnAction(event -> {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/layouts/profile_layout.fxml"));
                try {
                    BorderPane profileLayout = (BorderPane) loader.load();
                    ProfileController controller = loader.getController();
                    controller.initProfile(elem);
                    homeLayout.setCenter(profileLayout);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            favoritesGridPane.add(favoriteUser,PROFILE_COLUMN,row);
            row++;
        }
    }
}