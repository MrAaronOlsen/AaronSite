package com.aaronsite.database.transaction;

import com.aaronsite.database.metadata.ColumnMetadata;
import com.aaronsite.database.metadata.ResultMetadata;
import com.aaronsite.utils.exceptions.DatabaseException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;

import static com.aaronsite.models.System.ID;
import static com.aaronsite.utils.exceptions.DatabaseException.Code.FAILED_TO_BUILD_RESULT_DATA;

public class DBRecord {
  private Map<String, String> record = new LinkedHashMap<>();

  private LinkedList<String> columns = new LinkedList<>();
  private LinkedList<String> values = new LinkedList<>();

  public DBRecord() {
    // Default Constructor
  }

  public DBRecord(Map<String, String> body) {
    columns.addAll(body.keySet());
    values.addAll(body.values());

    this.record.putAll(body);
  }

  public DBRecord(ResultMetadata resultMetadata, ResultSet result) throws DatabaseException {
    try {
      for (ColumnMetadata column : resultMetadata.getColumns()) {
        String value = result.getString(column.getName());

        add(column.getName(), value);
      }
    } catch (SQLException e) {
      throw new DatabaseException(FAILED_TO_BUILD_RESULT_DATA).sqlEx(e);
    }
  }

  public DBRecord add(String column, String value) {
    if (value != null) {
      record.put(column, value);

      columns.add(column);
      values.add(value);
    }

    return this;
  }

  public String getId() {
    return record.get(ID);
  }

  public String get(String column) {
    return record.get(column);
  }

  public String getColumn(int index) {
    return columns.get(index);
  }

  public String getValue(int index) {
    return values.get(index);
  }

  public LinkedList<String> getColumns() {
    return columns;
  }

  public LinkedList<String> getValues() {
    return values;
  }

  public int size() {
    return columns.size();
  }

  public boolean contains(String column) {
    return record.containsKey(column);
  }

  @Override
  public String toString() {
    StringBuilder string = new StringBuilder();

    for (Map.Entry<String, String> entry : record.entrySet()) {
      string.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
    }

    return string.toString();
  }
}
