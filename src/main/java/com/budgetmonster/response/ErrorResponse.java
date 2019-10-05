package com.budgetmonster.response;

import java.util.ArrayList;
import java.util.List;

public class ErrorResponse {

  public static List<Data> build(Throwable e) {
    List<Data> errors = new ArrayList<>();
    errors.add(new Error(e));

    return errors;
  }
}
