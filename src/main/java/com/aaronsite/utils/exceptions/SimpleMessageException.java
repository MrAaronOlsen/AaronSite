package com.aaronsite.utils.exceptions;

public class SimpleMessageException extends ABException {
  private String message;

  public SimpleMessageException(String message) {
    this.message = message;
  }

  @Override
  public String getMessage() {
    return message;
  }
}
