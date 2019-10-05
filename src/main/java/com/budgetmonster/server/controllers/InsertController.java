package com.budgetmonster.server.controllers;

import com.budgetmonster.response.Response;
import com.budgetmonster.response.ResponseBuilder;
import com.budgetmonster.utils.enums.RequestType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class InsertController extends MasterController {

  @PostMapping(BASE_URL + "{table}")
  public Response insertOnTable(
      @RequestHeader Map<String, String> headers,
      @PathVariable(value = "table") String table,
      @RequestBody String body) {

    try {
      return new ResponseBuilder(RequestType.INSERT)
          .setTable(table)
          .setBody(body).build();

    } catch (Throwable e) {
      return ResponseBuilder.handleError(e);
    }
  }
}
