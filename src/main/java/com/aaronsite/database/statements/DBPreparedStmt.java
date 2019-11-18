package com.aaronsite.database.statements;

import com.aaronsite.database.transaction.DBResult;
import com.aaronsite.utils.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.aaronsite.utils.exceptions.DatabaseException.Code.FAILED_TO_CREATE_STATEMENT;
import static com.aaronsite.utils.exceptions.DatabaseException.Code.FAILED_TO_GET_RESULT_SET;
import static com.aaronsite.utils.exceptions.DatabaseException.Code.FAILED_TO_SET_VALUE;
import static com.aaronsite.utils.exceptions.DatabaseException.Code.TABLE_DOES_NOT_EXIST;
import static com.aaronsite.utils.exceptions.DatabaseException.Code.UNKNOWN_COLUMN;
import static com.aaronsite.utils.exceptions.DatabaseException.Code.UNKNOWN_SQL_ERROR;

public class DBPreparedStmt {
  private PreparedStatement stmt;
  private String sqlStmt;

  public DBPreparedStmt(Connection conn, String sqlStmt) throws DatabaseException {
    try {
      this.sqlStmt = sqlStmt;
      this.stmt = conn.prepareStatement(sqlStmt);
    } catch (SQLException e) {
      throw new DatabaseException(FAILED_TO_CREATE_STATEMENT).sqlEx(e);
    }
  }

  public DBPreparedStmt execute() throws DatabaseException {

    try {
      stmt.execute();
    } catch (SQLException e) {
      throwDBException(e);
    }

    return this;
  }

  void set(int i, String value) throws DatabaseException {
    try {
      stmt.setString(i, value);
    } catch (SQLException e) {
      throw new DatabaseException(FAILED_TO_SET_VALUE, Integer.toString(i)).sqlEx(e);
    }
  }

  public DBResult getResult() throws DatabaseException {
    try {
      return new DBResult(stmt.getResultSet());
    } catch (SQLException e) {
      throw new DatabaseException(FAILED_TO_GET_RESULT_SET).sqlEx(e);
    }
  }

  private void throwDBException(SQLException e) throws DatabaseException {
    String state = e.getSQLState();
    DatabaseException dbEx;

    switch (state) {
      case "42P01":
        dbEx = new DatabaseException(TABLE_DOES_NOT_EXIST, "");
        break;
      case "42703":
        dbEx = new DatabaseException(UNKNOWN_COLUMN);
        break;
      default:
        dbEx = new DatabaseException(UNKNOWN_SQL_ERROR, state);
    }

    throw dbEx.sqlStmt(sqlStmt).sqlEx(e);
  }

  @Override
  public String toString() {
    return stmt.toString();
  }
}
