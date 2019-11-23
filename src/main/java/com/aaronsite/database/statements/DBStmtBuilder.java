package com.aaronsite.database.statements;

import com.aaronsite.database.connection.DBConnection;
import com.aaronsite.utils.enums.Table;
import com.aaronsite.utils.exceptions.DatabaseException;

abstract class DBStmtBuilder {
  DBConnection dbConn;
  Table table;
  DBWhereStmtBuilder where;
  DBSelectStmtBuilder select;

  String tableSchema() {
    return dbConn.getSchema() + "." + table;
  }

  abstract DBPreparedStmt build() throws DatabaseException;
}
