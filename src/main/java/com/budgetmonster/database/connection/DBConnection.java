package com.budgetmonster.database.connection;

import com.budgetmonster.database.operations.DBStatement;
import com.budgetmonster.utils.enums.ConfigArg;
import com.budgetmonster.utils.ConfigProperties;
import com.budgetmonster.utils.exceptions.DBException;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.budgetmonster.utils.exceptions.DBException.Code.FAILED_TO_CLOSE_CONNECTION;
import static com.budgetmonster.utils.exceptions.DBException.Code.FAILED_TO_GET_CONN_METADATA;
import static com.budgetmonster.utils.exceptions.DBException.Code.FAILED_TO_MAKE_CONNECTION;

public class DBConnection implements AutoCloseable {
  private static final String SCHEMA = ConfigProperties.getValue(ConfigArg.DB_SCHEMA);
  private Connection conn;

  public DBConnection() throws DBException {
    try {
      conn = DriverManager.getConnection(
          ConfigProperties.getValue(ConfigArg.DB_URL),
          ConfigProperties.getValue(ConfigArg.DB_USER),
          ConfigProperties.getValue(ConfigArg.DB_PW));
    } catch (SQLException e) {
      throw new DBException(FAILED_TO_MAKE_CONNECTION).sqlEx(e);
    }

  }

  public DBStatement getDbStmt() throws DBException {
    return new DBStatement(conn);
  }

  public DatabaseMetaData getDbMetadata() throws DBException {
    try {
      return conn.getMetaData();
    } catch (SQLException e) {
      throw new DBException(FAILED_TO_GET_CONN_METADATA).sqlEx(e);
    }
  }

  public String getSchema() {
    return SCHEMA;
  }

  @Override
  public void close() throws DBException {
    try {
      conn.close();
    } catch (SQLException e) {
      throw new DBException(FAILED_TO_CLOSE_CONNECTION).sqlEx(e);
    }
  }
}
