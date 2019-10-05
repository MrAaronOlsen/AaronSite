package com.budgetmonster.server.controllers;

import com.budgetmonster.response.Response;
import com.budgetmonster.response.ResponseBuilder;
import com.budgetmonster.utils.enums.RequestType;
import com.budgetmonster.utils.enums.Table;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class QueryController {
  private static final String URL = "/budgetmonster/api/v1/";

  @GetMapping(URL + "{table}")
  public Response table(@PathVariable(value = "table") String table, @RequestParam Map<String, String> params) {

    try {
      return ResponseBuilder.handle(Table.get(table), params, RequestType.QUERY);
    } catch (Throwable e) {
      return ResponseBuilder.handle(e);
    }
  }
}
