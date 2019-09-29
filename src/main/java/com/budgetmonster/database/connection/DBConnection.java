package com.budgetmonster.database.connection;

import com.budgetmonster.utils.ConfigArg;
import com.budgetmonster.utils.ConfigProperties;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection implements AutoCloseable {
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

  @Override
  public void close() throws SQLException {
    conn.close();
  }
}
