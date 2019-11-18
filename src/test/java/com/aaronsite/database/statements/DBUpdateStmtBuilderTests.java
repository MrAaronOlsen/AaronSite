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

class DBUpdateStmtBuilderTests extends TestServer {

  @Test
  void simpleUpdateSQL() throws ABException {
    try (DBConnection conn = new DBConnection()) {
      String expectedSql = "UPDATE " + getTestSchema(TEST_SIMPLE) + " SET one='1', two='2' WHERE id='1' RETURNING *";

      DBRecord record = new DBRecord()
          .add("one", "1")
          .add("two", "2");

      DBPreparedStmt stmt = new DBUpdateStmtBuilder(conn, TEST_SIMPLE).setIdQuery("1").build(record);

      Assertions.assertEquals(expectedSql, stmt.toString());
    }
  }

  @Test
  void unescapedInsertSQL() throws ABException {
    try (DBConnection conn = new DBConnection()) {
      String expectedSql = "UPDATE " + getTestSchema(TEST_SIMPLE) + " SET one='Isn''t', two='Line\n' WHERE id='1' RETURNING *";

      DBRecord record = new DBRecord()
          .add("one", "Isn't")
          .add("two", "Line\n");

      DBPreparedStmt stmt = new DBUpdateStmtBuilder(conn, TEST_SIMPLE).setIdQuery("1").build(record);

      Assertions.assertEquals(expectedSql, stmt.toString());
    }
  }

  @Test
  void htmlInsertSQL() throws ABException {
    try (DBConnection conn = new DBConnection()) {
      String expectedSql = "UPDATE " + getTestSchema(TEST_SIMPLE) + " SET html='<div>html</div>' WHERE id='1' RETURNING *";

      DBRecord record = new DBRecord()
          .add("html", "<div>html</div>");

      DBPreparedStmt stmt = new DBUpdateStmtBuilder(conn, TEST_SIMPLE).setIdQuery("1").build(record);

      Assertions.assertEquals(expectedSql, stmt.toString());
    }
  }
}
