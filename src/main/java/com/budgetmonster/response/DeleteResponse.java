package com.budgetmonster.response;

import com.budgetmonster.database.connection.DBConnection;
import com.budgetmonster.database.operations.DBDelete;
import com.budgetmonster.database.operations.DBInsert;
import com.budgetmonster.database.operations.DBRecord;
import com.budgetmonster.database.operations.DBResult;
import com.budgetmonster.models.Model;
import com.budgetmonster.utils.enums.Table;
import com.budgetmonster.utils.exceptions.ABException;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

class DeleteResponse {

  static Response build(Table table, String id) throws ABException {
    return new Response(executeDelete(table, id));
  }

  private static List<Data> executeDelete(Table table, String id) throws ABException {
    List<Data> results = new LinkedList<>();
    Function<DBRecord, Data> dataBuilder = Model.getModelData(table);

    try (DBConnection dbConn = new DBConnection()) {
      DBDelete delete = new DBDelete(dbConn, table).setId(id);
      DBResult result = delete.execute();

      while (result.hasNext()) {
        results.add(dataBuilder.apply(result.getNext()));
      }
    }

    return results;
  }
}
