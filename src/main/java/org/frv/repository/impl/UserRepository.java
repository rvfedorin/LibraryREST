package org.frv.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.frv.exceptions.ItemNotFound;
import org.frv.model.User;
import org.frv.model.User_;
import org.frv.repository.AbstractJpaDao;
import org.frv.repository.IUserRepository;
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
public class UserRepository extends AbstractJpaDao<User> implements IUserRepository {

    public UserRepository() {
        this.setClazz(User.class);
    }

    @Override
    public User findUserByLogin(String login) {
        User user;
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<User> userCQ = cb.createQuery(User.class);
            Root<User> userRoot = userCQ.from(User.class);
            userCQ.select(userRoot);
            Predicate loginPredicate = cb.equal(userRoot.get(User_.login), login);
            userCQ.where(loginPredicate);
            user = entityManager.createQuery(userCQ).getSingleResult();
        } catch (NoResultException exception) {
            String errorMessage = String.format("Login %s not found.", login);
            log.warn(errorMessage);
            throw new ItemNotFound(errorMessage);
        }

        return user;
    }

}
