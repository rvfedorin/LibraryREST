package org.frv.service.impl;

import org.frv.config.DataConfigForTest;
import org.frv.config.SecurityConfig;
import org.frv.dto.BookDto;
import org.frv.model.Book;
import org.frv.model.CatalogType;
import org.frv.service.IBookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * @author Roman V. Fedorin
 */
@ActiveProfiles("test")
@SpringJUnitWebConfig({DataConfigForTest.class, SecurityConfig.class})
@Transactional
class BookServiceTest {

    @Autowired
    IBookService bookService;

    @Test
    void addNewBook() {
        assertThat(bookService.findAll(), hasSize(7));
        BookDto bookDtoToSave = new BookDto(bookService.findOne(1L));
        bookDtoToSave.setId(null);
        bookService.addNewBook(bookDtoToSave);
        assertThat(bookService.findAll(), hasSize(8));
        Book book = bookService.findOne(8L);
        assertThat(book.getTitle(), is(bookDtoToSave.getTitle()));
    }

    @Test
    void findDetailedOne() {
        assertThat(bookService.findDetailedOne(1L).getRentRecords(), hasSize(3));
        assertThat(bookService.findDetailedOne(1L).getRentRecords(), not(hasSize(2)));
    }

    @Test
    void changeBook() {
        Book book = bookService.findOne(1L);
        assertThat(book.getTitle(), is("Crime and Punishment"));
        BookDto bookDtoToChange = new BookDto(book);
        bookDtoToChange.setTitle("New Title");
        bookService.changeBook(bookDtoToChange);

        Book bookInDb = bookService.findOne(1L);
        assertThat(bookInDb.getTitle(), is("New Title"));

        bookDtoToChange.setTitle("Crime and Punishment");
        bookService.changeBook(bookDtoToChange);
        bookInDb = bookService.findOne(1L);
        assertThat(bookInDb.getTitle(), is("Crime and Punishment"));
    }

    @Test
    void getCatalog() {
        Map<String, List<BookDto>> catalog = bookService.getCatalog(CatalogType.AUTHOR);
        assertThat(catalog.entrySet(), hasSize(5));
        assertThat(catalog.toString(), is(startsWith("{Robert Jordan=[BookDto(id=6, title=The Wheel of Time")));
    }
}