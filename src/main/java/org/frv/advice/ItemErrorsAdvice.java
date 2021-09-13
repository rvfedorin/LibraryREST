package org.frv.advice;

import org.frv.dto.MessageDto;
import org.frv.exceptions.ItemNotAvailable;
import org.frv.exceptions.ItemNotFound;
import org.frv.exceptions.NoContentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Roman V.F.
 * Date: 13.08.2021
 */
@ControllerAdvice
public class ItemErrorsAdvice {

    @ResponseBody
    @ExceptionHandler({ItemNotFound.class})
    ResponseEntity<MessageDto> itemNotFoundHandler(ItemNotFound ex, HttpServletResponse response) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageDto(ex.getMessage()));
    }

    @ResponseBody
    @ExceptionHandler({NoContentException.class})
    ResponseEntity<MessageDto> noContentHandler(NoContentException ex, HttpServletResponse response) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageDto(ex.getMessage()));
    }

    @ResponseBody
    @ExceptionHandler({ItemNotAvailable.class})
    ResponseEntity<MessageDto> notAvailableHandler(ItemNotAvailable ex, HttpServletResponse response) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageDto(ex.getMessage()));
    }
}
