package com.budgetmonster.models;

import com.budgetmonster.database.operations.DBRecord;
import com.budgetmonster.utils.enums.Table;
import com.fasterxml.jackson.annotation.JsonIgnore;

import static com.budgetmonster.utils.enums.Table.BUDGETS;
import static com.budgetmonster.utils.enums.Table.BUDGET_PERIOD_TRANSACTIONS;

public class BudgetPeriodTransaction extends System implements Model {
  public static final String BUDGET_PERIOD_ID = "budget_period_id";
  public static final String TRANSACTION_ID = "transaction_id";
  public static final String AMOUNT = "amount";

  private String budgetPeriodId;
  private String transactionId;
  private String amount;

  public BudgetPeriodTransaction() {
    // Default Constructor
  }

  public BudgetPeriodTransaction(DBRecord record) {
    this.id = record.getId();
    this.budgetPeriodId = record.get(BUDGET_PERIOD_ID);
    this.transactionId = record.get(TRANSACTION_ID);
    this.amount = record.get(AMOUNT);
  }

  public String getAmount() {
    return amount;
  }

  public String getBudgetPeriodId() {
    return budgetPeriodId;
  }

  public String getTransactionId() {
    return transactionId;
  }

  public BudgetPeriodTransaction setAmount(String amount) {
    this.amount = amount;
    return this;
  }

  public BudgetPeriodTransaction setBudgetPeriodId(String budgetPeriodId) {
    this.budgetPeriodId = budgetPeriodId;
    return this;
  }

  public BudgetPeriodTransaction setTransactionId(String transactionId) {
    this.transactionId = transactionId;
    return this;
  }

  @Override
  public DBRecord buildRecord() {
    return new DBRecord()
        .add(BUDGET_PERIOD_ID, budgetPeriodId)
        .add(TRANSACTION_ID, transactionId)
        .add(AMOUNT, amount);
  }

  @Override
  @JsonIgnore
  public Table getTable() {
    return BUDGET_PERIOD_TRANSACTIONS;
  }

  @Override
  public String toString() {
    return "Table: " + getTable() + "\n"
        + ID + ": " + id + "\n"
        + BUDGET_PERIOD_ID + ": " + budgetPeriodId + "\n"
        + TRANSACTION_ID + ": " + transactionId + "\n"
        + AMOUNT + ": " + amount + "\n";
  }
}
