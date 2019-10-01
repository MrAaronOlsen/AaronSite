package com.budgetmonster.testutils;

import com.budgetmonster.server.TestServer;
import com.budgetmonster.utils.ConfigProperties;
import com.budgetmonster.utils.enums.ConfigArg;
import com.budgetmonster.utils.enums.Table;
import com.budgetmonster.utils.io.Logger;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class ClearDatabaseTests extends TestServer {

  @Test
  void getAllTables() {
    String schema = ConfigProperties.getValue(ConfigArg.DB_SCHEMA);
    String tables =
        Arrays.stream(Table.values()).map(table -> schema + "." + table.getName()).reduce((acu, table) -> table + ", " + acu).get();
    Logger.err(tables);
  }
}
