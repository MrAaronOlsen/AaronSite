package com.aaronsite.database.statements;

import com.aaronsite.database.connection.DBConnection;
import com.aaronsite.database.metadata.ColumnMetadata;
import com.aaronsite.database.metadata.TableMetadata;
import com.aaronsite.database.transaction.DBRecord;
import com.aaronsite.utils.enums.Table;
import com.aaronsite.utils.exceptions.DatabaseException;

import java.util.stream.Collectors;

public class DBInsertStmtBuilder extends DBStmtBuilder {
  private static final String INSERT_INTO = "INSERT INTO";
  private static final String RETURNING_ALL = "RETURNING *";

  private DBRecord record;

  public DBInsertStmtBuilder(DBConnection dbConn, Table table) {
    this.dbConn = dbConn;
    this.table = table;
  }

  public DBInsertStmtBuilder setRecord(DBRecord record) {
    this.record = record;
    return this;
  }

  public DBPreparedStmt build() throws DatabaseException {
    String fieldsSql = "(" + String.join(", ", record.getColumns()) + ")";
    String valuesSql = "VALUES(" + record.getValues().stream().map(value -> "?")
        .collect(Collectors.joining(", ")) + ")";

    String preparedStmt = String.format("%s %s %s %s %s", INSERT_INTO, tableSchema(), fieldsSql, valuesSql, RETURNING_ALL);

    DBPreparedStmt prepStmt = dbConn.getPreparedStmt(preparedStmt);
    DBStmtSetter stmtBuilder = new DBStmtSetter(prepStmt);

    TableMetadata tableMetadata = TableMetadata.getTableMetadata(dbConn, table);

    for (int i = 0; i < record.size(); i++) {
      ColumnMetadata column = tableMetadata.getColumn(record.getColumn(i));
      stmtBuilder.setValue(column, record.getValue(i));
    }

    return prepStmt;
  }
}
