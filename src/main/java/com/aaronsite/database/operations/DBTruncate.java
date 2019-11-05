package com.aaronsite.database.operations;

import com.aaronsite.database.connection.DBConnection;
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
    DBStatement dbStmt = dbConn.getDbStmt()
        .execute(this);

    return dbStmt.getResult();
  }

  @Override
  public String toString() {
    String tableS = tables.stream().map(t -> dbConn.getSchema() + "." + t.getName())
        .reduce((table, acu) -> table + ", " + acu).orElse("");

    return "TRUNCATE " + tableS;
  }
}
