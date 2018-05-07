package model;

import java.util.ArrayList;

/**
 * A Gallery is a collection of Auctions that the user can add Auctions to and view Auctions from.
 * <p>
 * A User can have any number of Galleries, each of which can contain any number of Auctions which can be
 * both ongoing or finished.
 * <p>
 * Note that a Gallery cannot have duplicate Auctions.
 *
 * @author ***REMOVED*** ***REMOVED***
 * @version 1.0
 * @see Artwork
 * @see Auction
 */
public class Gallery {

    private String galleryName;
    private static int ID = 0;
    private int localID;

    private ArrayList<Integer> listOfAuctionIDs;

    /**
     * Constructs a new Gallery Object
     *
     * @param galleryName      the name of the Gallery
     * @param listOfAuctionIDs List of AuctionIDs Integers of the Auctions of this Gallery
     */
    public Gallery(String galleryName, ArrayList<Integer> listOfAuctionIDs) {
        this.galleryName = galleryName;
        this.listOfAuctionIDs = listOfAuctionIDs;
        localID = ID;
        ID++;
    }

    /**
     * Adds an Auction to this Gallery
     *
     * @param auction the Auction to add to this Gallery
     */
    public void addAuctionToGallery(Auction auction) {
        listOfAuctionIDs.add(auction.getAuctionID());
    }

    /**
     * Removes the Auction from this Gallery
     *
     * @param auction the Auction to remove from this Gallery
     */
    public void removeAuctionFromGallery(Auction auction) {
        listOfAuctionIDs.remove(auction.getAuctionID());
    }

    /**
     * Gets this Gallery's ID
     *
     * @return this Gallery's ID
     */
    public int getLocalID() {
        return localID;
    }

    /**
     * Sets this Gallery's ID
     *
     * @param localID the ID to set it to
     */
    public void setLocalID(int localID) {
        this.localID = localID;
    }

    /**
     * Gets this Gallery's name
     *
     * @return the String of the name of this Gallery
     */
    public String getGalleryName() {
        return galleryName;
    }

    /**
     * Sets this Gallery's name
     *
     * @param galleryName the String to set this Gallery's name to
     */
    public void setGalleryName(String galleryName) {
        this.galleryName = galleryName;
    }

    /**
     * Gets the List of AuctionIDs of the Auctions that this Gallery has
     *
     * @return the List of AuctionIDs of the Auctions of this Gallery
     */
    public ArrayList<Integer> getListOfAuctionIDs() {
        return listOfAuctionIDs;
    }

    /**
     * Sets the List of Auction Ids of the Auctions that this Gallery has
     *
     * @param listOfAuctionIDs the List of AuctionIDs of the Auctions of this Gallery to be set to
     */
    public void setListOfAuctionIDs(ArrayList<Integer> listOfAuctionIDs) {
        this.listOfAuctionIDs = listOfAuctionIDs;
    }
}
