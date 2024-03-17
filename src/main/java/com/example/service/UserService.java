package com.example.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.example.model.User;
import com.example.model.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  UserRepository userRepository;

  public void resetInvalidAttemptsCounter(String email) {
  Optional<User> userResult = userRepository.findByEmailAddress(email);
  if (userResult.isPresent()) {
    User user = userResult.get();
    user.setFailureCounter(0);
    userRepository.save(user);
  }
  }

  public Optional<User> findByEmailAddress(String email) {
    return userRepository.findByEmailAddress(email);
  }

  public void resetCounterIfExpired(User user) {
    if (LocalDateTime.now().minusSeconds(10000).isAfter(user.getLockDate())) {
      User updatedUser = User.builder()
              .id(user.getId())
              .failureCounter(user.getFailureCounter())
              .isLocked(false)
              .lockDate(null)
              .password(user.getPassword())
              .emailAddress(user.getEmailAddress())
              .build();

      userRepository.save(user);
    }
  }

  public void save(User user) {
    userRepository.save(user);
  }

  public List<String> getAllLockedAccountsEmail() {
    return userRepository.getAllLockedAccounts();
  }
}
