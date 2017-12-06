package other;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestFx extends Application {
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("testFXML.fxml"));
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}
	
	public static void main(String[] args) {

		List<Profile> profiles = new ArrayList<>();
		List<Auction> auctions = new ArrayList<>();
		List<String> favourites = new ArrayList<>();
		List<Bid> bids = new ArrayList<>();


		Profile p1 = new Profile("bigbear1", "***REMOVED***", "***REMOVED***","07856912862",
                "Some Address","BitDifferent Address","someCity","UK, duh",
				"somePostcode","path", favourites, new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), new ArrayList<>(), LocalDateTime.now());
		Profile p2 = new Profile("bigbear2", "***REMOVED***", "***REMOVED***","07856912862",
                "Some Address","BitDifferent Address","someCity","UK, duh",
				"somePostcode", "path",
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),new ArrayList<>(), new ArrayList<>(),
				LocalDateTime.now());

//		Profile p3 = new Profile("bigbear3", "No contact info");
//		Profile p4 = new Profile("bigbear4", "No contact info");
//		Profile p5 = new Profile("bigbear5", "No contact info");
//		Profile p6 = new Profile("bigbear6", "No contact info");
//		Profile p7 = new Profile("bigbear7", "No contact info");

		profiles.add(p1);
		profiles.add(p2);
//		profiles.add(p3);
//		profiles.add(p4);
//		profiles.add(p5);
//		profiles.add(p6);
//		profiles.add(p7);

		favourites.add(p1.getUsername());
		favourites.add(p2.getUsername());

		Artwork art1 = new Painting("Monalisa", new StringBuilder("The most beautiful painting"), LocalDate.now(),
				"DaVinci", "string", 400, 400);
		Artwork art2 = new Sculpture("Scream", new StringBuilder("Can you hear the voices?"), LocalDate.now(),
				"James down the road", "string", 400, 400, 400, "Marble");

		Auction a1 = Auction.createNewAuction(art1, p1.getUsername(), 20, 18000.0);
		Auction a2 = new Auction(art2, p2.getUsername(), 02, new ArrayList<>() , 20000.0, 15,
				LocalDateTime.now(), 15, null, false, 0.0);

		Util.saveListOfProfilesToFile(profiles);
		Util.checkAndSetUser("bigbear1");

		Bid b1 = new Bid(02,20500.0);
		
		a2.placeBid(b1);

		auctions.add(a1);
		auctions.add(a2);


		//Json - Gson stuff

		Util.saveListOfAuctionsToFile(auctions);

        Profile p3 = new Profile("bigbear2", "James", "***REMOVED***","07856912862",
                "Some Address","BitDifferent Address","someCity","UK, duh",
				"somePostcode", null, new ArrayList<String>(), new ArrayList<Auction>(),
				new ArrayList<Auction>(), new ArrayList<Auction>(), new ArrayList<Bid>(),
				LocalDateTime.now());

		Util.saveProfileToFile(p3);

		Util.getActiveAuctions();

		//Test AuctionsID 100 times.
		for (int i=0; i<100; i++) {
			System.out.println(Util.getNewAuctionID());
		}

    	launch(args);
	}
	
}
