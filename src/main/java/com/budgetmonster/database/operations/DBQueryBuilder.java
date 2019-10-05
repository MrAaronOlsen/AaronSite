package com.budgetmonster.database.operations;

import java.util.HashMap;
import java.util.Map;

public class DBQueryBuilder {
  private Map<String, String> queryMap;

  public DBQueryBuilder() {
    queryMap = new HashMap<>();
  }

  public DBQueryBuilder(Map<String, String> params) {
    queryMap = params;
  }

  public DBQueryBuilder add(String name, String value) {
    queryMap.put(name, value);
    return this;
  }

  @Override
  public String toString() {
    if (queryMap == null || queryMap.isEmpty()) {
      return "";
    }

    return "WHERE " + queryMap.entrySet().stream()
        .map(set -> set.getKey() + "='" + set.getValue() + "'").reduce((con, acu) -> con + " AND " + acu).get();
  }
}
