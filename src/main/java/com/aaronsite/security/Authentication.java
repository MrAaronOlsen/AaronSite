package com.aaronsite.security;

import com.aaronsite.database.connection.DBConnection;
import com.aaronsite.database.operations.DbQuery;
import com.aaronsite.database.statements.DBWhereStmtBuilder;
import com.aaronsite.database.transaction.DBResult;
import com.aaronsite.models.User;
import com.aaronsite.utils.enums.Role;
import com.aaronsite.utils.enums.Table;
import com.aaronsite.utils.exceptions.AuthException;
import com.aaronsite.utils.exceptions.DatabaseException;
import com.aaronsite.utils.io.Logger;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.EnumSet;
import java.util.StringTokenizer;

import static com.aaronsite.utils.exceptions.AuthException.Code.BASIC_AUTH_DECODE_CHALLENGE;
import static com.aaronsite.utils.exceptions.AuthException.Code.BASIC_AUTH_MISSING_HEADER;
import static com.aaronsite.utils.exceptions.AuthException.Code.BASIC_AUTH_MISSING_PARTS;
import static com.aaronsite.utils.exceptions.AuthException.Code.BASIC_AUTH_PARSE_CHALLENGE;
import static com.aaronsite.utils.exceptions.AuthException.Code.USER_DOES_NOT_EXIST;
import static com.aaronsite.utils.exceptions.AuthException.Code.USER_NOT_AUTHENTICATED;
import static com.aaronsite.utils.exceptions.AuthException.Code.USER_NOT_AUTHORIZED;

public class Authentication {
  private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

  public static String encode(String pw) {
    return encoder.encode(pw);
  }

  public static void authenticate(String authHeader, EnumSet<Role> authRoles) throws AuthException, DatabaseException {
    if (authRoles.isEmpty()) {
      return;
    }

//    String token = authHeader.substring(7);
//
//    User authUser = new User(TokenHandler.parseToken(token));
//    User dbUser = fetchUser(authUser.getUserName());
//
//    Logger.out(dbUser.toString());
//
//    Document roles = dbUser.getRoles();

//    if (roles == null || roles.isEmpty()) {
//      throw new AuthException(USER_NOT_AUTHORIZED);
//    }

//    for (Role authRole : authRoles) {
//      Logger.out("Checking Role: " + authRole.getValue());
//
//      if (!roles.getBoolean(authRole.getValue(), false)) {
//        Logger.out("Failed Role Check: " + authRole.getValue());
//
//        throw new AuthException(USER_NOT_AUTHORIZED);
//      }
//    }
  }

  public static String basicAuth(String authHeader) throws AuthException, DatabaseException {
    if (StringUtils.isEmpty(authHeader)) {
      throw new AuthException(BASIC_AUTH_MISSING_HEADER);
    }

    String encodedCredentials = authHeader.substring(6);

    byte[] decodedBytes;
    try {
      decodedBytes = Base64.getDecoder().decode(encodedCredentials);
    } catch (AssertionError e) {
      throw new AuthException(BASIC_AUTH_DECODE_CHALLENGE, e.getMessage());
    }

    String userAndPw;
    try {
      userAndPw = new String(decodedBytes, StandardCharsets.UTF_8);
    } catch (Exception e) {
      throw new AuthException(BASIC_AUTH_PARSE_CHALLENGE, e.getMessage());
    }

    StringTokenizer tokenizer = new StringTokenizer(userAndPw, ":");

    String username = "";
    String hashpass = "";

    if (tokenizer.hasMoreTokens()) {
      username = tokenizer.nextToken().trim();
    }

    if (tokenizer.hasMoreTokens()) {
      hashpass = tokenizer.nextToken().trim();
    }

    if (StringUtils.isAnyEmpty(username, hashpass)) {
      throw new AuthException(BASIC_AUTH_MISSING_PARTS);
    }

    User user = fetchUser(username);

    if (encoder.matches(hashpass, user.getUserPw())) {
      return TokenHandler.buildToken(user);
    } else {
      throw new AuthException(USER_NOT_AUTHENTICATED);
    }
  }

  private static User fetchUser(String username) throws DatabaseException, AuthException {
    try (DBConnection conn = new DBConnection()) {
      DbQuery query = new DbQuery(conn, Table.USERS);
      query.setQuery(new DBWhereStmtBuilder(User.USER_NAME, username));

      DBResult result = query.execute();

      if (result.hasNext()) {
        return new User(result.getNext());
      } else {
        throw new AuthException(USER_DOES_NOT_EXIST, username);
      }
    }
  }
}
