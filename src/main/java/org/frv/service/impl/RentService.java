package org.frv.service.impl;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.frv.dto.BookDto;
import org.frv.dto.RentRecordDto;
import org.frv.exceptions.ItemNotAvailable;
import org.frv.exceptions.NoContentException;
import org.frv.model.*;
import org.frv.repository.IRentRepository;
import org.frv.service.CommonService;
import org.frv.service.IBookService;
import org.frv.service.IRentService;
import org.frv.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Roman V.F.
 * Date: 13.08.2021
 */
@Slf4j
@Service
@NoArgsConstructor
public class RentService extends CommonService<RentRecord> implements IRentService {

    @Value("${max-day-rent}")
    private int maxDayRent;

    @Autowired
    private IRentRepository rentRepository;

    @Autowired
    private IBookService bookService;

    @Autowired
    private IUserService userService;

    @PostConstruct
    private void init() {
        this.setRepository(rentRepository);
    }

    @Override
    @Transactional
    public BookDto rentBook(Long bookId, String userLogin) {
        Book bookForRent = bookService.findOne(bookId);
        if (bookForRent.getStatus().equals(AvailableStatus.AVAILABLE)) {
            LocalDateTime nowDate = LocalDateTime.now();
            LocalDateTime needReturnDate = nowDate.plusDays(maxDayRent);
            User user = userService.findUserByLogin(userLogin);

            RentRecord rentRecord = new RentRecord();
            rentRecord.setUser(user);
            rentRecord.setBook(bookForRent);
            rentRecord.setTakeDate(nowDate);
            rentRecord.setReturnDate(needReturnDate);
            rentRecord.setStatus(RentStatus.ON_RENT);
            rentRepository.save(rentRecord);

            bookForRent.setStatus(AvailableStatus.NOT_AVAILABLE);
        } else {
            String messageError = String.format("The book with id %s is not available.", bookId);
            log.warn(messageError);
            throw new ItemNotAvailable(messageError);
        }

        return new BookDto(bookForRent);
    }

    @Override
    @Transactional
    public BookDto returnBook(Long bookId) {
        Book bookFromRent = bookService.findOne(bookId);
        if (bookFromRent.getStatus().equals(AvailableStatus.NOT_AVAILABLE)) {
            RentRecord rentRecord = rentRepository.findRentActiveByBook(bookId);
            rentRecord.setFactReturnDate(LocalDateTime.now());
            rentRecord.setStatus(RentStatus.RETURNED);
            bookFromRent.setStatus(AvailableStatus.AVAILABLE);
        } else {
            String messageError = String.format("The book with id %s is already available.", bookId);
            log.warn(messageError);
            throw new ItemNotAvailable(messageError);
        }

        return new BookDto(bookFromRent);
    }

    @Override
    public List<RentRecordDto> showOnRent() {
        List<RentRecordDto> rentRecordDtoList = new ArrayList<>();
        for (RentRecord rentRecord : rentRepository.findAllOnRent()) {
            rentRecordDtoList.add(new RentRecordDto(rentRecord));
        }
        if (rentRecordDtoList.isEmpty()) {
            throw new NoContentException("There are no active rents.");
        }
        return rentRecordDtoList;
    }

    @Override
    public List<RentRecordDto> showOnRentExpired() {
        List<RentRecordDto> rentRecordDtoList = new ArrayList<>();
        for (RentRecord rentRecord : rentRepository.findAllOnRentExpired(LocalDateTime.now())) {
            rentRecordDtoList.add(new RentRecordDto(rentRecord));
        }
        if (rentRecordDtoList.isEmpty()) {
            throw new NoContentException("There are no active rents.");
        }
        return rentRecordDtoList;
    }

    @Override
    public List<RentRecordDto> showReturned() {
        List<RentRecordDto> rentRecordDtoList = new ArrayList<>();
        for (RentRecord rentRecord : rentRepository.showReturned()) {
            rentRecordDtoList.add(new RentRecordDto(rentRecord));
        }
        if (rentRecordDtoList.isEmpty()) {
            throw new NoContentException("There are no active rents.");
        }
        return rentRecordDtoList;
    }

    @Override
    public List<RentRecordDto> showReturnedExpired() {
        List<RentRecordDto> rentRecordDtoList = new ArrayList<>();
        for (RentRecord rentRecord : rentRepository.showReturnedExpired()) {
            rentRecordDtoList.add(new RentRecordDto(rentRecord));
        }
        if (rentRecordDtoList.isEmpty()) {
            throw new NoContentException("There are no active rents.");
        }
        return rentRecordDtoList;
    }
}
