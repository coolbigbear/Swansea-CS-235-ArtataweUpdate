package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import model.*;
import model.exception.IllegalBidException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

/**
 * The Controller for the Auction layout, this is in charge of <code>layouts.auction_view_layout.fxml</code>.
 * <p>
 * This is the Controller and Layout pair in charge of viewing the detailed page of an Auction.
 *
 * @author Bezhan Kodomani
 * @author Bassam Helal
 * @version 2.0
 * @see Initializable
 * @see Auction
 * @see Gallery
 */
public class AuctionController {


    @FXML
    private Label auctionNameLabel;
    @FXML
    private Hyperlink sellerLink;
    @FXML
    private Label reservePriceLabel;
    @FXML
    private Label highestBidLabel;
    @FXML
    private TextField bidInputTextField;
    @FXML
    private Label descriptionLabel;
    @FXML
    private Label creatorNameLabel;
    @FXML
    private Label creationYearLabel;
    @FXML
    private Label dimensionsLabel;
    @FXML
    private Label mainMaterialLabel;
    @FXML
    private Label mainMaterialLabelConstant;
    @FXML
    private GridPane usersBidAuctionGridPane;
    @FXML
    private ScrollPane viewAuctionScrollPane;
    @FXML
    private Button addToFavoritesButton;
    @FXML
    private MenuButton addToGalleryMenuButton;
    @FXML
    private Label bidsLeftLabel;
    @FXML
    private GridPane sellingInfoGridPane;
    @FXML
    private Label placeBidLabel;
    @FXML
    private Button bidButton;
    @FXML
    private Label errorMessageLabel;
    @FXML
    private ImageView mainArtworkImage;
    @FXML
    private ImageView optionalImage1;
    @FXML
    private ImageView optionalImage2;
    @FXML
    private ImageView optionalImage3;
    @FXML
    private ImageView optionalImage4;
    @FXML
    private ImageView mainImage2;
    private Auction currentAuction;
    private Artwork artwork;
    private ArtworkType artworkType;

    /**
     * Method which initializes auction, objects are being passed from one controller to another this way
     */
    public void initAuction(Auction auction) {
        currentAuction = auction;
        artwork = currentAuction.getArtwork();
        artworkType = currentAuction.getArtwork().getType();

        if (isFavorited()) {
            addToFavoritesButton.setText("Remove favorite");
        } else {
            addToFavoritesButton.setText("Add to favorites");
        }
        generateAuctionLabels();
        generateArtworkLabels();
        setSellerSpecificNodes();
        if (currentAuction.isCompleted() && (currentAuction.getHighestBidder().equalsIgnoreCase(Util.getCurrentUser().getUsername()))) {
            setBuyerSpecificNodes();
        }
        if (currentAuction.isCompleted() && (!currentAuction.getHighestBidder().equalsIgnoreCase(Util.getCurrentUser().getUsername()))
                && (!currentAuction.getSellerName().equalsIgnoreCase(Util.getCurrentUser().getUsername()))) {
            setViewerSpecificNodes();
        }
        setArtworkDisplayImages();
        initAddToGalleries();
    }

    /**
     * Method which refreshes the galleries in order for them to update
     */
    @FXML
    private void refreshGalleriesAction() {
        initAddToGalleries();
    }

