package model;

/* TODO: 27-Nov-17 Bassam Helal We should consider using something other than the java.lang.Date
 * we could use the Calendar class or better yet the javax.time package, I will look into this
 */

import java.util.Date;

/**
 * A Bid is an offer on an Auction with a proposed amount, all made by the current user
 * at a point in time.
 *
 * A Bid cannot exist without these attributes, specifically a Bid requires:
 * <ul>
 * <li>Auction which the Bid will be placed on.</li>
 * <li>Profile which is the current user, or the person placing the Bid</li>
 * <li>Bid Amount which is the amount associated with the Bid</li>
 * <li>Date which is the date and time the Bid was placed</li>
 * </ul>
 *
 * Notice that Bid validation on an Auction does not occur here, instead on the Auction,
 * meaning a Bid instance may exist where it does not satisfy the validation requirements of
 * the Auction, this Bid will not exist on the Database.
 *
 * @author Bassam Helal
 * @version 1.0
 * @see Auction
 */
public final class Bid {
	
	private final Auction auction;
	private final Double bidAmount;
	private final Profile bidder;
	private final Date timePlaced;
	
	/**
	 * The primary and only constructor for a Bid, this assumes the Profile
	 * placing the Bid is the current user.
	 *
	 * @param auction the Auction which the Bid will be placed on
	 * @param bidAmount the Double representing the amount or price associated
	 * 		with the Bid
	 */
	public Bid(Auction auction, Double bidAmount) {
		this.auction = auction;
		this.bidAmount = bidAmount;
		this.timePlaced = new Date();
		this.bidder = Util.currentUser;
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
	 * Gets the time the Bid was placed
	 *
	 * @return the time the Bid was placed
	 */
	public Date getTimePlaced() {
		return this.timePlaced;
	}
	
	/**
	 * Gets the Profile that placed the Bid
	 *
	 * @return the Profile that placed the Bid
	 */
	public Profile getBidder() {
		return this.bidder;
	}
	
	/**
	 * Gets the Auction that the Bid will be placed on
	 *
	 * @return the Auction the Bid will be placed on
	 */
	public Auction getAuction() {
		return this.auction;
	}
	
	@Override
	public int hashCode() {
		return this.timePlaced.hashCode() + this.bidAmount.intValue() + this.auction.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj instanceof Bid && obj.toString().equals(this.toString());
	}
	
	@Override
	public String toString() {
		return "Bid: " + this.hashCode() + "\n" +
				"\tAuction: " + this.auction.toString() + "\n" +
				"\tAmount: " + this.bidAmount.toString() + "\n" +
				"\tBidder: " + this.bidder.toString() + "\n" +
				"\tTime: " + this.timePlaced.toString() + "\n";
	}
}
