package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// TODO: Unsure on a 'Checkusername' method
// TODO: Have to add 'Filter' methods for profile
// TODO: Profile image as no class??

public final class Profile {
	
	private final String username;
	private final String firstName;
	private final String lastName;
	private final Integer phoneNumber;
	private final String addressLine1;
	private final String addressLine2;
	private final String city;
	private final String country;
	private final String postcode;
	private List<Profile> favouriteUsers;
	private List<Auction> wonAuctions;
	private List<Auction> completedAuctions;
	private List<Auction> currentlySelling;
	private List<Auction> newAuctions;      //Bassam Helal What's this??
	private List<Auction> auctionsNewBids;  //Bassam Helal What's this also??
	private List<Bid> allBidsPlaced;
	private LocalDateTime lastLogInTime;
	
	
	private Profile(String username, String firstName, String lastName, Integer phoneNumber,
	                String addressLine1, String addressLine2, String city, String country, String postcode) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.city = city;
		this.country = country;
		this.postcode = postcode;
		
		this.favouriteUsers = new ArrayList<>();
		this.wonAuctions = new ArrayList<>();
		this.completedAuctions = new ArrayList<>();
		this.currentlySelling = new ArrayList<>();
		this.newAuctions = new ArrayList<>();
		this.auctionsNewBids = new ArrayList<>();
		this.allBidsPlaced = new ArrayList<>();
		this.lastLogInTime = LocalDateTime.now();
	}
	
	public Profile(String username, String firstName, String lastName, Integer phoneNumber,
	               String addressLine1, String addressLine2, String city, String country,
	               String postcode, List<Profile> favouriteUsers, List<Auction> wonAuctions,
	               List<Auction> completedAuctions, List<Auction> currentlySelling,
	               List<Auction> newAuctions, List<Auction> auctionsNewBids, List<Bid> allBidsPlaced,
	               LocalDateTime lastLogInTime) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.city = city;
		this.country = country;
		this.postcode = postcode;
		this.favouriteUsers = favouriteUsers;
		this.wonAuctions = wonAuctions;
		this.completedAuctions = completedAuctions;
		this.currentlySelling = currentlySelling;
		this.newAuctions = newAuctions;
		this.auctionsNewBids = auctionsNewBids;
		this.allBidsPlaced = allBidsPlaced;
		this.lastLogInTime = lastLogInTime;
	}
	
	//Factory for making new Profiles in the System, new Users, not Objects!!!
	public Profile createNewProfile(String username, String firstName, String lastName, Integer phoneNumber,
	                                String addressLine1, String addressLine2, String city, String country,
	                                String postcode) {
		Profile profile = new Profile(username, firstName, lastName, phoneNumber, addressLine1, addressLine2, city,
				country, postcode);
		
		//send this to Database...
		//...
		//...
		
		return profile;
	}
	
	public List<Profile> getFavouriteUsers() {
		return this.favouriteUsers;
	}
	
	public List<Auction> getWonAuctions() {
		return this.wonAuctions;
	}
	
	public List<Auction> getCompletedAuctions() {
		return this.completedAuctions;
	}
	
	public List<Auction> getCurrentlySelling() {
		return this.currentlySelling;
	}
	
	public List<Auction> getNewAuctions() {
		return this.newAuctions;
	}
	
	public List<Auction> getAuctionsNewBids() {
		return this.auctionsNewBids;
	}
	
	public List<Bid> getAllBidsPlaced() {
		return this.allBidsPlaced;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public LocalDateTime getLastLogInTime() {
		return this.lastLogInTime;
	}
	
	public boolean checkUsername(String username) {
		String checkUsername = "[a-zA-Z0-9]";
		if (username.matches(checkUsername)) {
			return true;
		} else {
			return false;
		}
	}
	
	
	// Bassam Helal, I discourage using ContactInfo as we showed in the design, just put everything here directly
	
	
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}
	
	@Override
	public String toString() {
		return super.toString();
	}
}
