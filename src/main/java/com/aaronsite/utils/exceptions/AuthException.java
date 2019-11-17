package com.aaronsite.utils.exceptions;

public class AuthException extends ABException {
  public enum Code {
    USER_NOT_AUTHENTICATED("User is not authenticated."),
    USER_DOES_NOT_EXIST("User [%s] does not exist."),
    BASIC_AUTH_DECODE_CHALLENGE("Failed to decode Basic Authorization header. ERROR: %s"),
    BASIC_AUTH_PARSE_CHALLENGE("Failed to parse user:pw from Basic Authorization header. ERROR: %s"),
    BASIC_AUTH_MISSING_PARTS("Basic Authorization header is missing username or password (username:password.");

    private String message;

    Code(String message) {
      this.message = message;
    }

    public String format(String... args) {
      return String.format(message, args);
    }
  }

  private AuthException.Code code;
  private String[] args;

  public AuthException(AuthException.Code code, String... args) {
    this.code = code;
    this.args = args;
  }

  @Override
  public String getMessage() {
    return code.format(args);
  }
}
