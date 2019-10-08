package com.budgetmonster.testutils.dataseeder;

import com.budgetmonster.database.connection.DBConnection;
import com.budgetmonster.database.operations.DBInsert;
import com.budgetmonster.database.operations.DBRecord;
import com.budgetmonster.database.operations.DBResult;
import com.budgetmonster.database.operations.DBUpdate;
import com.budgetmonster.models.Model;
import com.budgetmonster.utils.exceptions.ABException;
import com.budgetmonster.utils.exceptions.SimpleMessageException;
import org.bson.Document;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

abstract class DataSeeder {
  private static final Class RESOURCE_LOADER = BudgetPeriodSeeder.class;

  public abstract void seed() throws ABException;
  public abstract Model get(String key);
  public abstract Map<String, ? extends Model> getMap();
  public abstract List<? extends Model> getList();

  static Document loadResourceAsDocument(String name) throws ABException {
    FileInputStream file;

    try {
      file = new FileInputStream(new File(RESOURCE_LOADER.getResource("/" + name).getFile()));
    } catch (IOException ioEx) {
      throw new SimpleMessageException(String.format("Failed to load file %s from resource. ERROR: %s", name, ioEx.getMessage()));
    }

    try {
      return Document.parse(new String(file.readAllBytes(), StandardCharsets.UTF_8));
    } catch (IOException ioEx) {
      throw new SimpleMessageException(String.format("Failed to parse file %s to Document. ERROR: %s", name, ioEx.getMessage()));
    }
  }

  static DBRecord insertRecord(Model model) throws ABException {
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

  static DBRecord updateRecord(Model model) throws ABException {
    try (DBConnection dbConn = new DBConnection()) {
      DBUpdate dbUpdate = new DBUpdate(dbConn, model.getTable())
          .addQueryId(model.getId())
          .addRecord(model);

      DBResult result = dbUpdate.execute();

      if (result.hasNext()) {
        return result.getNext();
      } else {
        throw new SimpleMessageException("Insert did not insert any data for model: " + model.toString());
      }
    }
  }
}
