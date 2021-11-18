package guru.springframework.msscbrewery.domain;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

/**
 * Lombok annotations
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class Customer {

    private UUID id;

    @NotBlank
    @Size(min = 3, max = 100)
    private String name;
}
