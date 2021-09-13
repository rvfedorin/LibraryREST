package org.frv.exceptions;

/**
 * @author Roman V.F.
 * Date: 13.08.2021
 */
public class ItemNotAvailable extends RuntimeException {
    public ItemNotAvailable(String error) {
        super(error);
    }
}
