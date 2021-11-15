package guru.springframework.msscbrewery.services;

import guru.springframework.msscbrewery.web.model.BeerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Slf4j //Lombok annotation
@Service
public class BeerServiceImpl implements BeerService {
    @Override
    public BeerDto getBeerById(UUID beerId) {
        log.info(" beerId: " + beerId);
        return BeerDto.builder()
                .id(UUID.randomUUID())
                .beerName("Galaxy Cat")
                .beerStyle("Pale Ale")
                .upc(1L)
                .build();
    }

    @Override
    public BeerDto saveNewBeer(BeerDto beerDto) {
        return BeerDto.builder()
                .id(UUID.randomUUID())
                .build();
    }

    @Override
    public void updateBeer(UUID beerId, BeerDto beerDto) {
        //todo impl - would add a real impl to update beer
    }

    @Override
    public void deleteById(UUID beerId) {
        log.debug("Deleting a beer...");
    }

    @Override
    public List<BeerDto> getAllBeers() {
        return List.of(BeerDto.builder()
                        .id(UUID.randomUUID())
                        .beerName("Estrella Galicia")
                        .beerStyle("Pale Ale")
                        .upc(1L)
                        .build(),
                BeerDto.builder()
                        .id(UUID.randomUUID())
                        .beerName("Damm")
                        .beerStyle("Pale Ale")
                        .upc(1L)
                        .build());
    }
}
