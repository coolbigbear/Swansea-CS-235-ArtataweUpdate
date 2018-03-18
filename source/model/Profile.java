package model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

/**
 * A Profile represents a single user in the Artatawe System.
 *
 * Note that a Profile has many of its fields marked as final, meaning a Profile cannot edit its fields, they are
 * immutable.
 *
 * @author Ben Sampson
 * @author ***REMOVED*** ***REMOVED***
 * @author Bassam Helal
 * @version 1.6
 */
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
	private String profileImagePath;
	private List<String> favouriteUsers;
	private List<Gallery> userGalleries;
	private List<Auction> wonAuctions;
	private List<Auction> completedAuctions;
	private List<Auction> currentlySelling;
	private List<Bid> allBidsPlaced;
	private LocalDateTime lastLogInTime;
	
	/**
	 * Private constructor to be used only by the factory
	 *
	 * @param username the String representing the username of the Profile
	 * @param firstName the String representing the first name of the Profile
	 * @param lastName the String representing the last name of the Profile
	 * @param phoneNumber the String representing the phone number of the Profile
	 * @param addressLine1 the String representing the address line 1 of the Profile
	 * @param addressLine2 the String representing the address line 2 of the Profile
	 * @param city the String representing the city of the Profile
	 * @param country the String representing the country of the Profile
	 * @param postcode the String representing the postcode of the Profile
	 * @param profileImagePath the String path of the profile image of the Profile
	 */
	private Profile(String username, String firstName, String lastName, String phoneNumber,
	                String addressLine1, String addressLine2, String city, String country, String postcode,
	                String profileImagePath) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.city = city;
		this.country = country;
		this.postcode = postcode;
		this.profileImagePath = profileImagePath;
		this.favouriteUsers = new ArrayList<>();
		this.wonAuctions = new ArrayList<>();
		this.completedAuctions = new ArrayList<>();
		this.currentlySelling = new ArrayList<>();
		this.allBidsPlaced = new ArrayList<>();
		this.userGalleries = new ArrayList<>();
		this.lastLogInTime = LocalDateTime.now();
	}

	/**
	 * Constructs a new Profile instance to be used by the system memory, this is not the way to add new Profiles to
	 * the system, instead use the factory method, this should only be used by the database
	 *
	 * @param username the String representing the username of the Profile
	 * @param firstName the String representing the first name of the Profile
	 * @param lastName the String representing the last name of the Profile
	 * @param phoneNumber the String representing the phone number of the Profile
	 * @param addressLine1 the String representing the address line 1 of the Profile
	 * @param addressLine2 the String representing the address line 2 of the Profile
	 * @param city the String representing the city of the Profile
	 * @param country the String representing the country of the Profile
	 * @param postcode the String representing the postcode of the Profile
	 * @param profileImagePath the String path of the profile image of the Profile
	 * @param favouriteUsers the List of Strings representing the favourite users of the Profile
	 * @param wonAuctions the List of Auctions representing the won Auctions of the Profile
	 * @param completedAuctions the List of Auctions representing the completed Auctions of the Profile
	 * @param currentlySelling the List of Auctions representing the currently selling Auctions of the Profile
	 * @param allBidsPlaced the List of Bids representing the Bids placed by the Profile
	 * @param userGalleries the List of galleries that the user has
	 * @param lastLogInTime the LocalDateTime representing the last log in time of the Profile
	 */
	public Profile(String username, String firstName, String lastName, String phoneNumber,
	               String addressLine1, String addressLine2, String city, String country,
	               String postcode, String profileImagePath, List<String> favouriteUsers,
	               List<Auction> wonAuctions, List<Auction> completedAuctions,
	               List<Auction> currentlySelling, List<Bid> allBidsPlaced, List<Gallery> userGalleries, LocalDateTime lastLogInTime) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.city = city;
		this.country = country;
		this.profileImagePath = profileImagePath;
		this.postcode = postcode;
		this.favouriteUsers = favouriteUsers;
		this.wonAuctions = wonAuctions;
		this.completedAuctions = completedAuctions;
		this.currentlySelling = currentlySelling;
		this.allBidsPlaced = allBidsPlaced;
		this.userGalleries = userGalleries;
		this.lastLogInTime = lastLogInTime;
	}
	
	/**
	 * A factory method used to create new Auctions into the system and save them to the database, this is the only
	 * recommended way of adding new Auctions into the system.
	 *
	 * @param username the String representing the username of the Profile
	 * @param firstName the String representing the first name of the Profile
	 * @param lastName the String representing the last name of the Profile
	 * @param phoneNumber the String representing the phone number of the Profile
	 * @param addressLine1 the String representing the address line 1 of the Profile
	 * @param addressLine2 the String representing the address line 2 of the Profile
	 * @param city the String representing the city of the Profile
	 * @param country the String representing the country of the Profile
	 * @param postcode the String representing the postcode of the Profile
	 * @param profileImagePath the String path of the profile image of the Profile
	 *
	 * @return the new Profile created
	 */
	public static Profile createNewProfile(String username, String firstName, String lastName, String phoneNumber,
	                                       String addressLine1, String addressLine2, String city, String country,
	                                       String postcode, String profileImagePath) {
		Profile profile = new Profile(username, firstName, lastName, phoneNumber, addressLine1, addressLine2, city,
				country, postcode, profileImagePath);
		
		//send this to Database...
		//...
		//...
		
		return profile;
	}
	
	/**
	 * Gets the username of the Profile
	 *
	 * @return the String representing the username of the Profile
	 */
	public String getUsername() {
		return this.username;
	}
	
	/**
	 * Gets the first name of the Profile
	 *
	 * @return the String representing the first name of the Profile
	 */
	public String getFirstName() {
		return this.firstName;
	}
	
	/**
	 * Gets the last name of the Profile
	 *
	 * @return the String representing the last name of the Profile
	 */
	public String getLastName() {
		return this.lastName;
	}
	
	/**
	 * Gets the phone number of the Profile
	 *
	 * @return the String representing the phone number of the Profile
	 */
	public String getPhoneNumber() {
		return this.phoneNumber;
	}
	
	/**
	 * Gets the address line 1 of the Profile
	 *
	 * @return the String representing the address line 1 of the Profile
	 */
	public String getAddressLine1() {
		return this.addressLine1;
	}
	
	/**
	 * Gets the address line 2 of the Profile
	 *
	 * @return the String representing the address line 2 of the Profile
	 */
	public String getAddressLine2() {
		return this.addressLine2;
	}
	
	/**
	 * Gets the city of the Profile
	 *
	 * @return the String representing the city of the Profile
	 */
	public String getCity() {
		return this.city;
	}
	
	/**
	 * Gets the country of the Profile
	 *
	 * @return the String representing the country of the Profile
	 */
	public String getCountry() {
		return this.country;
	}
	
	/**
	 * Gets the postcode of the Profile
	 *
	 * @return the String representing the postcode of the Profile
	 */
	public String getPostcode() {
		return this.postcode;
	}
	
	/**
	 * Sets the Profile's profile image path
	 *
	 * @param profileImg the String representing the path of the profile image
	 */
	public void setProfileImagePath(String profileImg) {
		this.profileImagePath = profileImg;
	}
	
	/**
	 * Gets the path of the Profile's Profile Image
	 *
	 * @return the String representing the path of the Profile's profile image
	 */
	public String getProfileImagePath() {
		return this.profileImagePath;
	}
	
	/**
	 * Gets the Profile's favourite users
	 *
	 * @return the List of Strings representing the Profile's favourite users
	 */
	public List<String> getFavouriteUsers() {
		return this.favouriteUsers;
	}

	/**
	 * Gets this Profile's galleries
	 * @return the List of Galleries this Profile has
	 */
	public List<Gallery> getUserGalleries() {
		return this.userGalleries;
	}
	
	/**
	 * Gets the List of Auctions the Profile has won
	 *
	 * @return the List of Auctions representing the Auctions the Profile has won
	 */
	public List<Auction> getWonAuctions() {
		return this.wonAuctions;
	}
	
	/**
	 * Gets the List of completed Auctions
	 *
	 * @return the List of Auctions representing the Profile's completed Auctions
	 */
	public List<Auction> getCompletedAuctions() {
		return this.completedAuctions;
	}
	
	/**
	 * Gets the list of Auctions the Profile is selling
	 *
	 * @return the List of Auctions representing the Auctions being sold by the Profile
	 */
	public List<Auction> getCurrentlySelling() {
		return this.currentlySelling;
	}
	
	/**
	 * Gets the List of Bids placed by the Profile
	 *
	 * @return the List of Bids representing the bids placed by the Profile
	 */
	public List<Bid> getAllBidsPlaced() {
		return this.allBidsPlaced;
	}
	
	/**
	 * Gets the last log in time of the Profile
	 *
	 * @return the LocalDateTime representing the last log in time of the Profile
	 */
	public LocalDateTime getLastLogInTime() {
		return this.lastLogInTime;
	}
	
	/**
	 * Checks if the username entered consists of a valid string that includes a-z, A-Z, 0-9.
	 * If username consists of something other it throws an exception.
	 *
	 * Used to ensure that the username is valid upon username creation.
	 *
	 * @param username The users custom username
	 *
	 * @return true if its a valid username, throws an InputMismatchException otherwise
	 *
	 * @throws InputMismatchException if the username is not a valid username
	 */
	public Boolean checkUsername(String username) {
		String checkUsername = "[a-zA-Z0-9]";
		if (username.matches(checkUsername)) {
			System.out.println("Valid username");
			return true;
		} else {
			throw new InputMismatchException("Invalid username!");
		}
	}

	/**
	 * Adds a new gallery to the Profile
	 * @param nameOfGallery name of the gallery to add
	 */
	public void addNewGallery(String nameOfGallery) {
		ArrayList<Integer> listOfAuctionIDs = new ArrayList<>();
		userGalleries.add(new Gallery(nameOfGallery, listOfAuctionIDs ));
	}

	/**
	 * Removes a gallery from the Profile
	 * @param galleryID the id of the gallery to remove
	 */
	public void removerGallery(int galleryID) {
		for(Gallery e : userGalleries) {
			if(e.getLocalID() == galleryID) {
				userGalleries.remove(e);
			}
		}
	}
	
	/**
	 * Gets the hashcode of the Profile
	 *
	 * @return the integer representing the hashcode of the Profile
	 *
	 * @see Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.username.hashCode();
	}
	
	/**
	 * Checks to see if the passed in Object is equals to this instance
	 *
	 * @param obj the Object to compare with
	 *
	 * @return true if equal, false otherwise
	 *
	 * @see Object#equals(Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Profile) && (obj.hashCode() == this.hashCode());
	}
	
	/**
	 * Gets the String representation of the Profile
	 *
	 * @return the String representation of the Profile
	 *
	 * @see Object#toString()
	 */
	@Override
	public String toString() {
		return this.username;
	}
}
