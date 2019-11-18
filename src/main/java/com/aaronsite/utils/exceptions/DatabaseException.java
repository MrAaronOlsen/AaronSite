package com.aaronsite.utils.exceptions;

import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;

public class DatabaseException extends ABException {
  public enum Code {
    // Initializer errors
    FAILED_TO_MAKE_CONNECTION("Failed to make connection."),
    FAILED_TO_CREATE_STATEMENT("Failed to create sql statement."),
    FAILED_TO_SET_VALUE("Failed to set value at index %s."),
    FAILED_TO_GET_RESULT_SET("Failed to get result set."),
    FAILED_TO_GET_CONN_METADATA("Failed to get connection metadata."),
    FAILED_TO_GET_TABLE_METADATA("FAiled to get table metadata."),
    FAILED_TO_CLOSE_CONNECTION("Failed to close connection."),

    // Execution errors
    UNKNOWN_SQL_ERROR("An unknown sql error was encounter. SQL State: %s."),
    TABLE_DOES_NOT_EXIST("Table %s does not exist."),
    UNKNOWN_COLUMN("An unknown column was encountered."),

    // Result errors
    FAILED_TO_GET_NEXT_RESULT("Failed to get next result."),
    FAILED_TO_BUILD_RESULT_METADATA("Failed to build result metadata."),
    FAILED_TO_BUILD_RESULT_DATA("Failed to build result data.");

    private String message;

    Code(String message) {
      this.message = message;
    }

    public String format(String... args) {
      return String.format(message, args);
    }
  }

  private Code code;
  private String[] args;
  private String sqlStmt;
  private SQLException sqlEx;

  public DatabaseException(Code code, String... args) {
    this.code = code;
    this.args = args;
  }

  public DatabaseException sqlStmt(String sqlStm) {
    this.sqlStmt = sqlStm;
    return this;
  }

  public DatabaseException sqlEx(SQLException sqlEx) {
    this.sqlEx = sqlEx;
    return this;
  }

  public Code getCode() {
    return code;
  }

  public SQLException getSqlEx() {
    return sqlEx;
  }

  @Override
  public String getMessage() {
    String message = "\nCODE: " + code.name() + " | ERROR: " + code.format(args);
    if (StringUtils.isNotEmpty(sqlStmt)) {
      message += "\nSQL Stmt: " + sqlStmt;
    }

    if (sqlEx != null) {
      message += "\n" + "SQL State: " + sqlEx.getSQLState();
      message += "\n" + "SQL Message: " + sqlEx.getMessage();
    }

    return message;
  }
}
