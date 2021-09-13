package org.frv.service;

import lombok.extern.slf4j.Slf4j;
import org.frv.exceptions.ItemNotFound;
import org.frv.repository.IGenericDao;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Roman V.F.
 * Date: 13.08.2021
 */
@Slf4j
public abstract class CommonService<T extends Serializable> {

    private IGenericDao<T> repository;

    public void setRepository(IGenericDao<T> repository) {
        this.repository = repository;
    }

    public T findOne(Long id) {
        T item = repository.findOne(id);
        if (item == null) {
            ResourceBundle bundle = ResourceBundle.getBundle("messages");
            String message = bundle.getString("item_not_found");
            log.warn(message);
            throw new ItemNotFound(message);
        }
        return item;
    }

    public List<T> findAll() {
        return repository.findAll();
    }

    public void save(T entity) {
        repository.save(entity);
    }

    public T update(T entity) {
        return repository.update(entity);
    }

    public void delete(T entity) {
        repository.delete(entity);
    }

    public void deleteById(Long entityId) {
        repository.deleteById(entityId);
    }
}
