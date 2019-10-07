package com.budgetmonster.utils.enums;

import com.budgetmonster.utils.constants.Tables;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public enum Table {
  INVALID_TABLE("InvalidTable", false),
  BUDGETS(Tables.BUDGETS),
  TRANSACTIONS(Tables.TRANSACTIONS),
  MONTHS(Tables.MONTHS),
  PERIODS(Tables.PERIODS),
  BUDGET_PERIODS(Tables.BUDGET_PERIODS),
  BUDGET_PERIOD_TRANSACTIONS(Tables.BUDGET_PERIOD_TRANSACTIONS);

  private String name;
  private boolean active;

  private static final Map<String, Table> nameMap = new ConcurrentHashMap<>();
  static {
    for (Table table : Table.values()) {
      nameMap.put(table.getName(), table);
    }
  }

  Table(String name, boolean active) {
    this.name = name;
    this.active = active;
  }

  Table(String name) {
    this.name = name;
    this.active = true;
  }

  public static Table get(String table) {
    return nameMap.getOrDefault(table, INVALID_TABLE);
  }

  public String getName() {
    return name;
  }

  public static List<Table> getActiveTables() {
    return Arrays.stream(Table.values()).filter(table -> table.active).collect(Collectors.toList());
  }

  public boolean isNotSupported() {
    return this == INVALID_TABLE;
  }

  @Override
  public String toString() {
    return name;
  }
}
