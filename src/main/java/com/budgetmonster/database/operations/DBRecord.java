package com.budgetmonster.database.operations;

import com.budgetmonster.database.metadata.ColumnMetadata;
import com.budgetmonster.database.metadata.ResultMetadata;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class DBRecord {
  private Map<String, String> record = new HashMap<>();

  public DBRecord() {
    // default public constructor
  }

  DBRecord(ResultMetadata resultMetadata, ResultSet result) throws SQLException {
    for (ColumnMetadata column : resultMetadata.getColumns()) {
      String value = result.getString(column.getName());
      record.put(column.getName(), value);
    }
  }

  public DBRecord add(String column, String value) {
    record.put(column, value);
    return this;
  }

  public String get(String column) {
    return record.get(column);
  }

  public boolean has(String column) {
    return record.containsKey(column);
  }

  String getSqlColumns() {
    return "(" + String.join(", ", record.keySet()) + ")";

  }

  String getSqlValues() {
    return  "VALUES(" + String.join(", ", record.values()) + ")";
  }
}
