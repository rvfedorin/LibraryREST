package org.frv.exceptions;

/**
 * @author Roman V.F.
 * Date: 13.08.2021
 */
public class ItemNotFound extends RuntimeException {
    public ItemNotFound(String error) {
        super(error);
    }
}
