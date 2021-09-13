package org.frv.service;

import org.frv.model.Author;

/**
 * @author Roman V.F.
 * Date: 13.08.2021
 */
public interface IAuthorService extends IService<Author> {
    void addAuthor(Author author);

    Author saveAuthorIfNotExistAndGet(Author author);
}
