package model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.exception.ProfileNotFoundException;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class Util {
	
	/*
	 * Notes:
	 *          Filter by Artwork type
	 *          Get all Auctions by a Profile
	 *          Get all new Auctions
	 *          Get all Bids from an Auction
	 *          Get highest Bidder and Bid of an Auction
	 */
	
	/**
	 * The current user who is signed in to the system.
	 */
	private static Profile currentUser;
	private static Gson gson = new Gson();

	/**
	 * Reads in all profiles from database.
	 *
	 * @return List of Profiles read from database
	 */
	private static Profile[] readInProfileFile() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("JSON Files/Profiles.json"));
			return gson.fromJson(br, Profile[].class);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		throw new ProfileNotFoundException("Profile not found!");
	}

	private static Auction[] readInAuctionFile() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("JSON Files/Auctions.json"));
			return gson.fromJson(br, Auction[].class);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		throw new ProfileNotFoundException("Auction not found!");
	}

	/**
	 * Read logged in user.
	 *
	 * @param username the username
	 */
	public static void readInLoggedInUser(String username) {

		Profile[] fromJson = readInProfileFile();
		for (Profile profile : fromJson) {
			//Read the variables required for constructor
			String name = profile.getUsername();

			if (Objects.equals(name, username)) {
				setCurrentUser(profile);
			}
		}
	}
	
	private static void setCurrentUser(Profile profile) {
		currentUser = new Profile(profile.getUsername(), profile.getFirstName(), profile.getLastName(),
				profile.getPhoneNumber(), profile.getAddressLine1(), profile.getAddressLine2(),
				profile.getCity(), profile.getCountry(), profile.getPostcode(), profile.getProfileImagePath(),
				profile.getFavouriteUsers(), profile.getWonAuctions(), profile.getCompletedAuctions(),
				profile.getCurrentlySelling(), profile.getNewAuctions(), profile.getAuctionsNewBids(),
				profile.getAllBidsPlaced(), profile.getLastLogInTime());
	}

	/**
	 * Gets profile by username from database.
	 *
	 * @param username the username
	 *
	 * @return the profile to be returned
	 */
	//Helper method, could be useful
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

	public static Auction getAuctionByAuctionID(Integer auctionID) {

		Auction[] allAuctions = readInAuctionFile();
		for (Auction auction: allAuctions) {
			Integer id = auction.getAuctionID();

			if (Objects.equals(id, auctionID)) {
				return auction;
			}
		}
		throw new ProfileNotFoundException("Profile not found!");
	}

	/**
	 * Saves a profile to file.
	 *
	 * @param profile the profile
	 */
	public static void saveProfileToFile(Profile profile) {
		try {
			addTypesToGson();
			Profile[] temp = readInProfileFile();
			String username = profile.getUsername();
			for (int i=0; i < temp.length; i++) {

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

	public static void saveAuctionToFile(Auction auction) {
		try {
			addTypesToGson();
			Auction[] temp = readInAuctionFile();
			Integer auctionID = auction.getAuctionID();
			for (int i=0; i < temp.length; i++) {

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
	 * Save all auctions to file.
	 *
	 * @param auctions Auctions to be saved to file
	 */
	public static void saveAuctionsToFile(List<Auction> auctions) {
		try {
			addTypesToGson();
			FileWriter fileWriter = new FileWriter("JSON Files/Auctions.json");
			gson.toJson(auctions, fileWriter);
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
public static void saveProfilesToFile(List<Profile> profiles) {
		try {
			addTypesToGson();
			FileWriter fileWriter = new FileWriter("JSON Files/Profiles.json");
			gson.toJson(profiles, fileWriter);
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Read in all auctions from database.
	 */
	public static void readInAllAuctions() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("JSON Files/Auctions.json"));
			
			Auction[] fromJson = gson.fromJson(br, Auction[].class);

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
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Add types to gson for artwork, sculpture and painting.
	 */
	public static void addTypesToGson() {
		RuntimeTypeAdapterFactory<Artwork> artworkAdapterFactory = RuntimeTypeAdapterFactory.of(Artwork.class, "type");

		artworkAdapterFactory.registerSubtype(Artwork.class);
		artworkAdapterFactory.registerSubtype(Sculpture.class);
		artworkAdapterFactory.registerSubtype(Painting.class);

		gson = new GsonBuilder()
				.registerTypeAdapterFactory(artworkAdapterFactory)
				.create();
	}

	/**
	 * Gets current user.
	 *
	 * @return the current user
	 */
	public static Profile getCurrentUser() {
		return currentUser;
	}
}