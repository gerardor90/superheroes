package com.atsistemas.superhero.models.service.implementation;

import com.atsistemas.superhero.models.entity.Superhero;
import com.atsistemas.superhero.models.service.ISuperheroService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SuperheroServiceImpl implements ISuperheroService {

    @Override
    public List<Superhero> findAll() {
        return new ArrayList<>();
    }

    @Override
    public Superhero findOne(int id) {
        return new Superhero();
    }

    @Override
    public List<Superhero> findByName(String name) {
        return new ArrayList<>();
    }

    @Override
    public Superhero update(Superhero superhero) {
        return new Superhero();
    }

    @Override
    public void delete(int id) { }

}
