package com.budgetmonster.database.operations;

import com.budgetmonster.database.connection.DBConnection;

import java.sql.SQLException;
import java.sql.Statement;

public class DBDelete implements DBOperation {
  private DBConnection dbConn;
  private String table;
  private String id;

  public DBDelete(DBConnection dbConn, String table) {
    this.dbConn = dbConn;
    this.table = table;
  }

  public DBDelete setId(String id) {
    this.id = id;
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
    return "DELETE from " + dbConn.getSchema() + "." + table + " WHERE id=" + id + " RETURNING id;";
  }
}
