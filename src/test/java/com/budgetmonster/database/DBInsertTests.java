package com.budgetmonster.database;

import com.budgetmonster.database.connection.DBConnection;
import com.budgetmonster.database.operations.DBDelete;
import com.budgetmonster.database.operations.DBInsert;
import com.budgetmonster.database.operations.DBRecord;
import com.budgetmonster.database.operations.DBResult;
import com.budgetmonster.database.operations.DbQuery;
import com.budgetmonster.server.TestServer;
import com.budgetmonster.testutils.DBTruncateAll;
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
      DBRecord insertRecord = new DBRecord()
          .add("name", "'testing 123'");

      DBInsert insert = new DBInsert(conn, "budgets")
          .addRecord(insertRecord);

      DBResult insertResult = insert.execute();

      String insertId = "";
      if (insertResult.hasNext()) {
        DBRecord insertRecordResult = insertResult.getNext();
        Assertions.assertTrue(insertRecordResult.has("id"));

        insertId = insertRecordResult.get("id");
      } else {
        Assertions.fail("Insert should have returned a result.");
      }

      DbQuery query = new DbQuery(conn, "budgets").setIdQuery(insertId);
      DBResult result1 = query.execute();

      if (result1.hasNext()) {
        DBRecord queryRecord = result1.getNext();

        Assertions.assertTrue(queryRecord.has("name"));
        Assertions.assertEquals("testing 123", queryRecord.get("name"));
      } else {
        Assertions.fail("Query should have returned a result.");
      }

      DBDelete delete = new DBDelete(conn, "budgets").setId(insertId);
      DBResult deleteResult = delete.execute();

      if (!deleteResult.hasNext()) {
        Assertions.fail("Delete should have returned a result.");

        DBRecord deleteRecord = deleteResult.getNext();
        Assertions.assertTrue(deleteRecord.has("id"));
        Assertions.assertEquals(insertId, deleteRecord.get("id"));
      }

      DBResult result2 = query.execute();

      if (result2.hasNext()) {
        Assertions.fail("Query should not have returned a result");
      }
    }
  }
}
