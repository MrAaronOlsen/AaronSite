package com.budgetmonster.database.operations;

import java.util.HashMap;
import java.util.Map;

public class DBQueryBuilder {
  private Map<String, String> queryMap = new HashMap<>();

  public DBQueryBuilder add(String name, String value) {
    queryMap.put(name, value);
    return this;
  }

  public DBQueryBuilder addAll(Map<String, String> params) {
    queryMap.putAll(params);
    return this;
  }

  @Override
  public String toString() {
    if (queryMap.isEmpty()) {
      return "";
    }

    return "WHERE " + queryMap.entrySet().stream()
        .map(set -> set.getKey() + "='" + set.getValue() + "'").reduce((con, acu) -> con + " AND " + acu).get();
  }
}
