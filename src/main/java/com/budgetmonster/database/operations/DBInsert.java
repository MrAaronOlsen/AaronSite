package com.budgetmonster.database.operations;

import com.budgetmonster.database.connection.DBConnection;
import com.budgetmonster.models.Model;
import com.budgetmonster.utils.enums.Table;
import com.budgetmonster.utils.exceptions.DBException;

public class DBInsert implements DBOperation {
  private Table table;
  private DBConnection dbConn;
  private DBRecord record;

  public DBInsert(DBConnection dbConn, Table table) {
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
    DBStatement dbStmt = dbConn.getDbStmt()
        .execute(this);

    return dbStmt.getResult();
  }

  @Override
  public String toString() {
    String stmt = "INSERT INTO " + dbConn.getSchema() + "." + table;

    String columns = record.getSqlColumns();
    String values = record.getSqlValues();

    stmt += " " + columns + " " + values + " RETURNING *;";

    return stmt;
  }
}
