package com.budgetmonster.response;

public class ErrorResponse implements ResponseData {
  private Throwable error;

  public ErrorResponse(Throwable e) {
    this.error = e;
  }

  public Throwable getError() {
    return error;
  }
}
