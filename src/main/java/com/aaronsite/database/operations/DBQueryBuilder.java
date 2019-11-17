package com.aaronsite.database.operations;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.aaronsite.models.System.ID;

public class DBQueryBuilder {
  private Map<String, String> queryMap;

  public DBQueryBuilder() {
    queryMap = new HashMap<>();
  }

  public DBQueryBuilder(String key, String value) {
    queryMap = Collections.singletonMap(key, value);
  }

  public DBQueryBuilder(String id) {
    queryMap = Collections.singletonMap(ID, id);
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
