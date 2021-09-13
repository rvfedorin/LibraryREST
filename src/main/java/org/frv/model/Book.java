package org.frv.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.frv.dto.BookDto;

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
@Table(name = "books")
public class Book implements Serializable {

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String releaseDate;
    private Double cost;

    @Enumerated(EnumType.STRING)
    private AvailableStatus status;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "book_authors",
            joinColumns = {@JoinColumn(name = "book_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "author_id", referencedColumnName = "id")})
    private Set<Author> authors;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "book")
    private Set<RentRecord> rentRecords;

    public void updateBook(BookDto newDataBookDto) {
        if (newDataBookDto.getId() != null) {
            this.id = newDataBookDto.getId();
        }
        if (newDataBookDto.getTitle() != null) {
            this.title = newDataBookDto.getTitle();
        }
        if (newDataBookDto.getDescription() != null) {
            this.description = newDataBookDto.getDescription();
        }
        if (newDataBookDto.getReleaseDate() != null) {
            this.releaseDate = newDataBookDto.getReleaseDate();
        }
        if (newDataBookDto.getCost() != null) {
            this.cost = newDataBookDto.getCost();
        }
        if (newDataBookDto.getStatus() != null) {
            this.status = newDataBookDto.getStatus();
        }
    }
}