    /**
     * Method which initializes a pop-up and a gallery is created
     */
    @FXML
    private void createNewGalleryAction() {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Galleries");
        dialog.setHeaderText("Create a new gallery");
        dialog.setContentText("Please enter the gallery's name");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String galleryName = result.get();
            ArrayList<Integer> auctions = new ArrayList<>();
            auctions.add(currentAuction.getAuctionID());
            Gallery gallery = new Gallery(galleryName, auctions);
            // update home controller
            Util.getCurrentUser().getUserGalleries().add(gallery);
            Util.saveProfileToFile(Util.getCurrentUser());
            Util.getGalleriesDynamic(Util.getGalleryMenuButton());
            initAddToGalleries();
        }
    }

    /**
     * Method which initializes galleries
     */
    private void initAddToGalleries() {
        addToGalleryMenuButton.getItems().clear();
        MenuItem staticMenuItem = new MenuItem();
        staticMenuItem.setText("Create new gallery");
        staticMenuItem.setOnAction(e -> createNewGalleryAction());
        addToGalleryMenuButton.getItems().add(staticMenuItem);
        for (Gallery elem : Util.getCurrentUser().getUserGalleries()) {
            MenuItem item = new MenuItem();
            item.setText(elem.getGalleryName());
            item.setOnAction(e -> {
                if (!elem.getListOfAuctionIDs().contains(currentAuction.getAuctionID())) {
                    elem.getListOfAuctionIDs().add(currentAuction.getAuctionID());
                    Util.saveProfileToFile(Util.getCurrentUser());
                }
            });
            addToGalleryMenuButton.getItems().add(item);
        }
    }

    /**
     * Method which handles all bids which are being placed
     */
    @FXML
    private void bidOnAction() {
        if (isTextFieldCorrect()) {
            try {
                Bid bid = new Bid(currentAuction.getAuctionID(), Double.valueOf(bidInputTextField.getText()));
                currentAuction.placeBid(bid);

                //Below will execute if the placing of the Bid was accepted
                bidInputTextField.clear();
                errorMessageLabel.setText("");
                bidInputTextField.setPromptText("Bid Accepted!");
                highestBidLabel.setText("£" + bid.getBidAmount().toString());
                Util.getCurrentUser().getAllBidsPlaced().add(bid);

                if (currentAuction.isCompleted()) {
                    auctionWon();
                    Profile seller = Util.getProfileByUsername(currentAuction.getSellerName());

                    //Updates the seller's selling and sold and the current user's won
                    seller.getCompletedAuctions().add(currentAuction);
                    seller.getCurrentlySelling().remove(currentAuction);

                    Util.getCurrentUser().getWonAuctions().add(currentAuction);

                    //Saves everything to the database
                    Util.saveProfileToFile(seller);

                }
                Util.dynamicFavoritesGridPane(Util.getFavoriteUsersGridPane(), populateFavoriteUsers());
                Util.saveProfileToFile(Util.getCurrentUser());

                Util.saveAuctionToFile(currentAuction);
            } catch (IllegalBidException exception) {
                if (exception.getType().equals(IllegalBidException.IllegalBidType.ALREADY_HIGHEST_BIDDER)) {
                    errorMessageLabel.setText("Already highest bidder!");
                }
                if (exception.getType().equals(IllegalBidException.IllegalBidType.LOWER_THAN_HIGHEST)) {
                    errorMessageLabel.setText("Lower than highest bidder!");
                }
                if (exception.getType().equals(IllegalBidException.IllegalBidType.LOWER_THAN_RESERVE_PRICE)) {
                    errorMessageLabel.setText("Lower than reserve price!");
                }
                //If entered input is not a Number
            } catch (NumberFormatException exception) {
                setErrorInputTextField("Please enter a Number!");
            }
        }
    }

    /**
     * Method which sets the labels of the layout
     */
    private void generateAuctionLabels() {
        auctionNameLabel.setText(artwork.getTitle());
        sellerLink.setText(currentAuction.getSellerName());
        reservePriceLabel.setText("£" + String.valueOf(currentAuction.getReservePrice()));
        highestBidLabel.setText("£" + String.valueOf(currentAuction.getHighestPrice()));
        bidInputTextField.setPromptText("Enter bid amount");
    }

    /**
     * Method which generates artwork specific labels
     */
    private void generateArtworkLabels() {
        if (artworkType.equals(ArtworkType.Painting)) {
            generateGeneralArtworkLabels();
            mainMaterialLabel.setVisible(false);
            mainMaterialLabelConstant.setVisible(false);
            dimensionsLabel.setText("Width: " + ((Painting) artwork).getWidth().toString() + " " +
                    "Height: " + ((Painting) artwork).getHeight().toString());
        } else if (artworkType.equals(ArtworkType.Sculpture)) {
            generateGeneralArtworkLabels();
            dimensionsLabel.setText("Width: " + ((Sculpture) artwork).getWidth().toString() + " " +
                    "Height: " + ((Sculpture) artwork).getHeight().toString() + " " +
                    "Depth: " + ((Sculpture) artwork).getDepth().toString());
            mainMaterialLabelConstant.setVisible(true);
            mainMaterialLabel.setText(((Sculpture) artwork).getMainMaterial());
        }
    }

    /**
     * Method which generates general artwork labels
     */
    private void generateGeneralArtworkLabels() {
        descriptionLabel.setText(artwork.getDescription().toString());
        creatorNameLabel.setText(artwork.getCreatorName());
        creationYearLabel.setText(String.valueOf(artwork.getCreationDate()));
    }

    /**
     * Method which gives an error text field settings
     *
     * @param message The corresponding error message
     */
    private void setErrorInputTextField(String message) {
        bidInputTextField.clear();
        bidInputTextField.setBackground(new Background(new BackgroundFill(Color.CRIMSON, new CornerRadii(0d),
                new Insets(0))));
        bidInputTextField.setPromptText(message);
    }

    /**
     * Method which populates the dynamic users who have placed a bid on this auction, only the seller can see them
     */
    private void populateUsersBidPane() {
        final int PROFILE_IMAGE_COLUMN = 0;
        final int PROFILE_USERNAME_COLUMN = 1;
        final int PROFILE_BID_AMOUNT_COLUMN = 2;
        final int PROFILE_TIME_PLACED = 3;
        final int PROFILE_IMAGE_SIZE = 20;
        usersBidAuctionGridPane.addRow(currentAuction.getBidList().size());
        int row = 1;
        ImageView profileImage;
        Hyperlink profileLink;
        Label bidAmount;
        Label timePlaced;
        for (Bid elem : currentAuction.getBidList()) {
            profileImage = new ImageView();
            profileImage.setFitHeight(PROFILE_IMAGE_SIZE);
            profileImage.setFitWidth(PROFILE_IMAGE_SIZE);
            try {
                profileImage.setImage(new Image(Util.getProfileByUsername(elem.getBidderUsername()).getProfileImagePath()));
            } catch (Exception e) {
                e.printStackTrace();
            }


            bidAmount = new Label();
            System.out.println("£" + String.valueOf(elem.getBidAmount()));
            bidAmount.setText("£" + String.valueOf(elem.getBidAmount()));


            profileLink = new Hyperlink();
            profileLink.setText(elem.getBidderUsername());


            profileLink.setOnAction(event -> {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Main.class.getResource("/layouts/profile_layout.fxml"));
                try {
                    BorderPane profileLayout = loader.load();
                    ProfileController controller = loader.getController();
                    controller.initProfile(Util.getProfileByUsername(elem.getBidderUsername()));
                    Util.getHomeLayout().setCenter(profileLayout);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            timePlaced = new Label();
            timePlaced.setText(elem.getDateTimePlaced().getHour() + ":" + +elem.getDateTimePlaced().getMinute() + " " +
                    elem.getDateTimePlaced().getDayOfMonth() + "." + elem.getDateTimePlaced().getMonthValue() + "." +
                    elem.getDateTimePlaced().getYear());

            usersBidAuctionGridPane.add(profileImage, PROFILE_IMAGE_COLUMN, row);
            usersBidAuctionGridPane.add(profileLink, PROFILE_USERNAME_COLUMN, row);
            usersBidAuctionGridPane.add(bidAmount, PROFILE_BID_AMOUNT_COLUMN, row);
            usersBidAuctionGridPane.add(timePlaced, PROFILE_TIME_PLACED, row);

            row++;
        }
    }

    /**
     * Method which links the UI to a profile account
     */
    @FXML
    private void sellerLinkAction() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("/layouts/profile_layout.fxml"));
        try {
            BorderPane profileLayout = loader.load();
            ProfileController controller = loader.getController();
            controller.initProfile(Util.getProfileByUsername(currentAuction.getSellerName()));
            Util.getHomeLayout().setCenter(profileLayout);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method sets the specific seller nodes, displayed only for the seller
     */
    private void setSellerSpecificNodes() {
        if (Util.getCurrentUser().getUsername().equalsIgnoreCase(currentAuction.getSellerName())) {
            viewAuctionScrollPane.setVisible(true);
            usersBidAuctionGridPane.setVisible(true);
            populateUsersBidPane();
            addToFavoritesButton.setDisable(true);
            addToFavoritesButton.setVisible(false);
            bidsLeftLabel.setText("Bids left:");
            sellingInfoGridPane.add(new Label(String.valueOf(currentAuction.getBidsLeft())), 1, 1);
            placeBidLabel.setText("Highest bidder:");
            if (currentAuction.isCompleted()) {
                placeBidLabel.setText("Winner:");
            }
            bidInputTextField.setDisable(true);
            bidInputTextField.setVisible(false);
            bidButton.setDisable(true);
            bidButton.setVisible(false);
            if (currentAuction.getHighestBidder() != null) {
                Hyperlink highestBidderProfile = new Hyperlink();
                highestBidderProfile.setText(currentAuction.getHighestBidder());
                highestBidderProfile.setOnAction(event -> {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(Main.class.getResource("/layouts/profile_layout.fxml"));
                    try {
                        BorderPane profileLayout = loader.load();
                        ProfileController controller = loader.getController();
                        controller.initProfile(Util.getProfileByUsername(currentAuction.getHighestBidder()));
                        Util.getHomeLayout().setCenter(profileLayout);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                sellingInfoGridPane.add(highestBidderProfile, 1, 5);
            }

        } else {
            usersBidAuctionGridPane.setVisible(false);
            viewAuctionScrollPane.setVisible(false);
        }
    }

    /**
     * Method which adds a user to favorite users
     */
    @FXML
    private void addToFavoritesButtonAction() {
        int counter = Util.getCurrentUser().getFavouriteUsers().size();
        if (addToFavoritesButton.getText().equalsIgnoreCase("Remove favorite")) {
            for (int i = 0; i < Util.getCurrentUser().getFavouriteUsers().size(); i++) {
                if (Util.getCurrentUser().getFavouriteUsers().get(i).equalsIgnoreCase(currentAuction.getSellerName())) {
                    Util.getCurrentUser().getFavouriteUsers().remove(i);
                }
            }
            for (int i = counter - 1; i >= 0; i--) {
                Util.deleteGridRow(Util.getFavoriteUsersGridPane(), i);
            }
            Util.dynamicFavoritesGridPane(Util.getFavoriteUsersGridPane(), populateFavoriteUsers());
            Util.saveProfileToFile(Util.getCurrentUser());
            addToFavoritesButton.setText("Add to favorites");
        } else {
            Util.getCurrentUser().getFavouriteUsers().add(currentAuction.getSellerName());
            for (int i = counter - 1; i >= 0; i--) {
                Util.deleteGridRow(Util.getFavoriteUsersGridPane(), i);
            }
            Util.dynamicFavoritesGridPane(Util.getFavoriteUsersGridPane(), populateFavoriteUsers());
            Util.saveProfileToFile(Util.getCurrentUser());
            addToFavoritesButton.setText("Remove favorite");
        }
    }

    /**
     * Helper method to check if a user is favorited
     *
     * @return true if the user is favorited, false if not
     */
    private boolean isFavorited() {
        boolean favorite = false;
        for (String elem : Util.getCurrentUser().getFavouriteUsers()) {
            if (elem.equalsIgnoreCase(currentAuction.getSellerName())) {
                favorite = true;
            }
        }
        return favorite;
    }

    /**
     * Helper method to populate an arraylist of all favorite users of a profile
     *
     * @return An array list of the favorite users
     */
    private ArrayList<Profile> populateFavoriteUsers() {
        ArrayList<Profile> profiles = new ArrayList<>();
        for (String elem : Util.getCurrentUser().getFavouriteUsers()) {
            profiles.add(Util.getProfileByUsername(elem));
        }
        return profiles;
    }

    /**
     * Method to check for valid input from the text field
     *
     * @return True if the text field is correct, false if not
     */
    private boolean isTextFieldCorrect() {
        if (bidInputTextField.getText().length() <= 10) {
            try {
                return Double.parseDouble(bidInputTextField.getText()) < 2000000000;
            } catch (NumberFormatException e) {
                errorMessageLabel.setText("Bid is not numerical!");
                return false;
            }
        } else {
            errorMessageLabel.setText("Bid is too long!");
            return false;
        }
    }

    /**
     * Method to set specific nodes only visible and usable to the buyer
     */
    private void setBuyerSpecificNodes() {
        placeBidLabel.setText("Winner:");
        bidInputTextField.setDisable(true);
        bidInputTextField.setVisible(false);
        sellingInfoGridPane.add(new Label("YOU"), 1, 5);
        bidButton.setVisible(false);
        bidButton.setDisable(true);
    }

    /**
     * Method which sets the nodes for a person who has not won the auction, but also is not the seller
     */
    private void setViewerSpecificNodes() {
        placeBidLabel.setText("STATUS:");
        bidInputTextField.setDisable(true);
        bidInputTextField.setVisible(false);
        sellingInfoGridPane.add(new Label("CLOSED"), 1, 5);
        bidButton.setVisible(false);
        bidButton.setDisable(true);
    }

    /**
     * Method which sets the nodes for a person who has not won the auction, but also is not the seller
     */
    private void auctionWon() {
        setBuyerSpecificNodes();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message from Artatawe");
        alert.setHeaderText(currentAuction.getArtwork().getTitle());
        String s = "Congratulations, you have just won the auction! ";
        alert.setContentText(s);
        alert.show();
    }

    /**
     * Method which dynamically displays of the auctions
     */
    private void setArtworkDisplayImages() {
        if (currentAuction.getArtwork() instanceof Painting) {
            try {
                mainArtworkImage.setImage(new Image(currentAuction.getArtwork().getMainImagePath()));
                mainImage2.setVisible(false);
                optionalImage1.setVisible(false);
                optionalImage2.setVisible(false);
                optionalImage3.setVisible(false);
                optionalImage4.setVisible(false);
            } catch (Exception e) {
                System.out.println("Image not found");
            }
        } else {
            mainArtworkImage.setImage(new Image(currentAuction.getArtwork().getMainImagePath()));
            Sculpture p = (Sculpture) currentAuction.getArtwork();
            try {
                switch (p.getAdditionalImagesPaths().size()) {
                    case 0:
                        mainImage2.setVisible(false);
                        optionalImage1.setVisible(false);
                        optionalImage2.setVisible(false);
                        optionalImage3.setVisible(false);
                        optionalImage4.setVisible(false);
                        break;
                    case 1:
                        optionalImage2.setVisible(false);
                        optionalImage3.setVisible(false);
                        optionalImage4.setVisible(false);
                        mainImage2.setImage((new Image(p.getMainImagePath())));
                        optionalImage1.setImage((new Image(p.getAdditionalImagesPaths().get(0))));
                        break;
                    case 2:
                        optionalImage3.setVisible(false);
                        optionalImage4.setVisible(false);
                        mainImage2.setImage((new Image(p.getMainImagePath())));
                        optionalImage1.setImage((new Image(p.getAdditionalImagesPaths().get(0))));
                        optionalImage2.setImage((new Image(p.getAdditionalImagesPaths().get(1))));
                        break;
                    case 3:
                        optionalImage4.setVisible(false);
                        mainImage2.setImage((new Image(p.getMainImagePath())));
                        optionalImage1.setImage((new Image(p.getAdditionalImagesPaths().get(0))));
                        optionalImage2.setImage((new Image(p.getAdditionalImagesPaths().get(1))));
                        optionalImage3.setImage((new Image(p.getAdditionalImagesPaths().get(2))));
                        break;
                    case 4:
                        mainImage2.setImage((new Image(p.getMainImagePath())));
                        optionalImage1.setImage((new Image(p.getAdditionalImagesPaths().get(0))));
                        optionalImage2.setImage((new Image(p.getAdditionalImagesPaths().get(1))));
                        optionalImage3.setImage((new Image(p.getAdditionalImagesPaths().get(2))));
                        optionalImage4.setImage((new Image(p.getAdditionalImagesPaths().get(3))));
                        break;
                }
            } catch (Exception e) {
                System.out.println("Image not found");
            }
        }
    }

    /**
     * Method which sets the main auction image
     */
    @FXML
    private void displayMainImage() {
        mainArtworkImage.setImage(mainImage2.getImage());
    }

    /**
     * Method which sets the optional 1 auction image
     */
    @FXML
    private void displayOptionalImage1() {
        mainArtworkImage.setImage(optionalImage1.getImage());
    }

    /**
     * Method which sets the optional 2 auction image
     */
    @FXML
    private void displayOptionalImage2() {
        mainArtworkImage.setImage(optionalImage2.getImage());
    }

    /**
     * Method which sets the optional 3 auction image
     */
    @FXML
    private void displayOptionalImage3() {
        mainArtworkImage.setImage(optionalImage3.getImage());
    }

    /**
     * Method which sets the optional 4 auction image
     */
    @FXML
    private void displayOptionalImage4() {
        mainArtworkImage.setImage(optionalImage4.getImage());
    }
}
