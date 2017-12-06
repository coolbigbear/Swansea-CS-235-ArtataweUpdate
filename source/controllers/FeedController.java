package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import model.Auction;
import model.Feed;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class FeedController implements Initializable {

    @FXML
    private GridPane cardsGridPane;
    private Feed feed;
    private ArrayList<AnchorPane> cards;
    private static int DEFAULT_NUMBER_OF_COLUMNS = 3;
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

    private void modifyCardGrid() {
        int numberOfRows = (int) Math.ceil(feed.size()/DEFAULT_NUMBER_OF_COLUMNS);
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
            cardLayout= (AnchorPane) loader.load();
            CardController controller = loader.getController();
            controller.getAuctionAndPopulate(elem);
            cardsGridPane.add(cardLayout,column,row);
            if (column == 2) {
                column = 0;
                row++;
            } else {
                column++;
            }
        }
    }
}
