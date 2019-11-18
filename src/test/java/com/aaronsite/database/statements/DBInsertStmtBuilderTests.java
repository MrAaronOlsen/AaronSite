package com.aaronsite.database.statements;

import com.aaronsite.database.connection.DBConnection;
import com.aaronsite.database.transaction.DBRecord;
import com.aaronsite.server.TestServer;
import com.aaronsite.utils.enums.Table;
import com.aaronsite.utils.exceptions.ABException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static com.aaronsite.utils.enums.Table.TEST_SIMPLE;

class DBInsertStmtBuilderTests extends TestServer {

  @Test
  void simpleInsertSQL() throws ABException {
    try (DBConnection conn = new DBConnection()) {
      String expectedSql = "INSERT INTO " + getTestSchema(TEST_SIMPLE) + " (one, two) VALUES('1', '2') RETURNING *";

      DBRecord record = new DBRecord()
          .add("one", "1")
          .add("two", "2");

      DBPreparedStmt stmt = new DBInsertStmtBuilder(conn, TEST_SIMPLE).build(record);

      Assertions.assertEquals(expectedSql, stmt.toString());
    }
  }

  @Test
  void unescapedInsertSQL() throws ABException {
    try (DBConnection conn = new DBConnection()) {
      String expectedSql = "INSERT INTO " + getTestSchema(TEST_SIMPLE) + " (one, two) VALUES('Isn''t', 'Line\n') RETURNING *";

      DBRecord record = new DBRecord()
          .add("one", "Isn't")
          .add("two", "Line\n");

      DBPreparedStmt stmt = new DBInsertStmtBuilder(conn, TEST_SIMPLE).build(record);

      Assertions.assertEquals(expectedSql, stmt.toString());
    }
  }

  @Test
  void htmlInsertSQL() throws ABException {
    try (DBConnection conn = new DBConnection()) {
      String expectedSql = "INSERT INTO " + getTestSchema(TEST_SIMPLE) + " (html) VALUES('<div>html</div>') RETURNING *";

      DBRecord record = new DBRecord()
          .add("html", "<div>html</div>");

      DBPreparedStmt stmt = new DBInsertStmtBuilder(conn, TEST_SIMPLE).build(record);

      Assertions.assertEquals(expectedSql, stmt.toString());
    }
  }
}
