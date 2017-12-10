package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import model.*;
import model.exception.IllegalBidException;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AuctionController implements Initializable {

	@FXML
	private Label auctionNameLabel;
	@FXML
	private Label sellerLabel;
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
	private Auction currentAuction;
	private Artwork artwork;
	private ArtworkType artworkType;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	
	}
	
	@FXML
	private void bidOnAction() {
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
	
	private void generateAuctionLabels() {
		auctionNameLabel.setText(artwork.getTitle());
		sellerLabel.setText(currentAuction.getSellerName());
		reservePriceLabel.setText(String.valueOf(currentAuction.getReservePrice()));
		highestBidLabel.setText(String.valueOf(currentAuction.getHighestPrice()));
		bidInputTextField.setPromptText("Enter your bid amount...");
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
		
		generateAuctionLabels();
		generateArtworkLabels();
		if (Util.getCurrentUser().getUsername().equalsIgnoreCase(currentAuction.getSellerName())) {
			viewAuctionScrollPane.setVisible(true);
			usersBidAuctionGridPane.setVisible(true);
			populateUsersBidPane();
		} else {
			usersBidAuctionGridPane.setVisible(false);
			viewAuctionScrollPane.setVisible(false);
		}
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
}
