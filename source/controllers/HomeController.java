package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.*;

/**
 * The Controller for the home layout, this is in charge of <code>layouts.home_layout.fxml</code>.
 * <p>
 * This is the Controller and Layout pair in charge of keeping the left and top bars persistent and are the basic
 * containers of the whole program.
 *
 * @author Bezhan Kodomani
 * @author Bassam Helal
 * @author Iliyan Garnev
 * @author Ben Sampson
 * @author Alex Wing
 * @author ***REMOVED*** ***REMOVED***
 * @version 4.0
 * @see Initializable
 */
public class HomeController implements Initializable {

    @FXML
    Button favouriteUsersAuctionsButton;
    @FXML
    MenuButton getGalleries;
    @FXML
    private Label welcomeLabel;
    @FXML
    private ImageView profileImageView;
    @FXML
    private BorderPane homeLayout;
    @FXML
    private AnchorPane searchAndSortBar;
    @FXML
    private GridPane favoritesGridPane;
    @FXML
    private Feed feed;
    @FXML
    private ChoiceBox<String> choiceBoxFilter;
    @FXML
    private ChoiceBox<String> sortingBoxFilter;
    @FXML
    private TextField searchBar;
    @FXML
    private Button searchButton;
    @FXML
    private MenuButton notificationsMenuButton;
    @FXML
    private Label notificationsNumberLabel;

    private ArrayList<Profile> favoriteUsers;

    // Options for dropdown menus
    private ObservableList<String> choiceBoxList =
            FXCollections.observableArrayList("Show All", "Paintings", "Sculptures");

    private ObservableList<String> sortingBoxList =
            FXCollections.observableArrayList("Popular", "Price: Low to High", "Price: High to Low", "Auction Name: A-Z", "Auction Name: Z-A" );

    /**
     * Method that gets the different pages that are viewable from the home view.
     * Initialise the dropdown boxes
     *
     * @param location  location of the page
     * @param resources actual resource used
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        choiceBoxFilter.setItems(choiceBoxList);
        choiceBoxFilter.setValue("Show All");

        sortingBoxFilter.setItems(sortingBoxList);
        sortingBoxFilter.setValue("Popular");
        setChoiceBox();
        setFilterBox();

        Util.setHomeLayout(homeLayout);
        Util.setGalleryMenuButton(getGalleries);
        Util.getActiveAuctions();
        feed = Feed.getInstance();
        favoriteUsers = populateFavoriteUsers();
        try {
            setProfileImageView(Util.getCurrentUser().getProfileImagePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        welcomeLabel.setText(Util.getCurrentUser().getLastName());
        try {
            setAuctionsCenter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        populateFavoritesView();
        Util.getGalleriesDynamic(getGalleries);
        initNotifications();
    }

    /**
     * Method that searches auctions depending on your search query
     *
     * @param searchQuery uses what you searched in to search through auctions
     * @throws IOException
     */
    public void search(String searchQuery) throws IOException {
        Util.getAuctionsByName(searchQuery);
        setAuctionsCenter();
    }

    /**
     * Method that allows you to use the search bar
     *
     * @throws IOException
     */
    public void searchButtonPress() throws IOException {
        search(searchBar.getText());
    }

