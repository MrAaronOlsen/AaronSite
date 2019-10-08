package com.budgetmonster.database.operations;

import com.budgetmonster.database.connection.DBConnection;
import com.budgetmonster.models.Budget;
import com.budgetmonster.server.TestServer;
import com.budgetmonster.utils.enums.Table;
import com.budgetmonster.utils.exceptions.ABException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DBDeleteTests extends TestServer {

  @Test
  void deletingSingleRecordByValidIdReturnsResult() throws ABException {
    Budget insertRecord = new Budget(insertRecord(new Budget().setName("test")));

    try (DBConnection dbConn = new DBConnection()) {
      DBDelete dbDelete = new DBDelete(dbConn, Table.BUDGETS).setId(insertRecord.getId());
      DBResult deleteResult = dbDelete.execute();

      if (deleteResult.hasNext()) {
        DBRecord record = deleteResult.getNext();

        DbQuery query = new DbQuery(dbConn,Table.BUDGETS).setIdQuery(record.getId());
        DBResult queryResult = query.execute();

        if (queryResult.hasNext()) {
          Assertions.fail("Query should not have returned any results.");
        }
      } else {
        Assertions.fail("Delete should have returned a record.");
      }
    }
  }

  @Test
  void deletingSingleRecordByInvalidIdReturnsNoResult() throws ABException {
    try (DBConnection dbConn = new DBConnection()) {
      DBDelete dbDelete = new DBDelete(dbConn, Table.BUDGETS).setId("0");
      DBResult deleteResult = dbDelete.execute();

      if (deleteResult.hasNext()) {
        Assertions.fail("Delete should not have returned any records.");
      }
    }
  }
}
