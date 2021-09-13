package org.frv.repository;

import org.frv.model.Book;

/**
 * @author Roman V.F.
 * Date: 13.08.2021
 */
public interface IBookRepository extends IGenericDao<Book> {
    Book findDetailedOne(Long bookId);
}
