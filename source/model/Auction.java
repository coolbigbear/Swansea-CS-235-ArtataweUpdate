package model;

import model.exception.IllegalBidException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * An Auction is the only means of selling and buying a product (Artworks) in the Artatawe system.
 * The buying and selling is done through means of bidding, similar to traditional auctioning systems.
 * <p>
 * Note that after an Auction has been placed it cannot be changed, it is immutable.
 * <p>
 * Note that all Bid validation is done here, not in the Bid class.
 *
 * @author Bassam Helal
 * @author ***REMOVED*** ***REMOVED***
 * @version 1.2
 * @see Bid
 * @see Artwork
 * @see Comparable
 */
public final class Auction implements Comparable<Auction> {

    private final Artwork artwork;
    private final String sellerName;
    private final Integer auctionID;
    private final List<Bid> bidList;
    private final Double reservePrice;
    private final Integer bidsAllowed;
    private final LocalDateTime dateTimePlaced;
    private Integer bidsLeft;
    @Nullable
    private String highestBidder;
    private Boolean isCompleted;
    private Double highestPrice = 0.0;

    /**
     * Private constructor used by the factory method to construct a new Auction
     *
     * @param artwork      the Artwork of the Auction
     * @param sellerName   the String representing the username of the seller
     * @param bidsAllowed  the number of bids allowed in the Auction
     * @param reservePrice the reserve price of the Auction
     * @see Auction#createNewAuction(Artwork, String, Integer, Double)
     */
    private Auction(Artwork artwork, String sellerName, Integer bidsAllowed, Double reservePrice) {
        this.artwork = artwork;
        this.sellerName = sellerName;
        this.auctionID = Util.getNewAuctionID();
        this.bidList = new ArrayList<>();
        this.bidsLeft = bidsAllowed;
        this.bidsAllowed = bidsAllowed;
        this.reservePrice = reservePrice;
        this.isCompleted = false;
        this.dateTimePlaced = LocalDateTime.now();
    }

    /**
     * A factory method used to create new Auctions into the Artatawe system, this is the only way of adding new
     * Auctions to the system for other users to see and Bid on, not using a constructor.
     *
     * @param artwork      the Artwork of the Auction
     * @param sellerName   the String representing the username of the seller
     * @param bidsAllowed  the number of bids allowed in the Auction
     * @param reservePrice the reserve price of the Auction
     * @return the new Auction that has been created and added to the system
     */
    public static Auction createNewAuction(Artwork artwork, String sellerName,
                                           Integer bidsAllowed, Double reservePrice) {

        Auction localAuction = new Auction(artwork, sellerName, bidsAllowed, reservePrice);
        Util.getCurrentUser().getCurrentlySelling().add(localAuction);

        return localAuction;
    }

    /**
     * The constructor used by the Database to populate the memory with all the Auctions of the system that are saved
     * persistently in the Database.
     * <p>
     * Note this is unsafe and not recommended to use by the programmer,instead use the createNewAuction method.
     *
     * @param artwork        the Artwork of the Auction
     * @param sellerName     the String representing the username of the seller
     * @param auctionID      the integer ID of the Auction
     * @param bidList        the List of Bids of the Auction
     * @param reservePrice   the reserve price of the Auction
     * @param bidsAllowed    the number of bids allowed in the Auction
     * @param dateTimePlaced the LocalDateTime representing the point in time the Auction was placed
     * @param bidsLeft       the number of Bids the Auction has left
     * @param highestBidder  the String representing the username of the highest bidder
     * @param isCompleted    the boolean representing whether the Auction is completed
     * @param highestPrice   the double representing the highest price in the Auction, this would be the amount of the
     *                       highest bid on the Auction
     * @see Auction#createNewAuction(Artwork, String, Integer, Double)
     */
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

    /**
     * The only way to place a Bid on an Auction, all Bid validation is done here, an IllegalBidException will be
     * thrown if the Bid violates the constraints.
     *
     * @param bid the Bid to be placed
     * @throws IllegalBidException if the Bid placed does not follow the constraints.
     * @see IllegalBidException
     * @see Bid
     */
    public void placeBid(Bid bid) throws IllegalBidException {
        if (validateBid(bid)) {
            this.highestBidder = bid.getBidderUsername();
            this.highestPrice = bid.getBidAmount();
            this.bidList.add(bid);
            this.bidsLeft--;
            updateIsCompleted();
        } else throw new IllegalBidException(IllegalBidException.IllegalBidType.UNEXPECTED_EXCEPTION);
    }

