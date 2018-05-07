package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import model.Auction;
import model.Bid;
import model.Painting;
import model.Util;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;

/**
 * The Controller for the Dashboard, this is in charge of <code>layouts.dashboard_layout.fxml</code>.
 * <p>
 * This is the Controller and Layout pair in charge of the page that the user will use to visualise a users
 * individual statistics for their artatawe account.
 *
 * @author Alex Wing
 * @author Bezhan Kodomani
 * @author Iliyan Garnev
 * @version 1.0
 */
public class DashboardController implements Initializable {

    @FXML
    private PieChart pieChart;
    @FXML
    private LineChart<String, Number> lineChart;
    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private PieChart wonLostPieChart;
    @FXML
    private Label moneyEarned;
    @FXML
    private Label moneySpent;
    @FXML
    private Label profit;
    @FXML
    private Label auctionsWon;
    @FXML
    private Label totalBids;
    @FXML
    private ImageView dashboardProfileImage;

    private List<Auction> boughtAuctions;
    private List<Auction> soldAuctions;
    private List<Auction> currentlySelling;
    private List<Bid> allBidsPlaced;
    private double profitTotal;


    /**
     * Method used to execute all the methods that need to be initialized
     *
     * @param location
     * @param resources
     */
    public void initialize(URL location, ResourceBundle resources) {
        boughtAuctions = Util.getCurrentUser().getWonAuctions();
        soldAuctions = Util.getCurrentUser().getCompletedAuctions();
        allBidsPlaced = Util.getCurrentUser().getAllBidsPlaced();
        currentlySelling = Util.getCurrentUser().getCurrentlySelling();
        try {
            initPieChartWonLost(boughtAuctions, allBidsPlaced);
        } catch (IOException e) {
            e.printStackTrace();
        }
        initProfileImage();
        initTextData();
        initBarChart(boughtAuctions, soldAuctions);
        initPieChart(soldAuctions);
        initLineChart(Util.getActualCurrentlySelling(), soldAuctions);
    }


    /**
     * Method initializing all of the additional data that is being displayed in the text field
     */
    private void initTextData() {
        double moneySpent = 0;
        for (Auction elem : boughtAuctions) {
            moneySpent = moneySpent + elem.getBidList().get(elem.getBidList().size() - 1).getBidAmount();
        }
        double moneyEarned = 0;
        for (Auction elem : soldAuctions) {
            moneyEarned = moneyEarned + elem.getBidList().get(elem.getBidList().size() - 1).getBidAmount();
        }
        profitTotal = moneyEarned - moneySpent;
        totalBids.setText(String.valueOf(allBidsPlaced.size()));
        auctionsWon.setText(String.valueOf(boughtAuctions.size()));
        profit.setText("£" + String.valueOf(profitTotal));
        this.moneySpent.setText("£" + String.valueOf(moneySpent));
        this.moneyEarned.setText("£" + String.valueOf(moneyEarned));

        if (profitTotal == 0) {
            profit.setTextFill(Color.BLACK);
        } else if (profitTotal > 0) {
            profit.setTextFill(Color.GREEN);
        } else if (profitTotal < 0) {
            profit.setTextFill(Color.RED);
        }
    }

