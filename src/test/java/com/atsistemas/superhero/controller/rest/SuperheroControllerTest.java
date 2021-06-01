package com.atsistemas.superhero.controller.rest;

import com.atsistemas.superhero.models.entity.Superhero;
import com.atsistemas.superhero.models.service.ISuperheroService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.hamcrest.Matchers.containsString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest
public class SuperheroControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    private ISuperheroService superheroService;

    @Test
    void findAll() throws Exception {
        List<Superhero> superheroes = new ArrayList<>();
        superheroes.add(new Superhero(1, "Spiderman", LocalDate.of(1990, Month.JANUARY, 8), "Spain", LocalDateTime.now(), LocalDateTime.now()));
        superheroes.add(new Superhero(2, "Batman", LocalDate.of(1995, Month.JANUARY, 4), "Spain", LocalDateTime.now(), LocalDateTime.now()));
        Mockito.when(superheroService.findAll()).thenReturn(superheroes);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/superheroes").contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).
                andExpect(content().string(containsString("Spiderman")))
                .andDo(print());
    }

    @Test
    void findSuperhero() throws Exception {
        Superhero superhero = new Superhero(1, "Spiderman", LocalDate.of(1990, Month.JANUARY, 8), "Spain", LocalDateTime.now(), LocalDateTime.now());
        Mockito.when(superheroService.findOne(1)).thenReturn(superhero);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/superheroes/"+1).contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).
                andExpect(content().string(containsString("Spiderman")))
                .andDo(print());
    }

    @Test
    void findSuperheroByName() throws Exception {
        List<Superhero> superheroes = new ArrayList<>();
        superheroes.add(new Superhero(1, "Spiderman", LocalDate.of(1990, Month.JANUARY, 8), "Spain", LocalDateTime.now(), LocalDateTime.now()));

        Mockito.when(superheroService.findByName("spi")).thenReturn(superheroes);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/superheroes/name/" + "spi").contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).
                andExpect(content().string(containsString("Spiderman")))
                .andDo(print());
    }

    @Test
    void deleteSuperhero() throws Exception {
        Superhero superhero = new Superhero(1, "Spiderman", LocalDate.of(1990, Month.JANUARY, 8), "Spain", LocalDateTime.now(), LocalDateTime.now());

        Mockito.when(superheroService.findOne(1)).thenReturn(superhero);
        Mockito.doNothing().when(superheroService).delete(superhero.getId());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/superheroes/"+1).contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).
                andDo(print());
    }

}
