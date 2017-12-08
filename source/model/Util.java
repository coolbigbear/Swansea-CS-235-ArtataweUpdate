package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import controllers.ArtataweMain;
import controllers.ProfileController;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.exception.ProfileNotFoundException;

import java.io.*;
import java.util.*;

public final class Util {
	
	/*
	 * Notes:
	 *          Get all Auctions by a Profile
	 */
	
	/**
	 * The current user who is signed in to the system.
	 */
	private static Profile currentUser;
	private static BorderPane homeLayout;
	private static Stage mainStage;
	private static Gson gson = addTypesToGson();
	private static ImageView profileImage;
	private static GridPane favoriteUsersGridPane;
	/**
	 * Reads in all profiles from database.
	 *
	 * @return List of Profiles read from database
	 */
	private static Profile[] readInProfileFile() {
		Gson gson = new Gson();
		Profile[] profiles = new Profile[0];
		try {
			BufferedReader br = new BufferedReader(new FileReader("JSON Files/Profiles.json"));
			profiles = gson.fromJson(br, Profile[].class);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return profiles;
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
	 * Read logged in user.
	 *
	 * @param username the username
	 */
	public static boolean checkAndSetUser(String username) {
		boolean found = false;
		
		Profile[] fromJson = readInProfileFile();
		for (Profile profile : fromJson) {
			//Read the variables required for constructor
			String name = profile.getUsername();
			
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
	 *
	 * @return the profile to be returned
	 */
	public static Profile getProfileByUsername(String username) {
		
		Profile[] allProfiles = readInProfileFile();
		for (Profile profile : allProfiles) {
			String name = profile.getUsername();
			
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
	 *
	 * @return the auction to be returned
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
		//Feed.getNewInstance().addAll(auctionArrayList);
		//System.out.println(Feed.getInstance());
	}
	
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
	 *
	 * @throws IOException the io exception
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
		//Feed.getNewInstance().addAll(auctionArrayList);
		//System.out.println(Feed.getInstance());
	}
	
	/**
	 * Reads in only active painting auctions.
	 *
	 * @throws IOException the io exception
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
	
	/**
	 * Saves a profile to file.
	 *
	 * @param profile the profile
	 */
	public static void saveProfileToFile(Profile profile) {
		try {
			Profile[] temp = readInProfileFile();
			String username = profile.getUsername();
			for (int i = 0; i < temp.length; i++) {
				
				if (Objects.equals(temp[i].getUsername(), username)) {
					temp[i] = profile;
				}
			}
			FileWriter fileWriter = new FileWriter("JSON Files/Profiles.json");
			gson.toJson(temp, fileWriter);
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Saves a auction to file.
	 *
	 * @param auction the auction
	 */
	public static void saveAuctionToFile(Auction auction) {
		Gson gson = new Gson();
		try {
			Auction[] temp = readInAuctionFile();
			Integer auctionID = auction.getAuctionID();
			for (int i = 0; i < temp.length; i++) {
				
				if (auctionID.equals(temp[i].getAuctionID())) {
					temp[i] = auction;
				}
			}
			FileWriter fileWriter = new FileWriter("JSON Files/Auctions.json");
			gson.toJson(temp, fileWriter);
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Save a list of auctions to file.
	 *
	 * @param auctions Auctions to be saved to file
	 */
	public static void saveListOfAuctionsToFile(List<Auction> auctions) {
		Gson gson = new Gson();
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
	 */
	public static Gson addTypesToGson() {
		RuntimeTypeAdapterFactory<Artwork> artworkAdapterFactory = RuntimeTypeAdapterFactory.of(Artwork.class, "type");
		
		artworkAdapterFactory.registerSubtype(Artwork.class);
		artworkAdapterFactory.registerSubtype(Painting.class);
		artworkAdapterFactory.registerSubtype(Sculpture.class);
		
		return new GsonBuilder()
				.registerTypeAdapterFactory(artworkAdapterFactory)
				.create();
	}
	
	public static int getNewAuctionID() {
		int auctionID = -1;
		
		try {
			Scanner scanner = new Scanner(new File("JSON Files/AuctionID.txt."));
			auctionID = scanner.nextInt();
			auctionID++;
			scanner.close();
			saveNewAuctionID(auctionID);
			return auctionID;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return auctionID;
	}
	
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
	
	public static BorderPane getHomeLayout() {
		return homeLayout;
	}
	
	public static void setHomeLayout(BorderPane borderPane) {
		homeLayout = borderPane;
	}
	
	public static Stage getMainStage() {
		return mainStage;
	}
	
	public static void setMainStage(Stage stage) {
		mainStage = stage;
	}

	public static void setProfileImage(ImageView imageView) {
		profileImage = imageView;
	}

	public static ImageView getProfileImage() {
		return profileImage;
	}

	public static void setFavoriteUsersGridPane(GridPane gridPane) {
		favoriteUsersGridPane = gridPane;
	}

	public static GridPane getFavoriteUsersGridPane() {
		return favoriteUsersGridPane;
	}

	public static void dynamicFavoritesGridPane(GridPane gridPane, List<Profile> favorites) {
		int IMAGE_COLUMN = 0;
		int PROFILE_COLUMN = 1;
		int row = 0;
		Hyperlink favoriteUser;
		ImageView profileImage;
		gridPane.addRow(favorites.size());
		for (Profile elem : favorites) {
			profileImage = new ImageView();
			try {
				profileImage.setImage(new Image(elem.getProfileImagePath()));
				profileImage.setFitHeight(20);
				profileImage.setFitWidth(20);
			} catch (Exception e) {
				e.printStackTrace();
			}
			favoriteUser = new Hyperlink();
			favoriteUser.setText(elem.getUsername());
			favoriteUser.setOnAction(event -> {
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(ArtataweMain.class.getResource("/layouts/profile_layout.fxml"));
				try {
					BorderPane profileLayout = (BorderPane) loader.load();
					ProfileController controller = loader.getController();
					controller.initProfile(elem);
					Util.getHomeLayout().setCenter(profileLayout);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			gridPane.add(favoriteUser,PROFILE_COLUMN,row);
			gridPane.add(profileImage,IMAGE_COLUMN,row);
			row++;
		}
	}

}