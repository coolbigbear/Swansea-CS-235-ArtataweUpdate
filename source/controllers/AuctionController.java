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
	@FXML
	private Label errorMessageLabel;
	@FXML
	private Label placedBidsLabel;
	@FXML
	private ImageView mainArtworkImage;
	@FXML
	private ImageView optionalImage1;
	@FXML
	private ImageView optionalImage2;
	@FXML
	private ImageView optionalImage3;
	@FXML
	private ImageView optionalImage4;
	private Auction currentAuction;
	private Artwork artwork;
	private ArtworkType artworkType;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
	}
	
	@FXML
	private void bidOnAction() {
		if (isTextFieldCorrect()) {
			try {
				Bid bid = new Bid(currentAuction.getAuctionID(), Double.valueOf(bidInputTextField.getText()));
				currentAuction.placeBid(bid);

				//Below will execute if the placing of the Bid was accepted
				bidInputTextField.clear();
				errorMessageLabel.setText("");
				bidInputTextField.setPromptText("Bid Accepted!");
				highestBidLabel.setText("£" + bid.getBidAmount().toString());
				
				//TODO If was the final Bid, inform user, add to won Auctions of the current user, and add this
				// Auction to the seller's completed Auctions list and remove it from his selling and then save all
				// this to database
				
				if(currentAuction.isCompleted()) {
					auctionWon();
					Profile seller = Util.getProfileByUsername(currentAuction.getSellerName());
					
					//Updates the seller's selling and sold and the current user's won
					seller.getCompletedAuctions().add(currentAuction);
					seller.getCurrentlySelling().remove(currentAuction);
					Util.getCurrentUser().getWonAuctions().add(currentAuction);
					
					//Saves everything to the database
					Util.saveProfileToFile(seller);
					Util.saveProfileToFile(Util.getCurrentUser());
				}
				
				Util.saveAuctionToFile(currentAuction);
				
				
			} catch (IllegalBidException exception) {
				if (exception.getType().equals(IllegalBidException.IllegalBidType.ALREADY_HIGHEST_BIDDER)) {
					errorMessageLabel.setText("Already highest bidder!");
				}
				if (exception.getType().equals(IllegalBidException.IllegalBidType.LOWER_THAN_HIGHEST)) {
					errorMessageLabel.setText("Lower than highest bidder!");
				}
				if (exception.getType().equals(IllegalBidException.IllegalBidType.LOWER_THAN_RESERVE_PRICE)) {
					errorMessageLabel.setText("Lower than reserve price!");
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
		if (currentAuction.isCompleted() && (currentAuction.getHighestBidder().equalsIgnoreCase(Util.getCurrentUser().getUsername()))) {
			setBuyerSpecificNodes();
		}
		if (currentAuction.isCompleted() && (!currentAuction.getHighestBidder().equalsIgnoreCase(Util.getCurrentUser().getUsername()))
				&& (!currentAuction.getSellerName().equalsIgnoreCase(Util.getCurrentUser().getUsername()))) {
			setViewerSpecificNodes();
		}
		setArtworkDisplayImages();
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
			placedBidsLabel.setText("Placed bids");
			viewAuctionScrollPane.setVisible(true);
			usersBidAuctionGridPane.setVisible(true);
			populateUsersBidPane();
			addToFavoritesButton.setDisable(true);
			addToFavoritesButton.setVisible(false);
			bidsLeftLabel.setText("Bids left:");
			sellingInfoGridPane.add(new Label(String.valueOf(currentAuction.getBidsLeft())), 1, 1);
			placeBidLabel.setText("Highest bidder:");
			if (currentAuction.isCompleted()) {
				placeBidLabel.setText("Winner:");
			}
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
				errorMessageLabel.setText("Bid is not numerical!");
				return false;
			}
		} else {
			errorMessageLabel.setText("Bid is too long!");
			return false;
		}
	}

	private void setBuyerSpecificNodes() {
			placeBidLabel.setText("Winner:");
			bidInputTextField.setDisable(true);
			bidInputTextField.setVisible(false);
			sellingInfoGridPane.add(new Label("YOU"), 1, 4);
			bidButton.setVisible(false);
			bidButton.setDisable(true);
	}

	private void setViewerSpecificNodes() {
		placeBidLabel.setText("STATUS:");
		bidInputTextField.setDisable(true);
		bidInputTextField.setVisible(false);
		sellingInfoGridPane.add(new Label("CLOSED"), 1, 4);
		bidButton.setVisible(false);
		bidButton.setDisable(true);
	}

	private void auctionWon() {
		setBuyerSpecificNodes();
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Message from Artatawe");
		alert.setHeaderText(currentAuction.getArtwork().getTitle());
		String s ="Congratulations, you have just won the auction! ";
		alert.setContentText(s);
		alert.show();
	}

	private void setArtworkDisplayImages() {
		if (currentAuction.getArtwork() instanceof Painting) {
			try {
				mainArtworkImage.setImage(new Image(currentAuction.getArtwork().getMainImagePath()));
			} catch (Exception e) {
				System.out.println("Image not found");
			}
		} else {
			mainArtworkImage.setImage(new Image(currentAuction.getArtwork().getMainImagePath()));
			Sculpture p = (Sculpture) currentAuction.getArtwork();
			try {
				switch (p.getAdditionalImagesPaths().size()) {
					case 0 :
						break;
					case 1:
						optionalImage1.setImage((new Image(p.getAdditionalImagesPaths().get(0))));
						break;
					case 2:
						optionalImage2.setImage((new Image(p.getAdditionalImagesPaths().get(1))));
						break;
					case 3:
						optionalImage3.setImage((new Image(p.getAdditionalImagesPaths().get(2))));
						break;
					case 4:
						optionalImage4.setImage((new Image(p.getAdditionalImagesPaths().get(3))));
						break;
				}
			} catch (Exception e) {
				System.out.println("Image not found");
			}
		}
	}
}
