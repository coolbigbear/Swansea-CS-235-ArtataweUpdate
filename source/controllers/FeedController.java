package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import model.ArtworkType;
import model.Auction;
import model.Feed;
import model.Util;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class FeedController implements Initializable {
	
	@FXML
	private GridPane cardsGridPane;
	@FXML
	private ChoiceBox choiceBoxFilter;
	private ObservableList<String> choiceBoxList = FXCollections.observableArrayList("Show All", "Paintings",
			"Sculptures");
	private Feed feed;
	private ArrayList<Auction> currentlySelectedAuctions;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		choiceBoxFilter.setItems(choiceBoxList);
		choiceBoxFilter.setValue("Show All");
		feed = Feed.getInstance();
		System.out.println(feed.size());
		modifyCardGrid();
		try {
			populateCardGrid();
		} catch (IOException e) {
			e.printStackTrace();
		}
		setChoiceBox();
		Util.setFilterChoiceBox(choiceBoxFilter);
	}
	
	// TODO: 09-Dec-17 Bassam please fix this thing!!!
	private void setChoiceBox() {
		
		choiceBoxFilter.getSelectionModel().selectedIndexProperty().addListener(
				(observable, oldValue, newValue) -> {
					try {
						switch (newValue.intValue()) {
							case 0:
								filterAll();
								break;
							
							case 1:
								filterPaintings();
								break;
							
							case 2:
								filterSculptures();
								break;
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				});
	}
	
	private void filterAll() throws IOException {
		System.out.println("Show all: ");
		ArrayList<Auction> resultList = new ArrayList<>();
		Util.getActiveAuctions();
		feed = Feed.getInstance();
		for (Auction auction : feed) {
			if (auction.getArtwork().getType().equals(ArtworkType.Sculpture) ||
					auction.getArtwork().getType().equals(ArtworkType.Painting)) {
				resultList.add(auction);
				System.out.println("\t" + auction.getArtwork().getTitle());
			}
		}
		feed.updateWith(resultList);
		setAuctionsCenter();
	}
	
	private void filterPaintings() throws IOException {
		System.out.println("Paintings: ");
		ArrayList<Auction> resultList = new ArrayList<>();
		Util.getActiveAuctions();
		feed = Feed.getInstance();
		for (Auction auction : feed) {
			if (auction.getArtwork().getType().equals(ArtworkType.Painting)) {
				resultList.add(auction);
				System.out.println("\t" + auction.getArtwork().getTitle());
			}
		}
		feed.updateWith(resultList);
		setAuctionsCenter();
	}
	
	private void filterSculptures() throws IOException {
		System.out.println("Sculptures: ");
		ArrayList<Auction> resultList = new ArrayList<>();
		Util.getActiveAuctions();
		feed = Feed.getInstance();
		for (Auction auction : feed) {
			if (auction.getArtwork().getType().equals(ArtworkType.Sculpture)) {
				resultList.add(auction);
				System.out.println("\t" + auction.getArtwork().getTitle());
			}
		}
		feed.updateWith(resultList);
		setAuctionsCenter();
	}
	
	private void modifyCardGrid() {
		final int DEFAULT_NUMBER_OF_COLUMNS = 3;
		int numberOfRows = (int) Math.ceil(feed.size() / DEFAULT_NUMBER_OF_COLUMNS);
		cardsGridPane.addRow(numberOfRows);
	}
	
	private void populateCardGrid() throws IOException {
		FXMLLoader loader;
		AnchorPane cardLayout;
		int row = 0;
		int column = 0;
		for (Auction elem : feed) {
			loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/layouts/card_layout.fxml"));
			cardLayout = (AnchorPane) loader.load();
			CardController controller = loader.getController();
			controller.getAuctionAndPopulate(elem);
			cardsGridPane.add(cardLayout, column, row);
			if (column == 2) {
				column = 0;
				row++;
			} else {
				column++;
			}
		}
	}
	
	private void setAuctionsCenter() throws IOException {
		BorderPane feedLayout = (BorderPane) FXMLLoader.load(getClass().getResource("/layouts/feed_layout.fxml"));
		feedLayout.getStylesheets().add(ArtataweMain.class.getResource("/css/home_layout.css").toExternalForm());
		Util.getHomeLayout().setCenter(feedLayout);
	}
}
