package com.aaronsite.server;

import com.aaronsite.database.connection.DBConnection;
import com.aaronsite.database.transaction.DBRecord;
import com.aaronsite.database.operations.DBTruncate;
import com.aaronsite.models.Model;
import com.aaronsite.testutils.dataseeder.DataSeeder;
import com.aaronsite.utils.enums.ConfigArg;
import com.aaronsite.utils.system.ConfigProperties;
import com.aaronsite.utils.enums.Table;
import com.aaronsite.utils.exceptions.ABException;
import com.aaronsite.utils.exceptions.DatabaseException;
import com.aaronsite.utils.io.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.util.concurrent.atomic.AtomicInteger;


public abstract class TestServer {
  private static final AtomicInteger testIncrement = new AtomicInteger();

  @BeforeEach
  void truncate() throws DatabaseException {
    try (DBConnection dbConn = new DBConnection()) {
      DBTruncate dbTruncate = new DBTruncate(dbConn, Table.getTestTables());
      dbTruncate.execute();
    }
  }

  @BeforeAll
  public static void startUp() {
    int testNumber = testIncrement.incrementAndGet();
    if (testNumber > 1) {
      return;
    }

    Logger.out("Starting Test Server...", Logger.Color.ANSI_CYAN);

    ConfigProperties.set(
        ConfigProperties.getSysArg(ConfigArg.DB_URL),
        ConfigProperties.getSysArg(ConfigArg.DB_USER),
        ConfigProperties.getSysArg(ConfigArg.DB_PW),
        ConfigProperties.getSysArg(ConfigArg.DB_SCHEMA)
    );
  }

  @AfterAll
  public static void shutDown() throws DatabaseException {
    try (DBConnection dbConn = new DBConnection()) {
      DBTruncate dbTruncate = new DBTruncate(dbConn, Table.getTestTables());
      dbTruncate.execute();
    }
  }

  protected String getTestSchema() {
    return ConfigProperties.getValue(ConfigArg.DB_SCHEMA);
  }

  protected DBRecord insertRecord(Model model) throws ABException {
    return DataSeeder.insertRecord(model);
  }
}
