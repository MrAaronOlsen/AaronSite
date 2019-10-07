package com.budgetmonster.testutils.dataseeder;

import com.budgetmonster.database.connection.DBConnection;
import com.budgetmonster.database.operations.DBInsert;
import com.budgetmonster.database.operations.DBRecord;
import com.budgetmonster.database.operations.DBResult;
import com.budgetmonster.models.Budget;
import com.budgetmonster.models.BudgetPeriod;
import com.budgetmonster.models.BudgetPeriodTransaction;
import com.budgetmonster.models.Model;
import com.budgetmonster.models.Month;
import com.budgetmonster.models.Period;
import com.budgetmonster.models.Transaction;
import com.budgetmonster.utils.exceptions.ABException;
import com.budgetmonster.utils.exceptions.SimpleMessageException;
import org.bson.Document;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;

public class SeedDataHandler {
  private static AtomicInteger increment = new AtomicInteger(0);
  private static final Class RESOURCE_LOADER = SeedDataHandler.class;

  private Document loadResourceAsDocument(String name) throws ABException {
    FileInputStream file;

    try {
      file = new FileInputStream(new File(RESOURCE_LOADER.getResource("months").getFile()));
    } catch (IOException ioEx) {
      throw new SimpleMessageException(String.format("Failed to load file %s from resource. ERROR: %s", name, ioEx.getMessage()));
    }

    try {
      return Document.parse(new String(file.readAllBytes(), StandardCharsets.UTF_8));
    } catch (IOException ioEx) {
      throw new SimpleMessageException(String.format("Failed to parse file %s to Document. ERROR: %s", name, ioEx.getMessage()));
    }
  }

  public void runDataSeedOne() throws ABException {
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

  public DBRecord insertRecord(Model model) throws ABException {
    try (DBConnection dbConn = new DBConnection()) {
      DBInsert dbInsert = new DBInsert(dbConn, model.getTable());
      dbInsert.addRecord(model);

      DBResult result = dbInsert.execute();

      if (result.hasNext()) {
        return result.getNext();
      } else {
        throw new SimpleMessageException("Insert did not insert any data for model: " + model.toString());
      }
    }
  }

  public void seedMonths() {
    Month month = new Month()
        .setShortName("Jan")
        .setLongName("January")
        .setNumber("1");
  }

  public String getNextString() {
    return Integer.toString(increment.incrementAndGet());
  }
}
