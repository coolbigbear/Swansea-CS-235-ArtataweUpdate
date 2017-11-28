package model;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

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
		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new FileReader("profile.json"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (bufferedReader != null) {
			currentUser = gson.fromJson(bufferedReader, Profile.class);
		}
	}
}
