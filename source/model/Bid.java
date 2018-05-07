package model;

import java.time.LocalDateTime;

/**
 * A Bid is an offer on an Auction with a proposed amount, all made by the current user
 * at a point in time.
 * <p>
 * A Bid cannot exist without these attributes, specifically a Bid requires:
 * <ul>
 * <li>Auction which the Bid will be placed on.</li>
 * <li>Profile Username which is the username of the current user, or the person placing the Bid</li>
 * <li>Bid Amount which is the amount associated with the Bid</li>
 * <li>Date which is the date and time the Bid was placed</li>
 * </ul>
 * <p>
 * Note that Bid validation on an Auction does not occur here, instead on the Auction,
 * meaning a Bid instance may exist where it does not satisfy the validation requirements of
 * the Auction, this Bid will not exist on the Database.
 *
 * @author Bassam Helal
 * @author ***REMOVED*** ***REMOVED***
 * @version 1.2
 * @see Auction
 */
public final class Bid {

    private final Integer auctionID;
    private final Double bidAmount;
    private final String bidder;
    private final LocalDateTime dateTimePlaced;

    /**
     * The primary and only constructor for a Bid, this assumes the Profile
     * placing the Bid is the current user.
     *
     * @param auctionID the Auction which the Bid will be placed on
     * @param bidAmount the Double representing the amount or price associated
     *                  with the Bid
     */
    public Bid(Integer auctionID, Double bidAmount) {
        this.auctionID = auctionID;
        this.bidAmount = bidAmount;
        this.dateTimePlaced = LocalDateTime.now();
        this.bidder = Util.getCurrentUser().getUsername();
    }

    /**
     * Gets the Bid amount
     *
     * @return the amount or price associated with the Bid
     */
    public Double getBidAmount() {
        return this.bidAmount;
    }

    /**
     * Gets the date and time the Bid was placed
     *
     * @return the date and time the Bid was placed
     */
    public LocalDateTime getDateTimePlaced() {
        return this.dateTimePlaced;
    }

    /**
     * Gets the username of the Profile that placed the Bid
     *
     * @return the username of the Profile that placed the Bid
     */
    public String getBidderUsername() {
        return this.bidder;
    }

    /**
     * Gets the Auction that the Bid will be placed on
     *
     * @return the Auction the Bid will be placed on
     */
    public Integer getAuctionID() {
        return this.auctionID;
    }

    /**
     * Returns the hashcode of the Bid, this is used to uniquely identify a Bid
     * and is used to check if two Bids are equal
     *
     * @return the int representing the hashcode of the Bid
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        return this.dateTimePlaced.hashCode() + this.bidAmount.intValue() + this.auctionID.hashCode();
    }

    /**
     * Checks to see if two Bids are equal, the current instance and the passed in parameter.
     * Note that this is not necessarily identity based, two different instances of Bid can be equal.
     *
     * @param obj the Object to check if it is equal to this current Bid
     * @return true if they are exactly equal and false otherwise
     * @see Object#hashCode()
     */
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Bid) && (obj.hashCode() == this.hashCode());
    }

    /**
     * Returns a String representation of the current Bid instance
     *
     * @return the String representation of the current Bid instance
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return "Bid: " + this.hashCode() + "\n" +
                "\tAuction: " + this.auctionID.toString() + "\n" +
                "\tAmount: " + this.bidAmount.toString() + "\n" +
                "\tBidder: " + this.bidder + "\n" +
                "\tTime: " + this.dateTimePlaced.toString() + "\n";
    }
}
