package com.budgetmonster.models;

import com.budgetmonster.database.operations.DBRecord;
import com.budgetmonster.utils.enums.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;

import static com.budgetmonster.utils.enums.Table.BUDGETS;
import static com.budgetmonster.utils.enums.Table.PERIODS;

public class Period extends System implements Model {
  public static final String YEAR = "year";
  public static final String MONTH_NUMBER = "month_number";

  private String year;
  private String monthNumber;

  public Period() {
    // Default Constructor
  }

  public Period(DBRecord record) {
    this.id = record.getId();
    this.year = record.get(YEAR);
    this.monthNumber = record.get(MONTH_NUMBER);
  }

  public String getYear() {
    return year;
  }

  public String getMonthNumber() {
    return monthNumber;
  }

  public Period setYear(String year) {
    this.year = year;
    return this;
  }

  public Period setMonthNumber(String monthNumber) {
    this.monthNumber = monthNumber;
    return this;
  }

  @Override
  public DBRecord buildRecord() {
    return new DBRecord()
        .add(YEAR, year)
        .add(MONTH_NUMBER, monthNumber);
  }

  @Override
  @JsonIgnore
  public Table getTable() {
    return PERIODS;
  }

  @Override
  public String toString() {
    return "Table: " + getTable() + "\n"
        + ID + ": " + id + "\n"
        + YEAR + ": " + year + "\n"
        + MONTH_NUMBER + ": " + monthNumber;
  }
}
