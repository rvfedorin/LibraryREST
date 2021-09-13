package org.frv.repository.impl;

import org.frv.dto.AddressDto;
import org.frv.model.Address;
import org.frv.model.Address_;
import org.frv.repository.AbstractJpaDao;
import org.frv.repository.IAddressRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Optional;

/**
 * @author Roman V.F.
 * Date: 13.08.2021
 */
@Repository
public class AddressRepository extends AbstractJpaDao<Address> implements IAddressRepository {

    public AddressRepository() {
        this.setClazz(Address.class);
    }

    @Override
    public Optional<Address> findAddressByFullParams(AddressDto addressDto) {
        Optional<Address> address;
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Address> query = cb.createQuery(Address.class);
            Root<Address> root = query.from(Address.class);
            query.select(root);
            Predicate cityPredicate = cb.equal(root.get(Address_.city), addressDto.getCity());
            Predicate streetPredicate = cb.equal(root.get(Address_.street), addressDto.getStreet());
            Predicate houseNumberPredicate = cb.equal(root.get(Address_.houseNumber), addressDto.getHouseNumber());
            Predicate apartmentNumberPredicate = cb.equal(root.get(Address_.apartmentNumber), addressDto.getApartmentNumber());
            query.where(cb.and(cityPredicate, streetPredicate, houseNumberPredicate, apartmentNumberPredicate));
            address = Optional.of(entityManager.createQuery(query).getSingleResult());
        } catch (NoResultException e) {
            address = Optional.empty();
        }

        return address;
    }
}
