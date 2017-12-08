package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.*;

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
        Util.setHomeLayout(homeLayout);
        Util.getActiveAuctions();
        auctionsFeed = Feed.getInstance();
        try {
            favoriteUsers = populateFavoriteUsers();
        } catch (IOException e) {
            e.printStackTrace();
        }
        welcomeLabel.setText(Util.getCurrentUser().getLastName());
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
        //TODO PROBLEM COMES FROM HERE
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
        System.out.print(Util.getCurrentUser());
        controller.initProfile(Util.getCurrentUser());
        homeLayout.setCenter(profileLayout);
    }

    @FXML
    private void logoutMenuItemAction(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/layouts/login_layout.fxml"));
        Scene login = new Scene(root);
        Stage stage = Util.getMainStage();
        stage.setScene(login);
        root.getStylesheets().add(ArtataweMain.class.getResource("/layouts/login.css").toExternalForm());
        stage.setMinHeight(480);
        stage.setMinWidth(640);
        stage.setHeight(480);
        stage.setWidth(640);
        stage.setResizable(false);
        stage.show();
    }

    //----------------------Auctions Bids----------------------

    //Auctions which you have placed Bids on and also current Auctions
    @FXML
    private void bidsPlacedMenuItemAction() throws IOException {
    	ArrayList<Bid> bidList = getAllBids();
	    Feed feed = Feed.getInstance();

	    for (Bid bid: bidList) {
	    	Auction auction = Util.getAuctionByAuctionID(bid.getAuctionID());

		    if (bid.getBidderUsername().equals(Util.getCurrentUser().getUsername()) &&
				    !auction.isCompleted()) {
			    feed.add(auction);
		    }
	    }


        setAuctionsCenter();
    }

    //Auctions that you have won and you finished them
    @FXML
    private void bidsWonMenuItemAction() throws IOException {
	    ArrayList<Bid> bidList = getAllBids();
	    Feed feed = Feed.getInstance();

	    for (Bid bid: bidList) {
		    Auction auction = Util.getAuctionByAuctionID(bid.getAuctionID());

		    if (bid.getBidderUsername().equals(Util.getCurrentUser().getUsername()) &&
				    auction.isCompleted() &&
				    auction.getHighestBidder().equals(Util.getCurrentUser().getUsername())) {
			    feed.add(auction);
		    }
	    }


	    setAuctionsCenter();
    }

    //All Auctions which you have placed Bids on
    @FXML
    private void allBidsMenuItemAction() throws IOException {

    	ArrayList<Bid> bidList = getAllBids();
    	Feed feed = Feed.getInstance();

	    for (Bid bid: bidList) {
		    Auction auction = Util.getAuctionByAuctionID(bid.getAuctionID());

    		if (bid.getBidderUsername().equals(Util.getCurrentUser().getUsername())) {
    			feed.add(auction);
		    }
	    }
    	// How to fill up the Feed UI with the new contents of Feed?
        setAuctionsCenter();
    }

    //--------------------Auctions selling---------------------

    //All Auctions that you are currently selling
    @FXML
    private void currentlySellingMenuItemAction() throws IOException {
    	Feed feed = Feed.getInstance();
    	ArrayList<Auction> resultList = new ArrayList<>();

    	for(Auction auction: feed) {
    		if(!auction.isCompleted() &&
				    auction.getSellerName().equals(Util.getCurrentUser().getUsername())) {
    			resultList.add(auction);
		    }
	    }
	    feed.updateWith(resultList);

        setAuctionsCenter();
    }

    //All Auctions you have sold
    @FXML
    private void auctionsSoldMenuItemAction() throws IOException {
	    Feed feed = Feed.getInstance();
	    ArrayList<Auction> resultList = new ArrayList<>();

	    for(Auction auction: feed) {
		    if(auction.isCompleted() &&
				    auction.getSellerName().equals(Util.getCurrentUser().getUsername())) {
			    resultList.add(auction);
		    }
	    }
	    feed.updateWith(resultList);

        setAuctionsCenter();
    }

    //All Auctions you have sold and are still selling
    @FXML
    private void allSellingSoldMenuItemAction() throws IOException {
	    Feed feed = Feed.getInstance();
	    ArrayList<Auction> resultList = new ArrayList<>();

	    for(Auction auction: feed) {
		    if(auction.getSellerName().equals(Util.getCurrentUser().getUsername())) {
			    resultList.add(auction);
		    }
	    }
	    feed.updateWith(resultList);

        setAuctionsCenter();
    }

    @FXML
    private void createSculptureButtonAction() throws IOException {
        AnchorPane profileLayout = (AnchorPane) FXMLLoader.load(getClass().getResource("/layouts/create_sculpture_layout.fxml"));
        homeLayout.setCenter(profileLayout);
    }

    @FXML
    private void createPaintingButtonAction() throws IOException {
        AnchorPane profileLayout = (AnchorPane) FXMLLoader.load(getClass().getResource("/layouts/create_painting_layout.fxml"));
        homeLayout.setCenter(profileLayout);
    }

    @FXML
    private void bidHistoryButtonAction() throws IOException {
        AnchorPane bidHistory = (AnchorPane) FXMLLoader.load(getClass().getResource("/layouts/bidhistory_layout.fxml"));
        homeLayout.setCenter(bidHistory);
    }

    private ArrayList<Bid> getAllBids() {

	    Util.getActiveAuctions();
	    Feed feed  = Feed.getInstance();
	    ArrayList<Bid> bidList = new ArrayList<>();

	    for (Auction auction: feed) {
		    bidList.addAll(auction.getBidList());
	    }
	    return bidList;
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