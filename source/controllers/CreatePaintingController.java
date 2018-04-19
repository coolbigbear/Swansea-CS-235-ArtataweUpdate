package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import model.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * The Controller for the Create Painting layout, this is in charge of <code>layouts.create_painting_layout.fxml</code>.
 *
 * This is the Controller and Layout pair in charge of the page that the user will use to create a new Painting Auction.
 *
 * @author ***REMOVED*** ***REMOVED***
 * @version 1.1
 * @see Initializable
 * @see Auction
 * @see Painting
 */
public class CreatePaintingController implements Initializable {
	
	/**
	 * The Error label.
	 */
	@FXML
	private Label errorLabel;

	/**
	 * The Artwork description.
	 */
	@FXML
	private TextArea artworkDescription;

	/**
	 * The Artwork title.
	 */
	@FXML
	private TextField artworkTitle;

	/**
	 * The Painting height.
	 */
	@FXML
	private TextField paintingHeight;

	/**
	 * The Painting width.
	 */
	@FXML
	private TextField paintingWidth;

	/**
	 * The Reserve price.
	 */
	@FXML
	private TextField reservePrice;

	/**
	 * The No of bids allowed.
	 */
	@FXML
	private TextField noOfBidsAllowed;

	/**
	 * The Year of creation.
	 */
	@FXML
	private TextField yearOfCreation;

	/**
	 * The Creator name.
	 */
	@FXML
	private TextField creatorName;

	/**
	 * Browse for main photo button.
	 */
	@FXML
	private Button browseForMainPhoto;

	/**
	 * Sell painting button.
	 */
	@FXML
	private Button sellPaintingButton;
	
	/**
	 * Variables pulled from text fields.
	 */
	private String artworkTitlePulled;
	private String descriptionPulled;
	private String nameOfCreatorPulled;
	private String yearOfCreationPulled;
	private String artImgPath;
	
	private Double reservePricePulled;
	
	private Integer numberOfBidsAllowedPulled;
	private Integer paintingWidthPulled;
	private Integer paintingHeightPulled;

	/**
	 * Boolean to check if user chose img.
	 */
	private Boolean imgChosen = false;

	/**
	 * Method which initializes the Painting Controller
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		sellPaintingButton.setOnAction(e -> {
			
			//If all values entered correctly
			if (getTextFieldValues()) {
				
				//If user chose an image (required)
				if (imgChosen) {
					
					//Creates temporary auction to be added to database and feed
					Artwork tempPainting = new Painting(artworkTitlePulled, new StringBuilder(descriptionPulled),
							yearOfCreationPulled, nameOfCreatorPulled, artImgPath, paintingWidthPulled, paintingHeightPulled);
					
					Auction tempAuction = Auction.createNewAuction(tempPainting, Util.getCurrentUser().getUsername(), numberOfBidsAllowedPulled, reservePricePulled);
					
					//Adds auction to feed
					Feed.getInstance().add(tempAuction);
					//Adds auction to database
					Util.saveListOfAuctionsToFile(Feed.getInstance().getAllAsArrayList());
					
					
					Util.saveProfileToFile(Util.getCurrentUser());
					
					//Load the homepage back up
					try {
						BorderPane feedLayout = FXMLLoader.load(getClass().getResource("/layouts/feed_layout.fxml"));
						feedLayout.getStylesheets().add(Main.class.getResource("/css/home_layout.css").toExternalForm());
						Util.getHomeLayout().setCenter(feedLayout);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					//Error case if user didn't choose image
				} else {
					errorLabel.setVisible(true);
					errorLabel.setTextFill(Color.RED);
					errorLabel.setText("Please choose a main image!");
				}
			}
		});
	}
	
	/**
	 * Checks if all fields are filled and correctly handled
	 *
	 * @return true if all handled correctly, false otherwise.
	 */
	private boolean getTextFieldValues() {
		try {
			artworkTitlePulled = artworkTitle.getText();
			descriptionPulled = artworkDescription.getText();
			nameOfCreatorPulled = creatorName.getText();
			yearOfCreationPulled = yearOfCreation.getText();
			reservePricePulled = Double.parseDouble(reservePrice.getText());
			numberOfBidsAllowedPulled = Integer.parseInt(noOfBidsAllowed.getText());
			paintingWidthPulled = Integer.parseInt(paintingWidth.getText());
			paintingHeightPulled = Integer.parseInt(paintingHeight.getText());
			
			/**
			 * Checks if fields are not null
			 */
			if (artworkTitlePulled == null || Objects.equals(artworkTitlePulled, "")) {
				throw new IllegalArgumentException();
			}
			if (descriptionPulled == null || Objects.equals(descriptionPulled, "")) {
				throw new IllegalArgumentException();
			}
			if (nameOfCreatorPulled == null || Objects.equals(nameOfCreatorPulled, "")) {
				throw new IllegalArgumentException();
			}
			if (yearOfCreationPulled == null || Objects.equals(yearOfCreationPulled, "")) {
				throw new IllegalArgumentException();
			}
			if (numberOfBidsAllowedPulled<=0) {
				throw new IllegalArgumentException();
			}
			if (reservePricePulled<=0) {
				throw new IllegalArgumentException();
			}
			if (paintingHeightPulled<=0 || paintingWidthPulled <= 0) {
				throw new IllegalArgumentException();
			}
			else {
				return true;
			}
			
		} catch (IllegalArgumentException t) {
			//Displays error msg to user
			errorLabel.setVisible(true);
			errorLabel.setTextFill(Color.RED);
			errorLabel.setText("Please check all fields are filled in correctly!");
			return false;
		}
	}
	
	
	/**
	 * Opens file explorer to let user choose image to add
	 */
	@FXML
	public void chooseMainPaintingImg() {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
		fileChooser.getExtensionFilters().add(extFilter);
		File file = fileChooser.showOpenDialog(Util.getMainStage());
		if (file != null) {
			artImgPath = ourString(file.getPath());
			imgChosen = true;
		}
	}
	
	/**
	 * Replaces characters because of escape characters
	 *
	 * @param input path to file
	 *
	 * @return new path with replaced characters
	 */
	private String ourString(String input) {
		String newPath = input.substring(input.indexOf("images"));
		return newPath.replaceAll("\\\\", "/");
	}
}
