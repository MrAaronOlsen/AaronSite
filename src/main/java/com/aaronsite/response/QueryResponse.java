package com.aaronsite.response;

import com.aaronsite.database.connection.DBConnection;
import com.aaronsite.database.operations.DbQuery;
import com.aaronsite.database.statements.DBSelectStmtBuilder;
import com.aaronsite.database.statements.DBWhereStmtBuilder;
import com.aaronsite.database.transaction.DBRecord;
import com.aaronsite.database.transaction.DBResult;
import com.aaronsite.models.Model;
import com.aaronsite.utils.enums.Table;
import com.aaronsite.utils.exceptions.ABException;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

class QueryResponse {
  private static final String SELECT_FIELDS = "fields";

  static Response build(Table table, Map<String, String> params) throws ABException {
    return new Response(executeQuery(table, parseSelectParams(params), new DBWhereStmtBuilder(params)));
  }

  static Response build(Table table, String id, Map<String, String> params) throws ABException {
    return new Response(executeQuery(table, parseSelectParams(params), new DBWhereStmtBuilder(id)));
  }

  private static DBSelectStmtBuilder parseSelectParams(Map<String, String> params) {
    return new DBSelectStmtBuilder(params.remove(SELECT_FIELDS));
  }

  private static List<ResponseData> executeQuery(Table table, DBSelectStmtBuilder select, DBWhereStmtBuilder where) throws ABException {
    List<ResponseData> results = new LinkedList<>();
    Function<DBRecord, Model> modelBuilder = Model.getModel(table);

    try (DBConnection dbConn = new DBConnection()) {
      DbQuery query = new DbQuery(dbConn, table)
          .setSelect(select)
          .setQuery(where);

      DBResult result = query.execute();

      while (result.hasNext()) {
        results.add(modelBuilder.apply(result.getNext()));
      }
    }

    return results;
  }
}
