package controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import model.Artwork;
import model.Auction;
import model.Util;

public class CreateAuctionController {
	@FXML
	private Button sellPaintingButton;
	private Button sellSculptureButton;

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
		sellPaintingButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				//label.setText("Accepted");
			}

		
		Auction auction = Auction.createNewAuction(artwork, Util.getCurrentUser().getUsername(), 5, 5.00);
		//Util.saveAuctionToFile(auction); // we can actually delegate this to where the method above is declared in
		// Auction instead of here
	}
}
