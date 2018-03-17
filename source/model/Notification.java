package model;

import java.time.LocalDateTime;
import java.util.List;

//TODO I think remove this. I'll have the algorithms here
public class Notification {

    //1. New artworks that are now on auction that were not on auction last time the user
    //logged in
    public List<Auction> getNewAuctionsSinceLastLogon() {
        LocalDateTime lastActivityTime = getTimeOfInterest();
        return Util.getNewAuctionsSince(lastActivityTime);
    }

    // could go to Util
    private LocalDateTime getTimeOfInterest() {
        Profile user = Util.getCurrentUser();
        LocalDateTime timeOfInterest = user.getLastLogInTime();

        for (Bid bid : user.getAllBidsPlaced()) {
            if (bid.getDateTimePlaced().isAfter(timeOfInterest)) {
                timeOfInterest = bid.getDateTimePlaced();
            }
        }
        for (Auction auction : user.getCurrentlySelling()) {
            if (auction.getDateTimePlaced().isAfter(timeOfInterest)) {
                timeOfInterest = auction.getDateTimePlaced();
            }
        }

        return timeOfInterest;
    }

    //2. A user that is selling an artwork will be able to see new bids on the items since the
    //last time they logged in.
    public List<Bid> getNewBidsSinceLastLogon() {
        LocalDateTime lastActivityTime = getTimeOfInterest();
        return Util.getNewBidsOnCurrentUserSince(lastActivityTime);
    }


    //3. Auctions that have been won / lost since the last login
    public List<Auction> getAuctionsWonSinceLastLogon() {
        LocalDateTime lastActivityTime = getTimeOfInterest();
        return null;
//TODO
    }
}
