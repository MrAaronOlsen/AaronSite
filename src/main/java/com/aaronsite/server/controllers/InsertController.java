package com.aaronsite.server.controllers;

import com.aaronsite.response.Response;
import com.aaronsite.response.ResponseBuilder;
import com.aaronsite.utils.enums.RequestType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.aaronsite.server.controllers.MasterController.BASE_URL;
import static com.aaronsite.server.controllers.MasterController.DEV_URL;
import static com.aaronsite.server.controllers.MasterController.PROD_URL;

@CrossOrigin(origins = {DEV_URL, PROD_URL})
@RestController
@RequestMapping(BASE_URL)
public class InsertController extends MasterController {

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
