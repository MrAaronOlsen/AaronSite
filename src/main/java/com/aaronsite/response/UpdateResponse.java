package com.aaronsite.response;

import com.aaronsite.database.connection.DBConnection;
import com.aaronsite.database.transaction.DBRecord;
import com.aaronsite.database.transaction.DBResult;
import com.aaronsite.database.operations.DBUpdate;
import com.aaronsite.models.Model;
import com.aaronsite.utils.enums.Table;
import com.aaronsite.utils.exceptions.ABException;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

class UpdateResponse {

  static Response build(Table table, String id, String body) throws ABException {
    return new Response(executeUpdate(table, id, ResponseUtils.convertBodyToModel(table, body)));
  }

  private static List<ResponseData> executeUpdate(Table table, String id, Model model) throws ABException {
    List<ResponseData> results = new LinkedList<>();
    Function<DBRecord, Model> dataBuilder = Model.getModel(table);

    try (DBConnection dbConn = new DBConnection()) {
      DBUpdate update = new DBUpdate(dbConn, table)
          .addQueryId(id)
          .addRecord(model);

      DBResult result = update.execute();

      while (result.hasNext()) {
        results.add(dataBuilder.apply(result.getNext()));
      }
    }

    return results;
  }
}
