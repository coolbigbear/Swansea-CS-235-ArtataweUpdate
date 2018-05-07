package model;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The Notification class is the class with static functions for gathering the Notifications information for
 * the current user.
 * <p>
 * Notifications are implemented by getting the user's time of last activity, this is the last point in time
 * the user has either placed a Bid or posted an Auction. If the user logs on and sees their notifications
 * and logs out without performing any actions then the next time they log in they will see the same notifications.
 * This is because there is no concurrent time checking in the system.
 * <p>
 * The notifications a user can see include the following based on the specification of the Artatawe System:
 * <ul>
 * <li>See new Auctions that are now being sold that weren't existing during the current user's last active
 * time</li>
 * <li>See new Bids placed on the current user's currently selling Auctions that weren't there during
 * the current user's last active time</li>
 * <li>See new Auctions that the current user has managed to sell since their last active time</li>
 * <li>See Auctions that the current user has bid on that they have now lost since their last active time</li>
 * <li>See Auctions that the current user has bid on that are coming to a close since last active time, in
 * this case this means Auctions with 2 bids or fewer but not completed</li>
 * </ul>
 *
 * @author Bassam Helal
 * @version 1.2
 * @see Util
 */
public class Notification {

    /**
     * Gets the current user's last active time, this is the point in time that the current user last did some
     * action, this is either placed a Bid or placed an Auction.
     *
     * @return the point in time representing the current user's last active time
     */
    private static LocalDateTime getTimeOfInterest() {
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

    /**
     * Gets the List of new Auctions that that are now being sold that weren't existing during the current user's last
     * active time.
     *
     * @return the List of new Auctions since this current user's last active time
     */
    public static List<Auction> getNewAuctionsSinceLastLogon() {
        LocalDateTime lastActivityTime = getTimeOfInterest();
        return Util.getNewAuctionsSince(lastActivityTime);
    }

    /**
     * Gets the List of new Bids placed on the current user's currently selling Auctions that weren't
     * there during the current user's last active time.
     *
     * @return the List of new Bids placed on the current user's current selling since their last active time
     */
    public static List<Bid> getNewBidsSinceLastLogon() {
        LocalDateTime lastActivityTime = getTimeOfInterest();
        return Util.getNewBidsOnCurrentUserSince(lastActivityTime);
    }

    /**
     * Gets the List of Auctions that the current user has managed to sell since their last active time.
     *
     * @return the List of Auctions that the current user has now sold since their last active time
     */
    public static List<Auction> getAuctionsCurrentUserSoldSinceLastLogon() {
        LocalDateTime lastActivityTime = getTimeOfInterest();
        return Util.getAuctionsSoldSince(lastActivityTime);
    }

    /**
     * Gets the List of Auctions that the current user has bid on that they have now lost since their last active time.
     *
     * @return the List of Auctions that the current user has bid on that they have now lost since their last active
     * time
     */
    public static List<Auction> getAuctionsCurrentUserLostSinceLastLogon() {
        LocalDateTime lastActivityTime = getTimeOfInterest();
        return Util.getAuctionsLostSince(lastActivityTime);
    }

    /**
     * Gets the List of Auctions that the current user has bid on that are coming to a close since last active
     * time, in this case this means Auctions with 2 bids or fewer but not completed.
     *
     * @return the List of Auctions that the current user has bid on that coming to a close since their last active time
     */
    public static List<Auction> getAuctionsComingToCloseSinceLastLogon() {
        LocalDateTime lastActivityTime = getTimeOfInterest();
        return Util.getAuctionsComingToCloseSince(lastActivityTime);
    }

}
