package com.atsistemas.superhero.models.service;

import com.atsistemas.superhero.models.entity.Superhero;

import java.util.List;

public interface ISuperheroService {

    public List<Superhero> findAll();

    public Superhero findOne(int id);

    public List<Superhero> findByName(String name);

    public Superhero update(Superhero superhero);

    public void delete(int id);

}
