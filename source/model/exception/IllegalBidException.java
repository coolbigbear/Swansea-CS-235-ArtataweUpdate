package model.exception;

public class IllegalBidException extends IllegalArgumentException{
	
	public IllegalBidException(String message) {
		super(message);
		super.printStackTrace();
	}
	
}
