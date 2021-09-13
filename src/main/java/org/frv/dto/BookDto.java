package org.frv.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.frv.model.Author;
import org.frv.model.AvailableStatus;
import org.frv.model.Book;

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
public class BookDto {

    private Long id;
    private String title;
    private String description;
    private String releaseDate;
    private Double cost;
    private AvailableStatus status;
    private GenreDto genre;
    private Set<AuthorDto> authors;

    public BookDto(Book book) {
        if (book != null) {
            this.id = book.getId();
            this.title = book.getTitle();
            this.description = book.getDescription();
            this.releaseDate = book.getReleaseDate();
            this.cost = book.getCost();
            this.status = book.getStatus();

            if (book.getGenre() != null) {
                this.genre = new GenreDto(book.getGenre());
            }

            if (book.getAuthors() != null) {
                authors = new HashSet<>();
                for (Author author : book.getAuthors()) {
                    authors.add(new AuthorDto(author));
                }
            }
        }
    }
}
