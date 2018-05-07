package model;

import com.google.gson.*;
import controllers.Main;
import controllers.ProfileController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.exception.ProfileNotFoundException;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * A Utility class used mostly for reading and writing to the JSON database but also to send receive global Objects.
 *
 * @author ***REMOVED*** ***REMOVED***
 * @author Bezhan Kodomani
 * @author Bassam Helal
 * @version 3.0
 */
public final class Util {

    /**
     * The current user who is signed in to the system.
     */
    private static Profile currentUser;
    private static BorderPane homeLayout;
    private static Stage mainStage;
    /**
     * Creates a gson object and adds types from factory.
     */
    private static Gson gson = addTypesToGson();
    private static ImageView profileImage;
    private static GridPane favoriteUsersGridPane;
    private static ChoiceBox filterChoiceBox;
    private static MenuButton galleryMenuButton;

    /**
     * Reads in all profiles from database.
     *
     * @return List of Profiles read from database
     */
    private static Profile[] readInProfileFile() {
        Profile[] profiles = new Profile[0];
        try {
            BufferedReader br = new BufferedReader(new FileReader("JSON Files/Profiles.json"));
            profiles = gson.fromJson(br, Profile[].class);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return profiles;
    }

    private static JsonArray readInHashFile() {
        JsonParser parser = new JsonParser();
        JsonArray hashes = new JsonArray();

        try {
            BufferedReader br = new BufferedReader(new FileReader("JSON Files/Passwords.json"));
            JsonArray obj = (JsonArray) parser.parse(br);
            hashes = obj;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return hashes;
    }

    /**
     * Reads in all auctions from database.
     *
     * @return List of Auctions read from database
     */
    private static Auction[] readInAuctionFile() {
        Auction[] auctions = new Auction[0];
        try {
            BufferedReader br = new BufferedReader(new FileReader("JSON Files/Auctions.json"));
            auctions = gson.fromJson(br, Auction[].class);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return auctions;
    }

    /**
     * Read in logged in user.
     *
     * @param username the username
     * @return the boolean
     */
    public static boolean checkAndSetUser(String username) {
        boolean found = false;

        Profile[] fromJson = readInProfileFile();
        for (Profile profile : fromJson) {
            //Read the unique username for each user and see if it matches the one input
            String name = profile.getUsername();
            //If database contains username return true.
            if (Objects.equals(name, username)) {
                currentUser = profile;
                found = true;
            }
        }
        return found;
    }

    /**
     * Gets profile by username from database.
     *
     * @param username the username
     * @return the profile to be returned
     */
    public static Profile getProfileByUsername(String username) {

        Profile[] allProfiles = readInProfileFile();
        for (Profile profile : allProfiles) {
            String name = profile.getUsername();
            //Read through all profiles and return the one that matches the username input
            if (Objects.equals(name, username)) {
                return profile;
            }
        }
        throw new ProfileNotFoundException("Profile not found!");
    }

    /**
     * Gets Auction by auctionID from database.
     *
     * @param auctionID the ID of auction to be found
     * @return the auction to be returned
     * @throws IOException the io exception
     */
    public static Auction getAuctionByAuctionID(Integer auctionID) throws IOException {

        Auction auction = null;
        Auction[] allAuctions = readInAuctionFile();
        for (Auction auctions : allAuctions) {
            Integer id = auctions.getAuctionID();

            if (Objects.equals(id, auctionID)) {
                auction = auctions;
            }
        }
        if (auction == null) {
            throw new IOException();
        } else {
            return auction;
        }
    }

    /**
     * Read in all auctions from database that are active (on sale).
     */
    public static void getActiveAuctions() {
        Auction[] fromJson = readInAuctionFile();

        ArrayList<Auction> auctionArrayList = new ArrayList<>(Arrays.asList(fromJson));

        Feed feed = Feed.getNewInstance();

        //for each Auction only add it to the Feed if it is not completed
        for (Auction auction : auctionArrayList) {
            if (!auction.isCompleted()) {
                feed.add(auction);
            }
        }
    }

    /**
     * Updates the feed with the Auctions that are active that have the search String in any of the following:
     * <ul>
     * <li>Artwork Title</li>
     * <li>Description</li>
     * <li>Creator name</li>
     * <li>Creation Date</li>
     * <li>Seller name</li>
     * </ul>
     *
     * @param search the search queryString
     */
    public static void getAuctionsByName(String search) {
        Auction[] fromJson = readInAuctionFile();

        ArrayList<Auction> auctionArrayList = new ArrayList<>(Arrays.asList(fromJson));

        Feed feed = Feed.getNewInstance();

        search = search.toLowerCase();

        //for each Auction only add it to the Feed if it is not completed
        for (Auction auction : auctionArrayList) {
            if (!auction.isCompleted()) {
                String nameLowerCase = auction.getArtwork().title.toLowerCase();
                String descriptionLowerCase = auction.getArtwork().getDescription().toString().toLowerCase();
                String creatorName = auction.getArtwork().getCreatorName().toLowerCase();
                String creationDate = auction.getArtwork().getCreationDate().toLowerCase();
                String userName = auction.getSellerName().toLowerCase();
                search = search.trim();
                if (nameLowerCase.contains(search)) {
                    feed.add(auction);
                } else if (descriptionLowerCase.contains(search)) {
                    feed.add(auction);
                } else if (creatorName.contains(search)) {
                    feed.add(auction);
                } else if (creationDate.contains(search)) {
                    feed.add(auction);
                } else if (userName.contains(search)) {
                    feed.add(auction);
                }
            }
        }
    }

    /**
     * Updates the feed with all the Auctions
     */
    public static void getAllAuctions() {
        Auction[] fromJson = readInAuctionFile();

        ArrayList<Auction> auctionArrayList = new ArrayList<>(Arrays.asList(fromJson));

        Feed feed = Feed.getNewInstanceWithCapacity(auctionArrayList.size());
        feed.updateWith(auctionArrayList);

    }

    public static ArrayList<Auction> getActualCurrentlySelling() {
        Auction[] fromJson = readInAuctionFile();

        ArrayList<Auction> auctionArrayList = new ArrayList<>(Arrays.asList(fromJson));

        ArrayList<Auction> currentlySelling = new ArrayList<>();

        for (Auction auction : auctionArrayList) {
            if (auction.getSellerName().equals(getCurrentUser().getUsername()) &&
                    !auction.isCompleted()) {
                currentlySelling.add(auction);
            }
        }
        return currentlySelling;
    }

    /**
     * Gets active auctions by username.
     *
     * @param username the username
     */
    public static void getActiveAuctionsByUsername(String username) {
        Auction[] fromJson = readInAuctionFile();

        ArrayList<Auction> auctionArrayList = new ArrayList<>(Arrays.asList(fromJson));

        Feed feed = Feed.getNewInstance();

        //for each Auction only add it to the Feed if its sold by the given user
        for (Auction auction : auctionArrayList) {
            if (auction.getSellerName().equals(username)) {
                feed.add(auction);
            }
        }
    }

    /**
     * Reads in only active sculpture auctions.
     */
    public static void getSculptureAuctions() {
        Auction[] fromJson = readInAuctionFile();

        ArrayList<Auction> auctionArrayList = new ArrayList<>(Arrays.asList(fromJson));

        Feed feed = Feed.getNewInstance();

        //for each Auction only add it to the Feed if it is not completed
        for (Auction auction : auctionArrayList) {
            if (auction.getArtwork().type == ArtworkType.Sculpture && !auction.isCompleted()) {
                feed.add(auction);
            }
        }
    }

    /**
     * Reads in only active painting auctions.
     */
    public static void getPaintingAuctions() {
        Auction[] fromJson = readInAuctionFile();

        ArrayList<Auction> auctionArrayList = new ArrayList<>(Arrays.asList(fromJson));

        Feed feed = Feed.getNewInstance();

        //for each Auction only add it to the Feed if it is not completed
        for (Auction auction : auctionArrayList) {
            if (auction.getArtwork().type == ArtworkType.Painting && !auction.isCompleted()) {
                feed.add(auction);
            }
        }
    }

    public static void saveNewHashToFile(String user, String hash) {
        JsonArray exisitingHashes = readInHashFile();
        JsonObject newHash = new JsonObject();
        newHash.addProperty("username",user);
        newHash.addProperty("hash",hash);
        exisitingHashes.add(newHash);
        updateHashFile(exisitingHashes);
    }

    private static void updateHashFile(JsonArray exisitingHashes) {
        try {
            FileWriter fileWriter = new FileWriter("JSON Files/Passwords.json");
            gson.toJson(exisitingHashes, fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Takes in a new profile and saves it to file.
     *
     * @param profile to be added to the database.
     */
    public static void saveNewProfileToFile(Profile profile) {
        Profile[] temp = readInProfileFile();
        //Read all profiles in from database
        List<Profile> tempList = new ArrayList<>(Arrays.asList(temp));
        //Add new profile to List and save the new list to database overwriting it
        tempList.add(profile);
        saveListOfProfilesToFile(tempList);
    }

    /**
     * Saves a profile to file.
     *
     * @param profile the profile
     */
    public static void saveProfileToFile(Profile profile) {
        Profile[] temp = readInProfileFile();
        String username = profile.getUsername();
        for (int i = 0; i < temp.length; i++) {
            //For all profiles in database find the one that matches the username and overwrite it in list
            if (Objects.equals(temp[i].getUsername(), username)) {
                temp[i] = profile;
            }
        }
        //Save new list to file overwriting previous
        saveListOfProfilesToFile(Arrays.asList(temp));
    }

    /**
     * Saves a auction to file.
     *
     * @param auction the auction
     */
    public static void saveAuctionToFile(Auction auction) {
        Auction[] temp = readInAuctionFile();
        Integer auctionID = auction.getAuctionID();
        for (int i = 0; i < temp.length; i++) {
            //For all auctions in database find the one that matches the auctionID and overwrite it in list
            if (auctionID.equals(temp[i].getAuctionID())) {
                temp[i] = auction;
            }
        }
        //Save new list to file overwriting previous
        saveListOfAuctionsToFile(Arrays.asList(temp));
    }

    /**
     * Save a list of auctions to file.
     *
     * @param auctions Auctions to be saved to file
     */
    public static void saveListOfAuctionsToFile(List<Auction> auctions) {
        try {
            FileWriter fileWriter = new FileWriter("JSON Files/Auctions.json");
            gson.toJson(auctions, fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Save a list of profiles to file.
     *
     * @param profiles Profiles to be saved to file
     */
    public static void saveListOfProfilesToFile(List<Profile> profiles) {
        try {
            FileWriter fileWriter = new FileWriter("JSON Files/Profiles.json");
            gson.toJson(profiles, fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add types to gson for artwork, sculpture and painting.
     *
     * @return Gson with added types (need to differentiate when reading and saving)
     */
    public static Gson addTypesToGson() {
        RuntimeTypeAdapterFactory<Artwork> artworkAdapterFactory = RuntimeTypeAdapterFactory.of(Artwork.class, "typeGSON");

        artworkAdapterFactory.registerSubtype(Artwork.class);
        artworkAdapterFactory.registerSubtype(Painting.class);
        artworkAdapterFactory.registerSubtype(Sculpture.class);

        return new GsonBuilder()
                .registerTypeAdapterFactory(artworkAdapterFactory)
                .create();
    }

    /**
     * Gets new auction id from file.
     *
     * @return the new auction id
     */
    public static int getNewAuctionID() {
        int auctionID = -1;

        try {
            //Reads in auctionID from file
            Scanner scanner = new Scanner(new File("JSON Files/AuctionID.txt."));
            auctionID = scanner.nextInt();

            //Adds one to auctionID to avoid conflicts
            auctionID++;
            scanner.close();

            //Saves the new auctionID back to file
            saveNewAuctionID(auctionID);
            return auctionID;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return auctionID;
    }

    /**
     * Saves new auctionID back to file
     *
     * @param auctionID to be saved to file
     */
    private static void saveNewAuctionID(int auctionID) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(
                    "JSON Files/AuctionID.txt"));
            writer.write(Integer.toString(auctionID));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets current user.
     *
     * @return the current user
     */
    public static Profile getCurrentUser() {
        return currentUser;
    }

    /**
     * Sets a new current user.
     *
     * @param currentUser the new current user
     */
    public static void setCurrentUser(Profile currentUser) {
        Util.currentUser = currentUser;
    }

    /**
     * Gets home layout.
     *
     * @return the home layout
     */
    public static BorderPane getHomeLayout() {
        return homeLayout;
    }

    /**
     * Sets home layout.
     *
     * @param borderPane the border pane
     */
    public static void setHomeLayout(BorderPane borderPane) {
        homeLayout = borderPane;
    }

    /**
     * Gets main stage.
     *
     * @return the main stage
     */
    public static Stage getMainStage() {
        return mainStage;
    }

    /**
     * Sets main stage.
     *
     * @param stage the stage
     */
    public static void setMainStage(Stage stage) {
        mainStage = stage;
    }

    /**
     * Sets profile image.
     *
     * @param imageView the image view
     */
    public static void setProfileImage(ImageView imageView) {
        profileImage = imageView;
    }

    /**
     * Gets profile image.
     *
     * @return the profile image
     */
    public static ImageView getProfileImage() {
        return profileImage;
    }

    /**
     * Sets favorite users grid pane.
     *
     * @param gridPane the grid pane
     */
    public static void setFavoriteUsersGridPane(GridPane gridPane) {
        favoriteUsersGridPane = gridPane;
    }

    /**
     * Gets favorite users grid pane.
     *
     * @return the favorite users grid pane
     */
    public static GridPane getFavoriteUsersGridPane() {
        return favoriteUsersGridPane;
    }

    /**
     * Gets filter choice box.
     *
     * @return the filter choice box
     */
    public static ChoiceBox getFilterChoiceBox() {
        return filterChoiceBox;
    }

    /**
     * Sets filter choice box.
     *
     * @param choiceBox the choice box
     */
    public static void setFilterChoiceBox(ChoiceBox choiceBox) {
        filterChoiceBox = choiceBox;
    }

    /**
     * Dynamic favorites grid pane.
     *
     * @param gridPane  the grid pane
     * @param favorites the favorites
     */
    public static void dynamicFavoritesGridPane(GridPane gridPane, List<Profile> favorites) {
        final int IMAGE_COLUMN = 0;
        final int PROFILE_COLUMN = 1;
        final int FAVORITES_PROFILE_IMAGE = 20;
        int row = 0;
        Hyperlink favoriteUser;
        ImageView profileImage;
        gridPane.addRow(favorites.size());
        for (Profile elem : favorites) {
            profileImage = new ImageView();
            profileImage.setFitHeight(FAVORITES_PROFILE_IMAGE);
            profileImage.setFitWidth(FAVORITES_PROFILE_IMAGE);
            try {
                profileImage.setImage(new Image(elem.getProfileImagePath()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            favoriteUser = new Hyperlink();
            favoriteUser.setText(elem.getUsername());
            favoriteUser.setOnAction(event -> {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Main.class.getResource("/layouts/profile_layout.fxml"));
                try {
                    BorderPane profileLayout = loader.load();
                    ProfileController controller = loader.getController();
                    controller.initProfile(elem);
                    Util.getHomeLayout().setCenter(profileLayout);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            gridPane.add(favoriteUser, PROFILE_COLUMN, row);
            gridPane.add(profileImage, IMAGE_COLUMN, row);
            row++;
        }
    }

    /**
     * Delete grid row.
     *
     * @param grid the grid
     * @param row  the row
     */
    public static void deleteGridRow(GridPane grid, final int row) {
        Set<Node> deleteNodes = new HashSet<>();
        for (Node child : grid.getChildren()) {
            Integer rowIndex = GridPane.getRowIndex(child);
            int r = rowIndex == null ? 0 : rowIndex;
            if (r > row) {
                GridPane.setRowIndex(child, r - 1);
            } else if (r == row) {
                deleteNodes.add(child);
            }
        }
        grid.getChildren().removeAll(deleteNodes);
    }

    /**
     * Gets the galleries to update dynamically
     *
     * @param galleryMenuButton the gallery menu button
     */
    public static void getGalleriesDynamic(MenuButton galleryMenuButton) {
        galleryMenuButton.getItems().clear();
        for (Gallery elem : Util.getCurrentUser().getUserGalleries()) {
            MenuItem item = new MenuItem();
            item.setText(elem.getGalleryName());
            galleryMenuButton.getItems().add(item);
            item.setOnAction(e -> {
                ArrayList<Auction> auctions = new ArrayList<>();
                for (Integer i : elem.getListOfAuctionIDs()) {
                    try {
                        auctions.add(Util.getAuctionByAuctionID(i));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                Feed.getInstance().updateWith(auctions);
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(Main.class.getResource("/layouts/feed_layout.fxml"));
                    BorderPane feedLayout = loader.load();
                    feedLayout.getStylesheets().add(Main.class.getResource("/css/home_layout.css").toExternalForm());
                    homeLayout.setCenter(feedLayout);
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            });
        }
    }

    /**
     * Sets the gallery menu button
     *
     * @param menuButton the gallery menu button to be set to
     */
    public static void setGalleryMenuButton(MenuButton menuButton) {
        galleryMenuButton = menuButton;
    }

    /**
     * Gets the gallery menu button
     *
     * @return the gallery menu button
     */
    public static MenuButton getGalleryMenuButton() {
        return galleryMenuButton;
    }

    //-------------------------NOTIFICATIONS---------------------------------

    /**
     * Gets the List of new Auctions that that are now being sold that weren't existing since the passed in time.
     * <p>
     * The Auctions must be:
     * <ul>
     * <li>Placed after the passed in time</li>
     * <li>Have the seller be not the current user</li>
     * </ul>
     *
     * @param time the time to compare with
     * @return the List of new Auctions that that are now being sold that weren't existing since the passed in time.
     */
    public static List<Auction> getNewAuctionsSince(LocalDateTime time) {
        Auction[] fromJson = readInAuctionFile();

        ArrayList<Auction> auctionArrayList = new ArrayList<>(Arrays.asList(fromJson));

        ArrayList<Auction> auctionsSinceTime = new ArrayList<>();

        for (Auction auction : auctionArrayList) {
            if (auction.getDateTimePlaced().isAfter(time) &&
                    !auction.getSellerName().equals(getCurrentUser().getUsername())) {
                auctionsSinceTime.add(auction);
            }
        }
        return auctionsSinceTime;
    }

    /**
     * Gets the List of new Bids placed on the current user's currently selling Auctions that weren't
     * there since the passed in time.
     * <p>
     * The Bids must be from Auctions the current user is selling and the Bids must be placed after the passed in time
     *
     * @param time the time to compare with
     * @return the List of new Bids placed on the current user's currently selling Auctions since the passed in time
     */
    public static List<Bid> getNewBidsOnCurrentUserSince(LocalDateTime time) {
        Auction[] fromJson = readInAuctionFile();

        ArrayList<Auction> auctionArrayList = new ArrayList<>(Arrays.asList(fromJson));

        ArrayList<Bid> bidsSinceTime = new ArrayList<>();

        for (Auction auction : auctionArrayList) {
            if (auction.getSellerName().equals(getCurrentUser().getUsername())) {

                for (Bid bid : auction.getBidList()) {
                    if (bid.getDateTimePlaced().isAfter(time)) {
                        bidsSinceTime.add(bid);
                    }

                }
            }

        }
        return bidsSinceTime;
    }

    /**
     * Gets the List of Auctions that the current user has managed to sell since the passed in time.
     * <p>
     * The Auctions must be:
     * <ul>
     * <li>Completed</li>
     * <li>Have the current user as the seller</li>
     * <li>Have the last bid placed after the passed in time</li>
     * </ul>
     *
     * @param time the time to compare with
     * @return the List of Auctions that the current user has managed to sell since the passed in time
     */
    public static List<Auction> getAuctionsSoldSince(LocalDateTime time) {
        Auction[] fromJson = readInAuctionFile();

        ArrayList<Auction> auctionArrayList = new ArrayList<>(Arrays.asList(fromJson));

        ArrayList<Auction> auctionsSoldSinceTime = new ArrayList<>();

        for (Auction auction : auctionArrayList) {
            if (auction.isCompleted() &&
                    auction.getSellerName().equals(getCurrentUser().getUsername()) &&
                    getLastBid(auction.getBidList()).getDateTimePlaced().isAfter(time)) {

                auctionsSoldSinceTime.add(auction);
            }
        }
        return auctionsSoldSinceTime;
    }

    /**
     * Gets the List of Auctions that the current user has bid on that they have now lost since the passed in time.
     * <p>
     * The Auctions must be:
     * <ul>
     * <li>Completed</li>
     * <li>Not have the current user as the highest bidder</li>
     * <li>Have the current user bidded on the Auction</li>
     * <li>Have the last bid placed after the passed in time</li>
     * </ul>
     *
     * @param time the time to compare with
     * @return the List of Auctions that the current user has bid on that they have now lost since the passed in time
     */
    public static List<Auction> getAuctionsLostSince(LocalDateTime time) {
        Auction[] fromJson = readInAuctionFile();

        ArrayList<Auction> auctionArrayList = new ArrayList<>(Arrays.asList(fromJson));

        ArrayList<Auction> auctionsSoldSinceTime = new ArrayList<>();

        for (Auction auction : auctionArrayList) {
            if (auction.isCompleted() &&
                    hasUserBiddedOnAuction(getCurrentUser(), auction) &&
                    !auction.getHighestBidder().equals(getCurrentUser().getUsername()) &&
                    getLastBid(auction.getBidList()).getDateTimePlaced().isAfter(time)) {

                auctionsSoldSinceTime.add(auction);
            }
        }
        return auctionsSoldSinceTime;
    }

    /**
     * Gets the List of Auctions coming to a close meaning fewer than 2 bids since the passed in time.
     * The Auctions must be:
     * <ul>
     * <li>Not completed</li>
     * <li>Not have an empty bid list</li>
     * <li>Not have the current user as the highest bidder</li>
     * <li>Have 2 or less bids left (but not 0)</li>
     * <li>Have the current user bidded on the Auction</li>
     * <li>Have the last bid placed after the passed in time</li>
     * </ul>
     *
     * @param time the time to compare with
     * @return the List of Auctions coming to a close since the passed in time
     */
    public static List<Auction> getAuctionsComingToCloseSince(LocalDateTime time) {
        Auction[] fromJson = readInAuctionFile();

        ArrayList<Auction> auctionArrayList = new ArrayList<>(Arrays.asList(fromJson));

        ArrayList<Auction> auctionsComingToClose = new ArrayList<>();

        for (Auction auction : auctionArrayList) {
            if (!auction.isCompleted() &&
                    !auction.getBidList().isEmpty() &&
                    !auction.getHighestBidder().equals(getCurrentUser().getUsername()) &&
                    auction.getBidsLeft() <= 2 &&
                    hasUserBiddedOnAuction(getCurrentUser(), auction) &&
                    getLastBid(auction.getBidList()).getDateTimePlaced().isAfter(time)) {
                auctionsComingToClose.add(auction);
            }
        }
        return auctionsComingToClose;
    }

    /**
     * Utility function to check whether the passed in user has bidded on an Auction or not
     *
     * @param user    the user to check whether they bidded on an Auction or not
     * @param auction the Auction to check whether the user has bidded on it or not
     * @return true if the Auction has a Bid with the placer of the Bid being the user passed in,
     * false otherwise
     */
    private static Boolean hasUserBiddedOnAuction(Profile user, Auction auction) {
        for (Bid bid : auction.getBidList()) {
            if (bid.getBidderUsername().equals(user.getUsername())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Utility function to get the last placed Bid given a list of Bids
     *
     * @param bids the list of Bids to check
     * @return the last placed Bid on the passed in list of Bids
     */
    private static Bid getLastBid(List<Bid> bids) {
        LocalDateTime time = LocalDateTime.MIN;
        Bid result = null;

        for (Bid bid : bids) {
            if (bid.getDateTimePlaced().isAfter(time)) {
                result = bid;
                time = bid.getDateTimePlaced();
            }
        }
        return result;
    }

    //-----------------------------------------------------------------------
}