package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

/**
 * The Controller for the home layout, this is in charge of <code>layouts.home_layout.fxml</code>.
 *
 * This is the Controller and Layout pair in charge of keeping the left and top bars persistent and are the basic
 * containers of the whole program.
 *
 * @author Bezhan Kodomani
 * @author Bassam Helal
 * @author Iliyan Garnev
 * @version 2.0
 * @see Initializable
 */
public class HomeController implements Initializable {
	
	@FXML
	Button favouriteUsersAuctionsButton;
    @FXML
    Button userGalleryButton;
	@FXML
	private Label welcomeLabel;
	@FXML
	private ImageView profileImageView;
	@FXML
	private BorderPane homeLayout;
	@FXML
	private GridPane favoritesGridPane;
	@FXML
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

	//method to set the profile image on the top right corner
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
	public void logoutMenuItemAction(ActionEvent e) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("/layouts/login_layout.fxml"));
		Scene login = new Scene(root);
		Stage stage = Util.getMainStage();
		stage.setScene(login);
		root.getStylesheets().add(Main.class.getResource("/css/login.css").toExternalForm());
		stage.setMinHeight(519);
		stage.setMinWidth(656);
		stage.setHeight(519);
		stage.setWidth(656);
		stage.setResizable(false);
		stage.show();
	}
	
	//----------------------Auctions Bids----------------------
	
	
	//Auctions which you have placed Bids on and also current Auctions
	@FXML
	private void auctionsPlacedMenuItemAction() throws IOException {
		List<Bid> bidList = Util.getCurrentUser().getAllBidsPlaced();
		Util.getAllAuctions();
		feed = Feed.getInstance();
		ArrayList<Auction> resultList = new ArrayList<>();
		
		for (Bid bid : bidList) {
			Auction auction = Util.getAuctionByAuctionID(bid.getAuctionID());
			
			if (bid.getBidderUsername().equals(Util.getCurrentUser().getUsername()) &&
					!auction.isCompleted() && !resultList.contains(auction)) {
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
		
		for (Bid bid : bidList) {
			Auction auction = Util.getAuctionByAuctionID(bid.getAuctionID());
			
			if (auction.isCompleted() &&
					Util.getCurrentUser().getUsername().equals(auction.getHighestBidder())
					&& !resultList.contains(auction)) {
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
		Util.getAllAuctions();
		feed = Feed.getInstance();
		ArrayList<Auction> resultList = new ArrayList<>();
		
		for (Auction auction : feed) {
			if (!auction.isCompleted() &&
					auction.getSellerName().equals(Util.getCurrentUser().getUsername())
					&& !resultList.contains(auction)) {
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
		
		for (Auction auction : feed) {
			if (auction.isCompleted() &&
					auction.getSellerName().equals(Util.getCurrentUser().getUsername())
					&& !resultList.contains(auction)) {
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
	private void ViewDashboardButtonAction() throws IOException {
		AnchorPane profileLayout = (AnchorPane) FXMLLoader.load(getClass().getResource("/layouts/dashboard_layout.fxml"));
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
		
		for (Auction auction : feed) {
			for (String user : favouriteUsers) {
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

	//TODO Create galleries button where it loads the galleries page like does for "favourite auctions"
	@FXML
	private void galleryButtonOnAction() throws IOException {
		Util.getActiveAuctions();
		feed = Feed.getInstance();
//		List<Auction> userGalleries = Util.getCurrentUser().getUserGalleries();
//		ArrayList<Auction> resultList = new ArrayList<>();
//		feed.updateWith(resultList);
		try {
			setAuctionsCenter();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//method to get all the bids from auctions
	private ArrayList<Bid> getAllBids() {
		Util.getAllAuctions();
		Feed feed = Feed.getInstance();
		ArrayList<Bid> bidList = new ArrayList<>();
		
		for (Auction auction : feed) {
			bidList.addAll(auction.getBidList());
		}
		return bidList;
	}

	// method to set the centre to feed
	private BorderPane setAuctionsCenter() throws IOException {
		BorderPane feedLayout = (BorderPane) FXMLLoader.load(getClass().getResource("/layouts/feed_layout.fxml"));
		feedLayout.getStylesheets().add(Main.class.getResource("/css/home_layout.css").toExternalForm());
		homeLayout.setCenter(feedLayout);
		return feedLayout;
	}

	//helper method to get all the favorite users of a profile
	private ArrayList<Profile> populateFavoriteUsers() {
		ArrayList<Profile> profiles = new ArrayList<>();
		for (String elem : Util.getCurrentUser().getFavouriteUsers()) {
			profiles.add(Util.getProfileByUsername(elem));
		}
		return profiles;
	}

	//method to populate the dynamic favorite bar on the left
	private void populateFavoritesView() {
		Util.dynamicFavoritesGridPane(favoritesGridPane, favoriteUsers);
		Util.setFavoriteUsersGridPane(favoritesGridPane);
	}
}