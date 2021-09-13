package org.frv.service;

import org.frv.dto.AddressDto;
import org.frv.model.Address;

import java.util.Optional;

/**
 * @author Roman V.F.
 * Date: 13.08.2021
 */
public interface IAddressService extends IService<Address> {
    Optional<Address> findAddressByFullParams(AddressDto addressDto);

}
