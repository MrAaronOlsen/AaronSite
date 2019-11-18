package com.aaronsite.models;

import com.aaronsite.database.transaction.DBRecord;
import com.aaronsite.utils.enums.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;

import static com.aaronsite.utils.enums.Table.TEST_SIMPLE;

public class TestSimple extends System implements Model {
  public static final String NAME = "name";
  public static final String TEXT = "text";

  private String name;
  private String text;

  public TestSimple() {
    // Default Constructor
  }

  public TestSimple(DBRecord record) {
    this.id = record.getId();
    this.name = record.get(NAME);
    this.text = record.get(TEXT);
  }

  public String getName() {
    return name;
  }

  public TestSimple setName(String name) {
    this.name = name;
    return this;
  }

  public TestSimple setText(String text) {
    this.text = text;
    return this;
  }

  @Override
  public DBRecord buildRecord() {
    return new DBRecord()
        .addNonNull(NAME, name)
        .addNonNull(TEXT, text);
  }

  @Override
  @JsonIgnore
  public Table getTable() {
    return TEST_SIMPLE;
  }

  @Override
  public String toString() {
    return "Table: " + getTable() + "\n"
        + ID + ": " + id + "\n"
        + NAME + ": " + name + "\n"
        + TEXT + ": " + text;
  }
}
