package guru.springframework.msscbrewery.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.msscbrewery.services.BeerService;
import guru.springframework.msscbrewery.web.model.BeerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @MockBean
    BeerService beerService;

    @Autowired
    MockMvc mockMvc;

    /**
     * ObjectMapper provides functionality for reading and writing JSON, either to and from basic POJOs
     * or to and from a general-purpose JSON Tree Model (JsonNode)
     */
    @Autowired
    ObjectMapper objectMapper;

    BeerDto validBeer;

    List<BeerDto> beerDtos = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        validBeer = BeerDto.builder().id(UUID.randomUUID())
                .beerName("Beer1")
                //.beerStyle("PALE_ALE")
                .upc(123456789012L)
                .build();

        beerDtos.add(validBeer);
    }

    @Test
    void getBeer() throws Exception {
        given(beerService.getBeerById(any(UUID.class))).willReturn(validBeer);

        MvcResult result = mockMvc.perform(get("/api/v1/beer/" + validBeer.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(validBeer.getId().toString())))
                .andExpect(jsonPath("$.beerName", is("Beer1")))
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
        then(beerService).should().getBeerById(any());
    }

    @Test
    void handlePost() throws Exception {
        //given
        BeerDto beerDto = validBeer;
        beerDto.setId(null); // Para que pase los test por las validaciones
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);
        beerDto.setId(UUID.randomUUID());

        //BeerDto savedDto = BeerDto.builder().id(UUID.randomUUID()).beerName("New Beer").build();
        BeerDto savedDto = beerDto;
        given(beerService.saveNewBeer(any())).willReturn(savedDto);

        mockMvc.perform(post("/api/v1/beer/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(beerDtoJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(validBeer.getId().toString())))
                .andExpect(jsonPath("$.beerName", is(validBeer.getBeerName())));

        then(beerService).should().saveNewBeer(any());
    }

    @Test
    void handleUpdate() throws Exception {
        //given
        BeerDto beerDto = validBeer;
        beerDto.setId(null); // Para que pase los test por las validaciones
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);
        beerDto.setId(UUID.randomUUID());

        //when
        mockMvc.perform(put("/api/v1/beer/" + validBeer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(beerDtoJson))
                .andExpect(status().isNoContent());

        then(beerService).should().updateBeer(any(), any());
    }

    @Test
    void getAllBeer() throws Exception {
        //given(beerService.getAllBeers()).willReturn(List.of(beerDtos));
        System.out.println("beerService = " + beerService);
        System.out.println("beerDtos = " + beerDtos);
        when(beerService.getAllBeers()).thenReturn(beerDtos);

        MvcResult result = mockMvc.perform(get("/api/v1/beer")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(System.out::println)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(beerDtos.size())))
                .andExpect(jsonPath("$.[*].beerName", hasItems("Beer1")))
                .andExpect(MockMvcResultMatchers.content().json(new ObjectMapper().writeValueAsString(beerDtos)))
                .andReturn();
        then(beerService).should().getAllBeers();
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    void deleteBeer() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/" + validBeer.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
