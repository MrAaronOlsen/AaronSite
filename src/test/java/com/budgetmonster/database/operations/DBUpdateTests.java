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
      insertRecord.setName("Testing 123");

      DBUpdate update = new DBUpdate(dbConn, Table.BUDGET)
          .addId(insertRecord.getId())
          .addRecord(insertRecord);

      DBResult updateResult = update.execute();

      if (!updateResult.hasNext()) {
        Assertions.fail("Update should have returned a record.");
      }

      Budget updatedRecordOne = new Budget(updateResult.getNext());
      Assertions.assertEquals("Testing 123", updatedRecordOne.getName());

      DbQuery query = new DbQuery(dbConn, Table.BUDGET);
      DBResult queryResult = query.execute();

      if (!queryResult.hasNext()) {
        Assertions.fail("Query Two should have returned a record.");
      }

      Budget updatedRecordTwo = new Budget(queryResult.getNext());
      Assertions.assertEquals("Testing 123", updatedRecordTwo.getName());
    }
  }
}
