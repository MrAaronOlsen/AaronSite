package com.budgetmonster.testutils.dataseeder;

import com.budgetmonster.models.Budget;
import com.budgetmonster.models.BudgetPeriod;
import com.budgetmonster.models.Period;
import com.budgetmonster.utils.exceptions.ABException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BudgetPeriodSeeder extends DataSeeder {
  private final Map<String, BudgetPeriod> STAGED_BUDGET_PERIODS = new HashMap<>();
  private final Map<String, BudgetPeriod> BUDGET_PERIODS = new HashMap<>();

  @Override
  public void seed() throws ABException {
    for (Map.Entry<String, BudgetPeriod> staged : STAGED_BUDGET_PERIODS.entrySet()) {
      String key = staged.getKey();
      BudgetPeriod budgetPeriod = staged.getValue();

      BUDGET_PERIODS.put(key, new BudgetPeriod(insertRecord(budgetPeriod)));
    }
  }

  @Override
  public BudgetPeriod get(String yearMonthName) {
    return BUDGET_PERIODS.get(yearMonthName);
  }

  @Override
  public Map<String, BudgetPeriod> getMap() {
    return BUDGET_PERIODS;
  }

  @Override
  public List<BudgetPeriod> getList() {
    return new ArrayList<>(BUDGET_PERIODS.values());
  }

  public BudgetPeriodSeeder stage(Period period, List<Budget> budgets) {

    for (Budget budget : budgets) {
      BudgetPeriod budgetPeriodIn = new BudgetPeriod()
          .setPeriodId(period.getId())
          .setBudgetId(budget.getId())
          .setAmount("0");

      String key = period.getYear() + period.getMonthNumber() + budget.getName();
      STAGED_BUDGET_PERIODS.put(key, budgetPeriodIn);
    }

    return this;
  }
}
