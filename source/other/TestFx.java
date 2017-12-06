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
		
		
		Profile profile2 = new Profile("bigbear2", "***REMOVED***", "***REMOVED***","07856912862",
                "Some Address","BitDifferent Address","someCity","UK, duh",
				"somePostcode", "path",
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),new ArrayList<>(), new ArrayList<>(),
				LocalDateTime.now());
		
		Profile profile3 = new Profile("fred", "Fred", "Schumer","07856912862",
				"Some Address","BitDifferent Address","someCity","USA",
				"somePostcode", "path",
				new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),new ArrayList<>(), new ArrayList<>(),
				LocalDateTime.now());
		
		Profile profile4 = new Profile("jkr", "John", "***REMOVED***","07856912862",
				"Some Address","BitDifferent Address","someCity","UK, duh",
				"somePostcode", "path",
				new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),new ArrayList<>(), new ArrayList<>(),
				LocalDateTime.now());
		
		Profile profile5 = new Profile("dva", "DVA", "***REMOVED***","07856912862",
				"Some Address","BitDifferent Address","someCity","UK, duh",
				"somePostcode", "path",
				new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),new ArrayList<>(), new ArrayList<>(),
				LocalDateTime.now());
		
		
		Profile profile6 = new Profile("S76", "Morrison", "***REMOVED***","07856912862",
				"Some Address","BitDifferent Address","someCity","UK, duh",
				"somePostcode", "path",
				new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),new ArrayList<>(), new ArrayList<>(),
				LocalDateTime.now());
		
		
		Profile profile7 = new Profile("Reaper", "Reaper", "***REMOVED***","07856912862",
				"Some Address","BitDifferent Address","someCity","UK, duh",
				"somePostcode", "path",
				new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),new ArrayList<>(), new ArrayList<>(),
				LocalDateTime.now());
		
		
		Profile profile8 = new Profile("funky", "Foo", "***REMOVED***","07856912862",
				"Some Address","BitDifferent Address","someCity","UK, duh",
				"somePostcode", "path",
				new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),new ArrayList<>(), new ArrayList<>(),
				LocalDateTime.now());
		
		
		Profile profile9 = new Profile("dancer", "Jackson", "***REMOVED***","07856912862",
				"Some Address","BitDifferent Address","someCity","UK, duh",
				"somePostcode", "path",
				new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),new ArrayList<>(), new ArrayList<>(),
				LocalDateTime.now());
		
		Profile profile10 = new Profile("Linuz", "Linus", "***REMOVED***","07856912862",
				"Some Address","BitDifferent Address","someCity","UK, duh",
				"somePostcode", "path",
				new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),new ArrayList<>(), new ArrayList<>(),
				LocalDateTime.now());
		
		favourites.add(profile2.getUsername());
		favourites.add(profile3.getUsername());
		favourites.add(profile4.getUsername());
		favourites.add(profile5.getUsername());
		favourites.add(profile6.getUsername());
		favourites.add(profile7.getUsername());
		favourites.add(profile8.getUsername());
		favourites.add(profile9.getUsername());
		favourites.add(profile10.getUsername());
		
		Profile profile1 = new Profile("bigbear1", "***REMOVED***", "***REMOVED***","07856912862",
				"Some Address","BitDifferent Address","someCity","UK, duh",
				"somePostcode","path", favourites, new ArrayList<>(), new ArrayList<>(),
				new ArrayList<>(), new ArrayList<>(), LocalDateTime.now());
		
		profiles.add(profile1);
		profiles.add(profile2);
		profiles.add(profile3);
		profiles.add(profile4);
		profiles.add(profile5);
		profiles.add(profile6);
		profiles.add(profile7);
		profiles.add(profile8);
		profiles.add(profile9);
		profiles.add(profile10);

		Artwork art1 = new Painting("Monalisa", new StringBuilder("The most beautiful painting"), LocalDate.now(),
				"DaVinci", "string", 400, 400);
		Artwork art2 = new Sculpture("Scream", new StringBuilder("Can you hear the voices?"), LocalDate.now(),
				"James down the road", "string", 400, 400, 400, "Marble");

		Auction a1 = Auction.createNewAuction(art1, profile1.getUsername(), 20, 18000.0);
		
		Auction a2 = new Auction(art2, profile2.getUsername(), 02, new ArrayList<>() , 20000.0, 15,
				LocalDateTime.now(), 15, null, false, 0.0);

		Util.saveListOfProfilesToFile(profiles);
		Util.checkAndSetUser("bigbear1");

		Bid b1 = new Bid(02,20500.0);
		
		a2.placeBid(b1);

		auctions.add(a1);
		auctions.add(a2);


		//Json - Gson stuff

		Util.saveListOfAuctionsToFile(auctions);

//        Profile p3 = new Profile("bigbear2", "James", "***REMOVED***","07856912862",
//                "Some Address","BitDifferent Address","someCity","UK, duh",
//				"somePostcode", null, new ArrayList<String>(), new ArrayList<Auction>(),
//				new ArrayList<Auction>(), new ArrayList<Auction>(), new ArrayList<Bid>(),
//				LocalDateTime.now());
//
//		Util.saveProfileToFile(p3);

		Util.getActiveAuctions();

//		//Test AuctionsID 100 times.
//		for (int i=0; i<100; i++) {
//			System.out.println(Util.getNewAuctionID());
//		}

    	launch(args);
	}
	
}
