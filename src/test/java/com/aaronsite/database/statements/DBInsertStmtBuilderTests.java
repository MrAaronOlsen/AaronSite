package com.aaronsite.database.statements;

import com.aaronsite.database.connection.DBConnection;
import com.aaronsite.database.transaction.DBRecord;
import com.aaronsite.server.TestServer;
import com.aaronsite.utils.exceptions.ABException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.aaronsite.utils.enums.Table.TEST_SIMPLE;

class DBInsertStmtBuilderTests extends TestServer {

  @Test
  void simpleInsertSQL() throws ABException {
    try (DBConnection conn = new DBConnection()) {
      String expectedSql = "INSERT INTO " + getTestSchema(TEST_SIMPLE) + " (name, text) VALUES('1', '2') RETURNING *";

      DBRecord record = new DBRecord()
          .add("name", "1")
          .add("text", "2");

      DBPreparedStmt stmt = new DBInsertStmtBuilder(conn, TEST_SIMPLE).setRecord(record).build();

      Assertions.assertEquals(expectedSql, stmt.toString());
    }
  }

  @Test
  void unescapedInsertSQL() throws ABException {
    try (DBConnection conn = new DBConnection()) {
      String expectedSql = "INSERT INTO " + getTestSchema(TEST_SIMPLE) + " (name, text) VALUES('Isn''t', 'Line\n') RETURNING *";

      DBRecord record = new DBRecord()
          .add("name", "Isn't")
          .add("text", "Line\n");

      DBPreparedStmt stmt = new DBInsertStmtBuilder(conn, TEST_SIMPLE).setRecord(record).build();

      Assertions.assertEquals(expectedSql, stmt.toString());
    }
  }

  @Test
  void htmlInsertSQL() throws ABException {
    try (DBConnection conn = new DBConnection()) {
      String expectedSql = "INSERT INTO " + getTestSchema(TEST_SIMPLE) + " (text) VALUES('<div>html</div>') RETURNING *";

      DBRecord record = new DBRecord()
          .add("text", "<div>html</div>");

      DBPreparedStmt stmt = new DBInsertStmtBuilder(conn, TEST_SIMPLE).setRecord(record).build();

      Assertions.assertEquals(expectedSql, stmt.toString());
    }
  }
}
