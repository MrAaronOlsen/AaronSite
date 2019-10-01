package com.budgetmonster.models;

import com.budgetmonster.database.operations.DBRecord;

public class Budget extends System implements Model {
  public static final String NAME = "name";

  private String name;

  private Budget(Builder builder) {
    this.name = builder.name;
  }

  @Override
  public DBRecord buildRecord() {
    return new DBRecord()
        .add(NAME, name);
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
