package com.aaronsite.server.controllers;

import com.aaronsite.response.Response;
import com.aaronsite.response.ResponseBuilder;
import com.aaronsite.utils.enums.RequestType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.aaronsite.server.controllers.MasterController.BASE_URL;

@RestController
@RequestMapping(BASE_URL)
public class InsertController extends MasterController {

  @CrossOrigin(origins = "http://localhost:3000")
  @PostMapping("/{table}")
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
