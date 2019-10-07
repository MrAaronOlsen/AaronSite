package com.budgetmonster.models;

import com.budgetmonster.database.connection.DBConnection;
import com.budgetmonster.database.operations.DBRecord;
import com.budgetmonster.database.operations.DBResult;
import com.budgetmonster.database.operations.DbQuery;
import com.budgetmonster.server.TestServer;
import com.budgetmonster.utils.enums.Table;
import com.budgetmonster.utils.exceptions.ABException;
import com.budgetmonster.utils.io.Logger;
import org.junit.jupiter.api.Test;

class ModelScrubberTests extends TestServer {

  @Test
  void doTheThing() throws ABException {
    seedTest();

    try (DBConnection dbConn = new DBConnection()) {
      DbQuery query = new DbQuery(dbConn, Table.BUDGET_PERIOD_TRANSACTIONS);

      DBResult result = query.execute();

      while (result.hasNext()) {
        Logger.out(new BudgetPeriodTransaction(result.getNext()).toString(), Logger.Color.ANSI_BLUE);
      }
    }
  }

  private void seedTest() throws ABException {
    Month month = new Month()
        .setShortName("Jan")
        .setLongName("January")
        .setNumber("1");

    insertRecord(month);

    Period period = new Period()
        .setYear("2019")
        .setMonthNumber("1");

    DBRecord periodR = insertRecord(period);

    Budget budget = new Budget()
        .setName("Groceries");

    DBRecord budgetR = insertRecord(budget);

    BudgetPeriod budgetPeriod = new BudgetPeriod()
        .setAmount("40000")
        .setBudgetId(budgetR.getId())
        .setPeriodId(periodR.getId());

    DBRecord budgetPeriodR = insertRecord(budgetPeriod);

    Transaction transOne = new Transaction()
        .setAmount("5000")
        .setDate("2019-01-10")
        .setVendor("King Soopers");

    Transaction transTwo = new Transaction()
        .setAmount("14055")
        .setDate("2019-01-14")
        .setVendor("Costco");

    DBRecord transOneR = insertRecord(transOne);
    DBRecord transTwoR = insertRecord(transTwo);

    BudgetPeriodTransaction budgetPeriodTransOne = new BudgetPeriodTransaction()
        .setAmount("5000")
        .setTransactionId(transOneR.getId())
        .setBudgetPeriodId(budgetPeriodR.getId());

    BudgetPeriodTransaction budgetPeriodTransTwo = new BudgetPeriodTransaction()
        .setAmount("14055")
        .setTransactionId(transTwoR.getId())
        .setBudgetPeriodId(budgetPeriodR.getId());

    insertRecord(budgetPeriodTransOne);
    insertRecord(budgetPeriodTransTwo);
  }
}
