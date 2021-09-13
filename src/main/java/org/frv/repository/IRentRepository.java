package org.frv.repository;

import org.frv.model.RentRecord;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Roman V.F.
 * Date: 13.08.2021
 */
public interface IRentRepository extends IGenericDao<RentRecord> {
    RentRecord findRentActiveByBook(Long bookId);

    List<RentRecord> findAllOnRent();

    List<RentRecord> findAllOnRentExpired(LocalDateTime expiredDate);

    List<RentRecord> showReturned();

    List<RentRecord> showReturnedExpired();
}
