package org.frv.controller;

import lombok.extern.slf4j.Slf4j;
import org.frv.dto.AuthorDto;
import org.frv.exceptions.NoContentException;
import org.frv.model.Author;
import org.frv.service.IAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    private IAuthorService authorService;

    @GetMapping("/all")
    public ResponseEntity<List<AuthorDto>> getAllAuthors() {
        List<Author> authorsInDB = authorService.findAll();

        if (authorsInDB.isEmpty()) {
            ResourceBundle bundle = ResourceBundle.getBundle("messages");
            String message = bundle.getString("author_no_content");
            log.warn(ResourceBundle.getBundle("messages", Locale.ENGLISH).getString("author_no_content"));
            throw new NoContentException(message);
        }

        List<AuthorDto> responseAuthors = new ArrayList<>();
        for (Author author : authorsInDB) {
            responseAuthors.add(new AuthorDto(author));
        }

        return ResponseEntity.ok(responseAuthors);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/add")
    public ResponseEntity<AuthorDto> addNewAuthor(@RequestBody AuthorDto newAuthor) {
        Author author = new Author(newAuthor.getFirstName(), newAuthor.getLastName());
        authorService.addAuthor(author);
        log.info("Added new author: {} {}", newAuthor.getFirstName(), newAuthor.getLastName());
        return ResponseEntity.ok(newAuthor);
    }
}
