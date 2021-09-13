package org.frv.advice;

import org.frv.dto.MessageDto;
import org.frv.exceptions.ItemNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Roman V.F.
 * Date: 20.08.2021
 */
@ControllerAdvice
public class SecurityErrorsAdvice {
    @ResponseBody
    @ExceptionHandler({UsernameNotFoundException.class})
    ResponseEntity<MessageDto> userNotFoundHandler(UsernameNotFoundException ex, HttpServletResponse response) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageDto(ex.getMessage()));
    }
}
