package com.aaronsite.database.operations;

import com.aaronsite.database.connection.DBConnection;
import com.aaronsite.database.statements.DBPreparedStmt;
import com.aaronsite.database.statements.DBUpdateStmtBuilder;
import com.aaronsite.database.statements.DBWhereStmtBuilder;
import com.aaronsite.database.transaction.DBRecord;
import com.aaronsite.database.transaction.DBResult;
import com.aaronsite.models.Model;
import com.aaronsite.utils.enums.Table;
import com.aaronsite.utils.exceptions.DatabaseException;

public class DBUpdate implements DBOperation {
  private Table table;
  private DBConnection dbConn;
  private DBRecord record;
  private DBWhereStmtBuilder where;

  public DBUpdate(DBConnection dbConn, Table table) {
    this.dbConn = dbConn;
    this.table = table;
  }

  public DBUpdate setQuery(DBWhereStmtBuilder where) {
    this.where = where;
    return this;
  }

  public DBUpdate setQuery(String id) {
    this.where = new DBWhereStmtBuilder(id);
    return this;
  }

  public DBUpdate setRecord(DBRecord record) {
    this.record = record;
    return this;
  }

  public DBUpdate setRecord(Model model) {
    this.record = model.buildRecord();
    return this;
  }

  @Override
  public DBResult execute() throws DatabaseException {
    DBPreparedStmt dbStmt = new DBUpdateStmtBuilder(dbConn, table)
        .setRecord(record)
        .setWhere(where)
        .build();

    dbStmt.execute();
    return dbStmt.getResult();
  }
}
