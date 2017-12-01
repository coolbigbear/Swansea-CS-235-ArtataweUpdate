package model;

import java.util.List;

public class Profile {

	String username;
	
	List<Profile> favouriteUsers;
	List<Auction> wonAuctions;
	List<Auction> completedAuctions;
	List<Auction> currentlySelling;
	List<Bid> allBidsPlaced;

	public Profile(String username, List<Profile> favouriteUsers) {
		this.username = username;
		this.favouriteUsers = favouriteUsers;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Profile> getFavouriteUsers() {
		return favouriteUsers;
	}

	public void setFavouriteUsers(List<Profile> favouriteUsers) {
		this.favouriteUsers = favouriteUsers;
	}

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
