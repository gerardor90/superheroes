package com.atsistemas.superhero.rest;

import com.atsistemas.superhero.models.entity.Superhero;
import com.atsistemas.superhero.models.service.ISuperheroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SuperheroController {

    @Autowired
    private ISuperheroService superheroService;

    @GetMapping("/superheroes")
    public ResponseEntity<List<Superhero>> findAll() {
        return new ResponseEntity<>(superheroService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/superheroes/{id}")
    public ResponseEntity<Superhero> findSuperhero(@PathVariable int id){
        return new ResponseEntity<>(superheroService.findOne(id), HttpStatus.OK);
    }

    @GetMapping("/superheroes/name/{name}")
    public ResponseEntity<List<Superhero>> findSuperheroByName(@PathVariable String name){
        return new ResponseEntity<>(superheroService.findByName(name), HttpStatus.OK);
    }

    @PutMapping("/superheroes")
    public ResponseEntity<Superhero> updateSuperhero(@RequestBody Superhero superhero) {
        return new ResponseEntity<>(superheroService.update(superhero), HttpStatus.OK);
    }

    @DeleteMapping("/superheroes/{id}")
    public ResponseEntity<String> deleteSuperhero(@PathVariable int id) {
        superheroService.delete(id);
        return new ResponseEntity<>("Deleted superhero id " + id, HttpStatus.OK);
    }

}
