package com.budgetmonster.models;

import com.budgetmonster.database.operations.DBRecord;
import com.budgetmonster.utils.enums.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;

import static com.budgetmonster.utils.enums.Table.BUDGET;

public class Budget extends System implements Model {
  public static final String NAME = "name";

  private String name;

  public Budget() {
    // Default Constructor
  }

  public Budget(DBRecord record) {
    this.id = record.getId();
    this.name = record.get(NAME);
  }

  public Budget setName(String name) {
    this.name = name;
    return this;
  }

  public String getName() {
    return name;
  }

  @Override
  public DBRecord buildRecord() {
    return new DBRecord()
        .add(NAME, name);
  }

  @Override
  @JsonIgnore
  public Table getTable() {
    return BUDGET;
  }

  @Override
  public String toString() {
    return "Table: " + getTable() + "\n"
        + ID + ": " + id + "\n"
        + NAME + ": " + name;
  }
}
