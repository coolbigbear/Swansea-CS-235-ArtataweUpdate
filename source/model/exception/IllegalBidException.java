package model.exception;

public class IllegalBidException extends IllegalArgumentException{
	
	private  IllegalBidType type;
	
	public IllegalBidException(IllegalBidType type) {
		super(type.toString());
		this.type = type;
	}
	
	public IllegalBidType getType() {
		return this.type;
	}
	
	public enum IllegalBidType {
		ALREADY_HIGHEST_BIDDER,
		LOWER_THAN_RESERVE_PRICE,
		LOWER_THAN_HIGHEST,
		UNEXPECTED_EXCEPTION
	}
	
}
