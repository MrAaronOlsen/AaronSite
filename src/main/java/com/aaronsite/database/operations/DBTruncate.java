package com.aaronsite.database.operations;

import com.aaronsite.database.connection.DBConnection;
import com.aaronsite.database.statements.DBPreparedStmt;
import com.aaronsite.database.statements.DBTruncateStmtBuilder;
import com.aaronsite.database.transaction.DBResult;
import com.aaronsite.utils.enums.Table;
import com.aaronsite.utils.exceptions.DatabaseException;

import java.util.List;

public class DBTruncate implements DBOperation {
  private List<Table> tables;
  private DBConnection dbConn;

  public DBTruncate(DBConnection dbConn, List<Table> tables) {
    this.dbConn = dbConn;
    this.tables = tables;
  }

  @Override
  public DBResult execute() throws DatabaseException {
    DBPreparedStmt dbStmt = new DBTruncateStmtBuilder(dbConn, tables).build();

    dbStmt.execute();
    return dbStmt.getResult();
  }
}
