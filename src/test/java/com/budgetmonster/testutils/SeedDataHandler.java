package com.budgetmonster.testutils;

import com.budgetmonster.database.connection.DBConnection;
import com.budgetmonster.database.operations.DBInsert;
import com.budgetmonster.database.operations.DBRecord;
import com.budgetmonster.database.operations.DBResult;
import com.budgetmonster.models.Model;
import com.budgetmonster.utils.exceptions.ABException;
import com.budgetmonster.utils.exceptions.SimpleMessageException;

import java.util.concurrent.atomic.AtomicInteger;

public class SeedDataHandler {
  private static AtomicInteger increment = new AtomicInteger(0);

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

  public String getNextString() {
    return Integer.toString(increment.incrementAndGet());
  }
}
