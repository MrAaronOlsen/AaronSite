package com.aaronsite.database.statements;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DBSortStmtBuilderTests {

  @Test
  void defaultSortSQL() {
    String expectedSql = "";

    DBSortStmtBuilder sortBuilder = new DBSortStmtBuilder(null);
    Assertions.assertEquals(expectedSql, sortBuilder.toString());
  }

  @Test
  void singleASCSortSQL() {
    String expectedSql = "ORDER BY name ASC";

    DBSortStmtBuilder sortBuilder = new DBSortStmtBuilder("name");
    Assertions.assertEquals(expectedSql, sortBuilder.toString());
  }

  @Test
  void singleDESCSortSQL() {
    String expectedSql = "ORDER BY name DESC";

    DBSortStmtBuilder sortBuilder = new DBSortStmtBuilder("-name");
    Assertions.assertEquals(expectedSql, sortBuilder.toString());
  }

  @Test
  void multipleASCSortSQL() {
    String expectedSql = "ORDER BY name ASC, age ASC";

    DBSortStmtBuilder sortBuilder = new DBSortStmtBuilder("name, age");
    Assertions.assertEquals(expectedSql, sortBuilder.toString());
  }

  @Test
  void multipleDESCSortSQL() {
    String expectedSql = "ORDER BY name DESC, age DESC";

    DBSortStmtBuilder sortBuilder = new DBSortStmtBuilder("-name, -age");
    Assertions.assertEquals(expectedSql, sortBuilder.toString());
  }

  @Test
  void multipleASCDESCSortSQL() {
    String expectedSql = "ORDER BY name DESC, age ASC";

    DBSortStmtBuilder sortBuilder = new DBSortStmtBuilder("-name, age");
    Assertions.assertEquals(expectedSql, sortBuilder.toString());
  }
}
