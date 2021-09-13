package org.frv.service;

import org.frv.dto.UserToSaveDto;
import org.frv.model.User;

/**
 * @author Roman V.F.
 * Date: 13.08.2021
 */
public interface IUserService extends IService<User> {

    User findUserByLogin(String login);

    void saveUser(UserToSaveDto user);

    void updateUser(UserToSaveDto user);

    void deleteUser(UserToSaveDto user);
}
