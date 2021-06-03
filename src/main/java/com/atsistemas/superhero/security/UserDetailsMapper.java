package com.atsistemas.superhero.security;

import com.atsistemas.superhero.models.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Set;

public class UserDetailsMapper {

    public static UserDetails build(User user) {
        return new org.springframework.security.core.userdetails.User(user.getUser(), user.getPassword(), getAuthorities(user));
    }

    private static Set<? extends GrantedAuthority> getAuthorities(User retrievedUser) {
        Set<SimpleGrantedAuthority> authorities = Set.of(new SimpleGrantedAuthority("ROLE_" + retrievedUser.getRole()));
        return authorities;
    }

}
