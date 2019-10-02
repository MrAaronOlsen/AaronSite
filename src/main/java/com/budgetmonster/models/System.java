package com.budgetmonster.models;

public class System {
  public static final String id = "id";

  String safeValue(String value) {
    return "'" + value + "'";
  }
}
