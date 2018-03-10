package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The Controller for the Dashboard, this is in charge of <code>layouts.view_dashboard.fxml</code>.
 *
 * This is the Controller and Layout pair in charge of the page that the user will use to visualise a users
 * individual statistics for their artatawe account.
 *
 * @author Alex Wing, Bezhan Kodomani, Iliyan Garnev
 * @version 0.2
 */
public class DashboardController implements Initializable {

	@FXML
	private ImageView profileImage;

	public void initialize(URL location, ResourceBundle resources) {
//		try {
//			setProfileImage(Util.getCurrentUser().getProfileImagePath());
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		try {
//			XYChart.Series seriesProfit = new XYChart.Series();
//			seriesProfit.setName("Profit");
//			seriesProfit.getData().add(new XYChart.Data(13, 2));
//			seriesProfit.getData().add(new XYChart.Data(4, 4));
//			seriesProfit.getData().add(new XYChart.Data(1, 2));
//			seriesProfit.getData().add(new XYChart.Data(5, 3));
//			seriesProfit.getData().add(new XYChart.Data(1, 4));
//			seriesProfit.getData().add(new XYChart.Data(65, 4));
//			seriesProfit.getData().add(new XYChart.Data(1, 9));
//			seriesProfit.getData().add(new XYChart.Data(1, 4));
//			profit.getData().addAll(seriesProfit);
//		}
//		catch (Exception e){
//			e.printStackTrace();
//		}
	}

}

