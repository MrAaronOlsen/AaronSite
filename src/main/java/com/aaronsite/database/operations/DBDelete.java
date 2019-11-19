package com.aaronsite.database.operations;

import com.aaronsite.database.connection.DBConnection;
import com.aaronsite.database.statements.DBDeleteStmtBuilder;
import com.aaronsite.database.statements.DBPreparedStmt;
import com.aaronsite.database.transaction.DBResult;
import com.aaronsite.utils.enums.Table;
import com.aaronsite.utils.exceptions.DatabaseException;

public class DBDelete implements DBOperation {
  private DBConnection dbConn;
  private Table table;
  private String id;

  public DBDelete(DBConnection dbConn, Table table) {
    this.dbConn = dbConn;
    this.table = table;
  }

  public DBDelete setId(String id) {
    this.id = id;
    return this;
  }

  @Override
  public DBResult execute() throws DatabaseException {
    DBPreparedStmt stmt = new DBDeleteStmtBuilder(dbConn, table)
        .setId(id).build();

    stmt.execute();
    return stmt.getResult();
  }
}
