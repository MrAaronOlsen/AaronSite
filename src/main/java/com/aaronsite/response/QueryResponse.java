package com.aaronsite.response;

import com.aaronsite.database.connection.DBConnection;
import com.aaronsite.database.operations.DbQuery;
import com.aaronsite.database.statements.DBSelectStmtBuilder;
import com.aaronsite.database.statements.DBSortStmtBuilder;
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
  private static final String SORTS = "sort";

  static Response build(Table table, Map<String, String> params) throws ABException {
    return new Response(executeQuery(table, parseSelectParams(params), parseSortParams(params), new DBWhereStmtBuilder(params)));
  }

  static Response build(Table table, String id, Map<String, String> params) throws ABException {
    return new Response(executeQuery(table, parseSelectParams(params), null, new DBWhereStmtBuilder(id)));
  }

  private static DBSelectStmtBuilder parseSelectParams(Map<String, String> params) {
    return new DBSelectStmtBuilder(params.remove(SELECT_FIELDS));
  }

  private static DBSortStmtBuilder parseSortParams(Map<String, String> params) {
    return new DBSortStmtBuilder(params.remove(SORTS));
  }

  private static List<ResponseData> executeQuery(Table table,
                                                 DBSelectStmtBuilder select,
                                                 DBSortStmtBuilder sort,
                                                 DBWhereStmtBuilder where) throws ABException {

    List<ResponseData> results = new LinkedList<>();
    Function<DBRecord, Model> modelBuilder = Model.getModel(table);

    try (DBConnection dbConn = new DBConnection()) {
      DbQuery query = new DbQuery(dbConn, table)
          .setSelect(select)
          .setQuery(where)
          .setSort(sort);

      DBResult result = query.execute();

      while (result.hasNext()) {
        results.add(Model.serialize(modelBuilder.apply(result.getNext())));
      }
    }

    return results;
  }
}
