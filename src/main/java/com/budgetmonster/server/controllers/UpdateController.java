package com.budgetmonster.server.controllers;

import com.budgetmonster.response.Response;
import com.budgetmonster.response.ResponseBuilder;
import com.budgetmonster.utils.enums.RequestType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.budgetmonster.server.controllers.MasterController.BASE_URL;

@RestController
@RequestMapping(BASE_URL)
public class UpdateController extends MasterController {

  @PutMapping("/{table}/{id}")
  public Response updateOnTableById(
      @RequestHeader Map<String, String> headers,
      @PathVariable(value = "table") String table,
      @PathVariable(value = "id") String id,
      @RequestBody String body) {

    try {
      return new ResponseBuilder(RequestType.UPDATE_BY_ID)
          .setTable(table)
          .setId(id)
          .setBody(body).build();

    } catch (Throwable e) {
      return ResponseBuilder.handleError(e);
    }
  }
}
