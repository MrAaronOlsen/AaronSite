package com.aaronsite.database.statements;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class DBSortStmtBuilder {
  private static final String ORDER_BY = "ORDER BY";

  private String sorts;

  DBSortStmtBuilder() {
    // Default constructor
  }

  public DBSortStmtBuilder(String sorts) {
    this.sorts = sorts;
  }

  @Override
  public String toString() {
    if (StringUtils.isEmpty(sorts)) {
      return "";
    }

    return ORDER_BY + " " + Arrays.stream(sorts.split(","))
        .map(String::trim)
        .map(sort -> sort.startsWith("-") ? sort.replace("-", "") + " DESC" : sort + " ASC")
        .collect(Collectors.joining(", "));
  }
}
