package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
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

/**
 * The Controller for the feed layout, this is in charge of <code>layouts.feed_layout.fxml</code>.
 * <p>
 * This is the Controller and Layout pair in charge of showing the Feed as well as the search bar,
 * this layout is in the center of {@link HomeController}.
 *
 * @author Bezhan Kodomani
 * @author Iliyan Garnev
 * @author ***REMOVED*** ***REMOVED***
 * @author Bassam Helal
 * @version 3.0
 * @see Initializable
 * @see HomeController
 */
public class FeedController implements Initializable {

    @FXML
    private GridPane cardsGridPane;
    @FXML
    private ChoiceBox choiceBoxFilter;
    @FXML
    private TextField searchBar;
    @FXML
    private Button searchButton;

    private ObservableList<String> choiceBoxList =
            FXCollections.observableArrayList("Show All", "Paintings", "Sculptures");
    private Feed feed;
    private ArrayList<Auction> currentlySelectedAuctions;

    /**
     * Method that calls the feed
     *
     * @param location  location of the page
     * @param resources actual resource used
     */
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

    /**
     * Method that allows you to use the search bar
     *
     * @throws IOException
     */
    public void searchButtonPress() throws IOException {
        search(searchBar.getText());
    }

    /**
     * Method that searches auctions depending on your search query
     *
     * @param searchQuery uses what you searched in to search through auctions
     * @throws IOException
     */
    public void search(String searchQuery) throws IOException {
        Util.getAuctionsByName(searchQuery);
        setAuctionsCenter();
    }

    /**
     * Method that reacts to when the enter key is pressed, and activates the search bar
     */
    @FXML
    public void onEnter() {
        searchBar.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                try {
                    search(searchBar.getText());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Method that allows you to filter out the feed to select items.
     */
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

    /**
     * Method that allows you to show all items on a feed
     *
     * @throws IOException
     */
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

    /**
     * Method that allows you to filter only paintings in the feed
     *
     * @throws IOException
     */
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

    /**
     * Method that allows you to filter out only sculptures in the feed
     *
     * @throws IOException
     */
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

    /**
     * Method that changes the card grid of items depending on the filter
     */
    private void modifyCardGrid() {
        final int DEFAULT_NUMBER_OF_COLUMNS = 3;
        int numberOfRows = (int) Math.ceil(feed.size() / DEFAULT_NUMBER_OF_COLUMNS);
        cardsGridPane.addRow(numberOfRows);
    }

    /**
     * Method that populates the card grid of items
     *
     * @throws IOException
     */
    private void populateCardGrid() throws IOException {
        FXMLLoader loader;
        AnchorPane cardLayout;
        int row = 0;
        int column = 0;
        for (Auction elem : feed) {
            loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/layouts/card_layout.fxml"));
            cardLayout = loader.load();
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

    /**
     * Method that sets the feed layout to center
     *
     * @throws IOException
     */
    private void setAuctionsCenter() throws IOException {
        BorderPane feedLayout = FXMLLoader.load(getClass().getResource("/layouts/feed_layout.fxml"));
        feedLayout.getStylesheets().add(Main.class.getResource("/css/home_layout.css").toExternalForm());
        Util.getHomeLayout().setCenter(feedLayout);
    }
}
