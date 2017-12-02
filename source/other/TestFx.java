package other;

import com.google.gson.Gson;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import model.*;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
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


//		Profile p1 = new Profile("bigbear1", "No contact info");
//		Profile p2 = new Profile("bigbear2", "No contact info");
//		Profile p3 = new Profile("bigbear3", "No contact info");
//		Profile p4 = new Profile("bigbear4", "No contact info");
//		Profile p5 = new Profile("bigbear5", "No contact info");
//		Profile p6 = new Profile("bigbear6", "No contact info");
//		Profile p7 = new Profile("bigbear7", "No contact info");

//		profiles.add(p1);
//		profiles.add(p2);
//		profiles.add(p3);
//		profiles.add(p4);
//		profiles.add(p5);
//		profiles.add(p6);
//		profiles.add(p7);

		Artwork art1 = new Painting("Monalisa", new StringBuilder("The most beautiful painting"), LocalDate.now(), "DaVinci");
		Artwork art2 = new Painting("Scream", new StringBuilder("Can you hear the voices?"), LocalDate.now(), "James down the road");
		Artwork art3 = new Painting("Sunflower", new StringBuilder("Only one like this"), LocalDate.now(), "VanGogh");

//		Auction a1 = new Auction(art1, p1, 20, 18000.0);
//		Auction a2 = new Auction(art2, p2, 30, 28000.0);
//
//		auctions.add(a1);
//		auctions.add(a2);

		//Json - Gson stuff
		Gson gson = new Gson();

		//Write JSON String to file
		try {
			FileWriter fileWriter = new FileWriter("JSON Files/Auction.json");
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
