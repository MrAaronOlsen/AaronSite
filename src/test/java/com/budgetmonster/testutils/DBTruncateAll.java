package com.budgetmonster.testutils;

import com.budgetmonster.database.connection.DBConnection;
import com.budgetmonster.database.operations.DBStatement;
import com.budgetmonster.utils.enums.Table;
import com.budgetmonster.utils.exceptions.DBException;

public class DBTruncateAll {

  public static void execute() throws DBException {
    try(DBConnection dbConn = new DBConnection()) {
      String tables = Table.getActiveTables().stream().map(t -> dbConn.getSchema() + "." + t.getName())
          .reduce((table, acu) -> table + ", " + acu).get();

      DBStatement dbStmt = dbConn.getDbStmt();
      dbStmt.execute("TRUNCATE " + tables);
    }
  }
}
