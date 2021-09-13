package org.frv.repository.impl;

import org.frv.model.Book;
import org.frv.model.Book_;
import org.frv.repository.AbstractJpaDao;
import org.frv.repository.IBookRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

/**
 * @author Roman V.F.
 * Date: 13.08.2021
 */
@Repository
public class BookRepository extends AbstractJpaDao<Book> implements IBookRepository {

    public BookRepository() {
        this.setClazz(Book.class);
    }

    @Transactional
    @Override
    public Book findDetailedOne(Long bookId) {
        Book book;

        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Book> cq = cb.createQuery(Book.class);
            Root<Book> root = cq.from(Book.class);
            Predicate bookIdPredicate = cb.equal(root.get(Book_.id), bookId);
            root.fetch(Book_.rentRecords);
            cq.select(root).where(bookIdPredicate);
            book = entityManager.createQuery(cq).getSingleResult();
        } catch (NoResultException exception) {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Book> cq = cb.createQuery(Book.class);
            Root<Book> root = cq.from(Book.class);
            Predicate bookIdPredicate = cb.equal(root.get(Book_.id), bookId);
            cq.select(root).where(bookIdPredicate);
            book = entityManager.createQuery(cq).getSingleResult();
            book.setRentRecords(null);
        }

        return book;
    }
}