    /**
     * Method displaying the profile image of the currently logged in user
     */
    private void initProfileImage() {
        try {
            dashboardProfileImage.setImage(new Image(Util.getCurrentUser().getProfileImagePath()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method populating the pie chart, displaying the won and lost auctions
     *
     * @param boughtAuctions List of bought auctions
     * @param allBidsPlaced  List of bids placed
     * @throws IOException
     */
    private void initPieChartWonLost(List<Auction> boughtAuctions, List<Bid> allBidsPlaced) throws IOException {
        Set<Auction> auctionsLost = new HashSet<>();

        for (Bid elem : allBidsPlaced) {
            if (Util.getAuctionByAuctionID(elem.getAuctionID()).isCompleted() &&
                    !Util.getAuctionByAuctionID(elem.getAuctionID()).getHighestBidder()
                            .equalsIgnoreCase(Util.getCurrentUser().getUsername())) {
//				System.out.println(elem.getAuctionID());
                auctionsLost.add(Util.getAuctionByAuctionID(elem.getAuctionID()));
            }

//			System.out.println(Util.getAuctionByAuctionID(elem.getAuctionID()).isCompleted());
//			System.out.println(Util.getAuctionByAuctionID(elem.getAuctionID()).getHighestBidder());
        }

        wonLostPieChart.getData().add(new PieChart.Data("Won", boughtAuctions.size()));
        wonLostPieChart.getData().add(new PieChart.Data("Lost", auctionsLost.size()));
    }

    /**
     * Method populating the line chart, displaying the bids our auctions have received over a period of 7 days
     *
     * @param currentlySelling List of currently selling auctions
     * @param soldAuctions     List of sold auctions
     */
    private void initLineChart(List<Auction> currentlySelling, List<Auction> soldAuctions) {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime sevenDaysEarlier = today.minusDays(7);

        int[] countBidsOnDay = new int[7];
        ArrayList<Auction> myAuctions = new ArrayList<>();
        myAuctions.addAll(currentlySelling);
        myAuctions.addAll(soldAuctions);

        for (Auction auction : myAuctions) {
            for (Bid bid : auction.getBidList()) {
                if (bid.getDateTimePlaced().isAfter(sevenDaysEarlier)) {
                    switch (bid.getDateTimePlaced().getDayOfWeek().getValue()) {
                        case 1:
                            countBidsOnDay[0]++;
                            break;
                        case 2:
                            countBidsOnDay[1]++;
                            break;
                        case 3:
                            countBidsOnDay[2]++;
                            break;
                        case 4:
                            countBidsOnDay[3]++;
                            break;
                        case 5:
                            countBidsOnDay[4]++;
                            break;
                        case 6:
                            countBidsOnDay[5]++;
                            break;
                        case 7:
                            countBidsOnDay[6]++;
                            break;
                    }
                }
            }
        }

        XYChart.Series series = new XYChart.Series();
        series.getData().add(new XYChart.Data("Mon", countBidsOnDay[0]));
        series.getData().add(new XYChart.Data("Tue", countBidsOnDay[1]));
        series.getData().add(new XYChart.Data("Wed", countBidsOnDay[2]));
        series.getData().add(new XYChart.Data("Thu", countBidsOnDay[3]));
        series.getData().add(new XYChart.Data("Fri", countBidsOnDay[4]));
        series.getData().add(new XYChart.Data("Sat", countBidsOnDay[5]));
        series.getData().add(new XYChart.Data("Sun", countBidsOnDay[6]));
        lineChart.getData().add(series);
        lineChart.legendVisibleProperty().setValue(false);
    }

    /**
     * Method populating the pie chart displaying how many auctions a user has purchased from someone
     *
     * @param soldAuctions List of sold auctions
     */
    private void initPieChart(List<Auction> soldAuctions) {
        ArrayList<String> users = new ArrayList<>();

        for (Auction elem : soldAuctions) {
            users.add(elem.getHighestBidder());
        }

        HashMap<String, Integer> usersCount = new HashMap<>();

        for (String word : users) {
            Integer count = usersCount.get(word);
            usersCount.put(word, (count == null) ? 1 : count + 1);
        }

        for (String key : usersCount.keySet()) {
            pieChart.getData().add(new PieChart.Data(key, usersCount.get(key)));
        }
    }

    /**
     * Method populating the bar chart, displaying information about earnings and spendings
     *
     * @param boughtAuctions List of bought auctions
     * @param soldAuctions   List of sold auctions
     */
    private void initBarChart(List<Auction> boughtAuctions, List<Auction> soldAuctions) {
        int moneySpentPaintings = 0;
        int moneySpentSculptures = 0;
        int moneyEarnedPaintings = 0;
        int moneyEarnedSculptures = 0;

        for (Auction elem : boughtAuctions) {
            if (elem.getArtwork() instanceof Painting) {
                moneySpentPaintings = moneySpentPaintings + (int) (double) elem.getBidList().get(elem.getBidList().size() - 1).getBidAmount();
            } else {
                moneySpentSculptures = moneySpentSculptures + (int) (double) elem.getBidList().get(elem.getBidList().size() - 1).getBidAmount();
            }
        }

        for (Auction elem : soldAuctions) {
            if (elem.getArtwork() instanceof Painting) {
                moneyEarnedPaintings = moneyEarnedPaintings + (int) (double) elem.getBidList().get(elem.getBidList().size() - 1).getBidAmount();
            } else {
                moneyEarnedSculptures = moneyEarnedSculptures + (int) (double) elem.getBidList().get(elem.getBidList().size() - 1).getBidAmount();
            }
        }

        XYChart.Series<String, Number> paintingsSold = new XYChart.Series();
        XYChart.Series<String, Number> sculpturesSold = new XYChart.Series();
        XYChart.Series<String, Number> paintingsBought = new XYChart.Series();
        XYChart.Series<String, Number> sculpturesBought = new XYChart.Series();


        paintingsSold.getData().add(new XYChart.Data("", moneyEarnedPaintings));
        sculpturesSold.getData().add(new XYChart.Data("", moneyEarnedSculptures));
        paintingsBought.getData().add(new XYChart.Data("", moneySpentPaintings));
        sculpturesBought.getData().add(new XYChart.Data("", moneySpentSculptures));
        barChart.getData().add(paintingsSold);
        barChart.getData().add(sculpturesSold);
        barChart.getData().add(paintingsBought);
        barChart.getData().add(sculpturesBought);
        paintingsSold.setName("£ earned p");
        sculpturesSold.setName("£ earned s");
        paintingsBought.setName("£ spent p");
        sculpturesBought.setName("£ spent s");
    }
}

