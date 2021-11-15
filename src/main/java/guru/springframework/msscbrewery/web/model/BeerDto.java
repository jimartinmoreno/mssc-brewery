package guru.springframework.msscbrewery.web.model;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import java.util.UUID;

/**
 * Lombok annotations
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BeerDto {
    /**
     *  @Null The annotated element must be null. Accepts any type.
     */
    @Null
    private UUID id;
    /**
     * @NotBlank The annotated element must not be null and must contain at least one
     * non-whitespace character.
     * non-whitespace character.
     */
    @NotBlank(message = "You should specify a valid Beer name")
    private String beerName;
    @NotBlank(message = "You should specify a valid Beer Style")
    private String beerStyle;
    /**
     * @Positive The annotated element must be a strictly positive number
     */
    @Positive(message = "You should specify a valid upc")
    private Long upc;
}
