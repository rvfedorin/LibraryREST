package org.frv.service.impl;

import lombok.NoArgsConstructor;
import org.frv.model.Role;
import org.frv.repository.IRoleRepository;
import org.frv.service.CommonService;
import org.frv.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author Roman V.F.
 * Date: 13.08.2021
 */
@Service
@NoArgsConstructor
public class RoleService extends CommonService<Role> implements IRoleService {

    @Autowired
    private IRoleRepository roleRepository;

    @PostConstruct
    private void init() {
        this.setRepository(roleRepository);
    }

    @Override
    public Role findRoleByName(String roleName) {
        return roleRepository.findRoleByName(roleName);
    }
}
