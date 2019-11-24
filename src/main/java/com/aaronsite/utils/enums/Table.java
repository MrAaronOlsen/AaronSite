package com.aaronsite.utils.enums;

import com.aaronsite.utils.exceptions.DatabaseException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static com.aaronsite.utils.exceptions.DatabaseException.Code.TABLE_DOES_NOT_EXIST;

public enum Table {
  // Production Tables
  PAGES("pages"),
  USERS("users"),

  // Test Tables
  TEST_SIMPLE("test_simple", true),

  // Default
  INVALID_TABLE("InvalidTable");

  private String name;
  private boolean test;

  private static final Map<String, Table> nameMap = new ConcurrentHashMap<>();
  static {
    for (Table table : Table.values()) {
      nameMap.put(table.getName(), table);
    }
  }

  Table(String name, boolean test) {
    this.name = name;
    this.test = test;
  }

  Table(String name) {
    this.name = name;
    this.test = false;
  }

  public static Table get(String tableS) throws DatabaseException {
    Table table = nameMap.get(tableS);

    if (table == INVALID_TABLE) {
      throw new DatabaseException(TABLE_DOES_NOT_EXIST, tableS);
    }

    return table;
  }

  public String getName() {
    return name;
  }

  public static List<Table> getTestTables() {
    return Arrays.stream(Table.values()).filter(Table::isTest).collect(Collectors.toList());
  }

  public boolean isNotSupported() {
    return this == INVALID_TABLE;
  }

  public boolean isTest() {
    return test;
  }

  @Override
  public String toString() {
    return name;
  }
}
