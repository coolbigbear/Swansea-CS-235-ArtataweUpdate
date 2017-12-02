package model.exception;

public class ProfileNotFoundException extends IllegalArgumentException {
	
	public ProfileNotFoundException(String message) {
		super(message);
	}
	
}
