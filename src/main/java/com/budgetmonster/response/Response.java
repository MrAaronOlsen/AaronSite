package com.budgetmonster.response;

import java.util.LinkedList;
import java.util.List;

public class Response {
  private List<ResponseData> data = new LinkedList<>();

  public Response(List<ResponseData> data) {
    this.data.addAll(data);
  }

  public List<ResponseData> getData() {
    return data;
  }
}
