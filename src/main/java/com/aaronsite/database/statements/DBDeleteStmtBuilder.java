package com.aaronsite.database.statements;

import com.aaronsite.database.connection.DBConnection;
import com.aaronsite.database.metadata.ColumnMetadata;
import com.aaronsite.database.metadata.TableMetadata;
import com.aaronsite.utils.enums.Table;
import com.aaronsite.utils.exceptions.DatabaseException;

public class DBDeleteStmtBuilder extends DBStmtBuilder {
  private static final String DELETE_FROM = "DELETE FROM";
  private static final String RETURN_ID = "RETURNING id";

  private DBWhereStmtBuilder where;

  public DBDeleteStmtBuilder(DBConnection dbConn, Table table) {
    this.dbConn = dbConn;
    this.table = table;
  }

  public DBDeleteStmtBuilder setId(String id) {
    this.where = new DBWhereStmtBuilder(id);
    return this;
  }

  public DBPreparedStmt build() throws DatabaseException {
    String sql = String.format("%s %s %s %s", DELETE_FROM, tableSchema(), where, RETURN_ID);

    DBPreparedStmt prepStmt = dbConn.getPreparedStmt(sql);

    TableMetadata tableMetadata = TableMetadata.getTableMetadata(dbConn, table);
    DBStmtSetter stmtBuilder = new DBStmtSetter(prepStmt);

    for (int i = 0; i < where.size(); i++) {
      ColumnMetadata column = tableMetadata.getColumn(where.getColumn(i));
      stmtBuilder.setValue(column, where.getValue(i));
    }

    return prepStmt;
  }
}
