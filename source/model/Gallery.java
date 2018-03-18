package model;

import java.util.ArrayList;

/**
 * A Gallery is a collection of Auctions that the user can add Auctions to and view Auctions from.
 *
 * A User can have any number of Galleries, each of which can contain any number of Auctions which can be
 * both ongoing or finished.
 *
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

    public Gallery(String galleryName, ArrayList<Integer> listOfAuctionIDs) {
        this.galleryName = galleryName;
        this.listOfAuctionIDs = listOfAuctionIDs;
        localID = ID;
        ID ++;
    }

    public void addAuctionToGallery(Auction auction) {
       listOfAuctionIDs.add(auction.getAuctionID());
    }

    public void removeAuctionFromGallery(Auction auction) {
        listOfAuctionIDs.remove(auction.getAuctionID());
    }

    public int getLocalID() {
        return localID;
    }

    public void setLocalID(int localID) {
        this.localID = localID;
    }

    public String getGalleryName() {
        return galleryName;
    }

    public void setGalleryName(String galleryName) {
        this.galleryName = galleryName;
    }

    public ArrayList<Integer> getListOfAuctionIDs() {
        return listOfAuctionIDs;
    }

    public void setListOfAuctionIDs(ArrayList<Integer> listOfAuctionIDs) {
        this.listOfAuctionIDs = listOfAuctionIDs;
    }
}
