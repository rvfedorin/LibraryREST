package org.frv.exceptions;

/**
 * @author Roman V.F.
 * Date: 13.08.2021
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String error) {
        super(error);
    }
}
