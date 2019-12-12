package com.aaronsite.models;

import com.aaronsite.response.ResponseData;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthToken implements ResponseData {
  private String token;

  public AuthToken(String token) {
    this.token = token;
  }

  @JsonDeserialize
  public String getToken() {
    return token;
  }
}
