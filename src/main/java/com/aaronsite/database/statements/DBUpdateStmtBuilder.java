package com.aaronsite.database.statements;

import com.aaronsite.database.connection.DBConnection;
import com.aaronsite.database.metadata.ColumnMetadata;
import com.aaronsite.database.metadata.TableMetadata;
import com.aaronsite.database.transaction.DBRecord;
import com.aaronsite.utils.enums.Table;
import com.aaronsite.utils.exceptions.DatabaseException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;

import static com.aaronsite.database.metadata.ColumnMetadata.Type.INTEGER;

public class DBUpdateStmtBuilder {
  private static final String UPDATE = "UPDATE";
  private static final String RETURNING_ALL = "RETURNING *";
  private static final BiFunction<DBConnection, Table, String> TABLE = (c, t) -> c.getSchema() + "." + t;

  private DBConnection dbConn;
  private Table table;
  private DBWhereStmtBuilder query;

  public DBUpdateStmtBuilder(DBConnection dbConn, Table table) {
    this.dbConn = dbConn;
    this.table = table;
  }

  public DBUpdateStmtBuilder setWhere(DBWhereStmtBuilder query) {
    this.query = query;
    return this;
  }

  public DBUpdateStmtBuilder setIdQuery(String id) {
    this.query = new DBWhereStmtBuilder(id);
    return this;
  }

  public DBPreparedStmt build(DBRecord record) throws DatabaseException {
    String sqlStmt = "UPDATE " + dbConn.getSchema() + "." + table + " SET " +
        record.getEntrySet().stream()
            .map((e) -> e.getKey() + "=" + "?")
            .reduce((e, a) -> e + ", " + a).orElse("") +
        " " + query + " RETURNING *;";

    DBPreparedStmt prepStmt = dbConn.getPreparedStmt(sqlStmt);

    List<String> columns = new ArrayList<>(record.getColumns());
    LinkedList<String> values = new LinkedList<>(record.getValues());

    TableMetadata tableMetadata = TableMetadata.getTableMetadata(dbConn, table);

    int i = 0;
    while(i < columns.size()) {
      ColumnMetadata column = tableMetadata.getColumn(columns.get(i));

      if (column.getType() == INTEGER) {
        prepStmt.set(i + 1, Integer.valueOf(values.get(i)));
      } else {
        prepStmt.set(i + 1, values.get(i));
      }

      i++;
    }

    for (int j = 0; j < query.size(); j++) {
      ColumnMetadata column = tableMetadata.getColumn(query.getColumn(j));

      if (column.getType() == INTEGER) {
        prepStmt.set(j + i + 1, Integer.valueOf(query.getValue(j)));
      } else {
        prepStmt.set(j + i + 1, query.getValue(j));
      }

    }

    return prepStmt;
  }
}
