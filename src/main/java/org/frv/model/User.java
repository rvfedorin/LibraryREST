package org.frv.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * @author Roman V.F.
 * Date: 13.08.2021
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements Serializable {

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private String email;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    private Address address;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "user")
    private Set<RentRecord> rentedRecords;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id", referencedColumnName = "id")})
    private Set<Role> roles;

    public User(String firstName, String lastName, String login, String password, String email, Set<Role> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }

    public void updateUser(User userToCopy) {
        if (userToCopy.getFirstName() != null) {
            this.firstName = userToCopy.getFirstName();
        }
        if (userToCopy.getLastName() != null) {
            this.lastName = userToCopy.getLastName();
        }
        if (userToCopy.getLogin() != null) {
            this.login = userToCopy.getLogin();
        }
        if (userToCopy.getPassword() != null) {
            this.password = userToCopy.getPassword();
        }
        if (userToCopy.getEmail() != null) {
            this.email = userToCopy.getEmail();
        }
        if (userToCopy.getAddress() != null) {
            this.address = userToCopy.getAddress();
        }
        if (userToCopy.getRoles() != null) {
            this.roles = userToCopy.getRoles();
        }
    }
}
