package com.budgetmonster.database.query;

import com.budgetmonster.database.connection.DBConnection;
import com.budgetmonster.database.metadata.ColumnMetadata;
import com.budgetmonster.database.metadata.ResultMetadata;
import com.budgetmonster.utils.ConfigArg;
import com.budgetmonster.utils.ConfigProperties;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class DbTableQuery {
  private String table;
  private Statement stmt;
  private ResultSet result;
  private ResultMetadata resultMetadata;

  public DbTableQuery(DBConnection dbConn, String table) throws SQLException {
    this.stmt = dbConn.getStmt();
    this.table = table;
  }

  public void execute() throws SQLException {
    result = stmt.executeQuery("SELECT * FROM " + ConfigProperties.getValue(ConfigArg.DB_SCHEMA) + "." + table);
  }

  public boolean hasNext() throws SQLException {
    return result.next();
  }

  public Map<String, String> getNext() throws SQLException {
    if (resultMetadata == null) {
      resultMetadata = new ResultMetadata(result);
    }

    Map<String, String> record = new HashMap<>();

    for (ColumnMetadata column : resultMetadata.getColumns()) {
      String value = result.getString(column.getName());
      record.put(column.getName(), value);
    }

    return record;
  }
}
