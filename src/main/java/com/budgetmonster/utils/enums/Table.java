package com.budgetmonster.utils.enums;

import com.budgetmonster.utils.constants.Tables;

public enum Table {
  BUDGET(Tables.BUDGETS),
  TRANSACTION(Tables.TRANSACTIONS),
  PERIOD(Tables.PERIODS),
  BUDGET_PERIODS(Tables.BUDGET_PERIODS),
  BUDGET_PERIOD_TRANSACTION(Tables.BUDGET_PERIOD_TRANSACTIONS);

  private String name;

  Table(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
