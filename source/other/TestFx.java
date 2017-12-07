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
		List<String> favouritesOfP1 = new ArrayList<>();
		List<String> favouritesOfP2 = new ArrayList<>();
		List<String> favouritesOfP3 = new ArrayList<>();
		List<String> favouritesOfP4 = new ArrayList<>();
		List<String> favouritesOfP5 = new ArrayList<>();
		List<String> favouritesOfP6 = new ArrayList<>();
		List<Bid> bids = new ArrayList<>();


		Profile p1 = new Profile("bigbear",
				"***REMOVED***",
				"***REMOVED***",
				"07856912862",
				"Some Address",
				"BitDifferent Address",
				"someCity",
				"UK, duh",
				"somePostcode",
				"path",
				favouritesOfP1,
				new ArrayList<>(),
				new ArrayList<>(),
				new ArrayList<>(),
				new ArrayList<>(),
				LocalDateTime.now());

		Profile p2 = new Profile("BHelal",
				"Bassam",
				"Helal",
				"07586734981",
				"Bassams Address",
				"Bassams second address",
				"Cairo",
				"Egypt",
				"Don't know egypt ostcodes",
				"path",
				favouritesOfP2,
				new ArrayList<>(),
				new ArrayList<>(),
				new ArrayList<>(),
				new ArrayList<>(),
				LocalDateTime.now());

		Profile p3 = new Profile("BKodomani",
				"Bezhan",
				"Kodomani",
				"07928471824",
				"Bezhans address",
				"Bezhands second address",
				"London?",
				"United Kingdom",
				"LO3 5PA",
				"path",
				favouritesOfP3,
				new ArrayList<>(),
				new ArrayList<>(),
				new ArrayList<>(),
				new ArrayList<>(),
				LocalDateTime.now());

		Profile p4 = new Profile("BSampson",
				"Ben",
				"Sampson",
				"08475849381",
				"Bens address",
				"Bens second address",
				"Manchester?",
				"United Kingdom",
				"MA2 5SE",
				"path",
				favouritesOfP4,
				new ArrayList<>(),
				new ArrayList<>(),
				new ArrayList<>(),
				new ArrayList<>(),
				LocalDateTime.now());

		Profile p5 = new Profile("IGarnev",
				"Iliyan",
				"Garnev",
				"07829481742",
				"Iliyans address",
				"Iliyans second address",
				"Sofia?",
				"Bulgaria",
				"Some bulgarian postcode",
				"path",
				favouritesOfP5,
				new ArrayList<>(),
				new ArrayList<>(),
				new ArrayList<>(),
				new ArrayList<>(),
				LocalDateTime.now());

		Profile p6 = new Profile("AWing",
				"Alex",
				"Wing",
				"07234567891",
				"Alexs address",
				"Alexs second address",
				"Swansea?",
				"Wales",
				"SA4 9TL",
				"path",
				favouritesOfP6,
				new ArrayList<>(),
				new ArrayList<>(),
				new ArrayList<>(),
				new ArrayList<>(),
				LocalDateTime.now());


		favouritesOfP1.add(p2.getUsername());
		favouritesOfP1.add(p6.getUsername());

		favouritesOfP2.add(p1.getUsername());
		favouritesOfP2.add(p6.getUsername());
		favouritesOfP2.add(p3.getUsername());
		favouritesOfP2.add(p4.getUsername());

		favouritesOfP4.add(p1.getUsername());
		favouritesOfP4.add(p2.getUsername());

		favouritesOfP5.add(p4.getUsername());

		favouritesOfP6.add(p1.getUsername());
		favouritesOfP6.add(p2.getUsername());
		favouritesOfP6.add(p3.getUsername());
		favouritesOfP6.add(p4.getUsername());
		favouritesOfP6.add(p5.getUsername());


		profiles.add(p1);
		profiles.add(p2);
		profiles.add(p3);
		profiles.add(p4);
		profiles.add(p5);
		profiles.add(p6);

		Artwork art1 = new Painting(
				"Monalisa",
				new StringBuilder("The most beautiful painting"),
				LocalDate.now(),
				"DaVinci",
				"string",
				400,
				400);
		Artwork art2 = new Sculpture(
				"Scream",
				new StringBuilder("Can you hear the voices? (Sculpture)"),
				LocalDate.now(),
				"James down the road",
				"string",
				400,
				400,
				400,
				"Marble");
		Artwork art3 = new Painting(
				"Scream",
				new StringBuilder("Can you hear the voices? (Painting)"),
				LocalDate.now(),
				"Philip down the road",
				"string",
				400,
				400);
		Artwork art4 = new Sculpture(
				"Cortana",
				new StringBuilder("Blue AI Girl"),
				LocalDate.now(),
				"Masterchief",
				"string",
				400,
				400,
				400,
				"Marble");
		Artwork art5 = new Painting(
				"Spartan Army",
				new StringBuilder("This is SPARTA!"),
				LocalDate.now(),
				"Me",
				"string",
				1000,
				1000);
		Artwork art6 = new Painting(
				"The Starry Night",
				new StringBuilder("Oil Canvas by the dude without the ear."),
				LocalDate.now(),
				"Vincent Van Gogh",
				"string",
				400,
				400);
		Artwork art7 = new Sculpture(
				"Venus of Willendorf",
				new StringBuilder("Figurine measuring just over four inches in height was discovered in Austria in 1908."),
				LocalDate.now(),
				"Some old bloke",
				"string",
				400,
				400,
				400,
				"Wood");
		Artwork art8 = new Painting(
				"The last supper",
				new StringBuilder("Our boi Jesus sitting with his mates around a table having time of his life"),
				LocalDate.now(),
				"That Assassin's creed bloke",
				"string",
				800,
				800);
		Artwork art9 = new Sculpture(
				"Bust of Nefertiti",
				new StringBuilder("Symbol of feminine beauty in Egypt"),
				LocalDate.now(),
				"Egypt bloke name Steve",
				"string",
				200,
				200,
				200,
				"Stone");
		Artwork art10 = new Painting(
				"The Persistence of Memory",
				new StringBuilder("Some melting clocks cuz it super hot"),
				LocalDate.now(),
				"Salvador Dali",
				"string",
				650,
				650);

		Auction a1 = Auction.createNewAuction(
				art1,
				p1.getUsername(),
				01,
				18000.0);
		Auction a2 = new Auction(
				art2,
				p2.getUsername(),
				02,
				new ArrayList<>(),//slap in a bid war here
				20000.0,
				15,
				LocalDateTime.now(),
				0,
				p1.getUsername(),
				true,
				1400000.0);
		Auction a3 = new Auction(
				art3,
				p3.getUsername(),
				03,
				new ArrayList<>(),
				20000.0,
				15,
				LocalDateTime.now(),
				15,
				null,
				false,
				0.0);
		Auction a4 = new Auction(
				art4,
				p4.getUsername(),
				04,
				new ArrayList<>(),
				20000.0,
				15,
				LocalDateTime.now(),
				15,
				null,
				false,
				0.0);
		Auction a5 = new Auction(
				art5,
				p6.getUsername(),
				05,
				new ArrayList<>(),
				20000.0,
				15,
				LocalDateTime.now(),
				15,
				null,
				false,
				0.0);
		Auction a6 = new Auction(
				art6,
				p1.getUsername(),
				06,
				new ArrayList<>(),
				20000.0,
				15,
				LocalDateTime.now(),
				15,
				null,
				false,
				0.0);
		Auction a7 = new Auction(
				art8,
				p2.getUsername(),
				07,
				new ArrayList<>(),
				20000.0,
				15,
				LocalDateTime.now(),
				15,
				null,
				false,
				0.0);
		Auction a8 = new Auction(
				art8,
				p3.getUsername(),
				8,
				new ArrayList<>(),
				20000.0,
				15,
				LocalDateTime.now(),
				15,
				null,
				false,
				0.0);
		Auction a9 = new Auction(
				art9,
				p4.getUsername(),
				9,
				new ArrayList<>(),
				20000.0,
				15,
				LocalDateTime.now(),
				15,
				null,
				false,
				0.0);
		Auction a10 = new Auction(
				art10,
				p6.getUsername(),
				10,
				new ArrayList<>(),
				20000.0,
				15,
				LocalDateTime.now(),
				15,
				null,
				false,
				0.0);



		Util.saveListOfProfilesToFile(profiles);
		Util.checkAndSetUser("bigbear");

		Bid b1 = new Bid(02,20500.0);
		
		//a2.placeBid(b1);

		auctions.add(a1);
		auctions.add(a2);
		auctions.add(a3);
		auctions.add(a4);


		//Json - Gson stuff

		Util.saveListOfAuctionsToFile(auctions);

//        Profile p3 = new Profile("bigbear2", "James", "***REMOVED***","07856912862",
//                "Some Address","BitDifferent Address","someCity","UK, duh",
//				"somePostcode", null, new ArrayList<String>(), new ArrayList<Auction>(),
//				new ArrayList<Auction>(), new ArrayList<Auction>(), new ArrayList<Bid>(),
//				LocalDateTime.now());
//
//		Util.saveProfileToFile(p3);

//		//Test AuctionsID 100 times.
//		for (int i=0; i<100; i++) {
//			System.out.println(Util.getNewAuctionID());
//		}
		
		Util.getActiveAuctions();

    	launch(args);
	}
	
}
