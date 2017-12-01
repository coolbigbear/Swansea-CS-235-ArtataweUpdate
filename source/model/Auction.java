package model;

import model.exception.IllegalBidException;

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
 * @see Bid
 * @see Artwork
 * @version 1.0
 */
public final class Auction {
	
	/*
	 * Bassam Helal 27-Nov-17
	 * I really discourage putting the following fields in the Artwork class
	 * so I've put them here where they belong better
	 *
	 * Double reservePrice;
	 * Integer bidsAllowed;
	 * Date dateTimePlaced;
	 *
	 */
	
	private final Artwork artwork;
	private final Profile seller;
	private final Integer auctionID; /*TODO: Bassam Helal 27-Nov-17 This needs to be taken care of using the Database */
	private final List<Bid> bidList;
	private final Double reservePrice;
	private final Integer bidsAllowed;
	private final LocalDateTime dateTimePlaced;
	private Integer bidsLeft;
	private Profile highestBidder;
	private Boolean isCompleted;
	private Double highestPrice;

	
	/**
	 * Constructs a new Auction, note that all the parameters are immutable
	 * @param artwork the Artwork that the Auction will be selling
	 * @param seller the Profile representing the seller of the Auction
	 * @param bidsAllowed the number of Bids allowed before the Auction ends
	 * @param reservePrice the minimum accepted price of a Bid
	 */
	public Auction(Artwork artwork, Profile seller, Integer bidsAllowed, Double reservePrice) {
		this.artwork = artwork;
		this.seller = seller;
		
		// TODO: 29-Nov-17 Bassam Helal, ***REMOVED*** ***REMOVED*** change this to correspond to Database
		this.auctionID = null;
		
		this.bidList = new ArrayList<>();
		this.bidsLeft = bidsAllowed;
		this.bidsAllowed = bidsAllowed;
		this.reservePrice = reservePrice;
		this.isCompleted = false;
		this.dateTimePlaced = LocalDateTime.now();
	}
	
	
	public Boolean placeBid(Bid bid) {
		if (validateBid(bid)) {
			this.highestBidder = bid.getBidder();
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
		return (!bid.getBidder().equals(this.highestBidder));
	}
	
	private Boolean checkIfHigherThanReservePrice(Bid bid) {
		return (bid.getBidAmount() >= this.reservePrice);
	}
	
	private Boolean checkIfHigherThanCurrentHighest(Bid bid) {
		return (bid.getBidAmount() > this.highestPrice);
	}
	
	public Artwork getArtwork() {
		return this.artwork;
	}
	
	public Profile getSeller() {
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
	
	public Profile getHighestBidder() {
		return this.highestBidder;
	}
	
	public Boolean getCompleted() {
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
	
	// TODO: 29-Nov-17 Bassam Helal need to do this
	@Override
	public String toString() {
		return super.toString();
	}
	
}
