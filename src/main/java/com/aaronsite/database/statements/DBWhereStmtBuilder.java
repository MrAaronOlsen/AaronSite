package com.aaronsite.database.statements;

import java.util.LinkedList;
import java.util.Map;

import static com.aaronsite.models.System.ID;

public class DBWhereStmtBuilder {
  private LinkedList<String> columns = new LinkedList<>();
  private LinkedList<String> values = new LinkedList<>();

  public DBWhereStmtBuilder(String key, String value) {
    columns.add(key);
    values.add(value);
  }

  public DBWhereStmtBuilder(String id) {
    columns.add(ID);
    values.add(id);
  }

  public DBWhereStmtBuilder(Map<String, String> params) {
    params.forEach((k, v) -> {
      columns.add(k);
      values.add(v);
    });
  }

  public DBWhereStmtBuilder add(String key, String value) {
    columns.add(key);
    values.add(value);

    return this;
  }

  public int size() {
    return columns.size();
  }

  public String getColumn(int i) {
    return columns.get(i);
  }

  public String getValue(int i) {
    return values.get(i);
  }

  @Override
  public String toString() {
    if (columns.isEmpty()) {
      return "";
    }

    return "WHERE " + columns.stream()
        .map(col -> col + "=?")
        .reduce((con, acu) -> con + " AND " + acu).get();
  }
}
