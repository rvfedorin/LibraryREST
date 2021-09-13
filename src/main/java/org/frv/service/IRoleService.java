package org.frv.service;

import org.frv.model.Role;

/**
 * @author Roman V.F.
 * Date: 13.08.2021
 */
public interface IRoleService extends IService<Role> {
    Role findRoleByName(String roleName);
}
