package com.budgetmonster.database.operations;

import com.budgetmonster.database.connection.DBConnection;

import java.sql.SQLException;
import java.sql.Statement;

public class DbQuery implements DBOperation {
  private String table;
  private DBConnection dbConn;
  private DBQueryBuilder query;

  public DbQuery(DBConnection dbConn, String table) {
    this.dbConn = dbConn;
    this.table = table;
  }

  @Override
  public DBResult execute() throws SQLException {
    String sqlStmt = buildSqlStmt();
    Statement stmt = dbConn.getStmt();

    return new DBResult(stmt.executeQuery(sqlStmt));
  }

  public DbQuery setQuery(DBQueryBuilder query) {
    this.query = query;
    return this;
  }

  public DbQuery setIdQuery(String id) {
    this.query = new DBQueryBuilder().add("id", id);
    return this;
  }

  private String buildSqlStmt() {
    String stmt = "SELECT * FROM " + dbConn.getSchema() + "." + table;

    if (query != null) {
      stmt += " WHERE " + query.buildSqlStmt();
    }

    return stmt;
  }
}
