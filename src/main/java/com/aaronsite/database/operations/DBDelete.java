package com.aaronsite.database.operations;

import com.aaronsite.database.connection.DBConnection;
import com.aaronsite.utils.enums.Table;
import com.aaronsite.utils.exceptions.DatabaseException;

import static com.aaronsite.models.System.ID;

public class DBDelete implements DBOperation {
  private DBConnection dbConn;
  private Table table;
  private DBQueryBuilder query;

  public DBDelete(DBConnection dbConn, Table table) {
    this.dbConn = dbConn;
    this.table = table;
  }

  public DBDelete setId(String id) {
    this.query = new DBQueryBuilder().add(ID, id);
    return this;
  }

  @Override
  public DBResult execute() throws DatabaseException {
    DBStatement stmt = dbConn.getDbStmt()
        .execute(this);

    return stmt.getResult();
  }

  @Override
  public String toString() {
    return "DELETE from " + dbConn.getSchema() + "." + table + " " + query + " RETURNING id;";
  }
}
