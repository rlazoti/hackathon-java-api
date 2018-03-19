package com.rodrigolazoti.auth.services;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rodrigolazoti.auth.models.User;
import com.rodrigolazoti.auth.repository.UserRepository;

@Service
@Transactional
public class UserDetailsService
    implements org.springframework.security.core.userdetails.UserDetailsService {

  UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByUsername(username)
        .map(user -> {
          return new org.springframework.security.core.userdetails.User(
              user.getUsername(),
              user.getPassword(), getGrantedAuthorities(user));
        })
        .orElseThrow(() -> new UsernameNotFoundException("User " + username + " Not found"));
  }

  @Autowired
  public void setUserRepository(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  private Collection<GrantedAuthority> getGrantedAuthorities(User user) {
    return user
        .getAuthorities()
        .stream()
        .map(authority -> new SimpleGrantedAuthority(authority.getName()))
        .collect(Collectors.toList());
  }

}
