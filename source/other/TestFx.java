package other;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import model.*;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestFx extends Application {
	
    @Override
    public void start(Stage primaryStage) throws  Exception {
	    Parent root = FXMLLoader.load(getClass().getResource("testFXML.fxml"));
    	primaryStage.setScene(new Scene(root));
    	primaryStage.show();
    }
	
	public static void main(String[] args) {

		List<Profile> profiles = new ArrayList<>();
		List<Auction> auctions = new ArrayList<>();


		Profile p1 = new Profile("bigbear1", "***REMOVED***", "***REMOVED***","07856912862",
                "Some Address","BitDifferent Address","someCity","UK, duh","somePostcode",
                profiles, new ArrayList<>(), new ArrayList<>(),new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), LocalDateTime.now());
		Profile p2 = new Profile("bigbear2", "***REMOVED***", "***REMOVED***","07856912862",
                "Some Address","BitDifferent Address","someCity","UK, duh","somePostcode",
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), LocalDateTime.now());
//		Profile p3 = new Profile("bigbear3", "No contact info");
//		Profile p4 = new Profile("bigbear4", "No contact info");
//		Profile p5 = new Profile("bigbear5", "No contact info");
//		Profile p6 = new Profile("bigbear6", "No contact info");
//		Profile p7 = new Profile("bigbear7", "No contact info");

//		profiles.add(p1);
		profiles.add(p2);
//		profiles.add(p3);
//		profiles.add(p4);
//		profiles.add(p5);
//		profiles.add(p6);
//		profiles.add(p7);

		Artwork art1 = new Painting("Monalisa", new StringBuilder("The most beautiful painting"), LocalDate.now(), "DaVinci");
		Artwork art2 = new Painting("Scream", new StringBuilder("Can you hear the voices?"), LocalDate.now(), "James down the road");
		Artwork art3 = new Painting("Sunflower", new StringBuilder("Only one like this"), LocalDate.now(), "VanGogh");

		Auction a1 = new Auction(art1, p1, 01, 20, 18000.0);
		Auction a2 = new Auction(art2, p2, 02, 30, 28000.0);

		auctions.add(a1);
		auctions.add(a2);

		//Json - Gson stuff
		RuntimeTypeAdapterFactory<Artwork> artworkAdapterFactory = RuntimeTypeAdapterFactory.of(Artwork.class, "type");

		artworkAdapterFactory.registerSubtype(Artwork.class);
		artworkAdapterFactory.registerSubtype(Sculpture.class);
		artworkAdapterFactory.registerSubtype(Painting.class);

		Gson gson = new GsonBuilder()
				.registerTypeAdapterFactory(artworkAdapterFactory)
				.create();

		//Write JSON String to file
		try {
			FileWriter fileWriter = new FileWriter("JSON Files/Auctions.json");
			gson.toJson(auctions, fileWriter);
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Util util = new Util();
		util.readInAllAuctions();

    	launch(args);
	}
	
}
