package com.budgetmonster.response;

import com.budgetmonster.database.connection.DBConnection;
import com.budgetmonster.database.operations.DBInsert;
import com.budgetmonster.database.operations.DBRecord;
import com.budgetmonster.database.operations.DBResult;
import com.budgetmonster.database.operations.DBUpdate;
import com.budgetmonster.models.Model;
import com.budgetmonster.utils.enums.Table;
import com.budgetmonster.utils.exceptions.ABException;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

class UpdateResponse {

  static Response build(Table table, String id, String body) throws ABException {
    return new Response(executeUpdate(table, id, ResponseUtils.convertBodyToModel(table, body)));
  }

  private static List<Data> executeUpdate(Table table, String id, Model model) throws ABException {
    List<Data> results = new LinkedList<>();
    Function<DBRecord, Data> dataBuilder = Model.getModelData(table);

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
