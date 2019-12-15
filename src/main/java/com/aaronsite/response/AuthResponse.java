package com.aaronsite.response;

import com.aaronsite.models.AuthToken;
import com.aaronsite.models.User;
import com.aaronsite.security.Authentication;
import com.aaronsite.security.TokenHandler;
import com.aaronsite.utils.exceptions.ABException;

import java.util.List;
import java.util.Map;

public class AuthResponse {

  public static Response build(Map<String, String> header) throws ABException {
    String token = Authentication.basicAuth(header.get("authorization"));
    User user = new User(TokenHandler.parseToken(token));

    return new Response(List.of(new AuthToken(user.getId(), token)));
  }
}
