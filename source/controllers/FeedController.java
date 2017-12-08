package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
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
    @FXML
    private ChoiceBox choiceBoxFilter;
    private ObservableList<String> choiceBoxList = FXCollections.observableArrayList("Show All", "Paintings", "Sculptures");
    private Feed feed;

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
    }

    private void modifyCardGrid() {
        final int DEFAULT_NUMBER_OF_COLUMNS = 3;
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
            cardLayout = (AnchorPane) loader.load();
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
