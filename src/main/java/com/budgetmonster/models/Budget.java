package com.budgetmonster.models;

import com.budgetmonster.database.operations.DBRecord;
import com.budgetmonster.utils.enums.Table;

import static com.budgetmonster.utils.enums.Table.BUDGET;

public class Budget extends System implements Model {
  public static final String NAME = "name";

  private String name;

  private Budget(Builder builder) {
    this.name = builder.name;
  }

  public Budget(DBRecord record) {
    this.id = record.getId();
    this.name = record.get(NAME);
  }

  public String getName() {
    return name;
  }

  @Override
  public DBRecord buildRecord() {
    return new DBRecord()
        .add(NAME, safeValue(name));
  }

  @Override
  public Table getTable() {
    return BUDGET;
  }

  @Override
  public String toString() {
    return "Table: " + getTable() + "\n" + NAME + ": " + name;
  }

  public static class Builder {
    private String name;

    public Builder setName(String name) {
      this.name = name;
      return this;
    }

    public Budget build() {
      return new Budget(this);
    }
  }
}
