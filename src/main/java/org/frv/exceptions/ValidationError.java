package org.frv.exceptions;

/**
 * @author Roman V.F.
 * Date: 13.08.2021
 */
public class ValidationError extends RuntimeException {
    public ValidationError(String error) {
        super(error);
    }
}
