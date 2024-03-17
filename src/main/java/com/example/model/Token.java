package com.example.model;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Data
@Builder
@Setter
@Getter
@AllArgsConstructor
@Table(name = "secret_token")
public class Token {

  public Token () {}
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String url;

  @Column(name = "token_v")
  private String value;
}
