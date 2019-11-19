package com.aaronsite.database.statements;

import com.aaronsite.database.connection.DBConnection;
import com.aaronsite.utils.enums.Table;
import com.aaronsite.utils.exceptions.DatabaseException;

import java.util.List;

public class DBTruncateStmtBuilder {
  private DBConnection dbConn;
  private List<Table> tables;

  public DBTruncateStmtBuilder(DBConnection dbConn, List<Table> tables) {
    this.dbConn = dbConn;
    this.tables = tables;
  }

  public DBPreparedStmt build() throws DatabaseException {
    String sqlStmt = "TRUNCATE " + tables.stream().map(t -> dbConn.getSchema() + "." + t.getName())
        .reduce((table, acu) -> table + ", " + acu).orElse("");

    return dbConn.getPreparedStmt(sqlStmt);
  }
}
