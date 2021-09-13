package org.frv.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.frv.model.RentRecord;
import org.frv.model.RentRecord_;
import org.frv.model.RentStatus;
import org.frv.repository.AbstractJpaDao;
import org.frv.repository.IRentRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Roman V.F.
 * Date: 13.08.2021
 */
@Slf4j
@Repository
public class RentRepository extends AbstractJpaDao<RentRecord> implements IRentRepository {

    public RentRepository() {
        this.setClazz(RentRecord.class);
    }

    @Transactional
    @Override
    public RentRecord findRentActiveByBook(Long bookId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<RentRecord> query = cb.createQuery(RentRecord.class);
        Root<RentRecord> rentRecordRoot = query.from(RentRecord.class);
        Predicate bookIdPredicate = cb.equal(rentRecordRoot.get(RentRecord_.book), bookId);
        Predicate rentStatusPredicate = cb.equal(rentRecordRoot.get(RentRecord_.status), RentStatus.ON_RENT);
        query.select(rentRecordRoot).where(cb.and(bookIdPredicate, rentStatusPredicate));
        RentRecord rentRecord = entityManager.createQuery(query).getSingleResult();
        log.info("Found rent: " + rentRecord);
        return rentRecord;
    }

    @Override
    public List<RentRecord> findAllOnRent() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<RentRecord> query = cb.createQuery(RentRecord.class);
        Root<RentRecord> rentRecordRoot = query.from(RentRecord.class);
        Predicate rentStatusPredicate = cb.equal(rentRecordRoot.get(RentRecord_.status), RentStatus.ON_RENT);
        query.select(rentRecordRoot).where(rentStatusPredicate);
        List<RentRecord> rentRecords = entityManager.createQuery(query).getResultList();
        log.info("Found rents with status ON_RENT: " + rentRecords.size());
        return rentRecords;
    }

    @Override
    public List<RentRecord> findAllOnRentExpired(LocalDateTime expiredDate) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<RentRecord> query = cb.createQuery(RentRecord.class);
        Root<RentRecord> rentRecordRoot = query.from(RentRecord.class);
        Predicate rentStatusPredicate = cb.equal(rentRecordRoot.get(RentRecord_.status), RentStatus.ON_RENT);
        Predicate rentReturnDatePredicate = cb.lessThan(rentRecordRoot.get(RentRecord_.RETURN_DATE), expiredDate);
        query.select(rentRecordRoot).where(cb.and(rentStatusPredicate, rentReturnDatePredicate));
        List<RentRecord> rentRecords = entityManager.createQuery(query).getResultList();
        log.info("Found expired rents with status ON_RENT: " + rentRecords.size());
        return rentRecords;
    }

    @Override
    public List<RentRecord> showReturned() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<RentRecord> query = cb.createQuery(RentRecord.class);
        Root<RentRecord> rentRecordRoot = query.from(RentRecord.class);
        Predicate rentStatusPredicate = cb.equal(rentRecordRoot.get(RentRecord_.status), RentStatus.RETURNED);
        query.select(rentRecordRoot).where(rentStatusPredicate);
        List<RentRecord> rentRecords = entityManager.createQuery(query).getResultList();
        log.info("Found rents with status RETURNED: " + rentRecords.size());
        return rentRecords;
    }

    @Override
    public List<RentRecord> showReturnedExpired() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<RentRecord> query = cb.createQuery(RentRecord.class);
        Root<RentRecord> rentRecordRoot = query.from(RentRecord.class);
        Predicate rentStatusPredicate = cb.equal(rentRecordRoot.get(RentRecord_.status), RentStatus.RETURNED);
        Predicate rentReturnDatePredicate = cb.lessThan(rentRecordRoot.get(RentRecord_.RETURN_DATE),
                rentRecordRoot.get(RentRecord_.FACT_RETURN_DATE));
        query.select(rentRecordRoot).where(cb.and(rentStatusPredicate, rentReturnDatePredicate));
        List<RentRecord> rentRecords = entityManager.createQuery(query).getResultList();
        log.info("Found expired rents with status RETURN_DATE: " + rentRecords.size());
        return rentRecords;
    }
}
