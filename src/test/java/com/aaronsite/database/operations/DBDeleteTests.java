package com.aaronsite.database.operations;

import com.aaronsite.database.connection.DBConnection;
import com.aaronsite.models.TestSimple;
import com.aaronsite.server.TestServer;
import com.aaronsite.utils.enums.Table;
import com.aaronsite.utils.exceptions.ABException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DBDeleteTests extends TestServer {

  @Test
  void deletingSingleRecordByValidIdReturnsResult() throws ABException {
    TestSimple insertRecord = new TestSimple(insertRecord(new TestSimple().setName("test")));

    try (DBConnection dbConn = new DBConnection()) {
      DBDelete dbDelete = new DBDelete(dbConn, Table.TEST_SIMPLE).setId(insertRecord.getId());
      DBResult deleteResult = dbDelete.execute();

      if (deleteResult.hasNext()) {
        DBRecord record = deleteResult.getNext();

        DbQuery query = new DbQuery(dbConn,Table.TEST_SIMPLE).setIdQuery(record.getId());
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
      DBDelete dbDelete = new DBDelete(dbConn, Table.TEST_SIMPLE).setId("0");
      DBResult deleteResult = dbDelete.execute();

      if (deleteResult.hasNext()) {
        Assertions.fail("Delete should not have returned any records.");
      }
    }
  }
}
