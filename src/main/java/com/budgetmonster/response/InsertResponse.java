package com.budgetmonster.response;

import com.budgetmonster.database.connection.DBConnection;
import com.budgetmonster.database.operations.DBInsert;
import com.budgetmonster.database.operations.DBRecord;
import com.budgetmonster.database.operations.DBResult;
import com.budgetmonster.models.Model;
import com.budgetmonster.utils.enums.Table;
import com.budgetmonster.utils.exceptions.ABException;

import java.util.LinkedList;
import java.util.List;

import java.util.function.Function;

class InsertResponse {

  static Response build(Table table, String body) throws ABException {
    return new Response(executeInsert(table, ResponseUtils.convertBodyToModel(table, body)));
  }

  private static List<Data> executeInsert(Table table, Model model) throws ABException {
    List<Data> results = new LinkedList<>();
    Function<DBRecord, Data> dataBuilder = Model.getModelData(table);

    try (DBConnection dbConn = new DBConnection()) {
      DBInsert insert = new DBInsert(dbConn, table).addRecord(model);
      DBResult result = insert.execute();

      while (result.hasNext()) {
        results.add(dataBuilder.apply(result.getNext()));
      }
    }

    return results;
  }
}
