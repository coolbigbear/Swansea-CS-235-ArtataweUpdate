package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
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
    private Feed feed;

    /**
     * Method that calls the feed
     *
     * @param location  location of the page
     * @param resources actual resource used
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        feed = Feed.getInstance();
        modifyCardGrid();
        try {
            populateCardGrid();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void updateFeed() {
        try {
            setAuctionsCenter();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sortFeed() {
        feed = Feed.getInstance();
        ArrayList<Auction> feedArrayList = feed.getAllAsArrayList();
        System.out.println(feedArrayList.get(1));
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
    public static void setAuctionsCenter() throws IOException {
        BorderPane feedLayout = FXMLLoader.load(FeedController.class.getResource("/layouts/feed_layout.fxml"));
        feedLayout.getStylesheets().add(Main.class.getResource("/css/home_layout.css").toExternalForm());
        Util.getHomeLayout().setCenter(feedLayout);
    }
}
