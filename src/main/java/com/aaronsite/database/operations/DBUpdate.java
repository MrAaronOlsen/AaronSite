package com.aaronsite.database.operations;

import com.aaronsite.database.connection.DBConnection;
import com.aaronsite.database.statements.DBPreparedStmt;
import com.aaronsite.database.statements.DBQueryStmtBuilder;
import com.aaronsite.database.statements.DBUpdateStmtBuilder;
import com.aaronsite.database.transaction.DBRecord;
import com.aaronsite.database.transaction.DBResult;
import com.aaronsite.models.Model;
import com.aaronsite.utils.enums.Table;
import com.aaronsite.utils.exceptions.DatabaseException;

import static com.aaronsite.models.System.ID;

public class DBUpdate implements DBOperation {
  private Table table;
  private DBConnection dbConn;
  private DBRecord record;
  private DBQueryStmtBuilder query;

  public DBUpdate(DBConnection dbConn, Table table) {
    this.dbConn = dbConn;
    this.table = table;
  }

  public DBUpdate addQuery(DBQueryStmtBuilder query) {
    this.query = query;
    return this;
  }

  public DBUpdate addQueryId(String id) {
    this.query = new DBQueryStmtBuilder().add(ID, id);
    return this;
  }

  public DBUpdate addRecord(DBRecord record) {
    this.record = record;
    return this;
  }

  public DBUpdate addRecord(Model model) {
    this.record = model.buildRecord();
    return this;
  }

  @Override
  public DBResult execute() throws DatabaseException {
    DBPreparedStmt dbStmt = new DBUpdateStmtBuilder(dbConn, table)
        .setQuery(query)
        .build(record);

    dbStmt.execute();
    return dbStmt.getResult();
  }

  @Override
  public String toString() {
    return "UPDATE " + dbConn.getSchema() + "." + table + " " + record.getSqlUpdate() + " " + query + " RETURNING *;";
  }
}
