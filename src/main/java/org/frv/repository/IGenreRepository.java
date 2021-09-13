package org.frv.repository;

import org.frv.model.Genre;

/**
 * @author Roman V.F.
 * Date: 13.08.2021
 */
public interface IGenreRepository extends IGenericDao<Genre>{
    Genre findByName(String name);
}
