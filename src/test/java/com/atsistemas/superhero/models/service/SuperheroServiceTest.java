package com.atsistemas.superhero.models.service;

import com.atsistemas.superhero.models.dao.ISuperheroDao;
import com.atsistemas.superhero.models.entity.Superhero;
import com.atsistemas.superhero.models.service.implementation.SuperheroServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class SuperheroServiceTest {

    @InjectMocks
    private SuperheroServiceImpl superheroService;
    @Mock
    private ISuperheroDao superheroDao;

    @Test
    void findAll() {
        List<Superhero> superheroes = new ArrayList<>();
        superheroes.add(new Superhero(1, "Spiderman", LocalDate.of(1990, Month.JANUARY, 8), "Spain", LocalDateTime.now(), LocalDateTime.now()));
        superheroes.add(new Superhero(2, "Batman", LocalDate.of(1995, Month.JANUARY, 4), "Spain", LocalDateTime.now(), LocalDateTime.now()));
        Mockito.when(superheroDao.findAll()).thenReturn(superheroes);

        List<Superhero> superheroesFinded = superheroService.findAll();

        assertEquals(superheroes.size(), superheroesFinded.size());
    }

    @Test
    void findOne() {
        Superhero superhero = new Superhero(1, "Spiderman", LocalDate.of(1990, Month.JANUARY, 8), "Spain", LocalDateTime.now(), LocalDateTime.now());
        Mockito.when(superheroDao.findById(superhero.getId())).thenReturn(Optional.of(superhero));

        Superhero superheroFinded = superheroService.findOne(superhero.getId());

        assertEquals(superheroFinded.getName(), superhero.getName());
    }

    @Test
    void findByName() {
        List<Superhero> superheroes = new ArrayList<>();
        superheroes.add(new Superhero(1, "Spiderman", LocalDate.of(1990, Month.JANUARY, 8), "Spain", LocalDateTime.now(), LocalDateTime.now()));
        Mockito.when(superheroDao.findByNameContainsIgnoreCase("spi")).thenReturn(superheroes);

        List<Superhero> superheroesFinded = superheroService.findByName("spi");

        assertEquals(superheroes.size(), superheroesFinded.size());
    }

    @Test
    void update() {
        Superhero superhero = new Superhero(1, "Spiderman", LocalDate.of(1990, Month.JANUARY, 8), "Spain", LocalDateTime.now(), LocalDateTime.now());
        Mockito.when(superheroDao.save(superhero)).thenReturn(superhero);

        Superhero superheroFinded = superheroService.update(superhero);

        assertEquals(superhero.getId(), superheroFinded.getId());
    }

    @Test
    void delete() {
        Superhero superhero = new Superhero(1, "Spiderman", LocalDate.of(1990, Month.JANUARY, 8), "Spain", LocalDateTime.now(), LocalDateTime.now());
        Mockito.doNothing().when(superheroDao).deleteById(superhero.getId());

        superheroService.delete(superhero.getId());
    }

}
