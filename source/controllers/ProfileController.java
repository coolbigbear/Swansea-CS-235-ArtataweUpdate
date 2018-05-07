package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Auction;
import model.Profile;
import model.Util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The Controller for the Profile layout, this is in charge of <code>layouts.profile_layout.fxml</code>.
 * <p>
 * This is the Controller and Layout pair in charge of generating and showing the profile page.
 *
 * @author Bezhan Kodomani
 * @author Ben Sampson
 * @version 1.8
 * @see Profile
 */
public class ProfileController {

    @FXML
    private ImageView profileImg;
    @FXML
    private Button chooseImage;
    @FXML
    private MenuButton browseDefaultImage;
    @FXML
    private Button createCustom;
    @FXML
    private Button favouriteUser;
    @FXML
    private Label postCode;
    @FXML
    private Label contactNumber;
    @FXML
    private Label firstName;
    @FXML
    private Label lastName;
    @FXML
    private Label addressLine1;
    @FXML
    private Label addressLine2;
    @FXML
    private Label city;
    @FXML
    private Label country;
    @FXML
    private Label lastLogin;
    @FXML
    private Label usernameLabelProfile;
    @FXML
    private GridPane currentlySellingAuctionsGridPane;
    private Profile selectedProfile;

    /**
     * Method setting the image, buttons, and selling auctions for the current user
     *
     * @param profile Instance of a profile
     */
    public void initProfile(Profile profile) {
        selectedProfile = profile;
        setUserSpecificButtons();
        setLabels();
        try {
            setImage();
        } catch (Exception e) {
            e.printStackTrace();
        }
        populateCurrentlySellingAuctions();
    }

