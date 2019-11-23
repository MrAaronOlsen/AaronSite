package com.aaronsite.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

public class Response {
  private List<ResponseData> data;

  public Response(List<ResponseData> data) {
    this.data = data;
  }

  @JsonDeserialize
  public List<ResponseData> getData() {
    return data;
  }
}
