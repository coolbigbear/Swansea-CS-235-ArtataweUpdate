package model;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//TODO I think remove this. I'll have the algorithms here
public class Notification {

    // could go to Util
    public static LocalDateTime getTimeOfInterest() {
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

    //1. New artworks that are now on auction that were not on auction last time the user
    //logged in
    public static List<Auction> getNewAuctionsSinceLastLogon() {
        LocalDateTime lastActivityTime = getTimeOfInterest();
        return Util.getNewAuctionsSince(lastActivityTime);
    }

    //2. A user that is selling an artwork will be able to see new bids on the items since the
    //last time they logged in.
    public static List<Bid> getNewBidsSinceLastLogon() {
        LocalDateTime lastActivityTime = getTimeOfInterest();
        return Util.getNewBidsOnCurrentUserSince(lastActivityTime);
    }

    public static List<Auction> getNewBidsAuctionsSinceLastLogon() {
        LocalDateTime lastActivityTime = getTimeOfInterest();

        ArrayList<Auction> auctionsWithNewBids = new ArrayList<>();

        for (Bid bid : Util.getNewBidsOnCurrentUserSince(lastActivityTime)) {
            try {
                auctionsWithNewBids.add(Util.getAuctionByAuctionID(bid.getAuctionID()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return auctionsWithNewBids;
    }

    //3. Auctions that have been won / lost since the last login
    // New Auctions you have sold
    public static List<Auction> getAuctionsCurrentUserSoldSinceLastLogon() {
        LocalDateTime lastActivityTime = getTimeOfInterest();
        return Util.getAuctionsSoldSince(lastActivityTime);
    }

    // New Auctions you have lost
    public static List<Auction> getAuctionsCurrentUserLostSinceLastLogon() {
        LocalDateTime lastActivityTime = getTimeOfInterest();
        return Util.getAuctionsLostSince(lastActivityTime);
    }

    //4. Active auctions that they have bid on that are coming to a close (i.e. approaching
    //their bid limit)
    public static List<Auction> getAuctionsComingToCloseSinceLastLogon() {
        LocalDateTime lastActivityTime = getTimeOfInterest();
        return Util.getAuctionsComingToCloseSince(lastActivityTime);
    }


}
