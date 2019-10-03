package com.budgetmonster.server;

import com.budgetmonster.database.connection.DBConnection;
import com.budgetmonster.database.operations.DBRecord;
import com.budgetmonster.database.operations.DBTruncate;
import com.budgetmonster.models.Model;
import com.budgetmonster.testutils.SeedDataHandler;
import com.budgetmonster.utils.enums.ConfigArg;
import com.budgetmonster.utils.ConfigProperties;
import com.budgetmonster.utils.enums.Table;
import com.budgetmonster.utils.exceptions.ABException;
import com.budgetmonster.utils.exceptions.DBException;
import com.budgetmonster.utils.io.Logger;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.util.concurrent.atomic.AtomicInteger;


public abstract class TestServer {
  private static SeedDataHandler seedDataHandler = new SeedDataHandler();
  private static AtomicInteger testIncrement = new AtomicInteger();

  @BeforeEach
  void truncate() throws DBException {
    try (DBConnection dbConn = new DBConnection()) {
      DBTruncate dbTruncate = new DBTruncate(dbConn, Table.getActiveTables());
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
    StartUp.main(
        ConfigProperties.getSysArg(ConfigArg.DB_URL),
        ConfigProperties.getSysArg(ConfigArg.DB_USER),
        ConfigProperties.getSysArg(ConfigArg.DB_PW),
        ConfigProperties.getSysArg(ConfigArg.DB_SCHEMA)
    );

  }

  protected String unique(String value) {
    return value + seedDataHandler.getNextString();
  }

  protected DBRecord insertRecord(Model model) throws ABException {
    return seedDataHandler.insertRecord(model);
  }
}
