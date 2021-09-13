package org.frv.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Roman V.F.
 * Date: 20.08.2021
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequestDto {
    private String username;
    private String password;
}
