package com.atsistemas.superhero.models.dao;

import com.atsistemas.superhero.models.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface IUserDao extends CrudRepository<User, String> {

}
