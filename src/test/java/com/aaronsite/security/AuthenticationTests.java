package com.aaronsite.security;

import com.aaronsite.models.User;
import com.aaronsite.server.TestServer;
import com.aaronsite.utils.exceptions.ABException;
import com.aaronsite.utils.exceptions.AuthException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Base64;

import static com.aaronsite.utils.exceptions.AuthException.Code.USER_NOT_AUTHENTICATED;

public class AuthenticationTests extends TestServer {

  @Test
  void canAuthenticateAUser_Green() throws ABException {
    User user = new User()
        .setUserName("humbug")
        .setUserPw("mom");

    insertRecord(user);

    String credentials = "Basic " + new String(Base64.getEncoder()
        .encode((user.getUserName() + ":" + "mom").getBytes()));

    String token = Authentication.basicAuth(credentials);

    Authentication.authenticate("Bearer " + token);
  }

  @Test
  void canAuthenticateAUser_Red() throws ABException {
    User user = new User()
        .setUserName("humbug")
        .setUserPw("mom");

    insertRecord(user);

    String credentials = "Basic " + new String(Base64.getEncoder()
        .encode((user.getUserName() + ":" + "mother").getBytes()));

    try {
      Authentication.basicAuth(credentials);
    } catch (AuthException e) {
      Assertions.assertEquals(USER_NOT_AUTHENTICATED, e.getCode());
    }
  }
}
