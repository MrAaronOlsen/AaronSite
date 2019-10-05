package com.budgetmonster.response;

import com.budgetmonster.database.operations.DBRecord;
import com.budgetmonster.models.Model;
import com.budgetmonster.utils.enums.Table;
import com.budgetmonster.utils.exceptions.ABException;
import com.budgetmonster.utils.exceptions.SimpleMessageException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

class ResponseUtils {

  static Model convertBodyToModel(Table table, String body) throws ABException {
    return convertMapToModel(table, convertBodyToMap(body));
  }

  static Map<String, String> convertBodyToMap(String body) throws ABException {

    try {
      ObjectMapper mapper = new ObjectMapper();
      return mapper.readValue(body, new TypeReference<Map<String, String>>() {});
    } catch (IOException e) {
      throw new SimpleMessageException("Failed to convert body to valid record. ERROR: " + e.getMessage());
    }
  }

  static Model convertMapToModel(Table table, Map<String, String> bodyMap) throws ABException {
    return Model.getModel(table).apply(new DBRecord(bodyMap));
  }
}
