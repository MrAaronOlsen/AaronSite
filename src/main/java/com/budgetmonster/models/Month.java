package com.budgetmonster.models;

import com.budgetmonster.database.operations.DBRecord;
import com.budgetmonster.utils.enums.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;

import static com.budgetmonster.utils.enums.Table.BUDGETS;
import static com.budgetmonster.utils.enums.Table.MONTHS;

public class Month extends System implements Model {
  public static final String SHORT_NAME = "name_short";
  public static final String LONG_NAME = "name_long";
  public static final String NUMBER = "number";


  private String shortName;
  private String longName;
  private String number;

  public Month() {
    // Default Constructor
  }

  public Month(DBRecord record) {
    this.id = record.getId();
    this.shortName = record.get(SHORT_NAME);
    this.longName = record.get(LONG_NAME);
    this.number = record.get(NUMBER);
  }

  public String getShortName() {
    return shortName;
  }

  public String getLongName() {
    return longName;
  }

  public String getNumber() {
    return number;
  }

  public Month setShortName(String shortName) {
    this.shortName = shortName;
    return this;
  }

  public Month setLongName(String longName) {
    this.longName = longName;
    return this;
  }

  public Month setNumber(String number) {
    this.number = number;
    return this;
  }

  @Override
  public DBRecord buildRecord() {
    return new DBRecord()
        .add(SHORT_NAME, shortName)
        .add(LONG_NAME, longName)
        .add(NUMBER, number);
  }

  @Override
  @JsonIgnore
  public Table getTable() {
    return MONTHS;
  }

  @Override
  public String toString() {
    return "Table: " + getTable() + "\n"
        + ID + ": " + id + "\n"
        + SHORT_NAME + ": " + shortName + "\n"
        + LONG_NAME + ": " + longName + "\n"
        + NUMBER + ": " + number;
  }
}
