package com.atsistemas.superhero.models.dao;

import com.atsistemas.superhero.models.entity.Superhero;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ISuperheroDao extends CrudRepository<Superhero, Integer> {

    List<Superhero> findByNameContainsIgnoreCase(String name);

}
