package com.aaronsite.database.operations;

import com.aaronsite.database.metadata.ColumnMetadata;
import com.aaronsite.database.metadata.ResultMetadata;
import com.aaronsite.utils.exceptions.DatabaseException;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.aaronsite.models.System.ID;
import static com.aaronsite.utils.exceptions.DatabaseException.Code.FAILED_TO_BUILD_RESULT_DATA;

public class DBRecord {
  private Map<String, String> record = new LinkedHashMap<>();

  public DBRecord() {
    // default public constructor
  }

  public DBRecord(Map<String, String> body) {
    this.record.putAll(body);
  }

  public DBRecord(Document body) {
    for (Map.Entry<String, Object> entry : body.entrySet()) {
      String field = entry.getKey();
      this.record.put(field, body.getString(field));
    }
  }

  DBRecord(ResultMetadata resultMetadata, ResultSet result) throws DatabaseException {
    try {
      for (ColumnMetadata column : resultMetadata.getColumns()) {
        String value = result.getString(column.getName());
        record.put(column.getName(), value);
      }
    } catch (SQLException e) {
      throw new DatabaseException(FAILED_TO_BUILD_RESULT_DATA).sqlEx(e);
    }
  }

  public DBRecord add(String column, String value) {
    record.put(column, value);
    return this;
  }

  public String get(String column) {
    return record.get(column);
  }

  public String getId() {
    return record.get(ID);
  }

  public boolean has(String column) {
    return record.containsKey(column);
  }

  String getSqlInsert() {
     return "(" + String.join(", ", record.keySet()) + ") VALUES(" + String.join(", ", record.values()) + ")";
  }

  String getSqlUpdate() {
    return "SET " + record.entrySet().stream()
        .filter(e -> e.getValue() != null)
        .map((e) -> e.getKey() + "=" + safeValue(e.getValue()))
        .reduce((e, a) -> e + ", " + a).orElse("");
  }

  private String safeValue(String value) {
    return "'" + value + "'";
  }

  public String toJson() {
    List<String> elements = new ArrayList<>();
    String json = "{";

    for (Map.Entry<String, String> entry : record.entrySet()) {
      elements.add("\"" + entry.getKey() + "\": \"" + entry.getValue() + "\"");
    }

    json += StringUtils.join(elements, ", ");
    json += "}";

    return json;
  }

  public Document toDocument() {
    Document doc = new Document();

    for (Map.Entry<String, String> entry : record.entrySet()) {
      doc.put(entry.getKey(), entry.getValue());
    }

    return doc;
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
