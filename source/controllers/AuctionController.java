package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import model.*;
import model.exception.IllegalBidException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AuctionController implements Initializable {

	@FXML
	private Label auctionNameLabel;
	@FXML
	private Hyperlink sellerLink;
	@FXML
	private Label reservePriceLabel;
	@FXML
	private Label highestBidLabel;
	@FXML
	private TextField bidInputTextField;
	@FXML
	private Label descriptionLabel;
	@FXML
	private Label creatorNameLabel;
	@FXML
	private Label creationYearLabel;
	@FXML
	private Label dimensionsLabel;
	@FXML
	private Label mainMaterialLabel;
	@FXML
	private Label mainMaterialLabelConstant;
	@FXML
	private GridPane usersBidAuctionGridPane;
	@FXML
	private ScrollPane viewAuctionScrollPane;
	@FXML
	private Button addToFavoritesButton;
	@FXML
	private Label bidsLeftLabel;
	@FXML
	private GridPane sellingInfoGridPane;
	@FXML
	private Label placeBidLabel;
	@FXML
	private Button bidButton;
	private Auction currentAuction;
	private Artwork artwork;
	private ArtworkType artworkType;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
	}
	
	@FXML
	private void bidOnAction() {
		System.out.println("IS IT CORRECT: " + isTextFieldCorrect());
		if (isTextFieldCorrect()) {
			try {
				Bid bid = new Bid(currentAuction.getAuctionID(), Double.valueOf(bidInputTextField.getText()));
				currentAuction.placeBid(bid);

				//Below will execute if the placing of the Bid was accepted
				bidInputTextField.clear();
				bidInputTextField.setPromptText("Bid Accepted!");
				highestBidLabel.setText(bid.getBidAmount().toString());
				Util.saveAuctionToFile(currentAuction);
			} catch (IllegalBidException exception) {
				if (exception.getType().equals(IllegalBidException.IllegalBidType.ALREADY_HIGHEST_BIDDER)) {
					setErrorInputTextField("Already highest bidder!");
				}
				if (exception.getType().equals(IllegalBidException.IllegalBidType.LOWER_THAN_HIGHEST)) {
					setErrorInputTextField("Lower than current highest!");
				}
				if (exception.getType().equals(IllegalBidException.IllegalBidType.LOWER_THAN_RESERVE_PRICE)) {
					setErrorInputTextField("Lower than reserve price!");
				}
				//If entered input is not a Number
			} catch (NumberFormatException exception) {
				setErrorInputTextField("Please enter a Number!");
			}
		}
	}
	
	private void generateAuctionLabels() {
		auctionNameLabel.setText(artwork.getTitle());
		sellerLink.setText(currentAuction.getSellerName());
		reservePriceLabel.setText("£" + String.valueOf(currentAuction.getReservePrice()));
		highestBidLabel.setText("£" + String.valueOf(currentAuction.getHighestPrice()));
		bidInputTextField.setPromptText("Enter bid amount");
	}
	
	private void generateArtworkLabels() {
		if (artworkType.equals(ArtworkType.Painting)) {
			generateGeneralArtworkLabels();
			mainMaterialLabel.setVisible(false);
			mainMaterialLabelConstant.setVisible(false);
			dimensionsLabel.setText("Width: " + ((Painting) artwork).getWidth().toString() + " " +
					"Height: " + ((Painting) artwork).getHeight().toString());
		} else if (artworkType.equals(ArtworkType.Sculpture)) {
			generateGeneralArtworkLabels();
			dimensionsLabel.setText("Width: " + ((Sculpture) artwork).getWidth().toString() + " " +
					"Height: " + ((Sculpture) artwork).getHeight().toString() + " " +
					"Depth: " + ((Sculpture) artwork).getDepth().toString());
			mainMaterialLabelConstant.setVisible(true);
			mainMaterialLabel.setText(((Sculpture) artwork).getMainMaterial());
		}
	}
	
	private void generateGeneralArtworkLabels() {
		descriptionLabel.setText(artwork.getDescription().toString());
		creatorNameLabel.setText(artwork.getCreatorName());
		creationYearLabel.setText(String.valueOf(artwork.getCreationDate()));
	}
	
	// TODO: 09-Dec-17  Someone make the error look nicer than this shit!
	private void setErrorInputTextField(String message) {
		bidInputTextField.clear();
		bidInputTextField.setBackground(new Background(new BackgroundFill(Color.CRIMSON, new CornerRadii(0d),
				new Insets(0))));
		bidInputTextField.setPromptText(message);
	}
	
	public void initAuction(Auction auction) {
		currentAuction = auction;
		artwork = currentAuction.getArtwork();
		artworkType = currentAuction.getArtwork().getType();

		if (isFavorited()) {
			addToFavoritesButton.setText("Remove favorite");
		} else {
			addToFavoritesButton.setText("Add to favorites");
		}
		generateAuctionLabels();
		generateArtworkLabels();
		setSellerSpecificNodes();
	}
	
	private void populateUsersBidPane() {
		final int PROFILE_IMAGE_COLUMN = 0;
		final int PROFILE_USERNAME_COLUMN = 1;
		final int PROFILE_BID_AMOUNT_COLUMN = 2;
		final int PROFILE_TIME_PLACED = 3;
		final int PROFILE_IMAGE_SIZE = 20;
		usersBidAuctionGridPane.addRow(currentAuction.getBidList().size());
		int row = 1;
		ImageView profileImage;
		Hyperlink profileLink;
		Label bidAmount;
		Label timePlaced;
		for (Bid elem : currentAuction.getBidList()) {
			profileImage = new ImageView();
			profileImage.setFitHeight(PROFILE_IMAGE_SIZE);
			profileImage.setFitWidth(PROFILE_IMAGE_SIZE);
			try {
				profileImage.setImage(new Image(Util.getProfileByUsername(elem.getBidderUsername()).getProfileImagePath()));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			bidAmount = new Label();
			System.out.println("£" + String.valueOf(elem.getBidAmount()));
			bidAmount.setText("£" + String.valueOf(elem.getBidAmount()));
			
			
			profileLink = new Hyperlink();
			profileLink.setText(elem.getBidderUsername());
			
			
			profileLink.setOnAction(event -> {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(ArtataweMain.class.getResource("/layouts/profile_layout.fxml"));
				try {
					BorderPane profileLayout = (BorderPane) loader.load();
					ProfileController controller = loader.getController();
					controller.initProfile(Util.getProfileByUsername(elem.getBidderUsername()));
					Util.getHomeLayout().setCenter(profileLayout);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});

			timePlaced = new Label();
			timePlaced.setText(elem.getDateTimePlaced().getHour() + ":" + + elem.getDateTimePlaced().getMinute() + " " +
					elem.getDateTimePlaced().getDayOfMonth() + "." + elem.getDateTimePlaced().getMonthValue() + "." +
					elem.getDateTimePlaced().getYear());

			usersBidAuctionGridPane.add(profileImage,PROFILE_IMAGE_COLUMN,row);
			usersBidAuctionGridPane.add(profileLink,PROFILE_USERNAME_COLUMN,row);
			usersBidAuctionGridPane.add(bidAmount,PROFILE_BID_AMOUNT_COLUMN,row);
			usersBidAuctionGridPane.add(timePlaced,PROFILE_TIME_PLACED,row);

			row++;
		}
	}

	@FXML
	private void sellerLinkAction() {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(ArtataweMain.class.getResource("/layouts/profile_layout.fxml"));
		try {
			BorderPane profileLayout = (BorderPane) loader.load();
			ProfileController controller = loader.getController();
			controller.initProfile(Util.getProfileByUsername(currentAuction.getSellerName()));
			Util.getHomeLayout().setCenter(profileLayout);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void setSellerSpecificNodes() {
		if (Util.getCurrentUser().getUsername().equalsIgnoreCase(currentAuction.getSellerName())) {
			viewAuctionScrollPane.setVisible(true);
			usersBidAuctionGridPane.setVisible(true);
			populateUsersBidPane();
			addToFavoritesButton.setDisable(true);
			addToFavoritesButton.setVisible(false);
			bidsLeftLabel.setText("Bids left:");
			sellingInfoGridPane.add(new Label(String.valueOf(currentAuction.getBidsLeft())), 1, 1);
			placeBidLabel.setText("Highest bidder:");
			bidInputTextField.setDisable(true);
			bidInputTextField.setVisible(false);
			bidButton.setDisable(true);
			bidButton.setVisible(false);
			if (currentAuction.getHighestBidder() != null) {
				Hyperlink highestBidderProfile = new Hyperlink();
				highestBidderProfile.setText(currentAuction.getHighestBidder());
				highestBidderProfile.setOnAction(event -> {
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(ArtataweMain.class.getResource("/layouts/profile_layout.fxml"));
					try {
						BorderPane profileLayout = (BorderPane) loader.load();
						ProfileController controller = loader.getController();
						controller.initProfile(Util.getProfileByUsername(currentAuction.getHighestBidder()));
						Util.getHomeLayout().setCenter(profileLayout);
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
				sellingInfoGridPane.add(highestBidderProfile, 1, 4);
			}

		} else {
			usersBidAuctionGridPane.setVisible(false);
			viewAuctionScrollPane.setVisible(false);
		}
	}

	@FXML
	private void addToFavoritesButtonAction() {
		int counter = Util.getCurrentUser().getFavouriteUsers().size();
		if (addToFavoritesButton.getText().equalsIgnoreCase("Remove favorite")) {
			for (int i = 0; i < Util.getCurrentUser().getFavouriteUsers().size(); i++) {
				if (Util.getCurrentUser().getFavouriteUsers().get(i).equalsIgnoreCase(currentAuction.getSellerName())) {
					Util.getCurrentUser().getFavouriteUsers().remove(i);
				}
			}
			for (int i = counter -1; i >= 0; i--) {
				Util.deleteGridRow(Util.getFavoriteUsersGridPane(), i);
			}
			Util.dynamicFavoritesGridPane(Util.getFavoriteUsersGridPane(), populateFavoriteUsers());
			Util.saveProfileToFile(Util.getCurrentUser());
			addToFavoritesButton.setText("Add to favorites");
		} else {
			Util.getCurrentUser().getFavouriteUsers().add(currentAuction.getSellerName());
			for (int i = counter -1; i >= 0; i--) {
				Util.deleteGridRow(Util.getFavoriteUsersGridPane(), i);
			}
			Util.dynamicFavoritesGridPane(Util.getFavoriteUsersGridPane(), populateFavoriteUsers());
			Util.saveProfileToFile(Util.getCurrentUser());
			addToFavoritesButton.setText("Remove favorite");
		}
	}

	private boolean isFavorited() {
		boolean favorite = false;
		for (String elem : Util.getCurrentUser().getFavouriteUsers()) {
			if (elem.equalsIgnoreCase(currentAuction.getSellerName())) {
				favorite = true;
			}
		}
		return favorite;
	}

	private ArrayList<Profile> populateFavoriteUsers() {
		ArrayList<Profile> profiles = new ArrayList<>();
		for (String elem : Util.getCurrentUser().getFavouriteUsers()) {
			profiles.add(Util.getProfileByUsername(elem));
		}
		return profiles;
	}

	private boolean isTextFieldCorrect() {
		if (bidInputTextField.getText().length() <= 10) {
			try {
				return Double.parseDouble(bidInputTextField.getText()) < 2000000000;
			} catch (NumberFormatException e) {
				return false;
			}
		} else {
			return false;
		}
	}
}
