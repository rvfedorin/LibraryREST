package org.frv.repository.impl;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.frv.exceptions.ItemNotFound;
import org.frv.model.Role;
import org.frv.model.Role_;
import org.frv.repository.AbstractJpaDao;
import org.frv.repository.IRoleRepository;
import org.springframework.stereotype.Repository;

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
public class RoleRepository extends AbstractJpaDao<Role> implements IRoleRepository {

    public RoleRepository() {
        this.setClazz(Role.class);
    }

    @Override
    public Role findRoleByName(String name) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Role> roleCQ = cb.createQuery(Role.class);
        Root<Role> roleRoot = roleCQ.from(Role.class);
        roleCQ.select(roleRoot);
        Predicate namePredicate = cb.equal(roleRoot.get(Role_.name), name);
        roleCQ.where(namePredicate);
        Role role = entityManager.createQuery(roleCQ).getSingleResult();

        if (role == null) {
            String errorMessage = String.format("Role %s not found", name);
            log.warn(errorMessage);
            throw new ItemNotFound(errorMessage);
        }

        return role;
    }


}
