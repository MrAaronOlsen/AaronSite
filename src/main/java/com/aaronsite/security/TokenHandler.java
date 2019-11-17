package com.aaronsite.security;

import com.aaronsite.database.operations.DBRecord;
import com.aaronsite.models.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.bson.Document;

import javax.crypto.SecretKey;

public class TokenHandler {
  private static SecretKey secret = Keys.secretKeyFor(SignatureAlgorithm.HS512);

  public static String getToken(User user) {
    Document publicClaims = new Document("id", user.getId())
        .append("username", user.getUserName())
        .append("roles", "Admin");

    return Jwts.builder().setClaims(publicClaims).signWith(secret).compact();
  }
}
