package org.frv.service;

import org.frv.model.Genre;

/**
 * @author Roman V.F.
 * Date: 13.08.2021
 */
public interface IGenreService extends IService<Genre> {
    Genre addNewGenreIfNeedAndGetIt(Genre genre);
}
