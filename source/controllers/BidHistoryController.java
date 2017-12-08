package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import model.Auction;
import model.Bid;
import model.Profile;
import model.Util;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BidHistoryController implements Initializable {

    @FXML
    private GridPane bidGridPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            populateBidGridPane();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //TODO divide this monster method into smaller ones or not
    private void populateBidGridPane() throws IOException {
        Label auctionID;
        Hyperlink auctionName;
        Label bidAmount;
        Label datePlaced;
        Label status;
        int currentRow = 0;
        final int GRID_ROWS = Util.getCurrentUser().getAllBidsPlaced().size();
        final int ID_COLUMN = 0;
        final int NAME_COLUMN = 1;
        final int BID_COLUMN = 2;
        final int DATE_COLUMN = 3;
        final int STATUS_COLUMN = 4;
        bidGridPane.addRow(GRID_ROWS);
        System.out.println("BID GRID ROWS: " + String.valueOf(GRID_ROWS));
        for (Bid elem : Util.getCurrentUser().getAllBidsPlaced()) {
            Auction auction = Util.getAuctionByAuctionID(elem.getAuctionID());

            auctionID = new Label();
            auctionID.setText(String.valueOf(auction.getAuctionID()));
            bidGridPane.add(auctionID, ID_COLUMN, currentRow);

            auctionName = new Hyperlink();
            auctionName.setText(auction.getArtwork().getTitle());
            auctionName.setOnAction(event -> {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/layouts/auction_view_layout.fxml"));
                try {
                    AnchorPane auctionLayout = (AnchorPane) loader.load();
                    AuctionController controller = loader.getController();
                    controller.initAuction(Util.getAuctionByAuctionID(elem.getAuctionID()));
                    Util.getHomeLayout().setCenter(auctionLayout);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            bidGridPane.add(auctionName, NAME_COLUMN, currentRow);

            bidAmount = new Label();
            bidAmount.setText(String.valueOf(elem.getBidAmount()));
            bidGridPane.add(bidAmount, BID_COLUMN, currentRow);

            datePlaced = new Label();
            datePlaced.setText(elem.getDateTimePlaced().getDayOfMonth() + "." + elem.getDateTimePlaced().getMonthValue() + "." + elem.getDateTimePlaced().getYear());
            bidGridPane.add(datePlaced, DATE_COLUMN, currentRow);

            status = new Label();
            status.setText(auctionStatus(Util.getAuctionByAuctionID(elem.getAuctionID())));
            bidGridPane.add(datePlaced, STATUS_COLUMN, currentRow);

            currentRow++;
        }
    }

    private String auctionStatus(Auction auction) {
        String status = "";
        if (auction.getHighestBidder().equalsIgnoreCase(Util.getCurrentUser().getUsername()) && auction.isCompleted()) {
            status = "WON";
        } else if (auction.isCompleted()) {
            status = "CLOSED";
        } else {
            status = "ONGOING";
        }
        return status;
    }
}
