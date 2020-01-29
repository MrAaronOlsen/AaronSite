package com.aaronsite.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse implements ResponseData {
  private String error;

  public ErrorResponse(Throwable e) {
    this.error = e.getMessage();
  }

  @JsonDeserialize
  public String getError() {
    return error;
  }
}
