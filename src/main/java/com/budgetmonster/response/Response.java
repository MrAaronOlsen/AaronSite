package com.budgetmonster.response;

import java.util.LinkedList;
import java.util.List;

public class Response {
  private List<Data> data = new LinkedList<>();

  public Response(List<Data> data) {
    this.data.addAll(data);
  }

  public List<Data> getData() {
    return data;
  }
}
