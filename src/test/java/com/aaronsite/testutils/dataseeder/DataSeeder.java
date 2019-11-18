package com.aaronsite.testutils.dataseeder;

import com.aaronsite.database.connection.DBConnection;
import com.aaronsite.database.operations.DBInsert;
import com.aaronsite.database.transaction.DBRecord;
import com.aaronsite.database.transaction.DBResult;
import com.aaronsite.database.operations.DBUpdate;
import com.aaronsite.models.Model;
import com.aaronsite.utils.exceptions.ABException;
import com.aaronsite.utils.exceptions.SimpleMessageException;
import org.bson.Document;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class DataSeeder {
  private static final Class RESOURCE_LOADER = DataSeeder.class;

  public static Document loadResourceAsDocument(String name) throws ABException {
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
