package com.aaronsite.database.statements;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.aaronsite.models.System.ID;

public class DBQueryStmtBuilder {
  private Map<String, String> queryMap;

  public DBQueryStmtBuilder() {
    queryMap = new HashMap<>();
  }

  public DBQueryStmtBuilder(String key, String value) {
    queryMap = Collections.singletonMap(key, value);
  }

  public DBQueryStmtBuilder(String id) {
    queryMap = Collections.singletonMap(ID, id);
  }

  public DBQueryStmtBuilder(Map<String, String> params) {
    queryMap = params;
  }

  public DBQueryStmtBuilder add(String name, String value) {
    queryMap.put(name, value);
    return this;
  }

  public String build() {
    if (queryMap == null || queryMap.isEmpty()) {
      return "";
    }

    return "WHERE " + queryMap.entrySet().stream()
        .map(set -> set.getKey() + "='" + set.getValue() + "'").reduce((con, acu) -> con + " AND " + acu).get();
  }

  @Override
  public String toString() {
    return build();
  }
}
