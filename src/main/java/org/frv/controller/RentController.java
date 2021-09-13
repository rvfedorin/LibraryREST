package org.frv.controller;

import lombok.extern.slf4j.Slf4j;
import org.frv.dto.BookDto;
import org.frv.dto.RentRecordDto;
import org.frv.exceptions.NoContentException;
import org.frv.model.RentRecord;
import org.frv.service.IRentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Roman V.F.
 * Date: 13.08.2021
 */
@Slf4j
@RestController
@Secured("ROLE_ADMIN")
@RequestMapping("/rent")
public class RentController {

    @Autowired
    private IRentService rentService;

    @GetMapping("/all")
    public ResponseEntity<List<RentRecordDto>> getAllRents() {
        List<RentRecord> rentsInDB = rentService.findAll();

        if (rentsInDB.isEmpty()) {
            ResourceBundle bundle = ResourceBundle.getBundle("messages");
            String message = bundle.getString("rent_no_content");
            log.warn(ResourceBundle.getBundle("messages", Locale.ENGLISH).getString("rent_no_content"));
            throw new NoContentException(message);
        }

        List<RentRecordDto> responseRents = new ArrayList<>();
        for (RentRecord rent : rentsInDB) {
            responseRents.add(new RentRecordDto(rent));
        }

        return ResponseEntity.ok(responseRents);
    }

    @GetMapping("/onRent")
    public ResponseEntity<List<RentRecordDto>> showOnRent() {
        return ResponseEntity.ok(rentService.showOnRent());
    }

    @GetMapping("/onRent/expired")
    public ResponseEntity<List<RentRecordDto>> showOnRentExpired() {
        return ResponseEntity.ok(rentService.showOnRentExpired());
    }

    @GetMapping("/returned")
    public ResponseEntity<List<RentRecordDto>> showReturned() {
        return ResponseEntity.ok(rentService.showReturned());
    }

    @GetMapping("/returned/expired")
    public ResponseEntity<List<RentRecordDto>> showReturnedExpired() {
        return ResponseEntity.ok(rentService.showReturnedExpired());
    }

    @Secured("ROLE_USER")
    @GetMapping("/make/{id}")
    public ResponseEntity<BookDto> rentBook(@PathVariable("id") Long bookId, Principal principal) {
        BookDto bookDto = rentService.rentBook(bookId, principal.getName());
        return ResponseEntity.ok(bookDto);
    }

    @GetMapping("/return/{id}")
    public ResponseEntity<BookDto> returnBook(@PathVariable("id") Long bookId) {
        BookDto bookDto = rentService.returnBook(bookId);
        return ResponseEntity.ok(bookDto);
    }
}

