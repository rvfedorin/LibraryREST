package org.frv.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.frv.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Roman V.F.
 * Date: 20.08.2021
 */
@Setter
@Getter
@AllArgsConstructor
public class JwtUserDetail implements UserDetails {

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private final Collection<? extends GrantedAuthority> authorities;

    public JwtUserDetail(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.username = user.getLogin();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.authorities = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
