package com.budgetmonster.server.controllers;

import com.budgetmonster.response.Data;
import com.budgetmonster.response.ErrorResponse;
import com.budgetmonster.response.ResponseFactory;
import com.budgetmonster.utils.enums.Table;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class TableEndpoints {
  private static final String URL = "/budgetmonster/api/v1/";

  @GetMapping(URL + "{table}")
  public List<Data> table(@PathVariable(value = "table") String table, @RequestParam Map<String, String> params) {

    try {
      return ResponseFactory.build(Table.get(table), params);
    } catch (Throwable e) {
      return ErrorResponse.build(e);
    }
  }
}
