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

    @FXML Label errorLabel;
    @FXML TextArea artworkDescription;
    @FXML TextField artworkTitle;
    @FXML TextField sculptureHeight;
    @FXML TextField sculptureWidth;
    @FXML TextField sculptureDepth;
    @FXML TextField sculptureMaterial;
    @FXML TextField reservePrice;
    @FXML TextField noOfBidsAllowed;
    @FXML TextField yearOfCreation;
    @FXML TextField creatorName;
    @FXML Button browseForMainPhoto;
    @FXML Button sellSculptureButton;

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

    private Boolean imgChosen = false;
    private ArrayList pathsToImages = new ArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        sellSculptureButton.setOnAction(e -> {
            if (getTextFieldValues()) {
                if (imgChosen) {
                    Artwork tempPainting = new Sculpture(artworkTitlePulled, new StringBuilder(descriptionPulled),
                            yearOfCreationPulled, nameOfCreatorPulled, artImgPath, sculptureWidthPulled, sculptureHeightPulled,sculptureDepthPulled,sculptureMaterialPulled);

                    Auction tempAuction = Auction.createNewAuction(tempPainting, Util.getCurrentUser().getUsername(), numberOfBidsAllowedPulled, reservePricePulled);

                    Feed.getInstance().add(tempAuction);
                    Util.saveListOfAuctionsToFile(Feed.getInstance().getAllAsArrayList());

                    BorderPane feedLayout = null;
                    try {
                        feedLayout = FXMLLoader.load(getClass().getResource("/layouts/feed_layout.fxml"));
                        feedLayout.getStylesheets().add(ArtataweMain.class.getResource("/css/home_layout.css").toExternalForm());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    Util.getHomeLayout().setCenter(feedLayout);
                } else {
                    errorLabel.setVisible(true);
                    errorLabel.setTextFill(Color.RED);
                    errorLabel.setText("Please choose a main image!");
                }
            }
        });
    }

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
            errorLabel.setVisible(true);
            errorLabel.setTextFill(Color.RED);
            errorLabel.setText("Please check all fields are filled in correctly!");
            return false;
        }
    }
    @FXML
    private void chooseMainSculptureImg() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(Util.getMainStage());
        if (file != null) {
            artImgPath = ourString(file.getPath());
            imgChosen = true;
        }
    }

    private void chooseAdditionalSculptureImg1() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(Util.getMainStage());
        if (file != null) {
            image1 = ourString(file.getPath());
            imgChosen = true;
        }
    }

    private void chooseAdditionalSculptureImg2() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(Util.getMainStage());
        if (file != null) {
            image2 = ourString(file.getPath());
            imgChosen = true;
        }
    }

    private void chooseAdditionalSculptureImg3() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(Util.getMainStage());
        if (file != null) {
            image3 = ourString(file.getPath());
            imgChosen = true;
        }
    }

    private String ourString(String input) {
        String newPath = input.substring(input.indexOf("images"));
        return newPath.replaceAll("\\\\", "/");
    }
}
