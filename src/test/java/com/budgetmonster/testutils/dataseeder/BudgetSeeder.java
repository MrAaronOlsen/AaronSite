package com.budgetmonster.testutils.dataseeder;

import com.budgetmonster.models.Budget;
import com.budgetmonster.utils.exceptions.ABException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BudgetSeeder extends DataSeeder {
  private final Map<String, Budget> BUDGETS = new HashMap<>();
  private String[] names;

  @Override
  public void seed() throws ABException {
    for (String name : names) {
      BUDGETS.put(name, new Budget(insertRecord(new Budget().setName(name))));
    }
  }

  @Override
  public Budget get(String name) {
    return BUDGETS.get(name);
  }

  @Override
  public Map<String, Budget> getMap() {
    return BUDGETS;
  }

  @Override
  public List<Budget> getList() {
    return (ArrayList<Budget>) BUDGETS.values();
  }

  public BudgetSeeder stage(String... names) {
    this.names = names;
    return this;
  }
}
