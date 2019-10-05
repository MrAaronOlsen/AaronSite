package com.budgetmonster.response;

import com.budgetmonster.utils.enums.RequestType;
import com.budgetmonster.utils.enums.Table;
import com.budgetmonster.utils.exceptions.ABException;
import com.budgetmonster.utils.exceptions.ResponseException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResponseBuilder {

  public static Response handle(Table table, Map<String, String> params, RequestType type) throws ABException {
    switch (type) {
      case QUERY:
        return QueryResponse.build(table, params);
      default:
        throw new ResponseException(ResponseException.Code.INVALID_REQUEST_TYPE, type.name());
    }
  }

  public static Response handle(Throwable e) {
    List<Data> errors = new ArrayList<>();
    errors.add(new Error(e));

    return new Response(errors);
  }
}
