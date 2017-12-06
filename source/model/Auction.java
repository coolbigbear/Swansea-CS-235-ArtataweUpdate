package model;

import model.exception.IllegalBidException;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * An Auction is the only means of selling and buying a product (Artworks) in the Artatawe system.
 * The buying and selling is done through means of bidding, similar to traditional auctioning systems.
 *
 * Note that after an Auction has been placed it cannot be changed, it is immutable.
 *
 * @author Bassam Helal
 * @version 1.0
 * @see Bid
 * @see Artwork
 */
public final class Auction implements Comparable<Auction> {

	private final Artwork artwork;
	private final String sellerName;
	private final Integer auctionID; //TODO: ***REMOVED***  Increment ID during new auction creation
	private final List<Bid> bidList;
	private final Double reservePrice;
	private final Integer bidsAllowed;
	private final LocalDateTime dateTimePlaced;
	private Integer bidsLeft;
	private String highestBidder;
	private Boolean isCompleted;
	private Double highestPrice = 0.0;

	/**
	 * Constructs a new Auction, note that all the parameters are immutable
	 *
	 * @param artwork the Artwork that the Auction will be selling
	 * @param sellerName the Profile representing the sellerName of the Auction
	 * @param bidsAllowed the number of Bids allowed before the Auction ends
	 * @param reservePrice the minimum accepted price of a Bid
	 */
	public Auction(Artwork artwork, String sellerName, Integer auctionID, Integer bidsAllowed, Double reservePrice) {
		this.artwork = artwork;
		this.sellerName = sellerName;

		// TODO: 29-Nov-17 Bassam Helal, ***REMOVED*** ***REMOVED*** change this to correspond to Database, it shouldn't be parameter
		this.auctionID = auctionID;
		this.bidList = new ArrayList<>();
		this.bidsLeft = bidsAllowed;
		this.bidsAllowed = bidsAllowed;
		this.reservePrice = reservePrice;
		this.isCompleted = false;
		this.dateTimePlaced = LocalDateTime.now();
	}

	//factory
	public static Auction createNewAuction(Artwork artwork, String seller, Integer auctionID,
	                                       Integer bidsAllowed, Double reservePrice) {
		return new Auction(artwork, seller, auctionID, bidsAllowed, reservePrice);
	}


	public Auction(Artwork artwork, String sellerName, Integer auctionID, List<Bid> bidList,
	               Double reservePrice, Integer bidsAllowed, LocalDateTime dateTimePlaced,
	               Integer bidsLeft, String highestBidder, Boolean isCompleted, Double highestPrice) {
		this.artwork = artwork;
		this.sellerName = sellerName;
		this.auctionID = auctionID;
		this.bidList = bidList;
		this.reservePrice = reservePrice;
		this.bidsAllowed = bidsAllowed;
		this.dateTimePlaced = dateTimePlaced;
		this.bidsLeft = bidsLeft;
		this.highestBidder = highestBidder;
		this.isCompleted = isCompleted;
		this.highestPrice = highestPrice;
	}

	public Boolean placeBid(Bid bid) {
		if (validateBid(bid)) {
			this.highestBidder = bid.getBidderUsername();
			this.highestPrice = bid.getBidAmount();
			return true;
		} else throw new IllegalBidException("Invalid Bid!");
	}

	private Boolean validateBid(Bid bid) {
		return (checkIfNotHighestBidder(bid) &&
				checkIfHigherThanReservePrice(bid) &&
				checkIfHigherThanCurrentHighest(bid));
	}

	private Boolean checkIfNotHighestBidder(Bid bid) {
		if (this.highestBidder == null) {
			return true;
		} else {
			return (!bid.getBidderUsername().equals(this.highestBidder));
		}
	}

	private Boolean checkIfHigherThanReservePrice(Bid bid) {
		return (bid.getBidAmount() >= this.reservePrice);
	}

	private Boolean checkIfHigherThanCurrentHighest(Bid bid) {
		if (this.highestPrice == null) {
			return true;
		} else {
			return (bid.getBidAmount() > this.highestPrice);
		}
	}

	public Artwork getArtwork() {
		return this.artwork;
	}

	public String getSellerName() {
		return this.sellerName;
	}

	public Integer getAuctionID() {
		return this.auctionID;
	}

	public List<Bid> getBidList() {
		return this.bidList;
	}

	public Integer getBidsLeft() {
		return this.bidsLeft;
	}

	public String getHighestBidder() {
		return this.highestBidder;
	}

	public Boolean isCompleted() {
		return this.isCompleted;
	}

	public Double getHighestPrice() {
		return this.highestPrice;
	}

	public Double getReservePrice() {
		return this.reservePrice;
	}

	public Integer getBidsAllowed() {
		return this.bidsAllowed;
	}

	public LocalDateTime getDateTimePlaced() {
		return this.dateTimePlaced;
	}

	@Override
	public int hashCode() {
		return this.dateTimePlaced.hashCode() + this.auctionID;
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof Auction) && (obj.hashCode() == this.hashCode());
	}

	@Override
	public String toString() {
		return "Auction id: " + getAuctionID();
	}

	@Override
	public int compareTo(@NotNull Auction otherAuction) {
		return this.getDateTimePlaced().compareTo(otherAuction.getDateTimePlaced());
	}
}
