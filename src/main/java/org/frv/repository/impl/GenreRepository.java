package org.frv.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.frv.exceptions.ItemNotFound;
import org.frv.model.Genre;
import org.frv.model.Genre_;
import org.frv.repository.AbstractJpaDao;
import org.frv.repository.IGenreRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * @author Roman V.F.
 * Date: 13.08.2021
 */
@Slf4j
@Repository
public class GenreRepository extends AbstractJpaDao<Genre> implements IGenreRepository {

    public GenreRepository() {
        this.setClazz(Genre.class);
    }

    public Genre findByName(String name) {
        Genre author;

        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Genre> cq = cb.createQuery(Genre.class);
            Root<Genre> root = cq.from(Genre.class);
            cq.select(root);
            Predicate namePredicate = cb.equal(root.get(Genre_.name), name);
            cq.where(namePredicate);
            author = entityManager.createQuery(cq).getSingleResult();
        } catch (NoResultException exception) {
            String errorMessage = String.format("Genre %s not found.", name);
            log.warn(errorMessage);
            throw new ItemNotFound(errorMessage);
        }

        return author;
    }
}
