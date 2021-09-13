package org.frv.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.frv.model.Genre;

/**
 * @author Roman V. Fedorin
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenreDto {

    @NotNull
    private String name;

    public GenreDto(Genre genre) {
        if (genre != null) {
            this.name = genre.getName();
        }
    }
}
