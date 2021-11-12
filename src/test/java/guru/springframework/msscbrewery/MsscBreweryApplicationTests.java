package guru.springframework.msscbrewery;

import guru.springframework.msscbrewery.services.BeerService;
import guru.springframework.msscbrewery.services.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class MsscBreweryApplicationTests {
	@Autowired
	BeerService beerService;

	@Autowired
	CustomerService customerService;

	@Test
	void contextLoads() {
		assertThat(beerService).isNotNull();
		assertThat(customerService).isNotNull();
	}

}
