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
@Table(name = "addresses")
public class Address implements Serializable {

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;
    private String street;
    private Integer houseNumber;
    private Integer apartmentNumber;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "address")
    private Set<User> users;

    public Address(String city, String street, Integer houseNumber, Integer apartmentNumber) {
        this.city = city;
        this.street = street;
        this.houseNumber = houseNumber;
        this.apartmentNumber = apartmentNumber;
    }
}
