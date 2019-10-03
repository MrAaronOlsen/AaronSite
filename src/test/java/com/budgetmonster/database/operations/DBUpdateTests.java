package com.budgetmonster.database.operations;

import com.budgetmonster.database.connection.DBConnection;
import com.budgetmonster.models.Budget;
import com.budgetmonster.server.TestServer;
import com.budgetmonster.utils.enums.Table;
import com.budgetmonster.utils.exceptions.ABException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DBUpdateTests extends TestServer {

  @Test
  void simpleUpdateOneColumn() throws ABException {
    try (DBConnection dbConn = new DBConnection()) {
      Budget insertRecord = new Budget(insertRecord(new Budget().setName("Test")));

      DbQuery query = new DbQuery(dbConn, Table.BUDGET).setIdQuery(insertRecord.getId());
      DBResult queryResultOne = query.execute();

      if (!queryResultOne.hasNext()) {
        Assertions.fail("Query One should have returned a record.");
      }

      Budget updateRecord = new Budget(queryResultOne.getNext());
      updateRecord.setName("Testing 123");

      DBUpdate update = new DBUpdate(dbConn, Table.BUDGET)
          .addId(updateRecord.getId())
          .addRecord(updateRecord);

      DBResult updateResult = update.execute();

      if (!updateResult.hasNext()) {
        Assertions.fail("Update should have returned a record.");
      }

      Budget updatedRecordOne = new Budget(updateResult.getNext());
      Assertions.assertEquals("Testing 123", updatedRecordOne.getName());

      DBResult queryResultTwo = query.execute();

      if (!queryResultTwo.hasNext()) {
        Assertions.fail("Query Two should have returned a record.");
      }

      Budget updatedRecordTwo = new Budget(queryResultTwo.getNext());
      Assertions.assertEquals("Testing 123", updatedRecordTwo.getName());
    }
  }
}
