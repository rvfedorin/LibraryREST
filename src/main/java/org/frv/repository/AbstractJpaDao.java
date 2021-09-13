package org.frv.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.List;

/**
 * @author Roman V.F.
 * Date: 13.08.2021
 */
public abstract class AbstractJpaDao<T extends Serializable> {
    private Class<T> clazz;

    @PersistenceContext
    public EntityManager entityManager;

    public void setClazz(Class<T> clazzToSet) {
        this.clazz = clazzToSet;
    }

    @Transactional
    public T findOne(Long id) {
        return entityManager.find(clazz, id);
    }

    @Transactional
    public List<T> findAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(clazz);
        Root<T> item = criteriaQuery.from(clazz);
        criteriaQuery.select(item);
        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Transactional
    public void save(T entity) {
        entityManager.persist(entity);
    }

    @Transactional
    public T update(T entity) {
        return entityManager.merge(entity);
    }

    @Transactional
    public void delete(T entity) {
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }

    @Transactional
    public void deleteById(Long entityId) {
        T entity = findOne(entityId);
        delete(entity);
    }
}
