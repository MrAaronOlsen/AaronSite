package com.budgetmonster.response;

import com.budgetmonster.database.connection.DBConnection;
import com.budgetmonster.database.operations.DBInsert;
import com.budgetmonster.database.operations.DBRecord;
import com.budgetmonster.database.operations.DBResult;
import com.budgetmonster.models.Model;
import com.budgetmonster.utils.enums.Table;
import com.budgetmonster.utils.exceptions.ABException;
import com.budgetmonster.utils.exceptions.SimpleMessageException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class InsertResponse {

  public static Response build(Table table, String body) throws ABException {
    List<Data> results = new LinkedList<>();
    Function<DBRecord, Data> dataBuilder = Model.getModelData(table);
    Function<DBRecord, Model> modelBuilder = Model.getModel(table);

    ObjectMapper mapper = new ObjectMapper();
    Model model;

    try {
      DBRecord record = new DBRecord(mapper.readValue(body, new TypeReference<Map<String, String>>() {}));
      model = modelBuilder.apply(record);
    } catch (IOException e) {
      throw new SimpleMessageException("Failed to convert body to valid record. ERROR: " + e.getMessage());
    }

    try (DBConnection dbConn = new DBConnection()) {
      DBInsert insert = new DBInsert(dbConn, table).addRecord(model);
      DBResult result = insert.execute();

      while (result.hasNext()) {
        results.add(dataBuilder.apply(result.getNext()));
      }
    }

    return new Response(results);
  }
}
