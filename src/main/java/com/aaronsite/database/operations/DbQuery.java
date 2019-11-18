package com.aaronsite.database.operations;

import com.aaronsite.database.connection.DBConnection;
import com.aaronsite.database.statements.DBQueryStmtBuilder;
import com.aaronsite.database.statements.DBStatement;
import com.aaronsite.database.transaction.DBResult;
import com.aaronsite.utils.enums.Table;
import com.aaronsite.utils.exceptions.DatabaseException;

import static com.aaronsite.models.System.ID;

public class DbQuery implements DBOperation {
  private Table table;
  private DBConnection dbConn;
  private DBQueryStmtBuilder query;

  public DbQuery(DBConnection dbConn, Table table) {
    this.dbConn = dbConn;
    this.table = table;
  }

  @Override
  public DBResult execute() throws DatabaseException {
    DBStatement dbStmt = dbConn.getDbStmt()
        .execute(this);

    return dbStmt.getResult();
  }

  public DbQuery setQuery(DBQueryStmtBuilder query) {
    this.query = query;
    return this;
  }

  public DbQuery setIdQuery(String id) {
    this.query = new DBQueryStmtBuilder().add(ID, id);
    return this;
  }

  @Override
  public String toString() {
    String stmt = "SELECT * FROM " + dbConn.getSchema() + "." + table;

    if (query != null) {
      stmt += " " + query;
    }

    return stmt;
  }
}
