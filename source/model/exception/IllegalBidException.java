package model.exception;

public class IllegalBidException extends IllegalArgumentException{
	
	private IllegalBidType type;
	
	@Override
	public String getMessage() {
		return super.getMessage() + this.type;
	}
	
	public IllegalBidException(String message) {
		super(message);
	}
	
	public IllegalBidException(IllegalBidType type) {
		super();
		this.type = type;
	}
	
	public IllegalBidType getType() {
		return this.type;
	}
	
	public enum IllegalBidType {
		ALREADY_HIGHEST_BIDDER,
		LOWER_THAN_RESERVE_PRICE,
		LOWER_THAN_HIGHEST
	}
	
}
