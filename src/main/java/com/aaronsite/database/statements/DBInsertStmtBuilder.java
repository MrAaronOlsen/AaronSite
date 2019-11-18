package com.aaronsite.database.statements;

import com.aaronsite.database.connection.DBConnection;
import com.aaronsite.database.transaction.DBRecord;
import com.aaronsite.utils.enums.Table;
import com.aaronsite.utils.exceptions.DatabaseException;

import java.util.LinkedList;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class DBInsertStmtBuilder {
  private static final String INSERT_INTO = "INSERT INTO";
  private static final String RETURNING_ALL = "RETURNING *";
  private static final BiFunction<DBConnection, Table, String> TABLE = (c, t) -> c.getSchema() + "." + t;

  private DBConnection dbConn;
  private Table table;

  public DBInsertStmtBuilder(DBConnection dbConn, Table table) {
    this.dbConn = dbConn;
    this.table = table;
  }

  public DBPreparedStmt build(DBRecord record) throws DatabaseException {
    Set<String> columns = record.getColumns();
    LinkedList<String> values = new LinkedList<>(record.getValues());

    String fieldsSql = "(" + String.join(", ", columns) + ")";
    String valuesSql = "VALUES(" + values.stream().map(value -> "?").collect(Collectors.joining(", ")) + ")";

    String preparedStmt = String.format("%s %s %s %s %s", INSERT_INTO, TABLE.apply(dbConn, table), fieldsSql, valuesSql, RETURNING_ALL);

    DBPreparedStmt prepStmt = dbConn.getPreparedStmt(preparedStmt);

    for (int i = 0; i < columns.size(); i++) {
      prepStmt.set(i + 1, values.get(i));
    }

    return prepStmt;
  }
}