    /**
     * Method that reacts to when the enter key is pressed, and activates the search bar
     */
    @FXML
    public void onEnter() {
        searchBar.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                try {
                    search(searchBar.getText());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Sorts the current Feed depending on user choice
     */
    private void setFilterBox() {
        sortingBoxFilter.getSelectionModel().selectedIndexProperty().addListener(
                (observable, oldValue, newValue) -> {
                    switch (newValue.intValue()) {
                        case 0:
                            //Popular
                            compare(0);
                            break;
                        case 1:
                            // Low to High
                            compare(1);
                            break;

                        case 2:
                            // High to Low
                            compare(2);
                            break;

                        case 3:
                            // A-Z
                            compare(3);
                            break;

                        case 4:
                            // Z-A
                            compare(4);
                            break;
                    }
                });
    }


    /**
     * Sorts the current feed depending on chose case.
     *
     * @param switchCase case to sort the feed by
     */
    private void compare(int switchCase) {
        Feed originalFeed = Feed.getInstance();
        ArrayList<Auction> asArrayList = originalFeed.getAllAsArrayList();
        Feed feed = Feed.getNewInstance();
        switch (switchCase) {
            case 0:
                asArrayList.sort(Auction::compareTo);
                break;
            case 1:
                asArrayList.sort(Auction.artworkComparatorPriceAsc);
                break;
            case 2:
                asArrayList.sort(Auction.artworkComparatorPriceDesc);
                break;
            case 3:
                asArrayList.sort(Auction.artworkComparatorNameAsc);
                break;
            case 4:
                asArrayList.sort(Auction.artworkComparatorNameDesc);
                break;
        }
        feed.addAll(asArrayList);
        FeedController.updateFeed();
    }

    /**
     * Method that allows you to filter out the feed to select items.
     */
    private void setChoiceBox() {
        choiceBoxFilter.getSelectionModel().selectedIndexProperty().addListener(
                (observable, oldValue, newValue) -> {
                    switch (newValue.intValue()) {
                        case 0:
                            Util.getActiveAuctions();
                            FeedController.updateFeed();
                            break;

                        case 1:
                            Util.getPaintingAuctions();
                            FeedController.updateFeed();
                            break;

                        case 2:
                            Util.getSculptureAuctions();
                            FeedController.updateFeed();
                            break;
                    }
                });
    }

    /**
     * Method to fill notifications icon
     */
    private void initNotifications() {
        notificationsNumberLabel.setText("0");
        Integer number = Notification.getNewAuctionsSinceLastLogon().size() +
                Notification.getNewBidsSinceLastLogon().size() +
                Notification.getAuctionsCurrentUserSoldSinceLastLogon().size() +
                Notification.getAuctionsCurrentUserLostSinceLastLogon().size() +
                Notification.getAuctionsComingToCloseSinceLastLogon().size();

        notificationsNumberLabel.setText(number.toString());

        getNewAuctionsSinceLastLogon();
        getNewBidsSinceLastLogon();
        getAuctionsCurrentUserSoldSinceLastLogon();
        getAuctionsCurrentUserLostSinceLastLogon();
        getAuctionsComingToCloseSinceLastLogon();
    }

    /**
     * Method to get a new auction since last logon
     */
    private void getNewAuctionsSinceLastLogon() {
        if (!Notification.getNewAuctionsSinceLastLogon().isEmpty()) {
            MenuItem item = new MenuItem();
            item.setText(Notification.getNewAuctionsSinceLastLogon().size() + " new auctions");

            item.setOnAction(onClick -> {
                feed.updateWith(Notification.getNewAuctionsSinceLastLogon());
                try {
                    setAuctionsCenter();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            notificationsMenuButton.getItems().add(item);
        }
    }

    /**
     * Method to get new bids since last log on
     */
    private void getNewBidsSinceLastLogon() {
        if (!Notification.getNewBidsSinceLastLogon().isEmpty()) {
            MenuItem item = new MenuItem();
            item.setText(Notification.getNewBidsSinceLastLogon().size() + " new bids on auctions");

            item.setOnAction(onClick -> {
                notificationPopUps();
            });

            notificationsMenuButton.getItems().add(item);
        }
    }

    /**
     * Method to get new auctions that have been sold to the current logged in user since last logon
     */
    private void getAuctionsCurrentUserSoldSinceLastLogon() {
        if (!Notification.getAuctionsCurrentUserSoldSinceLastLogon().isEmpty()) {
            MenuItem item = new MenuItem();
            item.setText(Notification.getAuctionsCurrentUserSoldSinceLastLogon().size() + " new sold auctions");

            item.setOnAction(onClick -> {
                feed.updateWith(Notification.getAuctionsCurrentUserSoldSinceLastLogon());
                try {
                    setAuctionsCenter();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            notificationsMenuButton.getItems().add(item);
        }
    }

    /**
     * Method to get auctions that have been lost since last logon
     */
    private void getAuctionsCurrentUserLostSinceLastLogon() {
        if (!Notification.getAuctionsCurrentUserLostSinceLastLogon().isEmpty()) {
            MenuItem item = new MenuItem();
            item.setText(Notification.getAuctionsCurrentUserLostSinceLastLogon().size() + " lost auctions");

            item.setOnAction(onClick -> {
                feed.updateWith(Notification.getAuctionsCurrentUserLostSinceLastLogon());
                try {
                    setAuctionsCenter();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            notificationsMenuButton.getItems().add(item);
        }
    }

    /**
     * Method to get auctions that are close to finishing
     */
    private void getAuctionsComingToCloseSinceLastLogon() {
        if (!Notification.getAuctionsComingToCloseSinceLastLogon().isEmpty()) {
            MenuItem item = new MenuItem();
            item.setText(Notification.getAuctionsComingToCloseSinceLastLogon().size() + " closing auctions");

            item.setOnAction(onClick -> {
                feed.updateWith(Notification.getAuctionsComingToCloseSinceLastLogon());
                try {
                    setAuctionsCenter();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            notificationsMenuButton.getItems().add(item);
        }
    }

    /**
     * Method to create a notifiaction popup when logged in
     */
    private void notificationPopUps() {
        ArrayList<Bid> bids = (ArrayList<Bid>) Notification.getNewBidsSinceLastLogon();
        List<String> choices = new ArrayList<>();
        choices.add("");
        for (Bid elem : bids) {
            try {
                choices.add(elem.getBidderUsername() + " bid on " + Util.getAuctionByAuctionID(elem.getAuctionID())
                        .getArtwork().getTitle());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>("", choices);
        dialog.setTitle("New bids");
        dialog.setHeaderText("Choose a bid to view");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            if (!result.get().equalsIgnoreCase("")) {
                for (int i = 0; i < choices.size(); i++) {
                    if (choices.get(i).equalsIgnoreCase(result.get())) {
                        try {
                            Auction auction = Util.getAuctionByAuctionID(bids.get(i - 1).getAuctionID());
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("/layouts/auction_view_layout.fxml"));
                            AnchorPane auctionLayout = loader.load();
                            AuctionController controller = loader.getController();
                            controller.initAuction(auction);
                            Util.getHomeLayout().setCenter(auctionLayout);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    /**
     * Method to set the profile image on the top right of the screen
     *
     * @param imagePath gets the image path
     */
    private void setProfileImageView(String imagePath) {
        Image img = new Image(imagePath);
        try {
            profileImageView.setImage(img);
            Util.setProfileImage(profileImageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to call current auctions once clicked
     *
     * @throws IOException
     */
    @FXML
    private void currentAuctionsButtonAction() throws IOException {
        Util.getActiveAuctions();
        feed = Feed.getInstance();
        sortingBoxFilter.setValue(sortingBoxList.get(0));
        choiceBoxFilter.setValue(choiceBoxList.get(0));
        setAuctionsCenter();
    }

    /**
     * Method to load the profile once clicked
     *
     * @throws IOException
     */
    @FXML
    private void myProfileMenuItemAction() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/layouts/profile_layout.fxml"));
        BorderPane profileLayout = loader.load();
        ProfileController controller = loader.getController();
        //System.out.print(Util.getCurrentUser());
        controller.initProfile(Util.getCurrentUser());
        homeLayout.setCenter(profileLayout);
        searchAndSortBar.setVisible(false);

    }

    /**
     * Method to load the logout menu once clicked logout
     *
     * @throws IOException
     */
    @FXML
    public void logoutMenuItemAction() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/layouts/login_layout.fxml"));
        Scene login = new Scene(root);
        Stage stage = Util.getMainStage();
        stage.setScene(login);
        stage.setMinHeight(519);
        stage.setMinWidth(656);
        stage.setHeight(519);
        stage.setWidth(656);
        stage.setResizable(false);
        stage.show();
    }

    //----------------------Auctions Bids----------------------


    /**
     * Method to see which auctions you have bid on and also current auctions
     *
     * @throws IOException
     */
    @FXML
    private void auctionsPlacedMenuItemAction() throws IOException {
        List<Bid> bidList = Util.getCurrentUser().getAllBidsPlaced();
        Util.getAllAuctions();
        feed = Feed.getInstance();
        ArrayList<Auction> resultList = new ArrayList<>();

        for (Bid bid : bidList) {
            Auction auction = Util.getAuctionByAuctionID(bid.getAuctionID());

            if (bid.getBidderUsername().equals(Util.getCurrentUser().getUsername()) &&
                    !auction.isCompleted() && !resultList.contains(auction)) {
                resultList.add(auction);
            }
        }
        feed.updateWith(resultList);
        setAuctionsCenter();
    }

    /**
     * Method to see which auctions that you have won and to show you have finished them
     *
     * @throws IOException
     */
    @FXML
    private void auctionsWonMenuItemAction() throws IOException {
        ArrayList<Bid> bidList = getAllBids();
        Util.getAllAuctions();
        Feed feed = Feed.getInstance();
        ArrayList<Auction> resultList = new ArrayList<>();

        for (Bid bid : bidList) {
            Auction auction = Util.getAuctionByAuctionID(bid.getAuctionID());

            if (auction.isCompleted() &&
                    Util.getCurrentUser().getUsername().equals(auction.getHighestBidder())
                    && !resultList.contains(auction)) {
                resultList.add(auction);
            }
        }
        feed.updateWith(resultList);
        setAuctionsCenter();
    }

    //--------------------Auctions selling---------------------

    /**
     * Method to show all auctions that you are currently selling
     *
     * @throws IOException
     */
    @FXML
    private void currentlySellingMenuItemAction() throws IOException {
        Util.getAllAuctions();
        feed = Feed.getInstance();
        ArrayList<Auction> resultList = new ArrayList<>();

        for (Auction auction : feed) {
            if (!auction.isCompleted() &&
                    auction.getSellerName().equals(Util.getCurrentUser().getUsername())
                    && !resultList.contains(auction)) {
                resultList.add(auction);
            }
        }
        feed.updateWith(resultList);
        setAuctionsCenter();
    }

    /**
     * Method to show all auctions that you have sold
     *
     * @throws IOException
     */
    @FXML
    private void auctionsSoldMenuItemAction() throws IOException {
        Util.getAllAuctions();
        Feed feed = Feed.getInstance();
        ArrayList<Auction> resultList = new ArrayList<>();

        for (Auction auction : feed) {
            if (auction.isCompleted() &&
                    auction.getSellerName().equals(Util.getCurrentUser().getUsername())
                    && !resultList.contains(auction)) {
                resultList.add(auction);
            }
        }
        feed.updateWith(resultList);
        setAuctionsCenter();
    }

    /**
     * Method that once clicked loads the make sculpture view
     *
     * @throws IOException
     */
    @FXML
    private void createSculptureButtonAction() throws IOException {
        AnchorPane profileLayout = FXMLLoader.load(getClass().getResource("/layouts/create_sculpture_layout.fxml"));
        homeLayout.setCenter(profileLayout);
        searchAndSortBar.setVisible(false);
    }

    /**
     * Method that once clicked, loads the dashboard view
     *
     * @throws IOException
     */
    @FXML
    private void ViewDashboardButtonAction() throws IOException {
        AnchorPane profileLayout = FXMLLoader.load(getClass().getResource("/layouts/dashboard_layout.fxml"));
        homeLayout.setCenter(profileLayout);
        searchAndSortBar.setVisible(false);
    }

    /**
     * Method that once clicked, loads the make painting view
     *
     * @throws IOException
     */
    @FXML
    private void createPaintingButtonAction() throws IOException {
        AnchorPane profileLayout = FXMLLoader.load(getClass().getResource("/layouts/create_painting_layout.fxml"));
        homeLayout.setCenter(profileLayout);
        searchAndSortBar.setVisible(false);
    }

    /**
     * Method that once clicked, loads the bid history view
     *
     * @throws IOException
     */
    @FXML
    private void bidHistoryButtonAction() throws IOException {
        AnchorPane bidHistory = FXMLLoader.load(getClass().getResource("/layouts/bidhistory_layout.fxml"));
        homeLayout.setCenter(bidHistory);
    }

    /**
     * Method that allows you to view your favourite users auctions once clicked
     */
    @FXML
    private void favouriteUsersAuctionsButtonOnAction() {
        Util.getActiveAuctions();
        feed = Feed.getInstance();
        List<String> favouriteUsers = Util.getCurrentUser().getFavouriteUsers();
        ArrayList<Auction> resultList = new ArrayList<>();

        for (Auction auction : feed) {
            for (String user : favouriteUsers) {
                if (auction.getSellerName().equals(user)) {
                    resultList.add(auction);
                }
            }
        }
        feed.updateWith(resultList);
        try {
            setAuctionsCenter();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that gets all the bids from auctions
     *
     * @return bid list
     */
    private ArrayList<Bid> getAllBids() {
        Util.getAllAuctions();
        Feed feed = Feed.getInstance();
        ArrayList<Bid> bidList = new ArrayList<>();

        for (Auction auction : feed) {
            bidList.addAll(auction.getBidList());
        }
        return bidList;
    }

    /**
     * Method to set the centre to feed
     *
     * @return feed layout
     * @throws IOException
     */
    private BorderPane setAuctionsCenter() throws IOException {
        BorderPane feedLayout = FXMLLoader.load(getClass().getResource("/layouts/feed_layout.fxml"));
        feedLayout.getStylesheets().add(Main.class.getResource("/css/home_layout.css").toExternalForm());
        homeLayout.setCenter(feedLayout);
        searchAndSortBar.setVisible(true);
        return feedLayout;
    }

    /**
     * Method that helps get all the favourite users of a profile.
     *
     * @return profiles
     */
    private ArrayList<Profile> populateFavoriteUsers() {
        ArrayList<Profile> profiles = new ArrayList<>();
        for (String elem : Util.getCurrentUser().getFavouriteUsers()) {
            profiles.add(Util.getProfileByUsername(elem));
        }
        return profiles;
    }

    /**
     * Method that populates the dynamic favourite bar displayed on the left
     */
    private void populateFavoritesView() {
        Util.dynamicFavoritesGridPane(favoritesGridPane, favoriteUsers);
        Util.setFavoriteUsersGridPane(favoritesGridPane);
    }
}