package com.aaronsite.testutils.dataseeder;

import com.aaronsite.database.connection.DBConnection;
import com.aaronsite.database.operations.DBInsert;
import com.aaronsite.database.operations.DBUpdate;
import com.aaronsite.database.transaction.DBRecord;
import com.aaronsite.database.transaction.DBResult;
import com.aaronsite.models.Model;
import com.aaronsite.utils.exceptions.ABException;
import com.aaronsite.utils.exceptions.SimpleMessageException;

public class DataSeeder {

  public static DBRecord insertRecord(Model model) throws ABException {
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

  public static DBRecord updateRecord(Model model) throws ABException {
    try (DBConnection dbConn = new DBConnection()) {
      DBUpdate dbUpdate = new DBUpdate(dbConn, model.getTable())
          .setQuery(model.getId())
          .setRecord(model);

      DBResult result = dbUpdate.execute();

      if (result.hasNext()) {
        return result.getNext();
      } else {
        throw new SimpleMessageException("Insert did not insert any data for model: " + model.toString());
      }
    }
  }
}
