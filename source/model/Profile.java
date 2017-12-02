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
	private final String phoneNumber;
	private final String addressLine1;
	private final String addressLine2;
	private final String city;
	private final String country;
	private final String postcode;
	private List<Profile> favouriteUsers;
	private List<Auction> wonAuctions;
	private List<Auction> completedAuctions;
	private List<Auction> currentlySelling;
	
	//maybe we can do these differently {
	private List<Auction> newAuctions;
	private List<Auction> auctionsNewBids;
	//}
	
	private List<Bid> allBidsPlaced;
	private LocalDateTime lastLogInTime;
	
	//Private because should only be used by the factory
	private Profile(String username, String firstName, String lastName, String phoneNumber,
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
	
	//Used by the Database
	public Profile(String username, String firstName, String lastName, String phoneNumber,
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
	
	//Factory for adding new Profiles in the System, new Users, not Objects!!!
	public static Profile createNewProfile(String username, String firstName, String lastName, String phoneNumber,
	                                       String addressLine1, String addressLine2, String city, String country,
	                                       String postcode) {
		Profile profile = new Profile(username, firstName, lastName, phoneNumber, addressLine1, addressLine2, city,
				country, postcode);
		
		//send this to Database...
		//...
		//...
		
		return profile;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public String getPhoneNumber() {
		return this.phoneNumber;
	}
	
	public String getAddressLine1() {
		return this.addressLine1;
	}
	
	public String getAddressLine2() {
		return this.addressLine2;
	}
	
	public String getCity() {
		return this.city;
	}
	
	public String getCountry() {
		return this.country;
	}
	
	public String getPostcode() {
		return this.postcode;
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
	
	public LocalDateTime getLastLogInTime() {
		return this.lastLogInTime;
	}
	
	//This is validation of a username I think
	public boolean checkUsername(String username) {
		String checkUsername = "[a-zA-Z0-9]";
		if (username.matches(checkUsername)) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return this.username.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Profile) && (obj.hashCode() == this.hashCode());
	}
	
	//Implement this later!!!
	@Override
	public String toString() {
		return super.toString();
	}
}
