package org.frv.controller;

import lombok.extern.slf4j.Slf4j;
import org.frv.dto.GenreDto;
import org.frv.exceptions.NoContentException;
import org.frv.model.Genre;
import org.frv.service.IGenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/genre")
public class GenreController {

    @Autowired
    private IGenreService genreService;

    @GetMapping("/all")
    public ResponseEntity<List<GenreDto>> getAllGenres() {
        List<Genre> genresInDB = genreService.findAll();

        if (genresInDB.isEmpty()) {
            ResourceBundle bundle = ResourceBundle.getBundle("messages");
            String message = bundle.getString("genre_no_content");
            log.warn(ResourceBundle.getBundle("messages", Locale.ENGLISH).getString("genre_no_content"));
            throw new NoContentException(message);
        }

        List<GenreDto> responseGenres = new ArrayList<>();
        for (Genre genre : genresInDB) {
            responseGenres.add(new GenreDto(genre));
        }

        return ResponseEntity.ok(responseGenres);
    }
}
