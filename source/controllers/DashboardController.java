package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import model.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.stage.Stage;


import javax.print.DocFlavor;
import javax.swing.plaf.ColorUIResource;
import javax.xml.soap.Text;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * The Controller for the Dahboard, this is in charge of <code>layouts.view_dashboard.fxml</code>.
 *
 * This is the Controller and Layout pair in charge of the page that the user will use to visualise a users individual statistics for their artatawe account.
 *
 * @author Alex Wing &
 * @version 0.0.033
 */
public class DashboardController implements Initializable {

	//final NumberAxis xAxis = new NumberAxis(1,81,1);
	//final NumberAxis yAxis = new NumberAxis();
	//final AreaChart<Number,Number> profit = new AreaChart<Number, Number>(xAxis,yAxis);

	/**
	 * The Error label.
	 */
	@FXML
	private ImageView profileImage;

//	@FXML
	//private Text title;

	//@FXML
	//private AreaChart profit;


	/**
	 * A reader for the user specific data... but i think we already have one. Ask bassam
	 */
	public void GetDataReader() {

	}


	/**
	 * This method is used to fill the data into the area graph to visualise the users..
	 */
	public void FillAreaGraph() {
	}

	public void FillLineGraph() {
	}

	public void FillPieChart() {
	}





	public void initialize(URL location, ResourceBundle resources) {
		try {
			setProfileImage(Util.getCurrentUser().getProfileImagePath());
		} catch (Exception e) {
			e.printStackTrace();
		}
//	//	try {
//	//		XYChart.Series seriesProfit = new XYChart.Series();
//	////		seriesProfit.setName("Profit");
//	//		seriesProfit.getData().add(new XYChart.Data(13, 2));
//	//		seriesProfit.getData().add(new XYChart.Data(4, 4));
//	//		seriesProfit.getData().add(new XYChart.Data(1, 2));
//	//		seriesProfit.getData().add(new XYChart.Data(5, 3));
//			seriesProfit.getData().add(new XYChart.Data(1, 4));
//			seriesProfit.getData().add(new XYChart.Data(65, 4));
//			seriesProfit.getData().add(new XYChart.Data(1, 9));
//			seriesProfit.getData().add(new XYChart.Data(1, 4));
//			profit.getData().addAll(seriesProfit);
//		}
//		catch (Exception e){
//			e.printStackTrace();
//		}
//	}method
		//method to set the profile image on the top right corner
	}
	private void setProfileImage(String imagePath) {
		Image img = new Image(imagePath);
		try {
			profileImage.setImage(img);
			Util.setProfileImage(profileImage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

