package com.budgetmonster.database.operations;

import com.budgetmonster.utils.exceptions.DBException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static com.budgetmonster.utils.exceptions.DBException.Code.FAILED_TO_CREATE_STATEMENT;
import static com.budgetmonster.utils.exceptions.DBException.Code.FAILED_TO_GET_RESULT_SET;
import static com.budgetmonster.utils.exceptions.DBException.Code.TABLE_DOES_NOT_EXIST;
import static com.budgetmonster.utils.exceptions.DBException.Code.UNKNOWN_COLUMN;
import static com.budgetmonster.utils.exceptions.DBException.Code.UNKNOWN_SQL_ERROR;

public class DBStatement {
  private DBOperation operation;
  private Statement stmt;
  private String sql;

  public DBStatement(Connection conn) throws DBException {
    try {
      this.stmt = conn.createStatement();
    } catch (SQLException e) {
      throw new DBException(FAILED_TO_CREATE_STATEMENT).sqlEx(e);
    }
  }

  public DBStatement execute(DBOperation operation) throws DBException {
    this.operation = operation;
    this.sql = operation.toString();

    try {
      stmt.execute(sql);
    } catch (SQLException e) {
      throwDBException(e);
    }

    return this;
  }

  public DBResult getResult() throws DBException {
    try {
      return new DBResult(stmt.getResultSet());
    } catch (SQLException e) {
      throw new DBException(FAILED_TO_GET_RESULT_SET).sqlEx(e);
    }
  }

  private void throwDBException(SQLException e) throws DBException {
    String state = e.getSQLState();
    DBException dbEx;

    switch (state) {
      case "42P01":
        dbEx = new DBException(TABLE_DOES_NOT_EXIST, "");
        break;
      case "42703":
        dbEx = new DBException(UNKNOWN_COLUMN);
        break;
      default:
        dbEx = new DBException(UNKNOWN_SQL_ERROR, state);
    }

    throw dbEx.sqlStmt(sql).sqlEx(e);
  }
}