    /**
     * Method used for choosing a profile image, using file browser
     */
    @FXML
    private void chooseProfileImg() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(Util.getMainStage());
        if (file != null) {
            String newPath = ourString(file.getPath());
            selectedProfile.setProfileImagePath(newPath);
            settingImageAll(newPath);
        }
    }

    /**
     * Method used for creating the custom image
     */
    @FXML
    private void createCustomImg() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/layouts/custom_drawing.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method used for setting a user as favourite user
     */
    @FXML
    private void setFavouriteUser() {
        int counter = Util.getCurrentUser().getFavouriteUsers().size();
        System.out.println("GRID ROWS:");
        if (favouriteUser.getText().equalsIgnoreCase("Remove favorite")) {
            for (int i = 0; i < Util.getCurrentUser().getFavouriteUsers().size(); i++) {
                if (Util.getCurrentUser().getFavouriteUsers().get(i).equalsIgnoreCase(selectedProfile.getUsername())) {
                    Util.getCurrentUser().getFavouriteUsers().remove(i);
                }
            }
            for (int i = counter - 1; i >= 0; i--) {
                Util.deleteGridRow(Util.getFavoriteUsersGridPane(), i);
            }
            Util.dynamicFavoritesGridPane(Util.getFavoriteUsersGridPane(), populateFavoriteUsers());
            System.out.println("GRIDPANE ROWS: " + Util.getFavoriteUsersGridPane().getRowConstraints().toString());
            Util.saveProfileToFile(selectedProfile);
            favouriteUser.setText("Add to favorites");
        } else {
            Util.getCurrentUser().getFavouriteUsers().add(selectedProfile.getUsername());
            for (int i = counter - 1; i >= 0; i--) {
                Util.deleteGridRow(Util.getFavoriteUsersGridPane(), i);
                System.out.println("REMOVING" + i);
            }
            Util.dynamicFavoritesGridPane(Util.getFavoriteUsersGridPane(), populateFavoriteUsers());
            Util.saveProfileToFile(selectedProfile);
            favouriteUser.setText("Remove favorite");
        }
    }

    /**
     * Next four methods are setting the four default images' paths
     */
    @FXML
    private void setDefaultImage1() {
        changeDefaultImage("images/profile/male4.png");
    }

    @FXML
    private void setDefaultImage2() {
        changeDefaultImage("images/profile/male3.png");
    }

    @FXML
    private void setDefaultImage3() {
        changeDefaultImage("images/profile/female3.png");
    }

    @FXML
    private void setDefaultImage4() {
        changeDefaultImage("images/profile/female2.png");
    }

    /**
     * Method used for changing the default image to one of the above
     *
     * @param defImagePath Image path, which one of the defaultImage methods will pass
     */
    //method to change the default image
    private void changeDefaultImage(String defImagePath) {
        selectedProfile.setProfileImagePath(defImagePath);
        settingImageAll(defImagePath);
    }

    /**
     * Method to set both images
     *
     * @param path Image path
     */
    private void settingImageAll(String path) {
        try {
            setImage();
            Util.getProfileImage().setImage(new Image(path));
            Util.saveProfileToFile(selectedProfile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to check if user has been favouited
     *
     * @return true if the user has been favourited, otherwise it returns false
     */
    //method to check if user has been favorited
    private boolean isFavorited() {
        boolean favorite = false;
        for (String elem : Util.getCurrentUser().getFavouriteUsers()) {
            if (elem.equalsIgnoreCase(selectedProfile.getUsername())) {
                favorite = true;
            }
        }
        return favorite;
    }

    /**
     * Method to set all relevant labels
     */
    private void setLabels() {
        usernameLabelProfile.setText(selectedProfile.getUsername());
        postCode.setText(selectedProfile.getPostcode());
        contactNumber.setText(selectedProfile.getPhoneNumber());
        firstName.setText(selectedProfile.getFirstName());
        lastName.setText(selectedProfile.getLastName());
        addressLine1.setText(selectedProfile.getAddressLine1());
        addressLine2.setText(selectedProfile.getAddressLine2());
        city.setText(selectedProfile.getCity());
        country.setText(selectedProfile.getCountry());
        lastLogin.setText(selectedProfile.getLastLogInTime().getHour() + ":" + selectedProfile.getLastLogInTime().getMinute() +
                " " + selectedProfile.getLastLogInTime().getDayOfMonth() + "." + selectedProfile.getLastLogInTime().getMonthValue() +
                "." + selectedProfile.getLastLogInTime().getYear());
        if (!isSignedInUser()) {
            if (isFavorited()) {
                favouriteUser.setText("Remove favorite");
            } else {
                favouriteUser.setText("Add to favorites");
            }
        }
    }

    /**
     * Method to set the main image
     */
    private void setImage() {
        Image profileImage = new Image(selectedProfile.getProfileImagePath());
        this.profileImg.setImage(profileImage);
    }

    /**
     * Method to set ser specific buttons, whether this is user's profile or somebody else's
     */
    private void setUserSpecificButtons() {
        if (isSignedInUser()) {
            chooseImage.setDisable(false);
            createCustom.setDisable(false);
            favouriteUser.setDisable(true);
            browseDefaultImage.setDisable(false);
            chooseImage.setVisible(true);
            createCustom.setVisible(true);
            favouriteUser.setVisible(false);
            browseDefaultImage.setVisible(true);
        } else {
            chooseImage.setDisable(true);
            createCustom.setDisable(true);
            favouriteUser.setDisable(false);
            browseDefaultImage.setDisable(true);
            chooseImage.setVisible(false);
            createCustom.setVisible(false);
            favouriteUser.setVisible(true);
            browseDefaultImage.setVisible(false);
        }
    }

    /**
     * method to check if the user is the same as the user logged into the system currently
     *
     * @return the name of the currently logged on user
     */
    private boolean isSignedInUser() {
        return selectedProfile.getUsername().equalsIgnoreCase(Util.getCurrentUser().getUsername());
    }

    /**
     * Helper method to replace chars until images
     *
     * @param input takes the image path
     * @return a new image path with replaced "\\\\" to "/"
     */
    private String ourString(String input) {
        String newPath = input.substring(input.indexOf("images"));
        return newPath.replaceAll("\\\\", "/");
    }

    /**
     * method to get all the users' favourite users
     *
     * @return the users' favourite users
     */
    private ArrayList<Profile> populateFavoriteUsers() {
        ArrayList<Profile> profiles = new ArrayList<>();
        for (String elem : Util.getCurrentUser().getFavouriteUsers()) {
            profiles.add(Util.getProfileByUsername(elem));
        }
        return profiles;
    }

    /**
     * method populating the currently selling grid of favourites
     */
    private void populateCurrentlySellingAuctions() {
        final int AUCTIONS_IMAGE_COLUMN = 0;
        final int AUCTIONS_SELLING_COLUMN = 1;
        final int AUCTIONS_PRICE_COLUMN = 2;
        final int AUCTIONS_IMAGE_SIZE = 20;
        int row = 1;
        System.out.println(selectedProfile.getCurrentlySelling().size());
        currentlySellingAuctionsGridPane.addRow(selectedProfile.getCurrentlySelling().size());
        ImageView auctionImage;
        Hyperlink auctionLink;
        Label auctionPrice;
        for (Auction elem : selectedProfile.getCurrentlySelling()) {
            System.out.println("IN CURRENTLY SELLING");
            auctionImage = new ImageView();
            auctionImage.setFitHeight(AUCTIONS_IMAGE_SIZE);
            auctionImage.setFitWidth(AUCTIONS_IMAGE_SIZE);
            try {
                auctionImage.setImage(new Image(elem.getArtwork().getMainImagePath()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            auctionPrice = new Label();
            if (elem.getReservePrice() < elem.getHighestPrice()) {
                auctionPrice.setText("£" + String.valueOf(elem.getHighestPrice()));
            } else {
                auctionPrice.setText("£" + String.valueOf(elem.getReservePrice()));
            }
            auctionLink = new Hyperlink();
            auctionLink.setText(elem.getArtwork().getTitle());
            auctionLink.setOnAction(event -> {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/layouts/auction_view_layout.fxml"));
                try {
                    AnchorPane auctionLayout = loader.load();
                    AuctionController controller = loader.getController();
                    controller.initAuction(elem);
                    Util.getHomeLayout().setCenter(auctionLayout);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            currentlySellingAuctionsGridPane.add(auctionImage, AUCTIONS_IMAGE_COLUMN, row);
            currentlySellingAuctionsGridPane.add(auctionLink, AUCTIONS_SELLING_COLUMN, row);
            currentlySellingAuctionsGridPane.add(auctionPrice, AUCTIONS_PRICE_COLUMN, row);
            row++;
        }
    }

}
