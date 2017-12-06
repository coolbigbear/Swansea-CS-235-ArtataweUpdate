package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import model.Auction;

import java.net.URL;
import java.util.ResourceBundle;

public class CardController implements Initializable {
    @FXML
    private Label titleCardAuctionLabel;
    @FXML
    private Label priceCardAuctionLabel;
    @FXML
    private Label artistCardAuctionLabel;
    @FXML
    private Label creationDateCardAuctionLabel;
    @FXML
    private Label datePlacedCardAuctionLabel;
    @FXML
    private ImageView cardAuctionImage;

    private Auction currentAuction;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void getAuctionAndPopulate(Auction auction) {
        currentAuction = auction;
        titleCardAuctionLabel.setText(currentAuction.getArtwork().getTitle());
        if (currentAuction.getHighestPrice() < currentAuction.getReservePrice()) {
            priceCardAuctionLabel.setText(String.valueOf(currentAuction.getReservePrice()));
        } else {
            priceCardAuctionLabel.setText(String.valueOf(currentAuction.getHighestPrice()));
        }
        artistCardAuctionLabel.setText(currentAuction.getArtwork().getCreatorName());
        creationDateCardAuctionLabel.setText(String.valueOf(currentAuction.getArtwork().getCreationDate().getYear()));
        datePlacedCardAuctionLabel.setText(currentAuction.getDateTimePlaced().getDayOfMonth() + " " + currentAuction.getDateTimePlaced().getMonth().toString() + " " + currentAuction.getDateTimePlaced().getYear());
        //add cardAuctionImage
    }
}
