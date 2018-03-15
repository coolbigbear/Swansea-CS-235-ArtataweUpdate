package model;

import java.time.LocalDateTime;
import java.util.List;

//TODO I think remove this. I'll have the algorithms here
public class Notification {

    //1. New artworks that are now on auction that were not on auction last time the user
    //logged in
    public List<Auction> getNewAuctionsSinceLastLogon() {
        // get time of last logon
        LocalDateTime lastActivityTime = getTimeOfInterest();
        // get auctions that have a time placed later than the last logon time
        return Util.getNewAuctionsSince(lastActivityTime);
    }

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


}
