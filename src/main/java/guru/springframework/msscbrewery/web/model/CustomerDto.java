package guru.springframework.msscbrewery.web.model;

import lombok.*;

import java.util.UUID;

/**
 *  Lombok annotations
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CustomerDto {

    private UUID id;
    private String name;
}
