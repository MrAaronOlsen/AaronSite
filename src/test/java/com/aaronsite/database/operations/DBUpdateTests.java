package com.aaronsite.database.operations;

import com.aaronsite.database.connection.DBConnection;
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
      TestSimple insertRecord = new TestSimple(insertRecord(new TestSimple().setName("Test")));
      insertRecord.setName("Testing 123");

      DBUpdate update = new DBUpdate(dbConn, Table.TEST_SIMPLE)
          .addQueryId(insertRecord.getId())
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
  void updateShouldReturnAResultOnPositiveValueQuery() throws ABException {
    try (DBConnection dbConn = new DBConnection()) {
      TestSimple insertRecord = new TestSimple(insertRecord(new TestSimple().setName("Test")));
      insertRecord.setName("Testing 123");

      DBUpdate update = new DBUpdate(dbConn, Table.TEST_SIMPLE)
          .addQuery(new DBQueryBuilder().add("name", "Test"))
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
          .addQuery(new DBQueryBuilder().add("name", "bad"))
          .addRecord(new TestSimple().setName("testing"));

      DBResult updateResult = update.execute();

      if (updateResult.hasNext()) {
        Assertions.fail("Update should have not have returned a result.");
      }
    }
  }
}
