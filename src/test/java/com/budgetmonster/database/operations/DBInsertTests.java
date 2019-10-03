package com.budgetmonster.database.operations;

import com.budgetmonster.database.connection.DBConnection;
import com.budgetmonster.models.Budget;
import com.budgetmonster.models.Model;
import com.budgetmonster.server.TestServer;
import com.budgetmonster.utils.enums.Table;
import com.budgetmonster.utils.exceptions.DBException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.budgetmonster.utils.exceptions.DBException.Code.TABLE_DOES_NOT_EXIST;
import static com.budgetmonster.utils.exceptions.DBException.Code.UNKNOWN_COLUMN;

class DBInsertTests extends TestServer {

  @Test
  void insertingValidRecordReturnsId() throws DBException {
    try (DBConnection conn = new DBConnection()) {
      Model budget = new Budget().setName("Test");

      DBInsert insert = new DBInsert(conn, Table.BUDGET)
          .addRecord(budget);

      DBResult insertResult = insert.execute();

      if (insertResult.hasNext()) {
        DBRecord insertRecordResult = insertResult.getNext();
        Assertions.assertTrue(insertRecordResult.has("id"));
      } else {
        Assertions.fail("Insert should have returned a result.");
      }
    }
  }

  @Test
  void insertsIntoInvalidTableThrowsException() throws DBException {
    try (DBConnection conn = new DBConnection()) {
      Model budget = new Budget().setName("Test");

      DBInsert insert = new DBInsert(conn, Table.INVALID_TABLE)
          .addRecord(budget);

      try {
        insert.execute();
        Assertions.fail("Should have failed insert because table does not exist.");
      } catch (DBException e) {
        Assertions.assertEquals(TABLE_DOES_NOT_EXIST, e.getCode());
      }
    }
  }

  @Test
  void insertsWithInvalidColumnThrowsException() throws DBException {
    try (DBConnection conn = new DBConnection()) {
      DBRecord record = new DBRecord()
          .add("bad", "'even worse'");

      DBInsert insert = new DBInsert(conn, Table.BUDGET)
          .addRecord(record);

      try {
        insert.execute();
        Assertions.fail("Should have failed insert because column does not exist.");
      } catch (DBException e) {
        Assertions.assertEquals(UNKNOWN_COLUMN, e.getCode());
      }
    }
  }
}
