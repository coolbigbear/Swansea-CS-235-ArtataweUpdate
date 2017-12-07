package controllers;

import javafx.fxml.Initializable;
import model.Auction;

import java.net.URL;
import java.util.ResourceBundle;

public class AuctionController implements Initializable {

    //TODO view users to the seller who have placed bids on the auction!!!!!!!!!!!!!!!
    private Auction currentAuction;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public void initAuction(Auction auction) {
        currentAuction = auction;
    }
}
