package org.frv.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.frv.model.Author;
import org.frv.model.Author_;
import org.frv.repository.AbstractJpaDao;
import org.frv.repository.IAuthorRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Optional;

/**
 * @author Roman V.F.
 * Date: 13.08.2021
 */
@Slf4j
@Repository
public class AuthorRepository extends AbstractJpaDao<Author> implements IAuthorRepository {

    public AuthorRepository() {
        this.setClazz(Author.class);
    }

    @Override
    public Optional<Author> findAuthorByFullName(String firstName, String lastName) {
        Optional<Author> author;

        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Author> cq = cb.createQuery(Author.class);
            Root<Author> root = cq.from(Author.class);
            cq.select(root);
            Predicate firstNamePredicate = cb.equal(root.get(Author_.firstName), firstName);
            Predicate lastNamePredicate = cb.equal(root.get(Author_.lastName), lastName);
            cq.where(cb.and(firstNamePredicate, lastNamePredicate));
            author = Optional.of(entityManager.createQuery(cq).getSingleResult());
            log.info("Found author: " + author.get());
        } catch (NoResultException exception) {
            String errorMessage = String.format("Author %s %s not found.", firstName, lastName);
            log.warn(errorMessage);
            author = Optional.empty();
        }

        return author;
    }
}
