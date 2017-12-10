package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import model.Auction;
import model.Bid;
import model.Util;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class BidHistoryController implements Initializable {
	
	private static int GRID_ROWS = Util.getCurrentUser().getAllBidsPlaced().size();
	private static final int ID_COLUMN = 0;
	private static final int NAME_COLUMN = 1;
	private static final int BID_COLUMN = 2;
	private static final int DATE_COLUMN = 3;
	private static final int STATUS_COLUMN = 4;
	private int currentRow = 1;
	private List<Bid> bids = Util.getCurrentUser().getAllBidsPlaced();
	
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
	
	private void populateBidGridPane() throws IOException {
		
		bidGridPane.addRow(GRID_ROWS);
		System.out.println("BID GRID ROWS: " + String.valueOf(GRID_ROWS));
		
		for (Bid elem : bids) {
			Auction auction = Util.getAuctionByAuctionID(elem.getAuctionID());
			
			Label auctionID = generateAuctionIDLabel(auction);
			bidGridPane.add(auctionID, ID_COLUMN, currentRow);
			
			Hyperlink auctionName = generateAuctionNameHyperLink(auction, elem);
			bidGridPane.add(auctionName, NAME_COLUMN, currentRow);
			
			Label bidAmount = generateBidAmountLabel(elem);
			bidGridPane.add(bidAmount, BID_COLUMN, currentRow);
			
			Label datePlaced = generateDatePlacedLabel(elem);
			bidGridPane.add(datePlaced, DATE_COLUMN, currentRow);
			
			Label status = generateStatusLabel(elem);
			
			generateStatusLabel(elem);
			
			bidGridPane.add(status, STATUS_COLUMN, currentRow);
			
			currentRow++;
		}
	}
	
	private Label generateAuctionIDLabel(Auction auction) {
		Label auctionID = new Label();
		auctionID.setText(String.valueOf(auction.getAuctionID()));
		return auctionID;
	}
	
	private Hyperlink generateAuctionNameHyperLink(Auction auction, Bid elem) {
		Hyperlink auctionName = new Hyperlink();
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
		return auctionName;
	}
	
	private Label generateBidAmountLabel(Bid elem) {
		Label bidAmount = new Label();
		bidAmount.setText(String.valueOf(elem.getBidAmount()));
		return bidAmount;
	}
	
	private Label generateDatePlacedLabel(Bid elem) {
		Label datePlaced = new Label();
		datePlaced.setText(elem.getDateTimePlaced().getHour() + ":" + + elem.getDateTimePlaced().getMinute() + " " +
				elem.getDateTimePlaced().getDayOfMonth() + "." + elem.getDateTimePlaced().getMonthValue() + "." +
				elem.getDateTimePlaced().getYear());
		return datePlaced;
	}
	
	private Label generateStatusLabel(Bid elem) {
		Label status = new Label();
		try {
			status.setText(auctionStatus(Util.getAuctionByAuctionID(elem.getAuctionID())));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return status;
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
