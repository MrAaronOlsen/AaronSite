package com.aaronsite.database.operations;

import com.aaronsite.database.connection.DBConnection;
import com.aaronsite.database.statements.DBPreparedStmt;
import com.aaronsite.database.statements.DBQueryStmtBuilder;
import com.aaronsite.database.statements.DBSelectStmtBuilder;
import com.aaronsite.database.statements.DBSortStmtBuilder;
import com.aaronsite.database.statements.DBWhereStmtBuilder;
import com.aaronsite.database.transaction.DBRecord;
import com.aaronsite.database.transaction.DBResult;
import com.aaronsite.utils.enums.Table;
import com.aaronsite.utils.exceptions.DatabaseException;
import com.aaronsite.utils.io.Logger;

public class DbQuery implements DBOperation {
  private Table table;
  private DBConnection dbConn;
  private DBWhereStmtBuilder where;
  private DBSelectStmtBuilder select;
  private DBSortStmtBuilder sort;

  private boolean doCount = false;

  public DbQuery(DBConnection dbConn, Table table) {
    this.dbConn = dbConn;
    this.table = table;
  }

  @Override
  public DBResult execute() throws DatabaseException {
    DBPreparedStmt stmt = new DBQueryStmtBuilder(dbConn, table)
        .setSelect(select)
        .setWhere(where)
        .setSort(sort)
        .build();

    Logger.out("Executing Query: " + stmt.toString());

    stmt.execute();
    DBResult result = stmt.getResult();

    if (doCount) {
      result.setCount(executeCount());
    }

    return result;
  }

  public int executeCount() throws DatabaseException {
    DBPreparedStmt stmt = new DBQueryStmtBuilder(dbConn, table)
        .setSelect(new DBSelectStmtBuilder().asCount())
        .setWhere(where)
        .setSort(sort)
        .build();

    Logger.out("Executing Count: " + stmt.toString());

    DBResult result = stmt.execute().getResult();

    if (result.hasNext()) {
      DBRecord countRecord = result.getNext();
      return Integer.parseInt(countRecord.get("count"));
    } else {
      return 0;
    }
  }

  public DbQuery setSelect(DBSelectStmtBuilder select) {
    this.select = select;
    return this;
  }

  public DbQuery setQuery(DBWhereStmtBuilder where) {
    this.where = where;
    return this;
  }

  public DbQuery setSort(DBSortStmtBuilder sort) {
    this.sort = sort;
    return this;
  }

  public DbQuery setIdQuery(String id) {
    this.where = new DBWhereStmtBuilder(id);
    return this;
  }

  public DbQuery doCount() {
    this.doCount = true;
    return this;
  }
}
