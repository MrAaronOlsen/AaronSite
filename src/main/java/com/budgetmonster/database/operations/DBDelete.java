package com.budgetmonster.database.operations;

import com.budgetmonster.database.connection.DBConnection;
import com.budgetmonster.utils.exceptions.DBException;

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
  public DBResult execute() throws DBException {
    String sqlStmt = buildSqlStmt();
    DBStatement stmt = dbConn.getDbStmt();
    stmt.execute(sqlStmt);

    return stmt.getResult();
  }

  private String buildSqlStmt() {
    return "DELETE from " + dbConn.getSchema() + "." + table + " WHERE id=" + id + " RETURNING id;";
  }
}
