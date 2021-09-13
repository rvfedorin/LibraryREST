package org.frv.service.impl;

import org.frv.config.DataConfigForTest;
import org.frv.config.SecurityConfig;
import org.frv.dto.AddressDto;
import org.frv.exceptions.ItemNotFound;
import org.frv.model.Address;
import org.frv.service.IAddressService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Roman V. Fedorin
 */
@ActiveProfiles("test")
@SpringJUnitWebConfig({DataConfigForTest.class, SecurityConfig.class})
@Transactional
class AddressServiceTest {

    @Autowired
    IAddressService addressService;

    @Test
    void findOne() {
        Address address = addressService.findOne(2L);
        assertThat(address.getId(), is(2L));
        assertThat(address.getCity(), is("Orel"));
        assertThat(address.getStreet(), is("M.Gorkogo"));
        assertThat(address.getApartmentNumber(), is(21));

        assertThrows(ItemNotFound.class, () -> addressService.findOne(21L));
    }

    @Test
    void findAll() {
        assertThat(addressService.findAll(), hasSize(3));
        assertThat(addressService.findAll().toString(), containsString("street=M.Gorkogo, houseNumber=133"));
    }

    @Test
    void save() {
        assertThat(addressService.findAll(), hasSize(3));
        Address address = new Address();
        addressService.save(address);
        assertThat(addressService.findAll(), hasSize(4));
        for (Address addressInDb : addressService.findAll()) {
            if (addressInDb.getCity() == null) {
                addressService.deleteById(addressInDb.getId());
            }
        }

        assertThat(addressService.findAll(), hasSize(3));
    }

    @Test
    void update() {
        Address address = addressService.findOne(2L);
        assertThat(address.getId(), is(2L));
        assertThat(address.getCity(), is("Orel"));
        address.setCity("Moscow");
        addressService.update(address);
        Address addressUpdated = addressService.findOne(2L);
        assertThat(addressUpdated.getId(), is(2L));
        assertThat(addressUpdated.getCity(), is("Moscow"));

        address.setCity("Orel");
        addressService.update(address);
        addressUpdated = addressService.findOne(2L);
        assertThat(addressUpdated.getId(), is(2L));
        assertThat(addressUpdated.getCity(), is("Orel"));
    }

    @Test
    void delete() {
        Address address = addressService.findOne(1L);
        addressService.delete(address);
        assertThrows(ItemNotFound.class, () -> addressService.findOne(1L));
        addressService.save(address);
    }

    @Test
    void deleteById() {
        Address address = addressService.findOne(1L);
        assertThat(address.getCity(), is("Orel"));
        addressService.deleteById(address.getId());
        assertThrows(ItemNotFound.class, () -> addressService.findOne(1L));
    }

    @Test
    void findAddressByFullParams() {
        Address address = addressService.findOne(1L);
        AddressDto addressDto = new AddressDto(address);
        Optional<Address> addressInDbOptional = addressService.findAddressByFullParams(addressDto);
        assertThat(addressInDbOptional.isPresent(), equalTo(true));
        Address addressInDb = addressInDbOptional.get();
        assertThat(addressInDb.getId(), is(1L));
        assertThat(addressDto.getCity(), is(addressInDb.getCity()));
        assertThat(addressDto.getStreet(), is(addressInDb.getStreet()));
        assertThat(addressDto.getHouseNumber(), is(addressInDb.getHouseNumber()));
        assertThat(addressDto.getApartmentNumber(), is(addressInDb.getApartmentNumber()));
    }
}