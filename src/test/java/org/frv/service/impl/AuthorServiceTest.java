package org.frv.service.impl;

import liquibase.pro.packaged.A;
import org.frv.config.DataConfigForTest;
import org.frv.config.SecurityConfig;
import org.frv.exceptions.ItemExist;
import org.frv.model.Author;
import org.frv.service.IAuthorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import javax.transaction.Transactional;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Roman V. Fedorin
 */
@ActiveProfiles("test")
@SpringJUnitWebConfig({DataConfigForTest.class, SecurityConfig.class})
@Transactional
class AuthorServiceTest {

    @Autowired
    IAuthorService authorService;

    @Test
    void addAuthor() {
        List<Author> authorsList = authorService.findAll();
        assertThat(authorService.findAll(), hasSize(7));
        Author newAuthor = new Author("New", "Author");
        authorService.addAuthor(newAuthor);
        assertThat(authorService.findAll(), hasSize(8));
    }

    @Test
    void saveAuthorIfNotExistAndGet() {
        //Author(firstName=William, lastName=Shakespeare)
        List<Author> authorsList = authorService.findAll();
        assertThat(authorService.findAll(), hasSize(7));
        Author authorInDb = authorService.findOne(1L);
        assertThrows(ItemExist.class, () -> authorService.addAuthor(authorInDb));

        Author newAuthor = new Author("Author", "NotExist");
        authorService.addAuthor(newAuthor);
        assertThat(authorService.findAll(), hasSize(8));
    }
}