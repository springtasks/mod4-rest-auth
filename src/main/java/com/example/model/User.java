package com.example.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

import lombok.*;

@Data
@Entity
@Builder
@Setter
@Getter
@AllArgsConstructor
@Table(name = "USER")
public class User {

  public User () {}
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false, unique = true)
  private String emailAddress;

  @Column(nullable = false)
  private String password;

  private boolean isLocked;

  private int failureCounter;

  private LocalDateTime lockDate;

  @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
  private Set<Authority> authorities;
}
