package com.aaronsite.utils.exceptions;

public class ResponseException extends ABException {
  public enum Code implements ExceptionCode {
    INVALID_RESPONSE_TABLE("Table [%s] does not exist or is not supported."),
    INVALID_REQUEST_TYPE("Invalid request type %s."),
    MALFORMED_REQUEST_BODY("Request body could not be converted to a valid record. ERROR: %s");

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
      return null;
    }
  }

  public ResponseException(ResponseException.Code code, String... args) {
    super(code, args);
  }
}
