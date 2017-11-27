package model.exceptions;

public class IllegalBidException extends IllegalArgumentException{
	
	public IllegalBidException(String message) {
		super(message);
		super.printStackTrace();
	}
	
}
