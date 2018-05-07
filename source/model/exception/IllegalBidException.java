package model.exception;

/**
 * An Exception that represents the condition that the current Bid placed is Illegal.
 * <p>
 * This would be used when a user has placed a Bid that does not satisfy conditions, namely:
 * <ul>
 * <li>The Bid is being placed by the person who is already the highest bidder</li>
 * <li>The Bid has an amount that is lower than the Auction's reserve price</li>
 * <li>The Bid has an amount that is lower than the Auction's current highest</li>
 * </ul>
 * <p>
 * These are all represented by a type, IllegalBidType.
 *
 * @author Bassam Helal
 * @version 1.0
 * @see model.Bid
 * @see IllegalBidType
 * @see IllegalArgumentException
 */
public class IllegalBidException extends IllegalArgumentException {

    private IllegalBidType type;

    /**
     * Constructs an IllegalBidException, with the passed in type.
     *
     * @param type the IllegalBidType representing the type of IllegalBidException.
     */
    public IllegalBidException(IllegalBidType type) {
        super(type.toString());
        this.type = type;
    }

    /**
     * Returns the type of <code>IllegalBidException</code> this instance is, this
     * would be represented using an <code>IllegalBidType</code>.
     *
     * @return the IllegalBidType representing the type of IllegalBidException
     */
    public IllegalBidType getType() {
        return this.type;
    }

    /**
     * A list of <code>IllegalBidException</code> types, currently there exist 4 types, 3 of which
     * represent Bid conditions and one representing an unknown condition, this should not happen and
     * is only used as a safety measure.
     *
     * @author Bassam Helal
     * @version 1.0
     * @see IllegalBidException
     * @see model.Bid
     */
    public enum IllegalBidType {
        ALREADY_HIGHEST_BIDDER,
        LOWER_THAN_RESERVE_PRICE,
        LOWER_THAN_HIGHEST,
        UNEXPECTED_EXCEPTION
    }

}
