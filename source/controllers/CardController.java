package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.Auction;
import model.Util;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CardController implements Initializable {

    @FXML
    private Hyperlink titleCardAuctionHyperlink;
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

    /**
     * Method which gets the auction and populates it
     * @param auction
     */
    public void getAuctionAndPopulate(Auction auction) {
        currentAuction = auction;
        titleCardAuctionHyperlink.setText(currentAuction.getArtwork().getTitle());
        if (currentAuction.getHighestPrice() < currentAuction.getReservePrice()) {
            priceCardAuctionLabel.setText(String.valueOf(currentAuction.getReservePrice()));
        } else {
            priceCardAuctionLabel.setText(String.valueOf(currentAuction.getHighestPrice()));
        }
        artistCardAuctionLabel.setText(currentAuction.getArtwork().getCreatorName());
        creationDateCardAuctionLabel.setText(String.valueOf(currentAuction.getArtwork().getCreationDate()));
        datePlacedCardAuctionLabel.setText(currentAuction.getDateTimePlaced().getDayOfMonth() + " " + currentAuction.getDateTimePlaced().getMonth().toString() + " " + currentAuction.getDateTimePlaced().getYear());
        setAuctionImage();
    }

    /**
     * Method which goes to the specific auction
     * @throws IOException If the auction is not found
     */
    @FXML
    private void goToAuctionAction() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/layouts/auction_view_layout.fxml"));
        AnchorPane profileLayout = loader.load();
        AuctionController controller = loader.getController();
        controller.initAuction(currentAuction);
        Util.getHomeLayout().setCenter(profileLayout);
    }

    /**
     * Method which sets the main Image of the auction
     */
    private void setAuctionImage() {
        try {
            cardAuctionImage.setImage(new Image(currentAuction.getArtwork().getMainImagePath()));
        } catch (Exception e) {
            System.out.println("NOT FOUND: " + currentAuction.getArtwork().getMainImagePath());
            System.out.println(" ON: " + currentAuction.getArtwork().getTitle());
        }
    }
}
