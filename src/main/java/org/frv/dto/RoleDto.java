package org.frv.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.frv.model.Role;

/**
 * @author Roman V. Fedorin
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleDto {
    private Long id;
    private String name;

    public RoleDto(Role role) {
        this.id = role.getId();
        this.name = role.getName();
    }
}
