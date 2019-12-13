package com.aaronsite.database.operations;

import com.aaronsite.database.connection.DBConnection;
import com.aaronsite.database.transaction.DBRecord;
import com.aaronsite.database.transaction.DBResult;
import com.aaronsite.models.TestSimple;
import com.aaronsite.server.TestServer;
import com.aaronsite.utils.enums.Table;
import com.aaronsite.utils.exceptions.ABException;
import com.aaronsite.utils.exceptions.DatabaseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.aaronsite.utils.exceptions.DatabaseException.Code.UNKNOWN_COLUMN;

class DBInsertTests extends TestServer {

  @Test
  void insertingRecordReturnsResultWithRecord() throws ABException {
    try (DBConnection conn = new DBConnection()) {
      TestSimple test = new TestSimple().setName("Test");

      DBInsert insert = new DBInsert(conn, Table.TEST_SIMPLE)
          .addRecord(test);

      DBResult insertResult = insert.execute();

      if (insertResult.hasNext()) {
        DBRecord newRecord = insertResult.getNext();

        Assertions.assertTrue(newRecord.contains("id"));
        Assertions.assertEquals("Test", newRecord.get("name"));
      } else {
        Assertions.fail("Insert should have returned a result with an id.");
      }
    }
  }

  @Test
  void insertingStringWithUnEscapedCharacters() throws ABException {
    String stringLiteral = "I'm an unescaped line.\nAnd here's a new line.";

    try (DBConnection conn = new DBConnection()) {
      TestSimple test = new TestSimple()
          .setName("Test")
          .setText(stringLiteral);

      DBInsert insert = new DBInsert(conn, Table.TEST_SIMPLE)
          .addRecord(test);

      DBResult result = insert.execute();

      if (result.hasNext()) {
        DBRecord newRecord = result.getNext();

        Assertions.assertTrue(newRecord.contains("id"));
        Assertions.assertEquals(stringLiteral, newRecord.get("text"));
      } else {
        Assertions.fail("Insert should have returned a result with an id.");
      }
    }
  }

  @Test
  void insertingWithInvalidColumnThrowsException() throws DatabaseException {
    try (DBConnection conn = new DBConnection()) {
      DBRecord record = new DBRecord()
          .add("bad", "even worse");

      DBInsert insert = new DBInsert(conn, Table.TEST_SIMPLE)
          .addRecord(record);

      try {
        insert.execute();
        Assertions.fail("Should have failed insert because column does not exist.");
      } catch (DatabaseException e) {
        Assertions.assertEquals(UNKNOWN_COLUMN, e.getCode());
      }
    }
  }
}
