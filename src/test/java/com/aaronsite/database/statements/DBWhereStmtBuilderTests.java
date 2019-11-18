package com.aaronsite.database.statements;

import com.aaronsite.server.TestServer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DBWhereStmtBuilderTests extends TestServer {

  @Test
  void idWhereSQL() {
    String expectedSql = "WHERE id=?";

    DBWhereStmtBuilder whereBuilder = new DBWhereStmtBuilder("1");
    Assertions.assertEquals(expectedSql, whereBuilder.toString());
  }

  @Test
  void singleWhereSQL() {
    String expectedSql = "WHERE name=?";

    DBWhereStmtBuilder whereBuilder = new DBWhereStmtBuilder("name", "1");
    Assertions.assertEquals(expectedSql, whereBuilder.toString());
  }

  @Test
  void multipleWhereSQL_Two() {
    String expectedSql = "WHERE name=? AND age=?";

    DBWhereStmtBuilder whereBuilder = new DBWhereStmtBuilder("name", "1").add("age", "2");
    Assertions.assertEquals(expectedSql, whereBuilder.toString());
  }

  @Test
  void multipleWhereSQL_Three() {
    String expectedSql = "WHERE one=? AND two=? AND three=?";

    DBWhereStmtBuilder whereBuilder = new DBWhereStmtBuilder("one", "1").add("two", "2").add("three", "3");
    Assertions.assertEquals(expectedSql, whereBuilder.toString());
  }
}
