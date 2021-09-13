package org.frv.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.frv.model.Role;
import org.frv.model.User;

import java.util.HashSet;
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
public class UserForAdminDto {
    @JsonIgnore
    private Long id;
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private String email;
    private AddressDto address;
    private Set<RoleDto> roles = new HashSet<>();

    public UserForAdminDto(User user) {
        if (user != null) {
            this.id = user.getId();
            this.firstName = user.getFirstName();
            this.lastName = user.getLastName();
            this.login = user.getLogin();
            this.password = user.getPassword();
            this.email = user.getEmail();
            this.address = new AddressDto(user.getAddress());

            if (user.getRoles() != null) {
                for (Role role : user.getRoles()) {
                    this.roles.add(new RoleDto(role));
                }
            }
        }
    }
}
