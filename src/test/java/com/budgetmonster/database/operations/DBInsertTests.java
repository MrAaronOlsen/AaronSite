package com.budgetmonster.database.operations;

import com.budgetmonster.database.connection.DBConnection;
import com.budgetmonster.database.operations.DBInsert;
import com.budgetmonster.database.operations.DBRecord;
import com.budgetmonster.database.operations.DBResult;
import com.budgetmonster.models.Budget;
import com.budgetmonster.models.Model;
import com.budgetmonster.server.TestServer;
import com.budgetmonster.testutils.DBTruncateAll;
import com.budgetmonster.utils.constants.Tables;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

class DBInsertTests extends TestServer {
  @BeforeEach
  void truncate() throws SQLException {
    DBTruncateAll.execute();
  }

  @Test
  void simpleInsert() throws SQLException {
    try (DBConnection conn = new DBConnection()) {
      Model budget = new Budget.Builder()
          .setName("Test").build();

      DBInsert insert = new DBInsert(conn, Tables.BUDGETS)
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
}
