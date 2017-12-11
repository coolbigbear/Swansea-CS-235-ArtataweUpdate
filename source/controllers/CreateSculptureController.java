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
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class CreateSculptureController implements Initializable {

    /**
     * The Error label.
     */
    @FXML Label errorLabel;
    /**
     * The Artwork description.
     */
    @FXML TextArea artworkDescription;
    /**
     * The Artwork title.
     */
    @FXML TextField artworkTitle;
    /**
     * The Sculpture height.
     */
    @FXML TextField sculptureHeight;
    /**
     * The Sculpture width.
     */
    @FXML TextField sculptureWidth;
    /**
     * The Sculpture depth.
     */
    @FXML TextField sculptureDepth;
    /**
     * The Sculpture material.
     */
    @FXML TextField sculptureMaterial;
    /**
     * The Reserve price.
     */
    @FXML TextField reservePrice;
    /**
     * The No of bids allowed.
     */
    @FXML TextField noOfBidsAllowed;
    /**
     * The Year of creation.
     */
    @FXML TextField yearOfCreation;
    /**
     * The Creator name.
     */
    @FXML TextField creatorName;
    /**
     * The Choose main sculpture img.
     */
    @FXML Button chooseMainSculptureImg;
    /**
     * The Sell sculpture button.
     */
    @FXML Button sellSculptureButton;

    /**
     * Variables pulled from text fields.
     */
    private String artworkTitlePulled;
    private String descriptionPulled;
    private String nameOfCreatorPulled;
    private String yearOfCreationPulled;
    private String sculptureMaterialPulled;
    private String artImgPath;
    private String image1;
    private String image2;
    private String image3;
    private String image4;

    private Double reservePricePulled;

    private Integer numberOfBidsAllowedPulled;
    private Integer sculptureWidthPulled;
    private Integer sculptureHeightPulled;
    private Integer sculptureDepthPulled;

    //Boolean to check if user chose img.
    private Boolean imgChosen = false;
    private ArrayList pathsToImages = new ArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /**
         * When sell button is clicked
         */
        sellSculptureButton.setOnAction(e -> {

            //If all values entered correctly
            if (getTextFieldValues()) {

                //If user chose an image (required)
                if (imgChosen) {
                    Artwork tempPainting;

                    //Since sculpture additional images are not required.
                    // Check if user chose additional images and user appropriate constructor
                    if (pathsToImages.size()> 0) {
                        tempPainting = new Sculpture(artworkTitlePulled, new StringBuilder(descriptionPulled),
                                yearOfCreationPulled, nameOfCreatorPulled, artImgPath, sculptureWidthPulled, sculptureHeightPulled, sculptureDepthPulled, sculptureMaterialPulled,pathsToImages);
                    } else {

                        //User chose no additional images
                        tempPainting = new Sculpture(artworkTitlePulled, new StringBuilder(descriptionPulled),
                                yearOfCreationPulled, nameOfCreatorPulled, artImgPath, sculptureWidthPulled, sculptureHeightPulled, sculptureDepthPulled, sculptureMaterialPulled);
                    }
                    //Creates temporary auction to be added to database and feed
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
            sculptureMaterialPulled = sculptureMaterial.getText();
            reservePricePulled = Double.parseDouble(reservePrice.getText());
            numberOfBidsAllowedPulled = Integer.parseInt(noOfBidsAllowed.getText());
            sculptureWidthPulled = Integer.parseInt(sculptureWidth.getText());
            sculptureHeightPulled = Integer.parseInt(sculptureHeight.getText());
            sculptureDepthPulled = Integer.parseInt(sculptureDepth.getText());

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
            if (sculptureMaterialPulled == null || Objects.equals(sculptureMaterialPulled, "")) {
                throw new IllegalArgumentException();
            } else {
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
     * Sets main image for sculpture shows error to user otherwise
     */
    @FXML
    private void chooseImg() {
        String temp = chooseMainSculptureImg();
        if (temp == null) {
            //Displays error msg
            errorLabel.setVisible(true);
            errorLabel.setTextFill(Color.RED);
            errorLabel.setText("Please choose a correct image!");
        } else {
            artImgPath = temp;
            imgChosen = true;
        }
    }

    /**
     * Opens file explorer to let user choose image to add
     *
     * @return Path to image
     */
    private String chooseMainSculptureImg() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(Util.getMainStage());
        if (file != null) {
            return ourString(file.getPath());

        }
        return null;
    }

    /**
     * Grabs additional images (up to 4)
     */
    @FXML
    private void imgButton1() {
        image1 = chooseMainSculptureImg();
        pathsToImages.add(image1);
    }

    @FXML
    private void imgButton2() {
        image2 = chooseMainSculptureImg();
        pathsToImages.add(image2);
    }

    @FXML
    private void imgButton3() {
        image3 = chooseMainSculptureImg();
        pathsToImages.add(image3);
    }

    @FXML
    private void imgButton4() {
        image4 = chooseMainSculptureImg();
        pathsToImages.add(image4);
    }

    /**
     * Replaces characters because of escape characters
     *
     * @param input path to file
     * @return new path with replaced characters
     */
    private String ourString(String input) {
        String newPath = input.substring(input.indexOf("images"));
        return newPath.replaceAll("\\\\", "/");
    }
}
