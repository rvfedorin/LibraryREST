package org.frv.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.frv.model.RentRecord;
import org.frv.model.RentStatus;
import org.frv.model.Role;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

/**
 * @author Roman V. Fedorin
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RentRecordDto {
    private Long id;
    private UserForRentRecordDto user;
    private BookDto book;
    private LocalDateTime takeDate;
    private LocalDateTime returnDate;
    private LocalDateTime factReturnDate;
    private RentStatus status;

    public RentRecordDto(RentRecord rentRecord) {
        if (rentRecord != null) {
            this.id = rentRecord.getId();
            this.user = new UserForRentRecordDto(rentRecord.getUser());
            this.book = new BookDto(rentRecord.getBook());
            this.takeDate = rentRecord.getTakeDate();
            this.returnDate = rentRecord.getReturnDate();
            this.factReturnDate = rentRecord.getFactReturnDate();
            this.status = rentRecord.getStatus();
        }
    }
}
