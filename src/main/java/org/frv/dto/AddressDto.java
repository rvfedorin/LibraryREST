package org.frv.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.frv.model.Address;

/**
 * @author Roman V. Fedorin
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressDto {
    private Long id;
    private String city;
    private String street;
    private Integer houseNumber;
    private Integer apartmentNumber;

    public AddressDto(Address address) {
        if (address != null) {
            this.id = address.getId();
            this.city = address.getCity();
            this.street = address.getStreet();
            this.houseNumber = address.getHouseNumber();
            this.apartmentNumber = address.getApartmentNumber();
        }
    }
}
