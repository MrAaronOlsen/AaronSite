package com.aaronsite.utils.exceptions;

public class AuthException extends ABException {
  public enum Code implements ExceptionCode {
    USER_NOT_AUTHENTICATED("User is not authenticated."),
    USER_NOT_AUTHORIZED("User is not authorized."),
    USER_DOES_NOT_EXIST("User [%s] does not exist."),
    BASIC_AUTH_MISSING_HEADER("Header authorization missing."),
    BASIC_AUTH_DECODE_CHALLENGE("Failed to decode Basic Authorization header. ERROR: %s"),
    BASIC_AUTH_PARSE_CHALLENGE("Failed to parse user:pw from Basic Authorization header. ERROR: %s"),
    BASIC_AUTH_MISSING_PARTS("Basic Authorization header is missing username or password (username:password.");

    private String message;

    Code(String message) {
      this.message = message;
    }

    @Override
    public String getMessage() {
      return message;
    }

    @Override
    public String getName() {
      return this.name();
    }
  }

  public AuthException(AuthException.Code code, String... args) {
    super(code, args);
  }
}
