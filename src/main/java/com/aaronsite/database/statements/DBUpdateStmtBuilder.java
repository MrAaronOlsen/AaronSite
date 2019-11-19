package com.aaronsite.database.statements;

import com.aaronsite.database.connection.DBConnection;
import com.aaronsite.database.metadata.ColumnMetadata;
import com.aaronsite.database.metadata.TableMetadata;
import com.aaronsite.database.transaction.DBRecord;
import com.aaronsite.utils.enums.Table;
import com.aaronsite.utils.exceptions.DatabaseException;

public class DBUpdateStmtBuilder extends DBStmtBuilder {
  private static final String UPDATE = "UPDATE";
  private static final String SET = "SET";
  private static final String RETURNING_ALL = "RETURNING *";

  private DBRecord record;

  public DBUpdateStmtBuilder(DBConnection dbConn, Table table) {
    this.dbConn = dbConn;
    this.table = table;
  }

  public DBUpdateStmtBuilder setRecord(DBRecord record) {
    this.record = record;
    return this;
  }

  public DBUpdateStmtBuilder setWhere(DBWhereStmtBuilder query) {
    this.where = query;
    return this;
  }

  public DBUpdateStmtBuilder setIdQuery(String id) {
    this.where = new DBWhereStmtBuilder(id);
    return this;
  }

  public DBPreparedStmt build() throws DatabaseException {
    String params = record.getColumns().stream()
        .map((col) -> col + "=" + "?")
        .reduce((e, a) -> e + ", " + a).orElse("");

    String sqlStmt = String.format("%s %s %s %s %s %s", UPDATE, tableSchema(), SET, params, where, RETURNING_ALL );

    DBPreparedStmt prepStmt = dbConn.getPreparedStmt(sqlStmt);

    DBStmtSetter stmtBuilder = new DBStmtSetter(prepStmt);
    TableMetadata tableMetadata = TableMetadata.getTableMetadata(dbConn, table);

    for(int i = 0; i < record.size(); i++) {
      ColumnMetadata column = tableMetadata.getColumn(record.getColumn(i));
      stmtBuilder.setValue(column, record.getValue(i));
    }

    for (int j = 0; j < where.size(); j++) {
      ColumnMetadata column = tableMetadata.getColumn(where.getColumn(j));
      stmtBuilder.setValue(column, where.getValue(j));
    }

    return prepStmt;
  }
}
