package com.budgetmonster.server;

import com.budgetmonster.database.operations.DBRecord;
import com.budgetmonster.models.Model;
import com.budgetmonster.testutils.DBTruncateAll;
import com.budgetmonster.testutils.SeedDataHandler;
import com.budgetmonster.utils.enums.ConfigArg;
import com.budgetmonster.utils.ConfigProperties;
import com.budgetmonster.utils.exceptions.ABException;
import com.budgetmonster.utils.exceptions.DBException;
import com.budgetmonster.utils.io.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;


public abstract class TestServer {
  private SeedDataHandler seedDataHandler = new SeedDataHandler();

  @BeforeEach
  void truncate() throws DBException {
    DBTruncateAll.execute();
  }

  @BeforeAll
  public static void startUp() {
    Logger.out("Starting Test Server...", Logger.Color.ANSI_CYAN);
    StartUp.main(
        ConfigProperties.getSysArg(ConfigArg.DB_URL),
        ConfigProperties.getSysArg(ConfigArg.DB_USER),
        ConfigProperties.getSysArg(ConfigArg.DB_PW),
        ConfigProperties.getSysArg(ConfigArg.DB_SCHEMA)
    );
  }

  @AfterAll
  public static void shutDown() {
    Logger.out("Shutting Down Test Server...", Logger.Color.ANSI_CYAN);
  }

  protected String unique(String value) {
    return value + seedDataHandler.getNextString();
  }

  protected DBRecord insertRecord(Model model) throws ABException {
    return seedDataHandler.insertRecord(model);
  }
}
