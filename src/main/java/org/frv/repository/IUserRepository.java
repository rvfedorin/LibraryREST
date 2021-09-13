package org.frv.repository;

import org.frv.model.User;

/**
 * @author Roman V.F.
 * Date: 13.08.2021
 */
public interface IUserRepository extends IGenericDao<User> {

    User findUserByLogin(String login);

}
