package org.frv.service.impl;

import lombok.NoArgsConstructor;
import org.frv.dto.AddressDto;
import org.frv.model.Address;
import org.frv.repository.IAddressRepository;
import org.frv.service.CommonService;
import org.frv.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;

/**
 * @author Roman V.F.
 * Date: 13.08.2021
 */
@Service
@NoArgsConstructor
public class AddressService extends CommonService<Address> implements IAddressService {

    @Autowired
    private IAddressRepository addressRepository;

    @PostConstruct
    private void init() {
        this.setRepository(addressRepository);
    }

    @Override
    public Optional<Address> findAddressByFullParams(AddressDto addressDto) {
        return this.addressRepository.findAddressByFullParams(addressDto);
    }
}
