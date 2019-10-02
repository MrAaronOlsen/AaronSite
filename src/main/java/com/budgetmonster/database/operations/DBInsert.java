package com.budgetmonster.database.operations;

import com.budgetmonster.database.connection.DBConnection;
import com.budgetmonster.models.Model;
import com.budgetmonster.utils.exceptions.DBException;

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

  public DBInsert addRecord(Model model) {
    this.record = model.buildRecord();
    return this;
  }

  @Override
  public DBResult execute() throws DBException {
    String sqlStmt = buildSqlStmt();
    DBStatement dbStmt = dbConn.getDbStmt();
    dbStmt.execute(sqlStmt);

    return dbStmt.getResult();
  }

  private String buildSqlStmt() {
    String stmt = "INSERT INTO " + dbConn.getSchema() + "." + table;

    String columns = record.getSqlColumns();
    String values = record.getSqlValues();

    stmt += " " + columns + " " + values + " RETURNING id;";

    return stmt;
  }
}
