package com.budgetmonster.database.operations;

import java.util.HashMap;
import java.util.Map;

public class DBQueryBuilder {
  private Map<String, String> queryMap = new HashMap<>();

  public DBQueryBuilder add(String name, String value) {
    queryMap.put(name, value);
    return this;
  }

  @Override
  public String toString() {
    return "WHERE " + queryMap.entrySet().stream()
        .map(set -> set.getKey() + "='" + set.getValue() + "'").reduce((con, acu) -> con + " AND " + acu).get();
  }
}
