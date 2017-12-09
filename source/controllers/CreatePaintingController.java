package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import model.*;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class CreatePaintingController implements Initializable {

    @FXML Label errorLabel;
    @FXML TextArea artworkDescription;
    @FXML TextField artworkTitle;
    @FXML TextField paintingHeight;
    @FXML TextField paintingWidth;
    @FXML TextField reservePrice;
    @FXML TextField noOfBidsAllowed;
    @FXML TextField yearOfCreation;
    @FXML TextField creatorName;
    @FXML Button browseForMainPhoto;
    @FXML Button sellPaintingButton;

    String artworkTitlePulled;
    String descriptionPulled;
    String nameOfCreatorPulled;
    String yearOfCreationPulled;
    String artImgPath;
    Double reservePricePulled;
    Integer numberOfBidsAllowedPulled;
    Integer paintingWidthPulled;
    Integer paintingHeightPulled;

    Boolean imgChosen = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        sellPaintingButton.setOnAction(e -> {
            try {
                getTextFieldValues();
            } catch (NumberFormatException t) {
                errorLabel.setVisible(true);
                errorLabel.setTextFill(Color.RED);
                errorLabel.setText("Please fill out all fields");
            }

            if (imgChosen) {
                Artwork tempPainting = new Painting(artworkTitlePulled,new StringBuilder(descriptionPulled),yearOfCreationPulled,nameOfCreatorPulled,artImgPath, paintingWidthPulled, paintingHeightPulled);
                Auction tempAuction = Auction.createNewAuction(tempPainting,Util.getCurrentUser().getUsername(),numberOfBidsAllowedPulled,reservePricePulled);
                Feed.getInstance().add(tempAuction);
                Util.saveListOfAuctionsToFile(Feed.getInstance().getAllAsArrayList());
                System.out.println(Feed.getInstance());
                System.out.println("Auction added");
            }
            else {
                errorLabel.setVisible(true);
                errorLabel.setTextFill(Color.RED);
                errorLabel.setText("Please choose a main image!");
            }
        });
    }

    private void getTextFieldValues() {
        artworkTitlePulled = artworkTitle.getText();
        descriptionPulled = artworkDescription.getText();
        nameOfCreatorPulled = creatorName.getText();
        yearOfCreationPulled = yearOfCreation.getText();

        reservePricePulled = Double.parseDouble(reservePrice.getText());
        numberOfBidsAllowedPulled = Integer.parseInt(noOfBidsAllowed.getText());
        paintingWidthPulled = Integer.parseInt(paintingWidth.getText());
        paintingHeightPulled = Integer.parseInt(paintingHeight.getText());
    }
    @FXML
    private void chooseMainPaintingImg() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(Util.getMainStage());
        if (file != null) {
            artImgPath = ourString(file.getPath());
            imgChosen = true;
        }
    }

    private String ourString(String input) {
        String newPath = input.substring(input.indexOf("images"));
        return newPath.replaceAll("\\\\", "/");
    }
}
