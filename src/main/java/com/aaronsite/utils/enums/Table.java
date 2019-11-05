package com.aaronsite.utils.enums;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public enum Table {
  // Production Tables
  POSTS("posts"),

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

  public static Table get(String table) {
    return nameMap.getOrDefault(table, INVALID_TABLE);
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
