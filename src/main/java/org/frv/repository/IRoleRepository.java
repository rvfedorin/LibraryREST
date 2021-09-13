package org.frv.repository;

import javassist.NotFoundException;
import org.frv.model.Role;

import javax.naming.NameNotFoundException;

/**
 * @author Roman V.F.
 * Date: 13.08.2021
 */
public interface IRoleRepository extends IGenericDao<Role>{

    Role findRoleByName(String name);
}
