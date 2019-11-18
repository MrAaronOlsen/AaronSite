package com.aaronsite.database.operations;

import com.aaronsite.database.connection.DBConnection;
import com.aaronsite.database.statements.DBQueryStmtBuilder;
import com.aaronsite.database.transaction.DBResult;
import com.aaronsite.models.TestSimple;
import com.aaronsite.server.TestServer;
import com.aaronsite.utils.enums.Table;
import com.aaronsite.utils.exceptions.ABException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DBUpdateTests extends TestServer {

  @Test
  void updateShouldReturnAResultOnPositiveIdQuery() throws ABException {
    try (DBConnection dbConn = new DBConnection()) {
      TestSimple insert = new TestSimple(insertRecord(new TestSimple().setName("Test")));
      insert.setName("Testing 123");

      DBUpdate update = new DBUpdate(dbConn, Table.TEST_SIMPLE)
          .addQueryId(insert.getId())
          .addRecord(insert);

      DBResult updateResult = update.execute();

      if (!updateResult.hasNext()) {
        Assertions.fail("Update should have returned a record.");
      }

      TestSimple updatedRecordOne = new TestSimple(updateResult.getNext());
      Assertions.assertEquals("Testing 123", updatedRecordOne.getName());

      DbQuery query = new DbQuery(dbConn, Table.TEST_SIMPLE).setIdQuery(insert.getId());
      DBResult queryResult = query.execute();

      if (!queryResult.hasNext()) {
        Assertions.fail("Query Two should have returned a record.");
      }

      TestSimple updatedRecordTwo = new TestSimple(queryResult.getNext());
      Assertions.assertEquals("Testing 123", updatedRecordTwo.getName());
    }
  }

  @Test
  void updateShouldReturnAResultOnPositiveValueQuery() throws ABException {
    try (DBConnection dbConn = new DBConnection()) {
      TestSimple insertRecord = new TestSimple(insertRecord(new TestSimple().setName("Test")));
      insertRecord.setName("Testing 123");

      DBUpdate update = new DBUpdate(dbConn, Table.TEST_SIMPLE)
          .addQuery(new DBQueryStmtBuilder().add("name", "Test"))
          .addRecord(insertRecord);

      DBResult updateResult = update.execute();

      if (!updateResult.hasNext()) {
        Assertions.fail("Update should have returned a record.");
      }

      TestSimple updatedRecordOne = new TestSimple(updateResult.getNext());
      Assertions.assertEquals("Testing 123", updatedRecordOne.getName());

      DbQuery query = new DbQuery(dbConn, Table.TEST_SIMPLE).setIdQuery(insertRecord.getId());
      DBResult queryResult = query.execute();

      if (!queryResult.hasNext()) {
        Assertions.fail("Query Two should have returned a record.");
      }

      TestSimple updatedRecordTwo = new TestSimple(queryResult.getNext());
      Assertions.assertEquals("Testing 123", updatedRecordTwo.getName());
    }
  }

  @Test
  void updateShouldWorkWithUnescapedCharacters() throws ABException {
    try (DBConnection dbConn = new DBConnection()) {
      TestSimple insertRecord = new TestSimple(insertRecord(new TestSimple().setName("Test")));
      insertRecord.setName("Isn't it\n");

      DBUpdate update = new DBUpdate(dbConn, Table.TEST_SIMPLE)
          .addQuery(new DBQueryStmtBuilder().add("name", "Test"))
          .addRecord(insertRecord);

      DBResult updateResult = update.execute();

      if (!updateResult.hasNext()) {
        Assertions.fail("Update should have returned a record.");
      }

      TestSimple updatedRecordOne = new TestSimple(updateResult.getNext());
      Assertions.assertEquals("Isn't it\n", updatedRecordOne.getName());

      DbQuery query = new DbQuery(dbConn, Table.TEST_SIMPLE).setIdQuery(insertRecord.getId());
      DBResult queryResult = query.execute();

      if (!queryResult.hasNext()) {
        Assertions.fail("Query Two should have returned a record.");
      }

      TestSimple updatedRecordTwo = new TestSimple(queryResult.getNext());
      Assertions.assertEquals("Isn't it\n", updatedRecordTwo.getName());
    }
  }

  @Test
  void updateShouldReturnNoResultsOnBadQueryById() throws ABException {
    try (DBConnection dbConn = new DBConnection()) {
      DBUpdate update = new DBUpdate(dbConn, Table.TEST_SIMPLE)
          .addQueryId("0")
          .addRecord(new TestSimple().setName("testing"));

      DBResult updateResult = update.execute();

      if (updateResult.hasNext()) {
        Assertions.fail("Update should have not have returned a result.");
      }
    }
  }

  @Test
  void updateShouldReturnNoResultsOnBadQueryByValue() throws ABException {
    try (DBConnection dbConn = new DBConnection()) {
      DBUpdate update = new DBUpdate(dbConn, Table.TEST_SIMPLE)
          .addQuery(new DBQueryStmtBuilder().add("name", "bad"))
          .addRecord(new TestSimple().setName("testing"));

      DBResult updateResult = update.execute();

      if (updateResult.hasNext()) {
        Assertions.fail("Update should have not have returned a result.");
      }
    }
  }
}
