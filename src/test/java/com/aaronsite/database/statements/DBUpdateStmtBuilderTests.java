package com.aaronsite.database.statements;

import com.aaronsite.database.connection.DBConnection;
import com.aaronsite.database.transaction.DBRecord;
import com.aaronsite.server.TestServer;
import com.aaronsite.utils.exceptions.ABException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.aaronsite.utils.enums.Table.TEST_SIMPLE;

class DBUpdateStmtBuilderTests extends TestServer {

  @Test
  void simpleUpdateSQL() throws ABException {
    try (DBConnection conn = new DBConnection()) {
      String expectedSql = "UPDATE " + getTestSchema(TEST_SIMPLE) + " SET name='1', text='2' WHERE id=1 RETURNING *";

      DBRecord record = new DBRecord()
          .add("name", "1")
          .add("text", "2");

      DBPreparedStmt stmt = new DBUpdateStmtBuilder(conn, TEST_SIMPLE).setIdQuery("1").setRecord(record).build();

      Assertions.assertEquals(expectedSql, stmt.toString());
    }
  }

  @Test
  void unescapedInsertSQL() throws ABException {
    try (DBConnection conn = new DBConnection()) {
      String expectedSql = "UPDATE " + getTestSchema(TEST_SIMPLE) + " SET name='Isn''t', text='Line\n' WHERE id=1 RETURNING *";

      DBRecord record = new DBRecord()
          .add("name", "Isn't")
          .add("text", "Line\n");

      DBPreparedStmt stmt = new DBUpdateStmtBuilder(conn, TEST_SIMPLE).setIdQuery("1").setRecord(record).build();

      Assertions.assertEquals(expectedSql, stmt.toString());
    }
  }

  @Test
  void htmlInsertSQL() throws ABException {
    try (DBConnection conn = new DBConnection()) {
      String expectedSql = "UPDATE " + getTestSchema(TEST_SIMPLE) + " SET text='<div>html</div>' WHERE id=1 RETURNING *";

      DBRecord record = new DBRecord()
          .add("text", "<div>html</div>");

      DBPreparedStmt stmt = new DBUpdateStmtBuilder(conn, TEST_SIMPLE).setIdQuery("1").setRecord(record).build();

      Assertions.assertEquals(expectedSql, stmt.toString());
    }
  }
}
