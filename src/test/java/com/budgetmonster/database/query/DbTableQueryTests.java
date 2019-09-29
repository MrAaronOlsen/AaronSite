package com.budgetmonster.database.query;

import com.budgetmonster.database.connection.DBConnection;
import com.budgetmonster.server.TestServer;
import com.budgetmonster.utils.Logger;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

public class DbTableQueryTests extends TestServer {

  @Test
  void simpleQuery() throws SQLException {
    try (DBConnection conn = new DBConnection()) {
      DbTableQuery query = new DbTableQuery(conn, "budgets");
      query.execute();

      while (query.hasNext()) {
        Logger.err(query.getNext().toString());
      }
    }
  }
}
