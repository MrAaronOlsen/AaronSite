package com.aaronsite.security;

import com.aaronsite.models.User;
import com.aaronsite.server.TestServer;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.aaronsite.models.User.USER_NAME;
import static com.aaronsite.models.User.USER_PW;

public class TokenHandlerTests extends TestServer {

  @Test
  void canBuildAndParseAToken() {
    User user = new User()
        .setUserName("test")
        .setUserPw("1234");

    String token = TokenHandler.buildToken(user);

    Claims claims = TokenHandler.parseToken(token);

    Assertions.assertEquals("test", claims.get(USER_NAME));
    Assertions.assertEquals("1234", claims.get(USER_PW));
  }
}
