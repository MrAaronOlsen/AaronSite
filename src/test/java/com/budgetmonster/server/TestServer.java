package com.budgetmonster.server;

import com.budgetmonster.utils.ConfigArg;
import com.budgetmonster.utils.ConfigProperties;
import com.budgetmonster.utils.Logger;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public abstract class TestServer {

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
}
