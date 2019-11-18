package com.aaronsite.database.statements;

import com.aaronsite.database.connection.DBConnection;
import com.aaronsite.database.transaction.DBRecord;
import com.aaronsite.utils.enums.Table;
import com.aaronsite.utils.exceptions.DatabaseException;

import java.util.LinkedList;
import java.util.Set;
import java.util.function.BiFunction;

import static com.aaronsite.models.System.ID;

public class DBUpdateStmtBuilder {
  private static final String UPDATE = "INSERT INTO";
  private static final String RETURNING_ALL = "RETURNING *";
  private static final BiFunction<DBConnection, Table, String> TABLE = (c, t) -> c.getSchema() + "." + t;

  private DBConnection dbConn;
  private Table table;
  private DBQueryStmtBuilder query;

  public DBUpdateStmtBuilder(DBConnection dbConn, Table table) {
    this.dbConn = dbConn;
    this.table = table;
  }

  public DBUpdateStmtBuilder setQuery(DBQueryStmtBuilder query) {
    this.query = query;
    return this;
  }

  public DBUpdateStmtBuilder setIdQuery(String id) {
    this.query = new DBQueryStmtBuilder().add(ID, id);
    return this;
  }

  public DBPreparedStmt build(DBRecord record) throws DatabaseException {
    String sqlStmt = "UPDATE " + dbConn.getSchema() + "." + table + " SET " +
        record.getEntrySet().stream()
            .filter(e -> e.getValue() != null)
            .map((e) -> e.getKey() + "=" + "?")
            .reduce((e, a) -> e + ", " + a).orElse("") +
        " " + query + " RETURNING *;";

    DBPreparedStmt prepStmt = dbConn.getPreparedStmt(sqlStmt);

    Set<String> columns = record.getColumns();
    LinkedList<String> values = new LinkedList<>(record.getValues());

    for (int i = 0; i < columns.size(); i++) {
      prepStmt.set(i + 1, values.get(i));
    }

    return prepStmt;
  }
}
