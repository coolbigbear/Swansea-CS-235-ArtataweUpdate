package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Auction {
	
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
	
	//region FIELDS
	
	private final Artwork artwork;
	private final Profile seller;
	private final Integer auctionID; // TODO: Bassam Helal 27-Nov-17 This needs to be taken care of using the Database
	private final List<Bid> bidList;
	private Integer bidsLeft;
	private Profile highestBidder;
	private Boolean isCompleted;
	private Double highestPrice;
	private final Double reservePrice;
	private final Integer bidsAllowed;
	private final Date datePlaced;
	
	//endregion
	
	//region CONSTRUCTORS
	
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
	
	//endregion
	
	//region METHODS
	
	
	//endregion
	
	//region SETTERS
	
	
	//endregion
	
	//region GETTERS
	
	
	//endregion
	
}
