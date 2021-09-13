package org.frv.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * @author Roman V.F.
 * Date: 13.08.2021
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "genres")
public class Genre implements Serializable {

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = "genre")
    private Set<Book> books;

    public Genre(String name) {
        this.name = name;
    }
}
