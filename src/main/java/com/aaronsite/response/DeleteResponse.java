package com.aaronsite.response;

import com.aaronsite.database.connection.DBConnection;
import com.aaronsite.database.operations.DBDelete;
import com.aaronsite.database.operations.DbQuery;
import com.aaronsite.database.transaction.DBRecord;
import com.aaronsite.database.transaction.DBResult;
import com.aaronsite.models.Model;
import com.aaronsite.triggers.TableTriggers;
import com.aaronsite.utils.enums.Table;
import com.aaronsite.utils.exceptions.ABException;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

class DeleteResponse {

  static Response build(Table table, String id) throws ABException {
    return new Response(executeDelete(table, id));
  }

  private static List<ResponseData> executeDelete(Table table, String id) throws ABException {
    List<ResponseData> results = new LinkedList<>();
    Function<DBRecord, Model> dataBuilder = Model.getModel(table);

    try (DBConnection dbConn = new DBConnection()) {
      TableTriggers triggers = TableTriggers.get(table);
      if (triggers != null) {
        DBResult result = new DbQuery(dbConn, table)
            .setIdQuery(id)
            .execute();

        if (result.hasNext()) {
          triggers.preDelete(dataBuilder.apply(result.getNext()));
        }
      }

      DBDelete delete = new DBDelete(dbConn, table).setId(id);
      DBResult result = delete.execute();

      while (result.hasNext()) {
        results.add(dataBuilder.apply(result.getNext()));
      }
    }

    return results;
  }
}
