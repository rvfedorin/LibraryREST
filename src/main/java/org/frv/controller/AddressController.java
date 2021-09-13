package org.frv.controller;

import lombok.extern.slf4j.Slf4j;
import org.frv.dto.AddressDto;
import org.frv.exceptions.NoContentException;
import org.frv.model.Address;
import org.frv.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Roman V.F.
 * Date: 13.08.2021
 */
@Slf4j
@RestController
@RequestMapping("/address")
public class AddressController {

    @Autowired
    private IAddressService addressService;

    @GetMapping("/all")
    public ResponseEntity<List<AddressDto>> getAllAddress() {
        List<Address> addressesInDB = addressService.findAll();

        if (addressesInDB.isEmpty()) {
            ResourceBundle bundle = ResourceBundle.getBundle("messages");
            String message = bundle.getString("address_no_content");
            log.warn(ResourceBundle.getBundle("messages", Locale.ENGLISH).getString("address_no_content"));
            throw new NoContentException(message);
        }

        List<AddressDto> responseAddress = new ArrayList<>();
        for (Address address : addressesInDB) {
            responseAddress.add(new AddressDto(address));
        }

        return ResponseEntity.ok(responseAddress);
    }
}
