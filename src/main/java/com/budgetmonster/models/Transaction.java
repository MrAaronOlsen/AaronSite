package com.budgetmonster.models;

import com.budgetmonster.database.operations.DBRecord;
import com.budgetmonster.utils.enums.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;

import static com.budgetmonster.utils.enums.Table.TRANSACTION;

public class Transaction extends System implements Model {
  public static final String AMOUNT = "amount";
  public static final String VENDOR = "vendor";
  public static final String DATE = "date";

  private String amount;
  private String vendor;
  private String date;

  public Transaction() {
    // Default constructor
  }

  public Transaction(DBRecord record) {
    this.id = record.getId();
    this.amount = record.get(AMOUNT);
    this.vendor = record.get(VENDOR);
    this.date = record.get(DATE);
  }

  public String getAmount() {
    return amount;
  }

  public String getVendor() {
    return vendor;
  }

  public String getDate() {
    return date;
  }

  public Transaction setAmount(String amount) {
    this.amount = amount;
    return this;
  }

  public Transaction setVendor(String vendor) {
    this.vendor = vendor;
    return this;
  }

  public Transaction setDate(String date) {
    this.date = date;
    return this;
  }

  @Override
  public DBRecord buildRecord() {
    return new DBRecord()
        .add(AMOUNT, amount)
        .add(VENDOR, vendor)
        .add(DATE, date);
  }

  @Override
  @JsonIgnore
  public Table getTable() {
    return TRANSACTION;
  }

  @Override
  public String toString() {
    return "Table: " + getTable() + "\n"
        + ID + ": " + id + "\n"
        + AMOUNT + ": " + amount + "\n"
        + VENDOR + ": " + vendor + "\n"
        + DATE + ": " + date + "\n";
  }
}
