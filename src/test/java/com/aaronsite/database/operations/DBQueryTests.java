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

import static com.aaronsite.models.TestSimple.NAME;

class DBQueryTests extends TestServer {

  @Test
  void queryByIdReturnsSingleRecord() throws ABException {
    TestSimple budgetInsert = new TestSimple(insertRecord(new TestSimple().setName("test")));

    try (DBConnection dbConn = new DBConnection()) {
      DbQuery dbQuery = new DbQuery(dbConn, Table.TEST_SIMPLE).setIdQuery(budgetInsert.getId());

      DBResult result = dbQuery.execute();
      if (result.hasNext()) {
        TestSimple budgetQuery = new TestSimple(result.getNext());

        Assertions.assertEquals(budgetInsert.getId(), budgetQuery.getId());
        Assertions.assertEquals(budgetInsert.getName(), budgetQuery.getName());
      } else {
        Assertions.fail("Query should have found record by id.");
      }
    }
  }

  @Test
  void queryByValueReturnsSingleRecord() throws ABException {
    TestSimple budgetInsert = new TestSimple(insertRecord(new TestSimple().setName("test")));

    try (DBConnection dbConn = new DBConnection()) {
      DbQuery dbQuery = new DbQuery(dbConn, Table.TEST_SIMPLE).setQuery(new DBQueryStmtBuilder().add(NAME, "test"));

      DBResult result = dbQuery.execute();
      if (result.hasNext()) {
        TestSimple budgetQuery = new TestSimple(result.getNext());

        Assertions.assertEquals(budgetInsert.getId(), budgetQuery.getId());
        Assertions.assertEquals(budgetInsert.getName(), budgetQuery.getName());
      } else {
        Assertions.fail("Query should have found record by id.");
      }
    }
  }

  @Test
  void queryByValueReturnsMultipleRecords() throws ABException {
    insertRecord(new TestSimple().setName("test"));
    insertRecord(new TestSimple().setName("test"));

    try (DBConnection dbConn = new DBConnection()) {
      DbQuery dbQuery = new DbQuery(dbConn, Table.TEST_SIMPLE).setQuery(new DBQueryStmtBuilder().add(NAME, "test"));

      DBResult result = dbQuery.execute();

      int count = 0;
      while (result.hasNext()) {
        count ++;
      }

      Assertions.assertEquals(2, count);
    }
  }

  @Test
  void queryOnNonExistingIdReturnsNoResults() throws ABException {
    try (DBConnection dbConn = new DBConnection()) {
      DbQuery dbQuery = new DbQuery(dbConn, Table.TEST_SIMPLE).setIdQuery("0");

      DBResult result = dbQuery.execute();

      if (result.hasNext()) {
        Assertions.fail("Query should not have returned any results.");
      }
    }
  }

  @Test
  void queryOnNonExistingValueReturnsNoResults() throws ABException {
    try (DBConnection dbConn = new DBConnection()) {
      DbQuery dbQuery = new DbQuery(dbConn, Table.TEST_SIMPLE).setQuery(new DBQueryStmtBuilder().add("name", "bad"));

      DBResult result = dbQuery.execute();

      if (result.hasNext()) {
        Assertions.fail("Query should not have returned any results.");
      }
    }
  }
}
