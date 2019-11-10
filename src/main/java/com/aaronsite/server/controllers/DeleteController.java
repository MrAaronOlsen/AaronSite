package com.aaronsite.server.controllers;

import com.aaronsite.response.Response;
import com.aaronsite.response.ResponseBuilder;
import com.aaronsite.utils.enums.RequestType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.aaronsite.server.controllers.MasterController.BASE_URL;

@RestController
@RequestMapping(BASE_URL)
public class DeleteController extends MasterController {

  @CrossOrigin(origins = "http://localhost:3000")
  @DeleteMapping("/{table}/{id}")
  public Response deleteOnTableById(
      @RequestHeader Map<String, String> headers,
      @PathVariable(value = "table") String table,
      @PathVariable(value = "id") String id) {

    try {
      return new ResponseBuilder(RequestType.DELETE_BY_ID)
          .setTable(table)
          .setId(id).build();

    } catch (Throwable e) {
      return ResponseBuilder.handleError(e);
    }
  }
}
