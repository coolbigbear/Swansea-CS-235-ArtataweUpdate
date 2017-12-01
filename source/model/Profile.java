package model;

import com.sun.xml.internal.bind.v2.TODO;

import java.util.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// TODO: Unsure on a 'Checkusername' method
// TODO: Have to add 'Filter' methods for profile
// TODO: Profile image as no class??

public class Profile {

    private List<Profile> favouriteUsers;
    private List<Auction> wonAuctions;
    private List<Auction> completedAuction;
    private List<Auction> currentlySelling;
    private List<Auction> newAuctions;
    private List<Auction> auctionsNewBids;
    private List<Bid> allBidsPlaced;
    private String username;
    private String contactInfo;
    private LocalDateTime lastLogInTime;

    public Profile(String username, String contactInfo){
        this.favouriteUsers = new ArrayList<>();
        this.wonAuctions = new ArrayList<>();
        this.completedAuction = new ArrayList<>();
        this.currentlySelling = new ArrayList<>();
        this.newAuctions = new ArrayList<>();
        this.auctionsNewBids = new ArrayList<>();
        this.allBidsPlaced = new ArrayList<>();
        this.username = username;
        this.contactInfo = contactInfo;
        this.lastLogInTime = LocalDateTime.now();
    }

    public List<Profile> getFavouriteUsers(){
        return this.favouriteUsers;
    }

    public List<Auction> getWonAuctions(){
        return this.wonAuctions;
    }

    public List<Auction> getCompletedAuction(){
        return this.completedAuction;
    }

    public List<Auction> getCurrentlySelling(){
        return this.currentlySelling;
    }

    public List<Auction> getNewAuctions(){
        return this.newAuctions;
    }

    public List<Auction> getAuctionsNewBids(){
        return this.auctionsNewBids;
    }

    public List<Bid> getAllBidsPlaced(){
        return this.allBidsPlaced;
    }

    public String getUsername(){
        return this.username;
    }

    public String getContactInfo(){
        return this.contactInfo;
    }

    public LocalDateTime getLastLogInTime() {
        return this.lastLogInTime;
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
