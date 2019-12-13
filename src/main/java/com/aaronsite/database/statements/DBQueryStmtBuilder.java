package com.aaronsite.database.statements;

import com.aaronsite.database.connection.DBConnection;
import com.aaronsite.database.metadata.ColumnMetadata;
import com.aaronsite.database.metadata.TableMetadata;
import com.aaronsite.utils.enums.Table;
import com.aaronsite.utils.exceptions.DatabaseException;

public class DBQueryStmtBuilder extends DBStmtBuilder {

  public DBQueryStmtBuilder(DBConnection dbConn, Table table) {
    this.dbConn = dbConn;
    this.table = table;
  }

  public DBQueryStmtBuilder setSelect(DBSelectStmtBuilder select) {
    this.select = select;
    return this;
  }

  public DBQueryStmtBuilder setWhere(DBWhereStmtBuilder where) {
    this.where = where;
    return this;
  }

  public DBQueryStmtBuilder setSort(DBSortStmtBuilder sort) {
    this.sort = sort;
    return this;
  }

  public DBQueryStmtBuilder setIdQuery(String id) {
    this.where = new DBWhereStmtBuilder(id);
    return this;
  }

  public DBPreparedStmt build() throws DatabaseException {
    if (select == null) {
      select = new DBSelectStmtBuilder("*");
    }

    if (where == null) {
      where = new DBWhereStmtBuilder();
    }

    if (sort == null) {
      sort = new DBSortStmtBuilder();
    }

    String sqlStmt = String.format("%s %s %s %s", select, tableSchema(), where, sort).trim();

    DBPreparedStmt prepStmt = dbConn.getPreparedStmt(sqlStmt);

    TableMetadata tableMetadata = TableMetadata.getTableMetadata(dbConn, table);
    DBStmtSetter stmtBuilder = new DBStmtSetter(prepStmt);

    for (int i = 0; i < where.size(); i++) {
      ColumnMetadata column = tableMetadata.getColumn(where.getColumn(i));
      stmtBuilder.setValue(column, where.getValue(i));
    }

    return prepStmt;
  }
}
