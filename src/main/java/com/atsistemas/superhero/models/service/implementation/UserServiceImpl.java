package com.atsistemas.superhero.models.service.implementation;

import com.atsistemas.superhero.models.dao.IUserDao;
import com.atsistemas.superhero.models.entity.User;
import com.atsistemas.superhero.models.service.IUserService;
import com.atsistemas.superhero.security.UserDetailsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    private static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private IUserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String user) throws UsernameNotFoundException {
        final Optional<User> retrievedUser = userDao.findById(user);

        retrievedUser.ifPresentOrElse(
                userPresent -> {
                    log.info("User " + userPresent.getUser() + " found");
                },
                () -> {
                    throw new UsernameNotFoundException("Invalid use " + user + " or password");
                }
        );

        return UserDetailsMapper.build(retrievedUser.get());
    }

}
