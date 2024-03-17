package com.example.model.repository;

import com.example.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TokenRepository extends JpaRepository<Token, Integer> {

  @Query("SELECT token_v FROM secret_token WHERE url = :url ")
  Token getByUrl(@Param("url") String url);
}
