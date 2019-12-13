package com.aaronsite.security;

import com.aaronsite.models.User;
import com.aaronsite.utils.enums.ConfigArg;
import com.aaronsite.utils.system.ConfigProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Base64;

public class TokenHandler {

  public static String buildToken(User user) {
    return Jwts.builder().setClaims(user.buildRecord().asDoc()).signWith(getSecret()).compact();
  }

  public static Claims parseToken(String token) {
    return Jwts.parser().setSigningKey(getSecret()).parseClaimsJws(token).getBody();
  }

  private static SecretKey getSecret() {
    return Keys.hmacShaKeyFor(Base64.getEncoder().encode(ConfigProperties.getValue(ConfigArg.API_KEY).getBytes()));
  }


}
