package org.frv.repository;

import org.frv.dto.AddressDto;
import org.frv.model.Address;

import java.util.Optional;

/**
 * @author Roman V.F.
 * Date: 13.08.2021
 */
public interface IAddressRepository extends IGenericDao<Address>{
    Optional<Address> findAddressByFullParams(AddressDto addressDto);
}
