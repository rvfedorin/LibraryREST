package org.frv.exceptions;

/**
 * @author Roman V.F.
 * Date: 13.08.2021
 */
public class ItemExist extends RuntimeException {
    public ItemExist(String error) {
        super(error);
    }
}