    /**
     * Used to validate the Bid placed, this is the thrower of the IllegalBidException.
     *
     * @param bid the Bid to ve validated
     * @return the boolean representing whether the Bid is valid
     * @throws IllegalBidException if the Bid violates any constraints
     * @see IllegalBidException
     * @see Bid
     */
    private Boolean validateBid(Bid bid) throws IllegalBidException {
        if (!checkIfNotHighestBidder(bid)) {
            throw new IllegalBidException(IllegalBidException.IllegalBidType.ALREADY_HIGHEST_BIDDER);
        }
        if (!checkIfHigherThanReservePrice(bid)) {
            throw new IllegalBidException(IllegalBidException.IllegalBidType.LOWER_THAN_RESERVE_PRICE);
        }
        if (!checkIfHigherThanCurrentHighest(bid)) {
            throw new IllegalBidException(IllegalBidException.IllegalBidType.LOWER_THAN_HIGHEST);
        } else
            return (checkIfNotHighestBidder(bid) &&
                    checkIfHigherThanReservePrice(bid) &&
                    checkIfHigherThanCurrentHighest(bid));
    }

    /**
     * Checks if the Bid is not placed by the current highest bidder.
     *
     * @param bid the Bid to be validated
     * @return true if the bidder is not the current highest, false otherwise
     */
    private Boolean checkIfNotHighestBidder(Bid bid) {
        return (this.highestBidder == null || !bid.getBidderUsername().equals(this.highestBidder));
    }

    /**
     * Checks if the Bid is higher than the reserve price of the Auction.
     *
     * @param bid the Bid to be validated
     * @return true if the Bid is higher than the reserve price, false otherwise
     */
    private Boolean checkIfHigherThanReservePrice(Bid bid) {
        return (bid.getBidAmount() > this.reservePrice);
    }

    /**
     * Checks if the Bid amount is higher than the current highest Bid's amount.
     *
     * @param bid the Bid to be validated
     * @return true if the Bid amount is greater than the current highest Bid's amount.
     */
    private Boolean checkIfHigherThanCurrentHighest(Bid bid) {
        return (this.highestPrice == null || bid.getBidAmount() > this.highestPrice);
    }

    /**
     * Used to update the Auction's status of completion, this is when there are no Bids left to place.
     */
    private void updateIsCompleted() {
        if (this.bidsLeft == 0) {
            this.isCompleted = true;
        }
    }

    /**
     * Gets the Artwork associated with the Auction.
     *
     * @return the Artwork of the Auction
     */
    public Artwork getArtwork() {
        return this.artwork;
    }

    /**
     * Gets the seller's username of the Auction.
     *
     * @return the String representing the Auction's seller's username
     */
    public String getSellerName() {
        return this.sellerName;
    }

    /**
     * Gets the Auctions number ID.
     *
     * @return the Integer representing the Auction's ID
     */
    public Integer getAuctionID() {
        return this.auctionID;
    }

    /**
     * Gets the List of Bids placed on the Auction.
     *
     * @return the List of Bids representing all the Bids placed on the Auction
     */
    public List<Bid> getBidList() {
        return this.bidList;
    }

    /**
     * Gets the number of Bids left on the Auction.
     *
     * @return the Integer representing the number of Bids left on the Auction
     */
    public Integer getBidsLeft() {
        return this.bidsLeft;
    }

    /**
     * Gets the Auction's highest bidder, this may be null.
     *
     * @return the String representing the Auction's highest bidder
     */
    @Nullable
    public String getHighestBidder() {
        return this.highestBidder;
    }

    /**
     * Gets the status of completion of the Auction.
     *
     * @return true if the Auction is completed and false otherwise
     */
    public Boolean isCompleted() {
        return this.isCompleted;
    }

