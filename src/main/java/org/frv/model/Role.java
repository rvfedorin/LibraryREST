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
@Table(name = "roles")
public class Role implements Serializable {

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private Set<User> users;
}
