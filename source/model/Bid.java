package model;

import java.util.Date;

public final class Bid {
	
	private final Double bidAmount;
	private final Date timePlaced;
	private final Profile bidder;
	private final Auction auction;
	
	public Bid(Auction auction, Double bidAmount) {
		this.auction = auction;
		this.bidAmount = bidAmount;
		this.timePlaced = new Date();
		this.bidder = Util.currentUser;
	}
	
	public Double getBidAmount() {
		return this.bidAmount;
	}
	
	public Date getTimePlaced() {
		return this.timePlaced;
	}
	
	public Profile getBidder() {
		return this.bidder;
	}
	
	public Auction getAuction() {
		return this.auction;
	}
}
