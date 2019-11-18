package com.aaronsite.database.statements;

import com.aaronsite.database.operations.DBOperation;
import com.aaronsite.database.transaction.DBResult;
import com.aaronsite.utils.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static com.aaronsite.utils.exceptions.DatabaseException.Code.FAILED_TO_CREATE_STATEMENT;
import static com.aaronsite.utils.exceptions.DatabaseException.Code.FAILED_TO_GET_RESULT_SET;
import static com.aaronsite.utils.exceptions.DatabaseException.Code.TABLE_DOES_NOT_EXIST;
import static com.aaronsite.utils.exceptions.DatabaseException.Code.UNKNOWN_COLUMN;
import static com.aaronsite.utils.exceptions.DatabaseException.Code.UNKNOWN_SQL_ERROR;

public class DBStatement {
  private DBOperation operation;
  private Statement stmt;
  private String sql;

  public DBStatement(Connection conn) throws DatabaseException {
    try {
      this.stmt = conn.createStatement();
    } catch (SQLException e) {
      throw new DatabaseException(FAILED_TO_CREATE_STATEMENT).sqlEx(e);
    }
  }

  public DBStatement execute(DBOperation operation) throws DatabaseException {
    this.operation = operation;
    this.sql = operation.toString();

    try {
      stmt.execute(sql);
    } catch (SQLException e) {
      throwDBException(e);
    }

    return this;
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

    throw dbEx.sqlStmt(sql).sqlEx(e);
  }
}