    /**
     * Gets ths highest bid amount of the Auction, this is referred to as the Auction's highest price.
     *
     * @return the Double representing the Auction's highest bid's amount, if the Auction has no Bids placed, this is
     * 0.0
     */
    public Double getHighestPrice() {
        return this.highestPrice;
    }

    /**
     * Gets the Auction's reserve price.
     *
     * @return the Double representing the Auction's reserve price
     */
    public Double getReservePrice() {
        return this.reservePrice;
    }

    /**
     * Gets the number of Bids allowed on the Auction.
     *
     * @return the Integer representing the number of Bids left on the Auction
     */
    public Integer getBidsAllowed() {
        return this.bidsAllowed;
    }

    /**
     * Gets the date and time the Auction was placed.
     *
     * @return the LocalDateTime representing the point in time the Auction was placed
     * @see LocalDateTime
     */
    public LocalDateTime getDateTimePlaced() {
        return this.dateTimePlaced;
    }

    /**
     * Used to get the hashcode of the Auction.
     *
     * @return the integer representing the hashcode of the Auction
     * @see Object#hashCode()
     */
    @Override
    public int hashCode() {
        return this.dateTimePlaced.hashCode() + this.auctionID;
    }

    /**
     * Used to check if an Object passed in is equal to this Auction.
     *
     * @param obj the Object used to check for equality
     * @return true if it is equal and false otherwise, will always return false if the Object is not an instance of
     * Auction
     * @see Object#equals(Object)
     */
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Auction) && (obj.hashCode() == this.hashCode());
    }

    /**
     * Used to get the string representation of the Auction, currently this is just the Auction's ID followed by it's
     * Artwork's title.
     *
     * @return the String representation of the Auction
     * @see Object#toString()
     */
    @Override
    public String toString() {
        return "Auction ID: " + getAuctionID() + "\nArtwork Name: " + this.artwork.getTitle() + "\n";
    }

    /**
     * Used to compare an Auction to another Auction, currently the comparison is done using the
     * <code>dateTimePlaced</code> filed of both Auctions to compare.
     *
     * @param otherAuction the Auction to be compared with
     * @return the integer representing the comparison
     * @see Comparable#compareTo(Object)
     * @see LocalDateTime#compareTo(ChronoLocalDateTime)
     */
    @Override
    public int compareTo(@NotNull Auction otherAuction) {
        return this.getDateTimePlaced().compareTo(otherAuction.getDateTimePlaced());
    }

    public static Comparator<Auction> artworkComparatorPriceAsc = (s1, s2) -> {

        double artworkPrice1;
        double artworkPrice2;

        if (s1.getReservePrice()>s1.getHighestPrice()) {
            artworkPrice1 = s1.getReservePrice();
        } else {
            artworkPrice1 = s1.getHighestPrice();
        }
        if (s2.getReservePrice()>s2.getHighestPrice()) {
            artworkPrice2 = s2.getReservePrice();
        } else {
            artworkPrice2 = s2.getHighestPrice();
        }

        /*For ascending order*/
        return (int) (artworkPrice1-artworkPrice2);

        /*For descending order*/
        //rollno2-rollno1;
    };

    public static Comparator<Auction> artworkComparatorPriceDesc = (s1, s2) -> {

        double artworkPrice1;
        double artworkPrice2;

        if (s1.getReservePrice()>s1.getHighestPrice()) {
            artworkPrice1 = s1.getReservePrice();
        } else {
            artworkPrice1 = s1.getHighestPrice();
        }
        if (s2.getReservePrice()>s2.getHighestPrice()) {
            artworkPrice2 = s2.getReservePrice();
        } else {
            artworkPrice2 = s2.getHighestPrice();
        }

        /*For descending order*/
        return (int) (artworkPrice2-artworkPrice1);

    };

    public static Comparator<Auction> artworkComparatorNameAsc = (s1, s2) -> {

        String artworkName1 = s1.getArtwork().title;
        String artworkName2 = s2.getArtwork().title;

        /*For descending order*/
        return artworkName1.compareTo(artworkName2);

    };

    public static Comparator<Auction> artworkComparatorNameDesc = (s1, s2) -> {

        String artworkName1 = s1.getArtwork().title;
        String artworkName2 = s2.getArtwork().title;

        /*For descending order*/
        return artworkName2.compareTo(artworkName1);

    };
}