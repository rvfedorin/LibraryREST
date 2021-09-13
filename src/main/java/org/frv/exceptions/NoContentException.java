package org.frv.exceptions;

import lombok.Getter;

/**
 * @author Roman V.F.
 * Date: 13.08.2021
 */
@Getter
public class NoContentException extends RuntimeException {
    private String error;

    public NoContentException(String error) {
        super(error);
        this.error = error;
    }

    @Override
    public String toString() {
        return "error message: " + this.getError();
    }
}
