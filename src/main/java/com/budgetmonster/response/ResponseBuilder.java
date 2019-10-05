package com.budgetmonster.response;

import com.budgetmonster.utils.enums.RequestType;
import com.budgetmonster.utils.enums.Table;
import com.budgetmonster.utils.exceptions.ABException;
import com.budgetmonster.utils.exceptions.ResponseException;
import com.budgetmonster.utils.exceptions.SimpleMessageException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResponseBuilder {
  private Table table;
  private Map<String , String> header;
  private Map<String, String> params;
  private String id;
  private String body;
  private RequestType requestType;

  public ResponseBuilder(RequestType requestType) {
    this.requestType = requestType;
  }

  public ResponseBuilder setTable(String tableIn) throws ABException {
    Table table = Table.get(tableIn);

    if (table == Table.INVALID_TABLE) {
      throw new SimpleMessageException(String.format("Invalid table %s for response.", tableIn));
    }

    this.table = table;
    return this;
  }

  public ResponseBuilder setHeader(Map<String, String> header) {
    this.header = header;
    return this;
  }

  public ResponseBuilder setParams(Map<String, String> params) {
    this.params = params;
    return this;
  }

  public ResponseBuilder setId(String id) {
    this.id = id;
    return this;
  }

  public ResponseBuilder setBody(String body) {
    this.body = body;
    return this;
  }

  public Response build() throws ABException {
    switch (requestType) {
      case QUERY:
        return QueryResponse.build(table, params);
      case QUERY_BY_ID:
        return QueryResponse.build(table, id);
      case INSERT:
        return InsertResponse.build(table, body);
      case DELETE_BY_ID:
        return DeleteResponse.build(table, id);
      default:
        throw new ResponseException(ResponseException.Code.INVALID_REQUEST_TYPE, requestType.name());
    }
  }

  public static Response handleError(Throwable e) {
    List<Data> errors = new ArrayList<>();
    errors.add(new Error(e));

    return new Response(errors);
  }
}
