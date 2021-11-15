package guru.springframework.msscbrewery.web.controller;

import guru.springframework.msscbrewery.services.BeerService;
import guru.springframework.msscbrewery.web.model.BeerDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api/v1/beer")
@RestController
@Slf4j //Lombok annotation
// @Validated
public class BeerController {

    private final BeerService beerService;

    public BeerController(BeerService beerService) {
        this.beerService = beerService;
    }

    @GetMapping
    public ResponseEntity<BeerDto[]> getAllBeer() {
        log.info("getAllBeer - Init");
        List<BeerDto> beerDtoList = beerService.getAllBeers();
        log.info("getAllBeer - beerDtoList: " + beerDtoList);
        BeerDto[] itemsArray = new BeerDto[beerDtoList.size()];
        return new ResponseEntity<>(beerDtoList.toArray(itemsArray), HttpStatus.OK);
    }

    @GetMapping({"/{beerId}"})
    public ResponseEntity<BeerDto> getBeer(@PathVariable("beerId") UUID beerId) {
        log.info("getBeer - beerId::: " + beerId);
        return new ResponseEntity<>(beerService.getBeerById(beerId), HttpStatus.OK);
    }

    /**
     * @Valid Marks a property, method parameter or method return type for validation cascading.
     * Constraints defined on the object and its properties are be validated when the property,
     * method parameter or method return type is validated.
     */
    @PostMapping // POST - create new beer
    public ResponseEntity<BeerDto> handlePost(@Valid @RequestBody BeerDto beerDto) {
        log.info("handlePost - beerDto: " + beerDto);
        BeerDto savedDto = beerService.saveNewBeer(beerDto);

        HttpHeaders headers = new HttpHeaders();

        //todo add hostname to url
        headers.add("Location", "/api/v1/beer/" + savedDto.getId());
        log.info("handlePost - headers: " + headers);
        return new ResponseEntity<>(savedDto, headers, HttpStatus.CREATED);
    }

    @PutMapping({"/{beerId}"})
    public ResponseEntity handleUpdate(@PathVariable("beerId") UUID beerId, @Valid @NotNull @RequestBody BeerDto beerDto) {
        log.info("handleUpdate - beerId: " + beerId);
        log.info("handleUpdate - beerDto: " + beerDto);
        beerService.updateBeer(beerId, beerDto);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping({"/{beerId}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBeer(@PathVariable("beerId") UUID beerId) {
        log.info("deleteBeer - beerId: " + beerId);
        beerService.deleteById(beerId);
    }
}
