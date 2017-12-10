package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
import java.util.List;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
	
	@FXML
	Button favouriteUsersAuctionsButton;
	@FXML
    private Label welcomeLabel;
    @FXML
    private ImageView profileImageView;
    @FXML
    private BorderPane homeLayout;
    @FXML
    private GridPane favoritesGridPane;
    private Feed feed;
    private ArrayList<Profile> favoriteUsers;
    private ChoiceBox choiceBox;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Util.setHomeLayout(homeLayout);
        Util.getActiveAuctions();
        feed = Feed.getInstance();
        favoriteUsers = populateFavoriteUsers();
        try {
            setProfileImageView(Util.getCurrentUser().getProfileImagePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        welcomeLabel.setText(Util.getCurrentUser().getLastName());
        try {
            setAuctionsCenter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        populateFavoritesView();
        choiceBox = Util.getFilterChoiceBox();
    }

    private void setProfileImageView(String imagePath) {
        Image img = new Image(imagePath);
        try {
            profileImageView.setImage(img);
            Util.setProfileImage(profileImageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void currentAuctionsButtonAction() throws IOException {
    	Util.getActiveAuctions();
    	feed = Feed.getInstance();
        setAuctionsCenter(); }

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
    public void logoutMenuItemAction(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/layouts/login_layout.fxml"));
        Scene login = new Scene(root);
        Stage stage = Util.getMainStage();
        stage.setScene(login);
        root.getStylesheets().add(ArtataweMain.class.getResource("/css/login.css").toExternalForm());
        stage.setMinHeight(519);
        stage.setMinWidth(656);
        stage.setHeight(519);
        stage.setWidth(656);
        //TODO: Magic numbers - Mike
        stage.setResizable(false);
        stage.show();
    }

    //----------------------Auctions Bids----------------------

    //Auctions which you have placed Bids on and also current Auctions
    @FXML
    private void auctionsPlacedMenuItemAction() throws IOException {
    	ArrayList<Bid> bidList = getAllBids();
    	Util.getAllAuctions();
	    feed = Feed.getInstance();
	    ArrayList<Auction> resultList = new ArrayList<>();

	    for (Bid bid: bidList) {
	    	Auction auction = Util.getAuctionByAuctionID(bid.getAuctionID());

		    if (bid.getBidderUsername().equals(Util.getCurrentUser().getUsername()) &&
				    !auction.isCompleted()) {
			    resultList.add(auction);
		    }
	    }
	    feed.updateWith(resultList);
        setAuctionsCenter();
    }

    //Auctions that you have won and you finished them
    @FXML
    private void auctionsWonMenuItemAction() throws IOException {
	    ArrayList<Bid> bidList = getAllBids();
	    Util.getAllAuctions();
	    Feed feed = Feed.getInstance();
	    ArrayList<Auction> resultList = new ArrayList<>();

	    for (Bid bid: bidList) {
		    Auction auction = Util.getAuctionByAuctionID(bid.getAuctionID());

		    if (bid.getBidderUsername().equals(Util.getCurrentUser().getUsername()) &&
				    auction.isCompleted() &&
				    Util.getCurrentUser().getUsername().equals(auction.getHighestBidder())) {
			    resultList.add(auction);
		    }
	    }
	    feed.updateWith(resultList);
	    setAuctionsCenter();
    }

    //--------------------Auctions selling---------------------

    //All Auctions that you are currently selling
    @FXML
    private void currentlySellingMenuItemAction() throws IOException {
    	Util.getActiveAuctions();
    	feed = Feed.getInstance();
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
    	Util.getAllAuctions();
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
    
    @FXML
    private void favouriteUsersAuctionsButtonOnAction() {
    	Util.getActiveAuctions();
    	feed = Feed.getInstance();
    	List<String> favouriteUsers = Util.getCurrentUser().getFavouriteUsers();
    	ArrayList<Auction> resultList = new ArrayList<>();
    	
    	for (Auction auction: feed) {
    		for (String user: favouriteUsers) {
    			if (auction.getSellerName().equals(user)) {
    				resultList.add(auction);
			    }
		    }
	    }
	    feed.updateWith(resultList);
	    try {
		    setAuctionsCenter();
	    } catch (IOException e) {
		    e.printStackTrace();
	    }
    }
    
    private ArrayList<Bid> getAllBids() {
	    Util.getAllAuctions();
	    Feed feed  = Feed.getInstance();
	    ArrayList<Bid> bidList = new ArrayList<>();

	    for (Auction auction: feed) {
		    bidList.addAll(auction.getBidList());
	    }
	    return bidList;
    }
    
    private BorderPane setAuctionsCenter() throws IOException {
        BorderPane feedLayout = (BorderPane) FXMLLoader.load(getClass().getResource("/layouts/feed_layout.fxml"));
        feedLayout.getStylesheets().add(ArtataweMain.class.getResource("/css/home_layout.css").toExternalForm());
        homeLayout.setCenter(feedLayout);
        return feedLayout;
    }

    private ArrayList<Profile> populateFavoriteUsers() {
        ArrayList<Profile> profiles = new ArrayList<>();
        for (String elem : Util.getCurrentUser().getFavouriteUsers()) {
            profiles.add(Util.getProfileByUsername(elem));
        }
        return profiles;
    }

    private void populateFavoritesView() {
        Util.dynamicFavoritesGridPane(favoritesGridPane, favoriteUsers);
        Util.setFavoriteUsersGridPane(favoritesGridPane);
    }
}