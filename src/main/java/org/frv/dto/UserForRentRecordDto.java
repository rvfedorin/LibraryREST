package org.frv.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.frv.model.User;

/**
 * @author Roman V. Fedorin
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserForRentRecordDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private AddressDto address;

    public UserForRentRecordDto(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.address = new AddressDto(user.getAddress());
    }
}
