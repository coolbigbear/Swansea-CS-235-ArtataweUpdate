package model;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The type Util.
 */
public class Util {
	
	/**
	 * The current user who is signed in to the system.
	 */
	public static Profile currentUser;
	
	Gson gson = new Gson();
	
	public void readCurrentUser() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("JSON files/profile.json"));
			Profile[] fromJson = gson.fromJson(br, (Type) Profile[].class);

			for(Profile profile: fromJson) {
				//Read the variables required for constructor
				String name = profile.getUsername();
				List favourites = profile.getFavouriteUsers();
				if (Objects.equals(name, currentUser.getUsername())) {
					currentUser = new Profile(name, favourites);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}