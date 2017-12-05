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
	private final String seller;
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
	 * @param seller the Profile representing the seller of the Auction
	 * @param bidsAllowed the number of Bids allowed before the Auction ends
	 * @param reservePrice the minimum accepted price of a Bid
	 */
	public Auction(Artwork artwork, String seller, Integer auctionID, Integer bidsAllowed, Double reservePrice) {
		this.artwork = artwork;
		this.seller = seller;
		this.auctionID = auctionID;
		this.bidList = new ArrayList<>();
		this.bidsLeft = bidsAllowed;
		this.bidsAllowed = bidsAllowed;
		this.reservePrice = reservePrice;
		this.isCompleted = false;
		this.dateTimePlaced = LocalDateTime.now();
	}
	
	public Auction(Artwork artwork, String seller, Integer auctionID, List<Bid> bidList,
	               Double reservePrice, Integer bidsAllowed, LocalDateTime dateTimePlaced,
	               Integer bidsLeft, String highestBidder, Boolean isCompleted, Double highestPrice) {
		this.artwork = artwork;
		this.seller = seller;
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
	
	public String getSeller() {
		return this.seller;
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
