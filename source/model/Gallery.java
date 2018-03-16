package model;

import java.util.ArrayList;

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
