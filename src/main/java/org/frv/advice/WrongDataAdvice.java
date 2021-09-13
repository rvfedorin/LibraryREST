package org.frv.advice;

import org.frv.dto.MessageDto;
import org.frv.exceptions.ItemExist;
import org.frv.exceptions.ValidationError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author Roman V. Fedorin
 */
@ControllerAdvice
public class WrongDataAdvice {

    @ResponseBody
    @ExceptionHandler({NumberFormatException.class})
    ResponseEntity<MessageDto> noContentHandler(NumberFormatException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageDto(ex.getMessage()));
    }

    @ResponseBody
    @ExceptionHandler({SQLIntegrityConstraintViolationException.class})
    ResponseEntity<MessageDto> noContentHandler(SQLIntegrityConstraintViolationException ex) {
        if (ex.getMessage() != null && ex.getMessage().contains("Duplicate") && ex.getMessage().contains("users.login")) {
            String errorMessage = "User with this login exists";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageDto(errorMessage));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageDto(ex.getMessage()));
    }

    @ResponseBody
    @ExceptionHandler({ValidationError.class})
    ResponseEntity<MessageDto> validationHandler(ValidationError ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageDto(ex.getMessage()));
    }

    @ResponseBody
    @ExceptionHandler({ItemExist.class})
    ResponseEntity<MessageDto> validationHandler(ItemExist ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageDto(ex.getMessage()));
    }
}
