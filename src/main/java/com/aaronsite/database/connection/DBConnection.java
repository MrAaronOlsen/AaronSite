package com.aaronsite.database.connection;

import com.aaronsite.database.statements.DBStatement;
import com.aaronsite.database.statements.DBPreparedStmt;
import com.aaronsite.utils.enums.ConfigArg;
import com.aaronsite.utils.system.ConfigProperties;
import com.aaronsite.utils.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.aaronsite.utils.exceptions.DatabaseException.Code.FAILED_TO_CLOSE_CONNECTION;
import static com.aaronsite.utils.exceptions.DatabaseException.Code.FAILED_TO_GET_CONN_METADATA;
import static com.aaronsite.utils.exceptions.DatabaseException.Code.FAILED_TO_MAKE_CONNECTION;

public class DBConnection implements AutoCloseable {
  private static final String SCHEMA = ConfigProperties.getValue(ConfigArg.DB_SCHEMA);
  private Connection conn;

  public DBConnection() throws DatabaseException {
    try {
      conn = DriverManager.getConnection(
          ConfigProperties.getValue(ConfigArg.DB_URL),
          ConfigProperties.getValue(ConfigArg.DB_USER),
          ConfigProperties.getValue(ConfigArg.DB_PW));
    } catch (SQLException e) {
      throw new DatabaseException(FAILED_TO_MAKE_CONNECTION).sqlEx(e);
    }

  }

  public DBStatement getDbStmt() throws DatabaseException {
    return new DBStatement(conn);
  }

  public DBPreparedStmt getPreparedStmt(String sqlStmt) throws DatabaseException {
    return new DBPreparedStmt(conn, sqlStmt);
  }

  public DatabaseMetaData getDbMetadata() throws DatabaseException {
    try {
      return conn.getMetaData();
    } catch (SQLException e) {
      throw new DatabaseException(FAILED_TO_GET_CONN_METADATA).sqlEx(e);
    }
  }

  public String getSchema() {
    return SCHEMA;
  }

  @Override
  public void close() throws DatabaseException {
    try {
      conn.close();
    } catch (SQLException e) {
      throw new DatabaseException(FAILED_TO_CLOSE_CONNECTION).sqlEx(e);
    }
  }
}
