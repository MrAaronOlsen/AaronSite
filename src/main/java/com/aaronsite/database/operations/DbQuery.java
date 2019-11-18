package com.aaronsite.database.operations;

import com.aaronsite.database.connection.DBConnection;
import com.aaronsite.database.statements.DBPreparedStmt;
import com.aaronsite.database.statements.DBQueryStmtBuilder;
import com.aaronsite.database.statements.DBWhereStmtBuilder;
import com.aaronsite.database.transaction.DBResult;
import com.aaronsite.utils.enums.Table;
import com.aaronsite.utils.exceptions.DatabaseException;

public class DbQuery implements DBOperation {
  private Table table;
  private DBConnection dbConn;
  private DBWhereStmtBuilder where;

  public DbQuery(DBConnection dbConn, Table table) {
    this.dbConn = dbConn;
    this.table = table;
  }

  @Override
  public DBResult execute() throws DatabaseException {
    DBPreparedStmt stmt = new DBQueryStmtBuilder(dbConn, table)
        .setWhere(where).build();

    stmt.execute();

    return stmt.getResult();
  }

  public DbQuery setQuery(DBWhereStmtBuilder where) {
    this.where = where;
    return this;
  }

  public DbQuery setIdQuery(String id) {
    this.where = new DBWhereStmtBuilder(id);
    return this;
  }
}
