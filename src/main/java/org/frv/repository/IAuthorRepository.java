package org.frv.repository;

import org.frv.model.Author;

import java.util.Optional;

/**
 * @author Roman V.F.
 * Date: 13.08.2021
 */
public interface IAuthorRepository extends IGenericDao<Author>{
    Optional<Author> findAuthorByFullName(String firstName, String lastName);
}

