package com.aaronsite.database.operations;

import com.aaronsite.database.connection.DBConnection;
import com.aaronsite.database.statements.DBWhereStmtBuilder;
import com.aaronsite.database.transaction.DBResult;
import com.aaronsite.models.TestSimple;
import com.aaronsite.server.TestServer;
import com.aaronsite.utils.enums.Table;
import com.aaronsite.utils.exceptions.ABException;
import com.aaronsite.utils.exceptions.DatabaseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.aaronsite.models.TestSimple.NAME;
import static com.aaronsite.utils.exceptions.DatabaseException.Code.DO_COUNT_NOT_SET;

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
      DbQuery dbQuery = new DbQuery(dbConn, Table.TEST_SIMPLE).setQuery(new DBWhereStmtBuilder(NAME, "test"));

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
      DbQuery dbQuery = new DbQuery(dbConn, Table.TEST_SIMPLE).setQuery(new DBWhereStmtBuilder(NAME, "test"));

      DBResult result = dbQuery.execute();

      int count = 0;
      while (result.hasNext()) {
        count ++;
      }

      Assertions.assertEquals(2, count);
    }
  }

  @Test
  void queryWithCountNone() throws ABException {

    try (DBConnection dbConn = new DBConnection()) {
      DbQuery dbQuery = new DbQuery(dbConn, Table.TEST_SIMPLE)
          .setQuery(new DBWhereStmtBuilder(NAME, "test"))
          .doCount();

      DBResult result = dbQuery.execute();
      Assertions.assertEquals(0, result.getCount());
    }
  }

  @Test
  void queryWithCountSingle() throws ABException {
    insertRecord(new TestSimple().setName("test"));

    try (DBConnection dbConn = new DBConnection()) {
      DbQuery dbQuery = new DbQuery(dbConn, Table.TEST_SIMPLE)
          .setQuery(new DBWhereStmtBuilder(NAME, "test"))
          .doCount();

      DBResult result = dbQuery.execute();
      Assertions.assertEquals(1, result.getCount());
    }
  }

  @Test
  void queryWithCountMultiple() throws ABException {
    insertRecord(new TestSimple().setName("test"));
    insertRecord(new TestSimple().setName("test"));

    try (DBConnection dbConn = new DBConnection()) {
      DbQuery dbQuery = new DbQuery(dbConn, Table.TEST_SIMPLE)
          .setQuery(new DBWhereStmtBuilder(NAME, "test"))
          .doCount();

      DBResult result = dbQuery.execute();
      Assertions.assertEquals(2, result.getCount());
    }
  }

  @Test
  void getCountWithoutDoCountThrowsException() throws ABException {
    try (DBConnection dbConn = new DBConnection()) {
      DbQuery dbQuery = new DbQuery(dbConn, Table.TEST_SIMPLE)
          .setQuery(new DBWhereStmtBuilder(NAME, "test"));

      DBResult result = dbQuery.execute();

      try {
        result.getCount();
        Assertions.fail("Should have thrown an exception.");
      } catch (DatabaseException e) {
        Assertions.assertEquals(e.getCode(), DO_COUNT_NOT_SET);
      }
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
      DbQuery dbQuery = new DbQuery(dbConn, Table.TEST_SIMPLE).setQuery(new DBWhereStmtBuilder("name", "bad"));

      DBResult result = dbQuery.execute();

      if (result.hasNext()) {
        Assertions.fail("Query should not have returned any results.");
      }
    }
  }
}
