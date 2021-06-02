package com.atsistemas.superhero.models.service.implementation;

import com.atsistemas.superhero.models.dao.ISuperheroDao;
import com.atsistemas.superhero.models.entity.Superhero;
import com.atsistemas.superhero.models.service.ISuperheroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuperheroServiceImpl implements ISuperheroService {

    @Autowired
    private ISuperheroDao superheroDao;

    @Override
    @Cacheable(value = "superheroes")
    public List<Superhero> findAll() {
        return (List<Superhero>) superheroDao.findAll();
    }

    @Override
    @Cacheable(value = "superheroe", key = "#id")
    public Superhero findOne(int id) {
        return superheroDao.findById(id).orElse(null);
    }

    @Override
    public List<Superhero> findByName(String name) {
        return superheroDao.findByNameContainsIgnoreCase(name);
    }

    @Override
    @Caching(
            put = {
                    @CachePut(value = "superheroe",  key = "#superhero.id")
            },
            evict = {
                    @CacheEvict(value = "superheroes", allEntries = true),
            }
    )
    public Superhero update(Superhero superhero) {
        return superheroDao.save(superhero);
    }

    @Override
    @Caching(
            evict = {
                @CacheEvict(value = "superheroes", allEntries = true),
                @CacheEvict(value = "superheroe", key = "#id")
            }
    )
    public void delete(int id) {
        superheroDao.deleteById(id);
    }

}
