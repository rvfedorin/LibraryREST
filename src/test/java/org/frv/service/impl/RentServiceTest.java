package org.frv.service.impl;

import org.frv.config.DataConfigForTest;
import org.frv.config.SecurityConfig;
import org.frv.dto.RentRecordDto;
import org.frv.exceptions.ItemNotFound;
import org.frv.exceptions.NoContentException;
import org.frv.model.RentRecord;
import org.frv.service.IRentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import javax.transaction.Transactional;
import java.util.List;

import static org.frv.model.RentStatus.ON_RENT;
import static org.frv.model.RentStatus.RETURNED;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Roman V. Fedorin
 */
@ActiveProfiles("test")
@SpringJUnitWebConfig({DataConfigForTest.class, SecurityConfig.class})
@Transactional
class RentServiceTest {

    @Autowired
    IRentService rentService;

    @Test
    void findOne() {
        RentRecord rent = rentService.findOne(6L);
        assertThat(rent.getId(), is(6L));
        assertThat(rent.getUser().getFirstName(), is("Admin"));
        assertThat(rent.getBook().getId(), is(1L));
    }

    @Test
    void findAll() {
        assertThat(rentService.findAll(), hasSize(6));
        assertThat(rentService.findAll().toString(), containsString("takeDate=2021-08-23T08:45:18"));
    }

    @Test
    void save() {
        assertThat(rentService.findAll(), hasSize(6));
        RentRecord rent = new RentRecord();
        rentService.save(rent);
        assertThat(rentService.findAll(), hasSize(7));
        for (RentRecord rentRecord : rentService.findAll()) {
            if (rentRecord.getUser() == null) {
                rentService.deleteById(rentRecord.getId());
            }
        }

        assertThat(rentService.findAll(), hasSize(6));
    }

    @Test
    void update() {
        RentRecord rent = rentService.findOne(6L);
        assertThat(rent.getId(), is(6L));
        assertThat(rent.getStatus(), is(ON_RENT));
        rent.setStatus(RETURNED);
        rentService.update(rent);
        RentRecord rentUpdated = rentService.findOne(6L);
        assertThat(rentUpdated.getId(), is(6L));
        assertThat(rentUpdated.getStatus(), is(RETURNED));

        rent.setStatus(ON_RENT);
        rentService.update(rent);
        rentUpdated = rentService.findOne(6L);
        assertThat(rentUpdated.getId(), is(6L));
        assertThat(rentUpdated.getStatus(), is(ON_RENT));
    }

    @Test
    void delete() {
        RentRecord rent = rentService.findOne(1L);
        rentService.delete(rent);
        assertThrows(ItemNotFound.class, () -> rentService.findOne(1L));
        rentService.save(rent);
    }

    @Test
    void deleteById() {
        List<RentRecordDto> rentRecordsList = rentService.showOnRent();
        assertThat(rentRecordsList, hasSize(1));
        rentService.deleteById(rentRecordsList.get(0).getId());
        assertThrows(NoContentException.class, () -> rentService.showOnRent());
    }

    @Test
    void rentBook() {
        assertThat(rentService.showOnRent(), hasSize(1));
        rentService.returnBook(1L);
        assertThrows(NoContentException.class, () -> rentService.showOnRent());
        rentService.rentBook(1L, "rf");
        assertThat(rentService.showOnRent(), hasSize(1));
        assertThat(rentService.showOnRent().get(0).getUser().getFirstName(), is("Admin"));
        assertThat(rentService.showOnRent().get(0).getBook().getId(), is(1L));
    }

    @Test
    void returnBook() {
        assertThat(rentService.showOnRent(), hasSize(1));
        rentService.returnBook(1L);
        assertThrows(NoContentException.class, () -> rentService.showOnRent());
    }

    @Test
    void showOnRent() {
        List<RentRecordDto> rentRecordDtoList = rentService.showOnRent();
        assertThat(rentRecordDtoList, hasSize(1));

        RentRecordDto rentDto = rentRecordDtoList.get(0);

        assertThat(rentDto.getId(), equalTo(6L));
        assertThat(rentDto.getId(), is(not(equalTo(2L))));
        assertThat(rentDto.getUser().getFirstName(), equalTo("Admin"));
        assertThat(rentDto.getStatus(), equalTo(ON_RENT));
        assertThat(rentDto.getBook().getTitle(), equalTo("Crime and Punishment"));
    }
}