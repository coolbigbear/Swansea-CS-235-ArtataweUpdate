package model;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The type Util.
 */
public class Util {
	
	/**
	 * The current user who is signed in to the system.
	 */
	public static Profile currentUser;
	
	Gson gson = new Gson();

	public void readInLoggedInUser(String username) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("JSON Files/Profile.json"));
			ArrayList<Profile> fromJson = gson.fromJson(br, (Type) Profile.class);

			for(Profile profile: fromJson) {
				//Read the variables required for constructor
				String name = profile.getUsername();
				String contactInfo = profile.getContactInfo();

				if (Objects.equals(name, username)) {
					currentUser = new Profile(name, contactInfo);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void readInAllAuctions() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("JSON Files/Auction.json"));
			Auction[] fromJson = gson.fromJson(br, Auction[].class); // TODO Error on reading JSON artwork is to abstract

			for(Auction auction: fromJson) {
				//Read the variables required for constructor
				Artwork artwork = auction.getArtwork();
				Profile seller = auction.getSeller();
				Integer auctionID = auction.getAuctionID();
				List<Bid> bidList = auction.getBidList();
				Double reservePrice = auction.getReservePrice();
				Integer bidsAllowed = auction.getBidsAllowed();
				LocalDateTime dateTimePlaced = auction.getDateTimePlaced();
				Integer bidsLeft = auction.getBidsLeft();
				Profile highestBidder = auction.getHighestBidder();
				Boolean isCompleted = auction.getCompleted();
				Double highestPrice = auction.getHighestPrice();

				BHFeed.getNewInstance().addAll(new Auction(artwork,seller,auctionID,bidList,reservePrice,bidsAllowed,dateTimePlaced,bidsLeft,highestBidder,isCompleted,highestPrice));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}