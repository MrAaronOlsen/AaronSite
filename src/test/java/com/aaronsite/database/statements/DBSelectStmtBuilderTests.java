package com.aaronsite.database.statements;

import com.aaronsite.server.TestServer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DBSelectStmtBuilderTests extends TestServer {

  @Test
  void defaultSelectSQL() {
    String expectedSql = "SELECT * FROM";

    DBSelectStmtBuilder selectBuilder = new DBSelectStmtBuilder(null);
    Assertions.assertEquals(expectedSql, selectBuilder.toString());
  }

  @Test
  void singleFieldSQL() {
    String expectedSql = "SELECT name FROM";

    DBSelectStmtBuilder selectBuilder = new DBSelectStmtBuilder("name");
    Assertions.assertEquals(expectedSql, selectBuilder.toString());
  }

  @Test
  void multipleFieldsSQL() {
    String expectedSql = "SELECT name, age, height FROM";

    DBSelectStmtBuilder selectBuilder = new DBSelectStmtBuilder("name,age,height");
    Assertions.assertEquals(expectedSql, selectBuilder.toString());
  }

  @Test
  void multipleFieldsWithSpacesSQL() {
    String expectedSql = "SELECT name, body, height FROM";

    DBSelectStmtBuilder selectBuilder = new DBSelectStmtBuilder("name, body, height");
    Assertions.assertEquals(expectedSql, selectBuilder.toString());
  }
}
