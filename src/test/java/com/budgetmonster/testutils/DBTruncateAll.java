package com.budgetmonster.testutils;

import com.budgetmonster.database.connection.DBConnection;
import com.budgetmonster.utils.enums.Table;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;

public class DBTruncateAll {

  public static void execute() throws SQLException {
    try(DBConnection dbConn = new DBConnection()) {
      String tables = Arrays.stream(Table.values()).map(t -> dbConn.getSchema() + "." + t.getName())
          .reduce((table, acu) -> table + ", " + acu).get();

      Statement stmt = dbConn.getStmt();
      stmt.execute("TRUNCATE " + tables);
    }
  }
}
