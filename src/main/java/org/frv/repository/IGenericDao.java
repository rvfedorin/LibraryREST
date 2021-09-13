package org.frv.repository;

import java.io.Serializable;
import java.util.List;

/**
 * @author Roman V.F.
 * Date: 13.08.2021
 */
public interface IGenericDao<T extends Serializable> {

    T findOne(final Long id);

    List<T> findAll();

    void save(final T entity);

    T update(final T entity);

    void delete(final T entity);

    void deleteById(final Long entityId);

}
