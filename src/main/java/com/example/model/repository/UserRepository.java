package com.example.model.repository;

import java.util.List;
import java.util.Optional;

import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Integer> {
  Optional<User> findByEmailAddress(String email);

  @Query("SELECT emailAddress FROM User WHERE isLocked = true")
  List<String> getAllLockedAccounts();
}
