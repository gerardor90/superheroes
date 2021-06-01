package com.atsistemas.superhero.models.service.implementation;

import com.atsistemas.superhero.models.dao.ISuperheroDao;
import com.atsistemas.superhero.models.entity.Superhero;
import com.atsistemas.superhero.models.service.ISuperheroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuperheroServiceImpl implements ISuperheroService {

    @Autowired
    private ISuperheroDao superheroDao;

    @Override
    public List<Superhero> findAll() {
        return (List<Superhero>) superheroDao.findAll();
    }

    @Override
    public Superhero findOne(int id) {
        return superheroDao.findById(id).orElse(null);
    }

    @Override
    public List<Superhero> findByName(String name) {
        return superheroDao.findByNameContainsIgnoreCase(name);
    }

    @Override
    public Superhero update(Superhero superhero) {
        return superheroDao.save(superhero);
    }

    @Override
    public void delete(int id) {
        superheroDao.deleteById(id);
    }

}
