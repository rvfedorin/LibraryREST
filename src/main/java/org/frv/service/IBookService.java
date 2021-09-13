package org.frv.service;

import org.frv.dto.BookDto;
import org.frv.model.Book;
import org.frv.model.CatalogType;

import java.util.List;
import java.util.Map;

/**
 * @author Roman V.F.
 * Date: 13.08.2021
 */
public interface IBookService extends IService<Book> {

    void addNewBook(BookDto bookDtoToSave);

    Book findDetailedOne(Long bookId);

    Map<String, List<BookDto>> getCatalog(CatalogType catalogType);

    void changeBook(BookDto bookDtoToChange);
}
