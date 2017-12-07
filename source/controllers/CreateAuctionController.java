package controllers;

import model.Artwork;
import model.Auction;
import model.Util;

public class CreateAuctionController {
	
	//All the FXML Views/ Nodes
	
	/* Used to create a new Auction and submit it to Database
	 * when the user has entered stuff in the textfields.
	 * This will be run when a "Submit" button is clicked
	 */
	private void submitNewAuction() {
		Artwork artwork = null; //fix this!
		
		//Get the type of Artwork and depending on it create the type
		// if (type is Artwork) { ... }
		// if (type is Sculpture) { ... }
		// inside these ifs we'd set the the Artwork constructor params from the TextFields
		
		Auction auction = Auction.createNewAuction(artwork, Util.getCurrentUser().getUsername(), 5, 5.00);
		Util.saveAuctionToFile(auction); // we can actually delegate this to where the method above is declared in
		// Auction instead of here
	}
}
