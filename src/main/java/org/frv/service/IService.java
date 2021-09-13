package org.frv.service;

import org.frv.exceptions.ItemNotFound;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Roman V. Fedorin
 */
public interface IService<T extends Serializable> {

    T findOne(Long id);

    List<T> findAll();

    void save(T entity);

    T update(T entity);

    void delete(T entity);

    void deleteById(Long entityId);
}
