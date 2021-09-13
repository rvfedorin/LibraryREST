package org.frv.controller;

import lombok.extern.slf4j.Slf4j;
import org.frv.dto.BookDetailedDto;
import org.frv.dto.BookDto;
import org.frv.dto.MessageDto;
import org.frv.exceptions.NoContentException;
import org.frv.model.Book;
import org.frv.model.CatalogType;
import org.frv.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author Roman V.F.
 * Date: 13.08.2021
 */
@Slf4j
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private IBookService bookService;

    @GetMapping("/all")
    public ResponseEntity<List<BookDto>> getAllBooks() {
        List<Book> booksInDB = bookService.findAll();

        if (booksInDB.isEmpty()) {
            ResourceBundle bundle = ResourceBundle.getBundle("messages");
            String message = bundle.getString("book_no_content");
            log.warn(ResourceBundle.getBundle("messages", Locale.ENGLISH).getString("book_no_content"));
            throw new NoContentException(message);
        }

        List<BookDto> responseBooks = new ArrayList<>();
        for (Book book : booksInDB) {
            responseBooks.add(new BookDto(book));
        }

        return ResponseEntity.ok(responseBooks);
    }

    @GetMapping("/catalog/{type}")
    public ResponseEntity<Map<String, List<BookDto>>> getCatalog(@PathVariable("type") String catalogTypeName) {
        return ResponseEntity.ok(bookService.getCatalog(CatalogType.getTypeByName(catalogTypeName)));
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/info/detailed/{id}")
    public ResponseEntity<BookDetailedDto> getBookDetailed(@PathVariable("id") Long bookId) {
        BookDetailedDto bookDetailedDto = new BookDetailedDto(bookService.findDetailedOne(bookId));
        return ResponseEntity.ok(bookDetailedDto);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/add")
    public ResponseEntity<BookDto> addNewBook(@RequestBody BookDto bookToSave) {
        bookService.addNewBook(bookToSave);
        return ResponseEntity.status(HttpStatus.CREATED).body(bookToSave);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/delete/{id}")
    public ResponseEntity<MessageDto> deleteBook(@PathVariable("id") Long bookId) {
        Book bookToDelete = bookService.findOne(bookId);
        BookDto bookDto = new BookDto(bookToDelete);
        bookService.delete(bookToDelete);
        String message = String.format("Book '%s' was deleted.", bookDto);
        return ResponseEntity.ok(new MessageDto(message));
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/update")
    public ResponseEntity<BookDto> changeBook(@RequestBody BookDto bookDtoToChange) {
        bookService.changeBook(bookDtoToChange);
        return ResponseEntity.ok(bookDtoToChange);
    }

}
