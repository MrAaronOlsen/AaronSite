package com.budgetmonster.database.operations;

import com.budgetmonster.database.connection.DBConnection;

import java.sql.SQLException;
import java.sql.Statement;

public class DBInsert implements DBOperation {
  private String table;
  private DBConnection dbConn;
  private DBRecord record;

  public DBInsert(DBConnection dbConn, String table) {
    this.dbConn = dbConn;
    this.table = table;
  }

  public DBInsert addRecord(DBRecord record) {
    this.record = record;
    return this;
  }

  @Override
  public DBResult execute() throws SQLException {
    String sqlStmt = buildSqlStmt();
    Statement stmt = dbConn.getStmt();
    stmt.execute(sqlStmt);

    return new DBResult(stmt.getResultSet());
  }

  private String buildSqlStmt() {
    String stmt = "INSERT INTO " + dbConn.getSchema() + "." + table;

    String columns = record.getSqlColumns();
    String values = record.getSqlValues();

    stmt += " " + columns + " " + values + " RETURNING id;";

    return stmt;
  }
}
