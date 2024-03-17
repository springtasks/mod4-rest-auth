package com.example.service;

import com.example.model.Token;
import com.example.model.repository.TokenRepository;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

  @Autowired
  TokenRepository tokenRepository;

  public String save(String input) {
    Token newSecretToken = Token.builder()
    .value(input)
    .url(UUID.randomUUID().toString()).build();
    tokenRepository.save(newSecretToken);
    return newSecretToken.getUrl();
  }

  public String getByUrl(String url) {
    Token token = tokenRepository.getByUrl(url);

    if(token != null) {
      tokenRepository.deleteById(token.getId());
      return token.getValue();
    }
    else {
      return null;
    }

  }
}
