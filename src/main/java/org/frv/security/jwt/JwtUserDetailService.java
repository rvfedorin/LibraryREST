package org.frv.security.jwt;

import lombok.extern.slf4j.Slf4j;
import org.frv.exceptions.ItemNotFound;
import org.frv.model.User;
import org.frv.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Roman V.F.
 * Date: 20.08.2021
 */
@Slf4j
@Service
public class JwtUserDetailService implements UserDetailsService {

    @Autowired
    private IUserService userService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        JwtUserDetail userDetails;
        try {
            User user = userService.findUserByLogin(login);
            userDetails = new JwtUserDetail(user);
        } catch (ItemNotFound ex) {
            String errorMessage = String.format("User %s not found", login);
            log.warn(errorMessage);
            throw new UsernameNotFoundException(errorMessage);
        }

        return userDetails;
    }
}
