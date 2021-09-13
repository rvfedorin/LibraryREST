package org.frv.service.impl;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.frv.Utils;
import org.frv.dto.AddressDto;
import org.frv.dto.UserToSaveDto;
import org.frv.exceptions.ItemNotFound;
import org.frv.model.Address;
import org.frv.model.Role;
import org.frv.model.User;
import org.frv.repository.IUserRepository;
import org.frv.service.CommonService;
import org.frv.service.IAddressService;
import org.frv.service.IRoleService;
import org.frv.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author Roman V.F.
 * Date: 13.08.2021
 */
@Slf4j
@Service
@NoArgsConstructor
public class UserService extends CommonService<User> implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IAddressService addressService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    private void init() {
        this.setRepository(userRepository);
    }


    @Override
    public User findUserByLogin(String login) {
        log.info("Looking for user: {}", login);
        return userRepository.findUserByLogin(login);
    }

    @Override
    public void saveUser(UserToSaveDto userToSaveDto) {
        User user = getUserFromUserToSaveDto(userToSaveDto);
        if (userToSaveDto.getAddress() != null) {
            Address addressToUser = saveAddressIfNeedAndGetIt(userToSaveDto.getAddress());
            user.setAddress(addressToUser);
        }
        userRepository.save(user);
    }

    @Override
    public void updateUser(UserToSaveDto userToSaveDto) {
        User user = userRepository.findUserByLogin(userToSaveDto.getLogin());
        User newDataUser = getUserFromUserToSaveDto(userToSaveDto);
        if (userToSaveDto.getAddress() != null) {
            Address addressToUser = saveAddressIfNeedAndGetIt(userToSaveDto.getAddress());
            newDataUser.setAddress(addressToUser);
        }
        user.updateUser(newDataUser);
        userRepository.update(user);
    }

    @Override
    public void deleteUser(UserToSaveDto user) {
        User userToDelete = userRepository.findUserByLogin(user.getLogin());
        userRepository.delete(userToDelete);
    }

    private User getUserFromUserToSaveDto(UserToSaveDto userToSaveDto) {
        String firstName = userToSaveDto.getFirstName();
        String lastName = userToSaveDto.getLastName();
        String login = userToSaveDto.getLogin();
        String password = userToSaveDto.getPassword();
        String email = userToSaveDto.getEmail();

        Set<Role> roles = new HashSet<>();
        for (String roleString : userToSaveDto.getRoles().split(",")) {
            String roleName = Utils.getRoleName(roleString.trim());
            roles.add(roleService.findRoleByName(roleName));
        }

        return new User(firstName, lastName, login, passwordEncoder.encode(password), email, roles);
    }

    private Address saveAddressIfNeedAndGetIt(AddressDto addressDto) {
        Optional<Address> addressToUserOptional = addressService.findAddressByFullParams(addressDto);
        Address addressToUser;
        if (addressToUserOptional.isEmpty()) {
            addressToUser = new Address(
                    addressDto.getCity(),
                    addressDto.getStreet(),
                    addressDto.getHouseNumber(),
                    addressDto.getApartmentNumber());
            addressService.save(addressToUser);
            log.info("Added a new address: " + addressToUser);
        } else {
            addressToUser = addressToUserOptional.get();
        }
        return addressToUser;
    }
}
