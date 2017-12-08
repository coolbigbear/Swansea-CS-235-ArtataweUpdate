package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import model.*;
import model.exception.IllegalBidException;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

//TODO view users to the seller who have placed bids on the auction!!!!!!!!!!!!!!!
public class AuctionController implements Initializable {
	
	
	private Auction currentAuction;
	private Artwork artwork;
	private ArtworkType artworkType;
	
	@FXML
	Label sellerLabel;
	@FXML
	Label reservePriceLabel;
	@FXML
	Label highestBidLabel;
	@FXML
	Button bidButton;
	@FXML
	TextField bidInputTextField;
	@FXML
	Label descriptionLabel;
	@FXML
	Label creatorNameLabel;
	@FXML
	Label creationYearLabel;
	@FXML
	Label dimensionsLabel;
	@FXML
	Label mainMaterialLabel;
	@FXML
	Label mainMaterialLabelConstant;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		//hardcoded Auction!
		try {
			initAuction(Util.getAuctionByAuctionID(3));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		generateAuctionLabels();
		setBidOnClickListener();
		generateArtworkLabels();
	}
	
	private void generateAuctionLabels() {
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
		creationYearLabel.setText(String.valueOf(artwork.getCreationDate().getYear()));
	}
	
	// TODO: 08-Dec-17 Something isn't working here
	private void setBidOnClickListener() {
		bidButton.setOnAction(e -> {
			try {
				Bid bid = new Bid(currentAuction.getAuctionID(), Double.valueOf(bidInputTextField.getText()));
				currentAuction.placeBid(bid);
				System.out.println("Bid accepted of amount " + bid.getBidAmount());
			} catch (IllegalBidException exception) {
				if (exception.getType().equals(IllegalBidException.IllegalBidType.ALREADY_HIGHEST_BIDDER)) {
					setErrorInputTextField("Already highest bidder!");
				}
				if (exception.getType().equals(IllegalBidException.IllegalBidType.LOWER_THAN_RESERVE_PRICE)) {
					setErrorInputTextField("Lower than reserve price!");
				}
				if (exception.getType().equals(IllegalBidException.IllegalBidType.LOWER_THAN_HIGHEST)) {
					setErrorInputTextField("Lower than current highest!");
				}
				//If entered input is not a Number
			} catch (NumberFormatException exception) {
				setErrorInputTextField("Please enter a Number!");
			}
		});
		// TODO: 08-Dec-17 Send the Bid to database
	}
	
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
		
	}
}
