package org.frv.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


/**
 * @author Roman V. Fedorin
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserToSaveDto {

    @NotNull
    @Size(min = 2, max = 30, message = "Firstname must be between 2 and 30")
    private String firstName;

    @NotNull
    @Size(min = 2, max = 30, message = "Lastname must be between 2 and 30")
    private String lastName;

    @NotNull
    @Size(min = 3, max = 30, message = "Login must be between 3 and 30")
    private String login;

    @NotNull
    @Size(min = 8, max = 30, message = "Password must be between 8 and 30")
    private String password;

    @Email
    private String email;
    private String roles;
    private AddressDto address;
}
