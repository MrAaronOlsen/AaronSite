package com.budgetmonster.database.connection;

import com.budgetmonster.utils.enums.ConfigArg;
import com.budgetmonster.utils.ConfigProperties;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection implements AutoCloseable {
  private static final String SCHEMA = ConfigProperties.getValue(ConfigArg.DB_SCHEMA);
  private Connection conn;

  public DBConnection() throws SQLException {
    conn = DriverManager.getConnection(
        ConfigProperties.getValue(ConfigArg.DB_URL),
        ConfigProperties.getValue(ConfigArg.DB_USER),
        ConfigProperties.getValue(ConfigArg.DB_PW));
  }

  public Statement getStmt() throws SQLException {
    return conn.createStatement();
  }

  public DatabaseMetaData getDbMetadata() throws SQLException {
    return conn.getMetaData();
  }

  public String getSchema() {
    return SCHEMA;
  }

  @Override
  public void close() throws SQLException {
    conn.close();
  }
}
