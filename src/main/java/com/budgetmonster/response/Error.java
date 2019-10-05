package com.budgetmonster.response;

public class Error implements Data {
  private Throwable error;

  public Error(Throwable e) {
    this.error = e;
  }

  public Throwable getError() {
    return error;
  }
}
