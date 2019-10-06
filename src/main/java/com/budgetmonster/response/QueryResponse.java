package com.budgetmonster.response;

import com.budgetmonster.database.connection.DBConnection;
import com.budgetmonster.database.operations.DBQueryBuilder;
import com.budgetmonster.database.operations.DBRecord;
import com.budgetmonster.database.operations.DBResult;
import com.budgetmonster.database.operations.DbQuery;
import com.budgetmonster.models.Model;
import com.budgetmonster.utils.enums.Table;
import com.budgetmonster.utils.exceptions.ABException;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

class QueryResponse {

  static Response build(Table table, Map<String, String> params) throws ABException {
    return new Response(executeQuery(table, new DBQueryBuilder(params)));
  }

  static Response build(Table table, String id) throws ABException {
    return new Response(executeQuery(table, new DBQueryBuilder(id)));
  }

  private static List<ResponseData> executeQuery(Table table, DBQueryBuilder queryBuilder) throws ABException {
    List<ResponseData> results = new LinkedList<>();
    Function<DBRecord, Model> modelBuilder = Model.getModel(table);

    try (DBConnection dbConn = new DBConnection()) {
      DbQuery query = new DbQuery(dbConn, table)
          .setQuery(queryBuilder);

      DBResult result = query.execute();

      while (result.hasNext()) {
        results.add(modelBuilder.apply(result.getNext()));
      }
    }

    return results;
  }
}
