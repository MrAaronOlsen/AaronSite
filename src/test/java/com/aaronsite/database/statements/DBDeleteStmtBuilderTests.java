package com.aaronsite.database.statements;

import com.aaronsite.database.connection.DBConnection;
import com.aaronsite.server.TestServer;
import com.aaronsite.utils.exceptions.ABException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.aaronsite.utils.enums.Table.TEST_SIMPLE;

class DBDeleteStmtBuilderTests extends TestServer {

  @Test
  void idDeleteSQL() throws ABException {
    String expectedSql = "DELETE FROM " + getTestSchema(TEST_SIMPLE) + " WHERE id=1 RETURNING id";

    try (DBConnection conn = new DBConnection()) {
      DBPreparedStmt deleteStmt = new DBDeleteStmtBuilder(conn, TEST_SIMPLE).setId("1").build();
      Assertions.assertEquals(expectedSql, deleteStmt.toString());
    }
  }
}
