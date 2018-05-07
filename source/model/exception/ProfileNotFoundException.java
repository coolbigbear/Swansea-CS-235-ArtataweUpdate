package model.exception;

/**
 * An Exception that represents the condition that the Profile cannot be found
 *
 * @author Bassam Helal
 * @version 1.0
 * @see model.Profile
 * @see IllegalArgumentException
 */
public class ProfileNotFoundException extends IllegalArgumentException {

    /**
     * Constructs a ProfileNotFoundException with the passed in message
     *
     * @param message the message to be shown on the stacktrace
     */
    public ProfileNotFoundException(String message) {
        super(message);
    }

}
