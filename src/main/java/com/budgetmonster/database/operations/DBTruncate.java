package com.budgetmonster.database.operations;

import com.budgetmonster.database.connection.DBConnection;
import com.budgetmonster.utils.enums.Table;
import com.budgetmonster.utils.exceptions.DBException;

import java.util.List;

public class DBTruncate implements DBOperation {
  private List<Table> tables;
  private DBConnection dbConn;

  public DBTruncate(DBConnection dbConn, List<Table> tables) {
    this.dbConn = dbConn;
    this.tables = tables;
  }

  @Override
  public DBResult execute() throws DBException {
    DBStatement dbStmt = dbConn.getDbStmt()
        .execute(this);

    return dbStmt.getResult();
  }

  @Override
  public String toString() {
    String tableS = tables.stream().map(t -> dbConn.getSchema() + "." + t.getName())
        .reduce((table, acu) -> table + ", " + acu).get();

    return "TRUNCATE " + tableS;
  }
}
