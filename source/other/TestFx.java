package other;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;

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

        List<Bid> bidsForAuction2 = new ArrayList<>();
        List<Bid> bidsForAuction3 = new ArrayList<>();
        List<Bid> bidsForAuction4 = new ArrayList<>();
        List<Bid> bidsForAuction5 = new ArrayList<>();
        List<Bid> bidsForAuction6 = new ArrayList<>();
        List<Bid> bidsForAuction7 = new ArrayList<>();
        List<Bid> bidsForAuction8 = new ArrayList<>();
        List<Bid> bidsForAuction9 = new ArrayList<>();
        List<Bid> bidsForAuction10 = new ArrayList<>();


        Profile p1 = new Profile("bigbear",
                "***REMOVED***",
                "***REMOVED***",
                "07856912862",
                "Some Address",
                "BitDifferent Address",
                "someCity",
                "UK, duh",
                "somePostcode",
                "images/profile/male1.png",
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
                "images/profile/male2.png",
                favouritesOfP2,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                LocalDateTime.now());

        Profile p3 = new Profile("shadowbez",
                "Bezhan",
                "Kodomani",
                "07928471824",
                "Midia Enos 1",
                "Ivan Dimitrov",
                "Ruse",
                "Bulgaria",
                "7000",
                "images/profile/male3.png",
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
                "images/profile/male4.png",
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
                "images/profile/female1.png",
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
                "images/profile/male5.png",
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
                "someDate",
                "DaVinci",
                "images/auctions/paintings/painting1.png",
                400,
                400);
        Artwork art2 = new Sculpture(
                "Scream",
                new StringBuilder("Can you hear the voices? (Sculpture)"),
                "someDate",
                "James down the road",
                "images/auctions/paintings/painting2.png",
                400,
                400,
                400,
                "Marble");
        Artwork art3 = new Painting(
                "Scream",
                new StringBuilder("Can you hear the voices? (Painting)"),
                "someDate",
                "Philip down the road",
                "images/auctions/paintings/painting3.png",
                400,
                400);
        Artwork art4 = new Sculpture(
                "Cortana",
                new StringBuilder("Blue AI Girl"),
                "someDate",
                "Masterchief",
                "images/auctions/paintings/painting4.png",
                400,
                400,
                400,
                "Marble");
        Artwork art5 = new Painting(
                "Spartan Army",
                new StringBuilder("This is SPARTA!"),
                "someDate",
                "Me",
                "images/auctions/paintings/painting5.png",
                1000,
                1000);
        Artwork art6 = new Painting(
                "The Starry Night",
                new StringBuilder("Oil Canvas by the dude without the ear."),
                "someDate",
                "Vincent Van Gogh",
                "images/auctions/paintings/painting6.png",
                400,
                400);
        Artwork art7 = new Sculpture(
                "Venus of Willendorf",
                new StringBuilder("Figurine measuring just over four inches in height was discovered in Austria in 1908."),
                "someDate",
                "Some old bloke",
                "images/auctions/paintings/painting7.png",
                400,
                400,
                400,
                "Wood");
        Artwork art8 = new Painting(
                "The last supper",
                new StringBuilder("Our boi Jesus sitting with his mates around a table having time of his life"),
                "someDate",
                "That Assassin's creed bloke",
                "images/auctions/sculptures/sculpture1.png",
                800,
                800);
        Artwork art9 = new Sculpture(
                "Bust of Nefertiti",
                new StringBuilder("Symbol of feminine beauty in Egypt"),
                "someDate",
                "YEAH",
                "images/auctions/sculptures/sculpture2.png",
                200,
                200,
                200,
                "Stone");
        Artwork art10 = new Painting(
                "The Persistence of Memory",
                new StringBuilder("Some melting clocks cuz it super hot"),
                "someDate",
                "My man",
                "images/auctions/sculptures/sculpture3.png",
                650,
                650);

        /**
         * Auctions
         */
        Auction a1 = Auction.createNewAuction(
                art1,
                p1.getUsername(),
                1,
                18000.0);
        Auction a2 = new Auction(
                art2,
                p2.getUsername(),
                2,
                bidsForAuction2,
                20000.0,
                6,
                LocalDateTime.now(),
                0,
                p1.getUsername(),
                true,
                7000000.0);
        Auction a3 = new Auction(
                art3,
                p3.getUsername(),
                3,
                bidsForAuction3,
                20000.0,
                15,
                LocalDateTime.now(),
                14,
                p1.getUsername(),
                false,
                25000.0);
        Auction a4 = new Auction(
                art4,
                p4.getUsername(),
                4,
                bidsForAuction4,
                100.0,
                2,
                LocalDateTime.now(),
                1,
                p6.getUsername(),
                false,
                200.0);
        Auction a5 = new Auction(
                art5,
                p6.getUsername(),
                5,
                bidsForAuction5,
                25.0,
                15,
                LocalDateTime.now(),
                14,
                p3.getUsername(),
                false,
                50.0);
        Auction a6 = new Auction(
                art6,
                p1.getUsername(),
                6,
                bidsForAuction6,
                0.0,
                5,
                LocalDateTime.now(),
                1,
                p4.getUsername(),
                false,
                111.0);
        Auction a7 = new Auction(
                art8,
                p2.getUsername(),
                7,
                bidsForAuction7,
                20.0,
                8,
                LocalDateTime.now(),
                7,
                p2.getUsername(),
                false,
                37.0);
        Auction a8 = new Auction(
                art8,
                p3.getUsername(),
                8,
                bidsForAuction8,
                348.0,
                1,
                LocalDateTime.now(),
                0,
                p1.getUsername(),
                true,
                350.0);
        Auction a9 = new Auction(
                art9,
                p4.getUsername(),
                9,
                bidsForAuction9,
                23.33,
                12,
                LocalDateTime.now(),
                11,
                p3.getUsername(),
                false,
                55.0);
        Auction a10 = new Auction(
                art10,
                p6.getUsername(),
                10,
                bidsForAuction10,
                899.99,
                4,
                LocalDateTime.now(),
                2,
                p4.getUsername(),
                false,
                1200.10);

        p1.getCurrentlySelling().add(a1);
        p1.getCurrentlySelling().add(a6);

        Util.saveListOfProfilesToFile(profiles);
        Util.checkAndSetUser("BHelal");

        Bid b1 = new Bid(2, 1000000.0);
        Util.checkAndSetUser("bigbear");
        Bid b2 = new Bid(2, 2000000.0);
        Util.checkAndSetUser("BHelal");
        Bid b3 = new Bid(2, 2500000.0);
        Util.checkAndSetUser("bigbear");
        Bid b4 = new Bid(2, 3500000.0);
        Util.checkAndSetUser("BHelal");
        Bid b5 = new Bid(2, 5000000.0);
        Util.checkAndSetUser("bigbear");
        Bid b6 = new Bid(2, 7000000.0);

        bidsForAuction2.add(b1);
        bidsForAuction2.add(b2);
        bidsForAuction2.add(b3);
        bidsForAuction2.add(b4);
        bidsForAuction2.add(b5);
        bidsForAuction2.add(b6);

        Bid b7 = new Bid(3, 25000.0);
        bidsForAuction3.add(b7);

        Util.checkAndSetUser("AWing");
        Bid b8 = new Bid(4, 200.0);
        bidsForAuction4.add(b8);

        Util.checkAndSetUser("BKodomani");
        Bid b9 = new Bid(5, 50.0);
        bidsForAuction5.add(b9);

        Util.checkAndSetUser("BSampson");
        Bid b10 = new Bid(6, 10.0);
        Util.checkAndSetUser("AWing");
        Bid b110 = new Bid(6, 50.0);
        Util.checkAndSetUser("BSampson");
        Bid b120 = new Bid(6, 100.0);
        Util.checkAndSetUser("AWing");
        Bid b130 = new Bid(6, 110.0);
        Util.checkAndSetUser("BSampson");
        Bid b140 = new Bid(6, 111.0);

        bidsForAuction6.add(b10);
        bidsForAuction6.add(b110);
        bidsForAuction6.add(b120);
        bidsForAuction6.add(b130);
        bidsForAuction6.add(b140);

        Util.checkAndSetUser("BHelal");
        Bid b11 = new Bid(7, 37.0);
        bidsForAuction7.add(b11);

        Util.checkAndSetUser("bigbear");
        Bid b12 = new Bid(8, 350.0);
        bidsForAuction8.add(b12);

        Util.checkAndSetUser("BKodomani");
        Bid b13 = new Bid(9, 55.0);
        bidsForAuction8.add(b13);

        Util.checkAndSetUser("IGarnev");
        Bid b14 = new Bid(10, 1000.0);
        Util.checkAndSetUser("BSampson");
        Bid b15 = new Bid(10, 1200.10);
        bidsForAuction8.add(b14);
        bidsForAuction8.add(b15);


        auctions.add(a1);
        auctions.add(a2);
        auctions.add(a3);
        auctions.add(a4);
        auctions.add(a5);
        auctions.add(a6);
        auctions.add(a7);
        auctions.add(a8);
        auctions.add(a9);
        auctions.add(a10);


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
