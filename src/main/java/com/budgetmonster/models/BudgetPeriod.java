package com.budgetmonster.models;

import com.budgetmonster.database.operations.DBRecord;
import com.budgetmonster.utils.annotations.Column;
import com.budgetmonster.utils.enums.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;

import static com.budgetmonster.utils.enums.ColumnType.CURRENCY;
import static com.budgetmonster.utils.enums.ColumnType.TABLE_LOOKUP;
import static com.budgetmonster.utils.enums.Table.BUDGETS;
import static com.budgetmonster.utils.enums.Table.BUDGET_PERIODS;
import static com.budgetmonster.utils.enums.Table.PERIODS;

public class BudgetPeriod extends System implements Model {
  public static final String BUDGET_ID = "budget_id";
  public static final String PERIOD_ID = "period_id";
  public static final String AMOUNT = "amount";

  @Column(type = TABLE_LOOKUP, table = BUDGETS)
  private String budgetId;

  @Column(type = TABLE_LOOKUP, table = PERIODS)
  private String periodId;

  @Column(type = CURRENCY)
  private String amount;

  public BudgetPeriod() {
    // Default Constructor
  }

  public BudgetPeriod(DBRecord record) {
    this.id = record.getId();
    this.budgetId = record.get(BUDGET_ID);
    this.periodId = record.get(PERIOD_ID);
    this.amount = record.get(AMOUNT);
  }

  public String getAmount() {
    return amount;
  }

  public String getBudgetId() {
    return budgetId;
  }

  public String getPeriodId() {
    return periodId;
  }

  public BudgetPeriod setAmount(String amount) {
    this.amount = amount;
    return this;
  }

  public BudgetPeriod setBudgetId(String budgetId) {
    this.budgetId = budgetId;
    return this;
  }

  public BudgetPeriod setPeriodId(String periodId) {
    this.periodId = periodId;
    return this;
  }

  @Override
  public DBRecord buildRecord() {
    return new DBRecord()
        .add(BUDGET_ID, budgetId)
        .add(PERIOD_ID, periodId)
        .add(AMOUNT, amount);
  }

  @Override
  @JsonIgnore
  public Table getTable() {
    return BUDGET_PERIODS;
  }

  @Override
  public String toString() {
    return "Table: " + getTable() + "\n"
        + ID + ": " + id + "\n"
        + BUDGET_ID + ": " + budgetId + "\n"
        + PERIOD_ID + ": " + periodId + "\n"
        + AMOUNT + ": " + amount + "\n";
  }
}
