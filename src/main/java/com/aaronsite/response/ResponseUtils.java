package com.aaronsite.response;

import com.aaronsite.database.operations.DBRecord;
import com.aaronsite.models.Model;
import com.aaronsite.utils.enums.Table;
import com.aaronsite.utils.exceptions.ABException;
import com.aaronsite.utils.exceptions.ResponseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

import static com.aaronsite.utils.exceptions.ResponseException.Code.MALFORMED_REQUEST_BODY;

class ResponseUtils {

  static Model convertBodyToModel(Table table, String body) throws ABException {
    return convertMapToModel(table, convertBodyToMap(body));
  }

  static Map<String, String> convertBodyToMap(String body) throws ABException {

    try {
      ObjectMapper mapper = new ObjectMapper();
      return mapper.readValue(body, new TypeReference<Map<String, String>>() {});
    } catch (IOException e) {
      throw new ResponseException(MALFORMED_REQUEST_BODY, e.getMessage());
    }
  }

  static Model convertMapToModel(Table table, Map<String, String> bodyMap) throws ABException {
    return Model.getModel(table).apply(new DBRecord(bodyMap));
  }
}
