package org.frv.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.frv.model.Author;

/**
 * @author Roman V. Fedorin
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthorDto {
    private Long id;
    private String firstName;
    private String lastName;

    public AuthorDto(Author author) {
        if (author != null) {
            this.id = author.getId();
            this.firstName = author.getFirstName();
            this.lastName = author.getLastName();
        }
    }

    @Override
    public String toString() {
        return (firstName == null ? "" : firstName) + (lastName == null ? "" : " " + lastName);
    }
}
