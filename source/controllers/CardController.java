package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
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

    public void getAuctionAndPopulate(Auction auction) {
        currentAuction = auction;
        titleCardAuctionHyperlink.setText(currentAuction.getArtwork().getTitle());
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

    @FXML
    private void goToAuctionAction() throws IOException {
        //TODO NOT COMPLETED
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/layouts/auction_view_layout.fxml"));
        AnchorPane profileLayout = (AnchorPane) loader.load();
        AuctionController controller = loader.getController();
        controller.initAuction(currentAuction);
        Util.getHomeLayout().setCenter(profileLayout);
    }
}