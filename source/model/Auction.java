package model;

import model.exceptions.IllegalBidException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class Auction {
	
	/*
	 * Bassam Helal 27-Nov-17
	 * I really discourage putting the following fields in the Artwork class
	 * so I've put them here where they belong better
	 *
	 * Double reservePrice;
	 * Integer bidsAllowed;
	 * Date datePlaced;
	 *
	 */
	
	
	private final Artwork artwork;
	private final Profile seller;
	private final Integer auctionID; /*TODO: Bassam Helal 27-Nov-17 This needs to be taken care of using the Database */
	private final List<Bid> bidList;
	private Integer bidsLeft;
	private Profile highestBidder;
	private Boolean isCompleted;
	private Double highestPrice;
	private final Double reservePrice;
	private final Integer bidsAllowed;
	private final Date datePlaced;
	
	
	public Auction(Artwork artwork, Profile seller, Integer bidsAllowed, Double reservePrice) {
		this.artwork = artwork;
		this.seller = seller;
		
		//something to set the auctionID from Database
		this.auctionID = null;
		
		this.bidList = new ArrayList<>();
		this.bidsLeft = bidsAllowed;
		this.bidsAllowed = bidsAllowed;
		this.reservePrice = reservePrice;
		this.isCompleted = false;
		this.datePlaced = new Date();
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
	
	public Date getDatePlaced() {
		return this.datePlaced;
	}
	
	
}
