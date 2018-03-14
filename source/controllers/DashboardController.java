package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.image.ImageView;
import model.Auction;
import model.Bid;
import model.Painting;
import model.Util;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * The Controller for the Dashboard, this is in charge of <code>layouts.dashboard_layout.fxml</code>.
 *
 * This is the Controller and Layout pair in charge of the page that the user will use to visualise a users
 * individual statistics for their artatawe account.
 *
 * @author Alex Wing
 * @author Bezhan Kodomani
 * @author Iliyan Garnev
 * @version 0.2
 */
public class DashboardController implements Initializable {

	@FXML
	private PieChart pieChart;
	@FXML
	private LineChart<String, Number> lineChart;
	@FXML
	private BarChart<String, Number> barChart;
	@FXML
	private CategoryAxis xBarAxis;
	@FXML
	private NumberAxis yBarAxis;
	private List<Auction> boughtAuctions;
	private List<Auction> soldAuctions;
	private List<Auction> currentlySelling;
	private List<Bid> allBidsPlaced;


	public void initialize(URL location, ResourceBundle resources) {
		boughtAuctions = Util.getCurrentUser().getWonAuctions();
		soldAuctions = Util.getCurrentUser().getCompletedAuctions();
		allBidsPlaced = Util.getCurrentUser().getAllBidsPlaced();
		currentlySelling = Util.getCurrentUser().getCurrentlySelling();

		initBarChart(boughtAuctions, soldAuctions);
		initPieChart(soldAuctions);
		initLineChart(currentlySelling);
	}

	private void initLineChart(List<Auction> currentlySelling) {
		LocalDateTime today = LocalDateTime.now();
		LocalDateTime sevenDaysEarlier = today.minusDays(7);
		int[] countBidsOnDay = new int[7];
		for (Auction elem : currentlySelling) {
			for (Bid elem2 : elem.getBidList()) {
				if (elem2.getDateTimePlaced().compareTo(sevenDaysEarlier) > 0) {
					switch (elem2.getDateTimePlaced().getDayOfWeek().getValue()) {
						case 1 :
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
	}

	private void initPieChart(List<Auction> soldAuctions) {
		ArrayList<String> users = new ArrayList<>();

		for (Auction elem : soldAuctions) {
			users.add(elem.getHighestBidder());
		}

		HashMap<String, Integer> usersCount = new HashMap<>();

		for(String word: users) {
			Integer count = usersCount.get(word);
			usersCount.put(word, (count == null) ? 1 : count+1);
		}

		for (String key : usersCount.keySet()) {
			pieChart.getData().add(new PieChart.Data(key, usersCount.get(key)));
		}
	}

	private void initBarChart(List<Auction> boughtAuctions, List<Auction> soldAuctions) {
		int moneySpentPaintings = 0;
		int moneySpentSculptures = 0;
		int moneyEarnedPaintings = 0;
		int moneyEarnedSculptures = 0;

		for (Auction elem : boughtAuctions) {
			if (elem.getArtwork() instanceof Painting) {
				moneySpentPaintings = moneySpentPaintings +  (int) (double) elem.getBidList().get(elem.getBidList().size() - 1).getBidAmount();
			} else {
				moneySpentSculptures = moneySpentSculptures + (int) (double) elem.getBidList().get(elem.getBidList().size() - 1).getBidAmount();
			}
		}

		for (Auction elem : soldAuctions) {
			if (elem.getArtwork() instanceof Painting) {
				moneyEarnedPaintings = moneyEarnedPaintings +  (int) (double) elem.getBidList().get(elem.getBidList().size() - 1).getBidAmount();
			} else {
				moneyEarnedSculptures = moneyEarnedSculptures + (int) (double) elem.getBidList().get(elem.getBidList().size() - 1).getBidAmount();
			}
		}

		XYChart.Series<String, Number> paintingsSold = new XYChart.Series();
		XYChart.Series<String, Number> sculpturesSold = new XYChart.Series();
		XYChart.Series<String, Number> paintingsBought = new XYChart.Series();
		XYChart.Series<String, Number> sculpturesBought = new XYChart.Series();

		paintingsSold.getData().add(new XYChart.Data("?", moneyEarnedPaintings));
		sculpturesSold.getData().add(new XYChart.Data("?", moneyEarnedSculptures));
		paintingsBought.getData().add(new XYChart.Data("?", moneySpentPaintings));
		sculpturesBought.getData().add(new XYChart.Data("?", moneySpentSculptures));
		barChart.getData().add(paintingsSold);
		barChart.getData().add(sculpturesSold);
		barChart.getData().add(paintingsBought);
		barChart.getData().add(sculpturesBought);
	}
}

