package com.aaronsite.utils.exceptions;

public class ResponseException extends ABException {
  public enum Code {
    INVALID_RESPONSE_TABLE("Table [%s] does not exist or is not supported."),
    INACTIVE_TABLE("Table [%s] is not currently active."),
    INVALID_REQUEST_TYPE("Invalid request type %s."),
    MALFORMED_REQUEST_BODY("Request body could not be converted to a valid record. ERROR: %s");

    private String message;

    Code(String message) {
      this.message = message;
    }

    public String format(String... args) {
      return String.format(message, args);
    }
  }

  private ResponseException.Code code;
  private String[] args;

  public ResponseException(ResponseException.Code code, String... args) {
    this.code = code;
    this.args = args;
  }

  @Override
  public String getMessage() {
    return code.format(args);
  }
}
