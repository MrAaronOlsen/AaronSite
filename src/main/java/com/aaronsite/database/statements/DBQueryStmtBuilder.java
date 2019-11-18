package com.aaronsite.database.statements;

import com.aaronsite.database.connection.DBConnection;
import com.aaronsite.database.metadata.ColumnMetadata;
import com.aaronsite.database.metadata.TableMetadata;
import com.aaronsite.utils.enums.Table;
import com.aaronsite.utils.exceptions.DatabaseException;

import java.util.function.BiFunction;

public class DBQueryStmtBuilder {
  private static final String UPDATE = "UPDATE";
  private static final String RETURNING_ALL = "RETURNING *";
  private static final BiFunction<DBConnection, Table, String> TABLE = (c, t) -> c.getSchema() + "." + t;

  private DBConnection dbConn;
  private Table table;
  private DBWhereStmtBuilder query;

  public DBQueryStmtBuilder(DBConnection dbConn, Table table) {
    this.dbConn = dbConn;
    this.table = table;
  }

  public DBQueryStmtBuilder setWhere(DBWhereStmtBuilder query) {
    this.query = query;
    return this;
  }

  public DBQueryStmtBuilder setIdQuery(String id) {
    this.query = new DBWhereStmtBuilder(id);
    return this;
  }

  public DBPreparedStmt build() throws DatabaseException {
    String sqlStmt = "SELECT * FROM " + TABLE.apply(dbConn, table)
        + " " + query + ";";

    DBPreparedStmt prepStmt = dbConn.getPreparedStmt(sqlStmt);

    TableMetadata tableMetadata = TableMetadata.getTableMetadata(dbConn, table);

    for (int i = 0; i < query.size(); i++) {
      ColumnMetadata column = tableMetadata.getColumn(query.getColumn(i));

      if (column != null) {
        if (ColumnMetadata.Type.INTEGER == column.getType()) {
          prepStmt.set(i + 1, Integer.valueOf(query.getValue(i)));
        } else {
          prepStmt.set(i + 1, query.getValue(i));
        }
      }
    }

    return prepStmt;
  }
}
