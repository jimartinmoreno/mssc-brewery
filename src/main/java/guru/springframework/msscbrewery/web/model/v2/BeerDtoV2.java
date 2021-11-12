package guru.springframework.msscbrewery.web.model.v2;

import lombok.*;

import java.util.UUID;

/**
 * Created by jt on 2019-04-23.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BeerDtoV2 {
    private UUID id;
    private String beerName;
    private BeerStyle beerStyle;
    private Long upc;
}
