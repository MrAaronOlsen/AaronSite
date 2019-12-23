package com.aaronsite.database.statements;

import org.apache.commons.lang3.StringUtils;

public class DBSelectStmtBuilder {
  private static final String SELECT = "SELECT";
  private static final String FROM = "FROM";

  private String fields;

  public DBSelectStmtBuilder() {
    // Default
  }

  public DBSelectStmtBuilder(String fields) {
    if (StringUtils.isEmpty(fields)) {
      fields = "*";
    }

    this.fields = normalize(fields);
  }

  public DBSelectStmtBuilder asCount() {
    fields = "COUNT(*)";
    return this;
  }

  private String normalize(String fields) {
    fields = fields.replaceAll("\\s", "");
    fields = fields.replaceAll(",", ", ");

    return fields;
  }

  @Override
  public String toString() {
    return String.format("%s %s %s", SELECT, fields, FROM);
  }
}
