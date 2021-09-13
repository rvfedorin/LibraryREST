package org.frv.service.impl;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.frv.exceptions.ItemNotFound;
import org.frv.model.Genre;
import org.frv.repository.IGenreRepository;
import org.frv.service.CommonService;
import org.frv.service.IGenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author Roman V.F.
 * Date: 13.08.2021
 */
@Slf4j
@Service
@NoArgsConstructor
public class GenreService extends CommonService<Genre> implements IGenreService {

    @Autowired
    private IGenreRepository genreRepository;

    @PostConstruct
    private void init() {
        this.setRepository(genreRepository);
    }

    public Genre addNewGenreIfNeedAndGetIt(Genre genre) {
        Genre genreResponse;
        try {
            genreResponse = genreRepository.findByName(genre.getName());
            log.info("Genre {} already exists in DB", genre.getName());
        } catch (ItemNotFound ex) {
            genreRepository.save(genre);
            genreResponse = genre;
        }
        return genreResponse;
    }
}
