package com.atsistemas.superhero.controller.rest;

import com.atsistemas.superhero.annotation.MeasureTime;
import com.atsistemas.superhero.exception.EmptyException;
import com.atsistemas.superhero.models.entity.Superhero;
import com.atsistemas.superhero.models.service.ISuperheroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SuperheroController {

    @Autowired
    private ISuperheroService superheroService;

    @MeasureTime
    @PreAuthorize("hasRole('ADMIN') OR hasRole('USER')")
    @GetMapping("/superheroes")
    public ResponseEntity<List<Superhero>> findAll() throws EmptyException {
        List<Superhero> superheroes = superheroService.findAll();

        if (superheroes.isEmpty()) {
            throw new EmptyException("The list of superheroes is empty.");
        }

        return new ResponseEntity<>(superheroes, HttpStatus.OK);
    }

    @MeasureTime
    @PreAuthorize("hasRole('ADMIN') OR hasRole('USER')")
    @GetMapping("/superheroes/{id}")
    public ResponseEntity<Superhero> findSuperhero(@PathVariable int id) throws EmptyException {
        Superhero superhero = superheroService.findOne(id);

        if(superhero == null) {
            throw new EmptyException("Superhero id not found - " + id);
        }

        return new ResponseEntity<>(superhero, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('USER')")
    @GetMapping("/superheroes/name/{name}")
    public ResponseEntity<List<Superhero>> findSuperheroByName(@PathVariable String name) throws EmptyException {
        List<Superhero> superheroes = superheroService.findByName(name);

        if(superheroes.isEmpty()) {
            throw new EmptyException("Superhero name not found - " + name);
        }

        return new ResponseEntity<>(superheroes, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/superheroes")
    public ResponseEntity<Superhero> updateSuperhero(@RequestBody Superhero superhero) {
        return new ResponseEntity<>(superheroService.update(superhero), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/superheroes/{id}")
    public ResponseEntity<String> deleteSuperhero(@PathVariable int id) throws EmptyException {
        Superhero superhero = superheroService.findOne(id);

        if(superhero == null) {
            throw new EmptyException("Superhero id not found - " + id);
        }

        superheroService.delete(id);

        return new ResponseEntity<>("Deleted superhero id " + id, HttpStatus.OK);
    }

}
