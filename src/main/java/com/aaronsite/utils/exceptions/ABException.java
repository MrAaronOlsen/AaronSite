package com.aaronsite.utils.exceptions;

public class ABException extends Exception {
  private ExceptionCode code;
  private String[] args;

  public ABException() {
    // Default Constructor
  }

  public ABException(ExceptionCode code, String[] args) {
    this.code = code;
    this.args = args;
  }

  public void setCode(ExceptionCode code) {
    this.code = code;
  }

  public ExceptionCode getCode() {
    return code;
  }

  @Override
  public String getMessage() {
    return String.format(code.getMessage(), args);
  }
}
