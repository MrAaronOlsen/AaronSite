package com.aaronsite.database.statements;

import com.aaronsite.database.connection.DBConnection;
import com.aaronsite.server.TestServer;
import com.aaronsite.utils.exceptions.ABException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.aaronsite.utils.enums.Table.TEST_SIMPLE;

class DBQueryStmtBuilderTests extends TestServer {

  @Test
  void simpleIdQuerySql() throws ABException {
    try (DBConnection conn = new DBConnection()) {
      String expectedSql = "SELECT * FROM " + getTestSchema(TEST_SIMPLE) + " WHERE id='1'";

      DBPreparedStmt stmt = new DBQueryStmtBuilder(conn, TEST_SIMPLE)
          .setIdQuery("1").build();

      Assertions.assertEquals(expectedSql, stmt.toString());
    }
  }

  @Test
  void simpleColumnQuerySql() throws ABException {
    try (DBConnection conn = new DBConnection()) {
      String expectedSql = "SELECT * FROM " + getTestSchema(TEST_SIMPLE) + " WHERE one='1'";

      DBPreparedStmt stmt = new DBQueryStmtBuilder(conn, TEST_SIMPLE)
          .setWhere(new DBWhereStmtBuilder("one", "1")).build();

      Assertions.assertEquals(expectedSql, stmt.toString());
    }
  }
}
