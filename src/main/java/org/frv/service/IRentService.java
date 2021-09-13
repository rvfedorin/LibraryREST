package org.frv.service;

import org.frv.dto.BookDto;
import org.frv.dto.RentRecordDto;
import org.frv.model.RentRecord;

import java.util.List;

/**
 * @author Roman V.F.
 * Date: 13.08.2021
 */
public interface IRentService extends IService<RentRecord> {
    BookDto rentBook(Long bookId, String userLogin);

    BookDto returnBook(Long bookId);

    List<RentRecordDto> showOnRent();

    List<RentRecordDto> showOnRentExpired();

    List<RentRecordDto> showReturned();

    List<RentRecordDto> showReturnedExpired();
}
