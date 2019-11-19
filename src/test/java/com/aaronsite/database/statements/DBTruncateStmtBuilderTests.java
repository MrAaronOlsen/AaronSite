package com.aaronsite.database.statements;

import com.aaronsite.database.connection.DBConnection;
import com.aaronsite.server.TestServer;
import com.aaronsite.utils.exceptions.ABException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.aaronsite.utils.enums.Table.TEST_SIMPLE;

class DBTruncateStmtBuilderTests extends TestServer {

  @Test
  void truncateTableSQL() throws ABException {
    String expectedSql = "TRUNCATE " + getTestSchema(TEST_SIMPLE);

    try (DBConnection conn = new DBConnection()) {
      DBPreparedStmt truncateStmt = new DBTruncateStmtBuilder(conn, List.of(TEST_SIMPLE)).build();
      Assertions.assertEquals(expectedSql, truncateStmt.toString());
    }
  }
}
