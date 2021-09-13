package org.frv.service.impl;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.frv.exceptions.ItemExist;
import org.frv.model.Author;
import org.frv.repository.IAuthorRepository;
import org.frv.service.CommonService;
import org.frv.service.IAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;

/**
 * @author Roman V.F.
 * Date: 13.08.2021
 */
@Slf4j
@Service
@NoArgsConstructor
public class AuthorService extends CommonService<Author> implements IAuthorService {

    @Autowired
    private IAuthorRepository authorRepository;

    @PostConstruct
    private void init() {
        this.setRepository(authorRepository);
    }

    public void addAuthor(Author author) {
        Optional<Author> authorInDbOptional = authorRepository.findAuthorByFullName(author.getFirstName(), author.getLastName());
        if (authorInDbOptional.isPresent()) {
            String message = String.format("Author %s %s already exists in DB.", author.getFirstName(), author.getLastName());
            log.info(message);
            throw new ItemExist(message);
        }
        authorRepository.save(author);
    }

    public Author saveAuthorIfNotExistAndGet(Author author) {
        Author authorResponse;
        Optional<Author> authorInDbOptional = authorRepository.findAuthorByFullName(author.getFirstName(), author.getLastName());
        if (authorInDbOptional.isPresent()) {
            authorResponse = authorInDbOptional.get();
        } else {
            authorRepository.save(author);
            authorResponse = author;
            log.info("Created a new author: {} {}", author.getFirstName(), author.getLastName());
        }
        return authorResponse;
    }

}

