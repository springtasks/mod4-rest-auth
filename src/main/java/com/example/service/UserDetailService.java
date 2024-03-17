package com.example.service;


import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.example.model.User;
import com.example.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailService implements UserDetailsService {

  @Autowired
  UserRepository userRepository;


  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findByEmailAddress(email);

    if (user.isEmpty()) {
      throw new UsernameNotFoundException("User Not Found ! ");
    }
    Set<GrantedAuthority> authorities = user.get().getAuthorities().stream()
        .map((authority) -> new SimpleGrantedAuthority(authority.getRole()))
        .collect(Collectors.toSet());
    return new AuthUserDetails(user.get(), authorities);
  }
}
