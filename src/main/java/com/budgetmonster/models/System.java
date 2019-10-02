package com.budgetmonster.models;

public class System {
  public static final String ID = "id";

  protected String id;

  public String getId() {
    return id;
  }

  // TODO - this does not belong here.
  String safeValue(String value) {
    return "'" + value + "'";
  }
}
