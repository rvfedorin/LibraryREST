package org.frv.dto;

import lombok.Getter;

/**
 * @author Roman V.F.
 * Date: 13.08.2021
 */
@Getter
public class MessageDto {
    private String message;

    public MessageDto(String message) {
        this.message = message;
    }
}
